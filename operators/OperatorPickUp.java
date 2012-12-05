/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package operators;

import java.util.ArrayList;
import blocksworld.Block;
import blocksworld.State;
import predicates.PredicateUsedColsNum;
import predicates.*;

/**
 *
 * @author gaspercat
 */
public class OperatorPickUp extends Operator {
    public OperatorPickUp(Block a){
        super(Operator.PICK_UP);
        
        // Add preconditions
        pres.add(new PredicateUsedColsNum(PredicateUsedColsNum.N_UNDEFINED));
        pres.add(new PredicateOnTable(a));
        pres.add(new PredicateFreeArm());
        pres.add(new PredicateFree(a));
        
        // Add deletions
        rmvs.add(new PredicateOnTable(a));
        rmvs.add(new PredicateFreeArm());
        rmvs.add(new PredicateUsedColsNum(PredicateUsedColsNum.N_UNDEFINED));
        
        // Add additions
        adds.add(new PredicatePickedUp(a));
        adds.add(new PredicateUsedColsNum(PredicateUsedColsNum.N_DECREASED));
    }
    
    public OperatorPickUp(OperatorPickUp op){
        super(op);
    }
    
    @Override
    public void instanceValues(Predicate pred, State state){
        instanceA(state);
    }
    
    private void instanceA(State state){
        Block val = null;
        
        // TODO: Select value
        
        // Give value to predicates
        // *******************************
        
        pres.get(1).setA(val);
        pres.get(3).setA(val);
        rmvs.get(0).setA(val);
        adds.get(0).setA(val);
    }
    
    @Override
    public Operator clone(){
        OperatorPickUp ret = new OperatorPickUp(this); 
        return ret;
    }
}
