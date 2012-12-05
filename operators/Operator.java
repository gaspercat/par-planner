/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package operators;

import java.util.ArrayList;

import blocksworld.Preconditions;
import blocksworld.State;
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
    
    protected int type;
    protected ArrayList<Predicate> pres;
    protected ArrayList<Predicate> rmvs;
    protected ArrayList<Predicate> adds;
    
    protected Operator(int type){
        this.type = type;
    }

    // * ** GETTER METHODS
    // * ******************************************
    
    public Preconditions getPreconditions(){
        return new Preconditions(this.pres);
    }
    
    // * ** OPERATORS
    // * ******************************************
    
    public void apply(State s){
        ArrayList<Predicate> preds = s.getPredicates();
        int n;
        
        // Remove predicates
        for(Predicate predA: this.rmvs){
            for(Predicate predB: preds){
                if(predA.equals(predB)){
                    preds.remove(predB);
                    break;
                }
            }
        }
        
        // Add predicates
        preds.addAll(adds);
    }
    
    // * ** ABSTRACT METHODS
    // * ******************************************
    
    public abstract void instanceValues(Predicate pred, State state);
}
