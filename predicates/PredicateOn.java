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
public class PredicateOn extends Predicate{
    private Block paramA;
    private Block paramB;
    
    public PredicateOn(Block a, Block b){
        super(Predicate.ON);
        this.paramA = a;
        this.paramB = b;
    }
    
    protected PredicateOn(ArrayList<Object> params){
        super(Predicate.ON);
        
        if(params.size() != 2){
            System.out.println("ERROR: Wrong number of parameters for 'on' predicate (must have two)");
            return;
        }

        if(!(params.get(0) instanceof Block)){
            System.out.println("ERROR: First parameter for 'on' predicate must be a block");
            return;
        }

        if(!(params.get(1) instanceof Block)){
            System.out.println("ERROR: Second parameter for 'on' predicate must be a block");
            return;
        }

        this.paramA = (Block)params.get(0);
        this.paramB = (Block)params.get(1);
    }
    
    private PredicateOn(PredicateOn pred){
        super((Predicate)pred);
        this.paramA = pred.paramA;
        this.paramB = pred.paramB;
    }
    
    @Override
    public String getTypeName(){
        return "on";
    }
    
    @Override
    public Block getA(){
        return this.paramA;
    }
    
    @Override
    public Block getB(){
        return this.paramB;
    }
    
    @Override
    public int getN(){
        return -5;
    }
    
    @Override
    public boolean isInstanced(){
        return (this.paramA != null) && (this.paramB != null);
    }
    
    public boolean isInstancedA(){
        return (this.paramA != null);
    }
    
    public boolean isInstancedB(){
        return (this.paramB != null);
    }
    
    @Override
    public void setA(Block a){
        this.paramA = a;
    }
    
    @Override
    public void setB(Block b){
        this.paramB = b;
    }
    
    @Override
    public void setN(int n){
        // STUB!!! Just for generalization purposes!
    }
    
    @Override
    public boolean equals(Predicate pred){
        if(!(pred instanceof PredicateOn)) return false;
        
        PredicateOn p = (PredicateOn)pred;
        boolean ret = true;
        
        ret = ret && (this.paramA == p.paramA);
        ret = ret && (this.paramB == p.paramB);
        
        return ret;
    }
    
    @Override
    public Predicate clone(){
        Predicate ret = new PredicateOn(this);
        return ret;
    }
    
    @Override
    public String toString(){
        String a = (this.paramA != null) ? this.paramA.getName() : "undef";
        String b = (this.paramB != null) ? this.paramB.getName() : "undef";
        return this.getTypeName() + "(" + a + ", " + b + ")";
    }
    
    @Override
    public int compareTo(Object obj){
        if(obj instanceof PredicateOnTable || obj instanceof PredicateHeavier){
            return 1;
        }else if(obj instanceof PredicateFree){
            return 0;
        }else{
            return -1;
        }
    }
}
