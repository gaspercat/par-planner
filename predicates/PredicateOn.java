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
    
    public PredicateOn(Block A, Block B){
        super(Predicate.ON);
        this.paramA = A;
        this.paramB = B;
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
    public boolean isInstanced(){
        return (this.paramA != null) && (this.paramB != null);
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
}
