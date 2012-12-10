/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package predicates;

import blocksworld.Block;
import java.util.ArrayList;

/**
 *
 * @author gaspercat
 */
public class PredicateOnTable extends Predicate{
    private Block paramA;
    
    public PredicateOnTable(Block a){
        super(Predicate.ON_TABLE);
        this.paramA = a;
    }
    
    protected PredicateOnTable(ArrayList<Object> params){
        super(Predicate.ON_TABLE);
        
        if(params.size() != 1){
            this.is_valid = false;
            System.out.println("ERROR: Wrong number of parameters for 'on-table' predicate (must have one)");
            return;
        }

        if(!(params.get(0) instanceof Block)){
            this.is_valid = false;
            System.out.println("ERROR: First parameter for 'on-table' predicate must be a block");
            return;
        }
        
        this.paramA = (Block)params.get(0);
    }
    
    private PredicateOnTable(PredicateOnTable pred){
        super((Predicate)pred);
        this.paramA = pred.paramA;
    }
    
    @Override
    public String getTypeName(){
        return "on-table";
    }
    
    @Override
    public Block getA(){
        return this.paramA;
    }
    
    @Override
    public Block getB(){
        return null;
    }
    
    @Override
    public int getN(){
        return -5;
    }
    
    @Override
    public boolean isInstanced(){
        return this.paramA != null;
    }
    
    @Override
    public void setA(Block a){
        this.paramA = a;
    }
    
    @Override
    public void setB(Block b){
        // STUB!!! Just for generalization purposes!
    }
    
    @Override
    public void setN(int n){
        // STUB!!! Just for generalization purposes!
    }
    
    @Override
    public boolean equals(Predicate pred){
        if(!(pred instanceof PredicateOnTable)) return false;
        
        PredicateOnTable p = (PredicateOnTable)pred;
        boolean ret = true;
        
        ret = ret && (this.paramA == p.paramA);
        
        return ret;
    }
    
    @Override
    public Predicate clone(){
        Predicate ret = new PredicateOnTable(this);
        return ret;
    }
    
    @Override
    public String toString(){
        String a = (this.paramA != null) ? this.paramA.getName() : "undef";
        return this.getTypeName() + "(" + a + ")";
    }
    
    @Override
    public int compareTo(Object obj){
        if(obj instanceof PredicateHeavier){
            return 1;
        }else if(obj instanceof PredicateFree){
            return 0;
        }else{
            return -1;
        }
    }
}
