/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package blocksworld;

import java.util.ArrayList;
import java.util.Random;
import operators.*;
import predicates.*;
import predicates.PredicateUsedColsNum;

/**
 *
 * @author gaspercat
 */
public class Algorithm {
    Random rnd = new Random(11);
    
    private ArrayList<State>     states;      // List of states transited
    private ArrayList<Operator>  operators;   // List of operators applied
    private ArrayList<Object>    stack;       // Stack of the algorithm
    
    private State init_state;                 // Initial state
    private State goal_state;                 // Goal state
    private State curr_state;                 // Current state
    private boolean is_valid;                 // Has the algorithm found a valid solution?
    
    public Algorithm(){
        this.init_state = null;
        this.goal_state = null;
        this.is_valid = true;
        
        this.states = new ArrayList<State>();
        this.operators = new ArrayList<Operator>();
        this.stack = new ArrayList<Object>();
    }
    
    // * ** CONTROL METHODS
    // * ******************************************
    
    // Execute a complete problem
    public void run(State initial, State goal){
        this.init_state = initial;
        this.goal_state = goal;
        
        this.curr_state = initial;
        this.states.add(this.curr_state);
        
        this.stack.add(new Preconditions(goal.getPredicates()));
        this.stack.addAll(goal.getPredicates());
        
        execute();
        
        // Add goal state to plan
        this.states.add(goal);
    }
    
    public boolean isValid(){
        return this.is_valid;
    }
    
    public ArrayList<Operator> getOperators(){
        ArrayList<Operator> ret = new ArrayList<Operator>();
        
        for(int i=0;i<this.operators.size();i++){
            ret.add(this.operators.get(i).clone());
        }
        
        return ret;
    }
    
    public ArrayList<State> getStates(){
        ArrayList<State> ret = new ArrayList<State>();
        
        for(int i=0;i<this.states.size();i++){
            ret.add(this.states.get(i).clone());
        }
        
        return ret;
    }
    
    public void clear(){
        this.init_state = null;
        this.goal_state = null;
        this.is_valid = true;
        
        this.states.clear();
        this.operators.clear();
        this.stack.clear();
    }

    // * ** ALGORITHM
    // * ******************************************
    
    public void execute(){
        while(this.stack.size() > 0){
            Object c = this.stack.remove(this.stack.size()-1);
            
            // If c is an operator
            if(c instanceof Operator){
                // Update current state
                System.out.println("Applying operator: " + c.toString());
                this.curr_state = new State(this.curr_state, (Operator)c);
                
                // If new state already visited, return
                /*if(isStateVisited(this.curr_state)){
                    System.out.println("OUCH!");
                    this.is_valid = false;
                    return;
                }*/
                
                // Add new state & operator to plan
                this.states.add(this.curr_state);
                this.operators.add((Operator)c);
                System.out.println("CURRENT PLAN: "+this.operators);
                System.out.println("CURRENT STATE: "+this.curr_state);
                
                
            // If c is a condition not fully instanced
            }else if((c instanceof Predicate) && !((Predicate)c).isInstanced()){
                System.out.println("Instantiating condition: " + c);
                heuristicInstanceCondition((Predicate)c);
                this.stack.add(c);
                
            // If c is a condition fully instanced
            }else if((c instanceof Predicate) && ((Predicate)c).isInstanced()){
                System.out.println("Checking condition: " + c);
                Predicate pred = (Predicate)c;
                if(!this.curr_state.hasPredicate(pred)){                    
                    ArrayList<Operator> ops = heuristicSelectOperator(pred);
                    if(ops == null){
                        return;
                    }
                    for(Operator op: ops){
                        System.out.println("Adding new operator to the stack: " + op);
                        this.stack.add(op);

                        this.stack.add(op.getPreconditions());
                        this.stack.addAll(op.getPreconditions().getPredicates());
                    }
                }
                
            // If c is a list of conditions
            }else if(c instanceof Preconditions){
                System.out.println("Checking list of conditions: " + c);
                ArrayList<Predicate> unmet = this.curr_state.getUnmetConditions((Preconditions)c);
                if(unmet.size() > 0){
                    this.stack.add(c);
                    this.stack.addAll(unmet);
                }
            }
        }
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
        op.instanceValues(pred, this.curr_state, this.goal_state);
    }
    
