/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package operators;

import java.util.ArrayList;
import blocksworld.Block;
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
}
