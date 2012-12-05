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
public class OperatorUnstack extends Operator {
    public OperatorUnstack(Block a, Block b){
        super(Operator.UNSTACK);

        // Add preconditions
        preconds.addPredicate(new PredicateOn(a, b));
        preconds.addPredicate(new PredicateFree(a));
        preconds.addPredicate(new PredicateFreeArm());
        
        // Add deletions
        add.add(new PredicateOn(a, b));
        add.add(new PredicateFreeArm());
        
        // Add additions
        remove.add(new PredicatePickedUp(a));
        remove.add(new PredicateFree(b));
    }
}