    private ArrayList<Operator> heuristicSelectOperator(Predicate pred){
        ArrayList<Operator> ret = new ArrayList<Operator>();
        ArrayList<Block> bl = new ArrayList<Block>();
        Predicate p;
        
        switch(pred.getType()){
            
            // If piece must be free but it isn't
            case Predicate.FREE:
                ret.add(new OperatorUnstack((Block)null, pred.getA()));
                return ret;
                
            // If free arm needed but is currently used
            case Predicate.FREE_ARM:
                if(this.curr_state.getNumColumns() < 3){
                    ret.add(new OperatorLeave(this.curr_state.getPredicate(Predicate.PICKED_UP).getA()));
                    return ret;
                }else{
                    Predicate tp = this.curr_state.getPredicate(Predicate.PICKED_UP);
                    //p = this.goal_state.matchPredicate(new PredicateOnTable(tp.getA()));
                    //if(p != null){

                    if(!this.operators.isEmpty()){
                        Operator prev_op = this.operators.get(this.operators.size()-1);
                        
                        // If block was picked up, make a stack
                        if(prev_op.getType() == Operator.PICK_UP){
                            ret.add(new OperatorStack(tp.getA(), null));
                            return ret;
                        
                        // If block was unstacked, blacklist origin stack
                        }else if(prev_op.getType() == Operator.UNSTACK){
                            bl.add(prev_op.getB());
                            
                        }
                    }

                    p = this.curr_state.matchPredicate(new PredicateHeavier(null, tp.getA()));
                    if(p==null || rnd.nextInt(10)<5){
                        ret.add(new OperatorLeave(tp.getA()));
                        return ret;
                        //op.add(new OperatorStack(tp.getA(), null));
                    }else{
                        ret.add(new OperatorStack(tp.getA(), null, null, bl));
                        return ret;
                        //op.add(new OperatorLeave(tp.getA()));
                    }
                }
                
            // If free stack needed but 3 already used
            case Predicate.FREE_STACK:
                ret.add(new OperatorStack((Block)null, (Block)null));
                ret.add(new OperatorPickUp((Block)null));
                return ret;
                
            // If a block must be over another
            case Predicate.ON:
                ret.add(new OperatorStack(pred.getA(), pred.getB()));
                return ret;
                
            // If a block must be on the table
            case Predicate.ON_TABLE:
                ret.add(new OperatorLeave(pred.getA()));
                return ret;
                
            // If a block must be picked up
            case Predicate.PICKED_UP:
                p = this.curr_state.matchPredicate(new PredicateOnTable(pred.getA()));
                if(p != null){
                
                //if(rnd.nextInt(10)<5){
                    ret.add(new OperatorPickUp(pred.getA()));
                    return ret;
                    //op.add(new OperatorUnstack(pred.getA(), null));
                }else{
                    ret.add(new OperatorUnstack(pred.getA(), null));
                    return ret;
                    //op.add(new OperatorPickUp(pred.getA()));
                }
        
            case Predicate.USED_COLS_NUM:
                ret.add(new OperatorPickUp((Block)null));
                return ret;
        }
        
        return null;
    }
    
    // * ** HELPER METHODS
    // * ******************************************
    
    private boolean isStateVisited(State s){
        for(State ts: this.states){
            if(ts.equals(s)) return true;
        }
        
        return false;
    }
    
    private Operator getStackedOperator(int n){
        for(int i=this.stack.size()-1;i>=0;i--){
            if(this.stack.get(i) instanceof Operator){
                if(n == 0) return (Operator)this.stack.get(i);
                n--;
            }
        }
        
        return null;
    }
}
