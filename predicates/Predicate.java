/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package predicates;

import blocksworld.*;
import java.util.ArrayList;

public abstract class Predicate implements Comparable{
    public static final int ON_TABLE = 1;
    public static final int ON = 2;
    public static final int FREE = 3;
    public static final int FREE_ARM = 4;
    public static final int PICKED_UP = 5;
    public static final int USED_COLS_NUM = 6;
    public static final int HEAVIER = 7;
    public static final int FREE_STACK = 8;
    
    protected int type;
    protected boolean is_valid;
    
    // * ** FACTORY
    // * ******************************************
    
    public static Predicate create(String pred, ArrayList<Object> params){
        Predicate ret = null;
        
        if(pred.equals("on-table")){
            ret = new PredicateOnTable(params);
        }else if(pred.equals("on")){
            ret = new PredicateOn(params);
        }else if(pred.equals("free")){
            ret = new PredicateFree(params);
        }else if(pred.equals("free-arm")){
            ret = new PredicateFreeArm(params);
        }else if(pred.equals("picked-up")){
            ret = new PredicatePickedUp(params);
        }else if(pred.equals("used-cols-num")){
            ret = new PredicateUsedColsNum(params);
        }else if(pred.equals("heavier")){
            ret = new PredicateHeavier(params);
        }else{
            System.out.println("ERROR: Statement '" + pred + "' is not recognized");
        }
        
        return ret;
    }
    
    // * ** CONSTRUCTORS
    // * ******************************************
    
    protected Predicate(int type){
        this.type = type;
        this.is_valid = true;
    }
    
    protected Predicate(Predicate pred){
        this.type = pred.type;
        this.is_valid = pred.is_valid;
    }
    
    // * ** GETTER METHODS
    // * ******************************************
    
    public int getType(){
        return this.type;
    }
    
    public boolean isValid(){
        return this.is_valid;
    }
    
    // * ** OPERATORS
    // * ******************************************
    
    public boolean matches(Predicate pred){
        if(pred.type != this.type) return false;
        if(pred.getA() != null && this.getA() != null && this.getA() != pred.getA()) return false;
        if(pred.getB() != null && this.getB() != null && this.getB() != pred.getB()) return false;
        if(pred.getN() > 0 && this.getN() > 0 && pred.getN() != this.getN()) return false;
        
        return true;
    }
    
    // * ** ABSTRACT METHODS
    // * ******************************************
    
    public abstract boolean isInstanced();
    public abstract String getTypeName();
    public abstract Block getA();
    public abstract Block getB();
    public abstract int getN();
    
    public abstract void setA(Block a);
    public abstract void setB(Block b);
    public abstract void setN(int n);
    
    public abstract boolean equals(Predicate pred);
    public abstract Predicate clone();
    
    @Override
    public abstract int compareTo(Object obj);
}
