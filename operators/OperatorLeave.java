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
public class OperatorLeave extends Operator {
    public OperatorLeave(Block a){
        super(Operator.LEAVE);
        
        // Add preconditions
        preconds.addPredicate(new PredicatePickedUp(a));
        preconds.addPredicate(new PredicateUsedColsNum(PredicateUsedColsNum.N_UNDEFINED));
        
        // Add deletions
        remove.add(new PredicatePickedUp(a));
        remove.add(new PredicateUsedColsNum(PredicateUsedColsNum.N_UNDEFINED));
        
        // Add additions
        add.add(new PredicateOnTable(a));
        add.add(new PredicateUsedColsNum(PredicateUsedColsNum.N_INCREASED));
        add.add(new PredicateFreeArm());
    }
    
    @Override
    public void instanceValues(Predicate pred, State state){
        instanceA(state);
    }
    
    private void instanceA(State state){
        Block val = null;
        
        // TODO: Select value
        // *******************************
        
        // Give value to predicates
        // *******************************
        
        ArrayList<Predicate> preds = preconds.getPredicates();
        preds.get(0).setA(val);
        remove.get(0).setA(val);
        add.get(0).setA(val);
    }
}
