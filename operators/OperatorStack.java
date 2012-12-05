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
public class OperatorStack extends Operator {
    public OperatorStack(Block a, Block b){
        super(Operator.STACK);

        // Add preconditions
        preconds.addPredicate(new PredicatePickedUp(a));
        preconds.addPredicate(new PredicateFree(b));
        preconds.addPredicate(new PredicateHeavier(b, a));
        
        // Add deletions
        remove.add(new PredicatePickedUp(a));
        remove.add(new PredicateFree(b));
        
        // Add additions
        add.add(new PredicateOn(a, b));
        add.add(new PredicateFreeArm());
    }
    
    @Override
    public void instanceValues(Predicate pred, State state){
        if(pred instanceof PredicatePickedUp){
            instanceA(state);
        }else if(pred instanceof PredicateFree){
            instanceB(state);
        }else if(pred instanceof PredicateHeavier){
            PredicateHeavier p = (PredicateHeavier)pred;
            if(!p.isInstancedA()) instanceB(state);
            if(!p.isInstancedB()) instanceA(state);
        }
    }
    
    private void instanceA(State state){
        Block val = null;
        
        // TODO: Select value
        
        // Give value to predicates
        // *******************************
        
        ArrayList<Predicate> preds = preconds.getPredicates();
        preds.get(0).setA(val);
        preds.get(2).setB(val);
        remove.get(0).setA(val);
        add.get(0).setA(val);
    }
    
    private void instanceB(State state){
        Block val = null;
        
        // TODO: Select value
        
        // Give value to predicates
        // *******************************
        
        ArrayList<Predicate> preds = preconds.getPredicates();
        preds.get(1).setA(val);
        preds.get(2).setA(val);
        remove.get(1).setA(val);
    }
}