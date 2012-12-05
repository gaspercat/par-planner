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
public class PredicateFree extends Predicate{
    private Block paramA;
    
    public PredicateFree(Block a){
        super(Predicate.FREE);
        this.paramA = a;
    }
    
    protected PredicateFree(ArrayList<Object> params){
        super(Predicate.FREE);
        
        if(params.size() != 1){
            this.is_valid = false;
            System.out.println("ERROR: Wrong number of parameters for 'free' predicate (must have one)");
            return;
        }

        if(!(params.get(0) instanceof Block)){
            this.is_valid = false;
            System.out.println("ERROR: First parameter for 'free' predicate must be a block");
            return;
        }

        this.paramA = (Block)params.get(0);
    }
    
    private PredicateFree(PredicateFree pred){
        super((Predicate)pred);
        this.paramA = pred.paramA;
    }
    
    @Override
    public String getTypeName(){
        return "free";
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
    public boolean equals(Predicate pred){
        if(!(pred instanceof PredicateFree)) return false;
        
        PredicateFree p = (PredicateFree)pred;
        boolean ret = true;
        
        ret = ret && (this.paramA == p.paramA);
        
        return ret;
    }
    
    @Override
    public Predicate clone(){
        Predicate ret = new PredicateFree(this);
        return ret;
    }
}
