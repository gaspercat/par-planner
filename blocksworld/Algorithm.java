/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package blocksworld;

import java.util.ArrayList;
import operators.*;
import predicates.*;
import predicates.PredicateUsedColsNum;

/**
 *
 * @author gaspercat
 */
public class Algorithm {
    private ArrayList<State>    states;
    private ArrayList<Operator> operators;
    
    
    private State curr_state;
    private ArrayList<Object> stack;
    
    public Algorithm(){
        this.states = new ArrayList<State>();
        this.operators = new ArrayList<Operator>();
        this.stack = new ArrayList<Object>();
    }
    
    // * ** CONTROL METHODS
    // * ******************************************
            
    public void run(State initial, State goal){
        this.curr_state = initial;
        this.states.add(this.curr_state);
        
        this.stack.add(new Preconditions(goal.getPredicates()));
        this.stack.addAll(goal.getPredicates());
        
        while(this.stack.size() > 0){
            Object c = this.stack.remove(this.stack.size()-1);
            
            // If c is an operator
            if(c instanceof Operator){
                // Update current state
                this.operators.add((Operator)c);
                this.curr_state = new State(this.curr_state, (Operator)c);
                
                // Add new state & operator to plan
                this.states.add(this.curr_state);
                this.operators.add((Operator)c);
                
                
            // If c is a condition not fully instanced
            }else if((c instanceof Predicate) && !((Predicate)c).isInstanced()){
                heuristicInstanceCondition((Predicate)c);
                this.stack.add(c);
                
            // If c is a condition fully instanced
            }else if((c instanceof Predicate) && ((Predicate)c).isInstanced()){
                Predicate pred = (Predicate)c;
                if(!this.curr_state.hasPredicate(pred)){
                    Operator op = heuristicSelectOperator(pred);
                    this.stack.add(op);
                    this.stack.add(op.getPreconditions());
                }
                
            // If c is a list of conditions
            }else if(c instanceof Preconditions){
                ArrayList<Predicate> unmet = this.curr_state.getUnmetConditions((Preconditions)c);
                if(unmet.size() > 0){
                    this.stack.add(c);
                    this.stack.addAll(unmet);
                }
            }
        }
        
        // Add goal state to plan
        this.states.add(goal);
    }
    
    public ArrayList<Operator> getPlan(){
        ArrayList<Operator> ret = new ArrayList<Operator>();
        
        for(int i=0;i<this.operators.size();i++){
            ret.add(this.operators.get(i).clone());
        }
        
        return ret;
    }
    
    public void clear(){
        this.states.clear();
        this.operators.clear();
        this.stack.clear();
    }
    
    // * ** HEURISTIC METHODS
    // * ******************************************
    
    private void heuristicInstanceCondition(Predicate pred){
        // Get related operator
        Operator op = null;
        for(int i=this.stack.size()-1;;i--){
            if(this.stack.get(i) instanceof Operator){
                op = (Operator)this.stack.get(i);
                break;
            }
        }
        
        // Define value at operator
        op.instanceValues(pred, this.curr_state);
    }
    
    private Operator heuristicSelectOperator(Predicate pred){
        // TODO: Finish this heuristic
        Operator op = null;
        
        switch(pred.getType()){
            
            // If piece must be free but it isn't
            case Predicate.FREE:
                op = new OperatorUnstack((Block)null, pred.getA());
                break;
                
            // If free arm needed but is currently used
            case Predicate.FREE_ARM:
                
                // Can be a leave or a stack
                break;
                
            // If free stack needed but 3 already used
            case Predicate.FREE_STACK:
                op = new OperatorPickUp((Block)null);
                break;
                
            // If a block must be over another
            case Predicate.ON:
                op = new OperatorStack(pred.getA(), pred.getB());
                break;
                
            // If a block must be on the table
            case Predicate.ON_TABLE:
                op = new OperatorLeave(pred.getA());
                break;
                
            // If a block must be picked up
            case Predicate.PICKED_UP:
                op = new OperatorPickUp(pred.getA());
                break;
        }
        
        return op;
    }
}
