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
public class PredicatePickedUp extends Predicate{
    private Block paramA;
    
    public PredicatePickedUp(Block A){
        super(Predicate.PICKED_UP);
        this.paramA = A;
    }
    
    protected PredicatePickedUp(ArrayList<Object> params){
        super(Predicate.PICKED_UP);
        
        if(params.size() != 1){
            this.is_valid = false;
            System.out.println("ERROR: Wrong number of parameters for 'picked-up' predicate (must have one)");
            return;
        }

        if(!(params.get(0) instanceof Block)){
            this.is_valid = false;
            System.out.println("ERROR: First parameter for 'picked-up' predicate must be a block");
            return;
        }

        this.paramA = (Block)params.get(0);
    }
    
    private PredicatePickedUp(PredicatePickedUp pred){
        super((Predicate)pred);
        this.paramA = pred.paramA;
    }
    
    @Override
    public String getTypeName(){
        return "picked-up";
    }
    
    @Override
    public boolean isInstanced(){
        return this.paramA != null;
    }
    
    @Override
    public boolean equals(Predicate pred){
        if(!(pred instanceof PredicatePickedUp)) return false;
        
        PredicatePickedUp p = (PredicatePickedUp)pred;
        boolean ret = true;
        
        ret = ret && (this.paramA == p.paramA);
        
        return ret;
    }
    
    @Override
    public Predicate clone(){
        Predicate ret = new PredicatePickedUp(this);
        return ret;
    }
}
