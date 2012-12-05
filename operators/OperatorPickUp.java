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
public class OperatorPickUp extends Operator {
    public OperatorPickUp(Block a){
        super(Operator.PICK_UP);
        
        // Add preconditions
        preconds.addPredicate(new PredicateUsedColsNum(PredicateUsedColsNum.N_UNDEFINED));
        preconds.addPredicate(new PredicateOnTable(a));
        preconds.addPredicate(new PredicateFreeArm());
        preconds.addPredicate(new PredicateFree(a));
        
        // Add deletions
        remove.add(new PredicateOnTable(a));
        remove.add(new PredicateFreeArm());
        remove.add(new PredicateUsedColsNum(PredicateUsedColsNum.N_UNDEFINED));
        
        // Add additions
        add.add(new PredicatePickedUp(a));
        add.add(new PredicateUsedColsNum(PredicateUsedColsNum.N_DECREASED));
    }
}
