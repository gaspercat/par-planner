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
public class OperatorUnstack extends Operator {
    public OperatorUnstack(Block a, Block b){
        super(Operator.UNSTACK);

        // Add preconditions
        preconds.addPredicate(new PredicateOn(a, b));
        preconds.addPredicate(new PredicateFree(a));
        preconds.addPredicate(new PredicateFreeArm());
        
        // Add deletions
        remove.add(new PredicateOn(a, b));
        remove.add(new PredicateFreeArm());
        
        // Add additions
        add.add(new PredicatePickedUp(a));
        add.add(new PredicateFree(b));
    }
    
    @Override
    public void instanceValues(Predicate pred, State state){
        if(pred instanceof PredicateOn){
            PredicateOn p = (PredicateOn)pred;
            if(!p.isInstancedA()) instanceA(state);
            if(!p.isInstancedB()) instanceB(state);
        }else if(pred instanceof PredicateFree){
            instanceA(state);
        }
    }
    
    private void instanceA(State state){
        Block val = null;
        
        // TODO: Select value
        
        // Give value to predicates
        // *******************************
        
        ArrayList<Predicate> preds = preconds.getPredicates();
        preds.get(0).setA(val);
        preds.get(1).setA(val);
        remove.get(0).setA(val);
        add.get(0).setA(val);
        
    }
    
    private void instanceB(State state){
        Block val = null;
        
        // TODO: Select value
        
        // Give value to predicates
        // *******************************
        
        ArrayList<Predicate> preds = preconds.getPredicates();
        preds.get(0).setB(val);
        remove.get(0).setB(val);
        add.get(1).setA(val);
    }
}
