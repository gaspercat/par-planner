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
public class OperatorStack extends Operator {
    public OperatorStack(Block a, Block b){
        super(Operator.STACK);

        // Add preconditions
        preconds.addPredicate(new PredicatePickedUp(a));
        preconds.addPredicate(new PredicateFree(b));
        preconds.addPredicate(new PredicateHeavier(a, b));
        
        // Add deletions
        remove.add(new PredicatePickedUp(a));
        remove.add(new PredicateFree(b));
        
        // Add additions
        add.add(new PredicateOn(a, b));
        add.add(new PredicateFreeArm());
    }
}
