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
public class PredicateFreeArm extends Predicate{
    public PredicateFreeArm(){
        super(Predicate.FREE_ARM);
    }
    
    protected PredicateFreeArm(ArrayList<Object> params){
        super(Predicate.FREE_ARM);
        
        if(params.size() != 0){
            System.out.println("ERROR: Wrong number of parameters for 'free-arm' predicate (must have none)");
            return;
        }
    }
    
    private PredicateFreeArm(PredicateFreeArm pred){
        super((Predicate)pred);
    }
    
    @Override
    public String getTypeName(){
        return "free-arm";
    }
    
    @Override
    public Block getA(){
        return null;
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
        return true;
    }
    
    @Override
    public void setA(Block a){
        // STUB!!! Just for generalization purposes!
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
        return pred instanceof PredicateFreeArm;
    }
    
    @Override
    public Predicate clone(){
        Predicate ret = new PredicateFreeArm(this);
        return ret;
    }
    
    @Override
    public String toString(){
        return this.getTypeName() + "()";
    }
    
    @Override
    public int compareTo(Object obj){
        if(obj instanceof PredicateUsedColsNum){
            return -1;
        }else if(obj instanceof PredicateFree){
            return 0;
        }else{
            return 1;
        }
    }
}
