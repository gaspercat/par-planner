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
        pres.add(new PredicatePickedUp(a));
        pres.add(new PredicateFree(b));
        pres.add(new PredicateHeavier(b, a));
        
        // Add deletions
        rmvs.add(new PredicatePickedUp(a));
        rmvs.add(new PredicateFree(b));
        
        // Add additions
        adds.add(new PredicateOn(a, b));
        adds.add(new PredicateFreeArm());
    }
    
    public OperatorStack(OperatorStack op){
        super(op);
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
        
        pres.get(0).setA(val);
        pres.get(2).setB(val);
        rmvs.get(0).setA(val);
        adds.get(0).setA(val);
    }
    
    private void instanceB(State state){
        Block val = null;
        
        // TODO: Select value
        
        // Give value to predicates
        // *******************************
        
        pres.get(1).setA(val);
        pres.get(2).setA(val);
        rmvs.get(1).setA(val);
    }
    
    @Override
    public Operator clone(){
        OperatorStack ret = new OperatorStack(this); 
        return ret;
    }
}