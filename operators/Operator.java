/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package operators;

import java.util.ArrayList;
import java.util.Random;

import blocksworld.Preconditions;
import blocksworld.State;
import predicates.Predicate;
import blocksworld.Block;

/**
 *
 * @author gaspercat
 */
public abstract class Operator {
    protected static Random rnd = new Random(12);
    
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
        this.pres = new ArrayList<Predicate>();
        this.rmvs = new ArrayList<Predicate>();
        this.adds = new ArrayList<Predicate>();
    }
    
    protected Operator(Operator op){
        this.type = op.type;
        
        this.pres = new ArrayList<Predicate>();
        for(Predicate p: op.pres){
            this.pres.add(p.clone());
        }
        
        this.rmvs = new ArrayList<Predicate>();
        for(Predicate p: op.rmvs){
            this.rmvs.add(p.clone());
        }
        
        this.adds = new ArrayList<Predicate>();
        for(Predicate p: op.adds){
            this.adds.add(p.clone());
        }
    }

    // * ** GETTER METHODS
    // * ******************************************
    
    public int getType(){
        return this.type;
    }
    
    public abstract Block getA();
    public abstract Block getB();
    
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
    
    public abstract boolean canBeInstanced();
    public abstract void instanceValues(Predicate pred, State state, State goalState);
    public abstract Operator clone();
    
    @Override
    public abstract String toString();
    
}
