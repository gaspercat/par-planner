/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package operators;

import blocksworld.*;
import java.util.ArrayList;
import predicates.Predicate;

/**
 *
 * @author gaspercat
 */
public abstract class Operator {
    public static final int PICK_UP = 1;
    public static final int LEAVE = 2;
    public static final int UNSTACK = 3;
    public static final int STACK = 4;
    
    public class Preconditions {
        ArrayList<Predicate> preconds;
        
        public Preconditions(){
            preconds = new ArrayList<Predicate>();
        }
        
        public void addPredicate(Predicate pred){
            preconds.add(pred);
        }
        
        public ArrayList<Predicate> getPredicates(){
            return preconds;
        }
    }
    
    protected int type;
    protected Preconditions preconds;
    protected ArrayList<Predicate> remove;
    protected ArrayList<Predicate> add;
    
    protected Operator(int type){
        this.type = type;
    }
    
    // * ** OPERATORS
    // * ******************************************
    
    public void apply(State s){
        
    }
    
    // * ** ABSTRACT METHODS
    // * ******************************************
    
    public abstract void instanceValues(Predicate pred, State state);
}
