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
        pres.add(new PredicateOn(a, b));
        pres.add(new PredicateFree(a));
        pres.add(new PredicateFreeArm());
        
        // Add deletions
        rmvs.add(new PredicateOn(a, b));
        rmvs.add(new PredicateFreeArm());
        
        // Add additions
        adds.add(new PredicatePickedUp(a));
        adds.add(new PredicateFree(b));
    }
    
    public OperatorUnstack(OperatorUnstack op){
        super(op);
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
        
        pres.get(0).setA(val);
        pres.get(1).setA(val);
        rmvs.get(0).setA(val);
        adds.get(0).setA(val);
        
    }
    
    private void instanceB(State state){
        Block val = null;
        
        // TODO: Select value
        
        // Give value to predicates
        // *******************************
        
        pres.get(0).setB(val);
        rmvs.get(0).setB(val);
        adds.get(1).setA(val);
    }
    
    @Override
    public Operator clone(){
        OperatorUnstack ret = new OperatorUnstack(this); 
        return ret;
    }
    
    @Override
    public String toString(){
        Block a = this.pres.get(0).getA();
        Block b = this.pres.get(0).getB();
        return "unstack(" + a.getName() + ", " + b.getName() + ")";
    }
}
