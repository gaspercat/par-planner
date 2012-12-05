/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package blocksworld;

import java.util.ArrayList;
import operators.Operator;
import predicates.Predicate;
import predicates.PredicateUsedColsNum;

/**
 *
 * @author gaspercat
 */
public class Algorithm {
    private ArrayList<Operator> plan;
    private State state;
    private ArrayList<Object> stack;
    
    public Algorithm(){
        this.plan = new ArrayList<Operator>();
        this.stack = new ArrayList<Object>();
    }
            
    public void run(State initial, State goal){
        this.state = initial;
        
        this.stack.add(goal);
        this.stack.add(goal.getPredicates());
        
        while(this.stack.size() > 0){
            Object c = this.stack.remove(this.stack.size()-1);
            
            // If c is an operator
            if(c instanceof Operator){
                this.plan.add((Operator)c);
                this.state = new State(this.state, (Operator)c);
                
            // If c is a condition not fully instanced
            }else if((c instanceof Predicate) && !((Predicate)c).isInstanced()){
                instanceCondition((Predicate)c);
                this.stack.add(c);
                
            // If c is a condition fully instanced
            }else if((c instanceof Predicate) && ((Predicate)c).isInstanced()){
                Predicate pred = (Predicate)c;
                if(!this.state.hasPredicate(pred)){
                    Operator op = heuristicSelectOperator(pred);
                    this.stack.add(op);
                    this.stack.add(op.getPreconditions());
                }
                
            // If c is a list of conditions
            }else if(c instanceof Preconditions){
                ArrayList<Predicate> unmet = state.getUnmetConditions((Preconditions)c);
                if(unmet.size() > 0){
                    this.stack.add(c);
                    this.stack.addAll(sortConditions(unmet));
                }
            }
        }
    }
    
    public ArrayList<Operator> getPlan(){
        ArrayList<Operator> ret = new ArrayList<Operator>();
        
        for(int i=0;i<this.plan.size();i++){
            ret.add(this.plan.get(i).clone());
        }
        
        return ret;
    }
    
    private ArrayList<Predicate> sortConditions(ArrayList<Predicate> conditions){
        ArrayList<Predicate> ret = new ArrayList<Predicate>();
        
        // First stack PredicateUsedColsNum elemement (if present)
        for(Predicate pred: conditions){
            if(pred instanceof PredicateUsedColsNum){
                conditions.remove(pred);
                ret.add(pred);
                break;
            }
        }
        
        // TODO: Sort remaining conditions
        
        return ret;
    }
    
    private void instanceCondition(Predicate pred){
        // Get related operator
        Operator op = null;
        for(int i=this.stack.size()-1;;i--){
            if(this.stack.get(i) instanceof Operator){
                op = (Operator)this.stack.get(i);
                break;
            }
        }
        
        // Define value at operator
        op.instanceValues(pred, this.state);
    }
    
    private Operator heuristicSelectOperator(Predicate pred){
        
    }
    
    private void clear(){
        this.plan.clear();
        this.stack.clear();
    }
}
