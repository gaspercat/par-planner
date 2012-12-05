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
    public boolean isInstanced(){
        return this.paramA != null;
    }
    
    @Override
    public void setA(Block a){
        this.paramA = a;
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
}
