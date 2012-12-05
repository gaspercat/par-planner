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
    public boolean isInstanced(){
        return true;
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
}
