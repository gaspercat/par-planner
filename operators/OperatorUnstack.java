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
        pres.add(new PredicateFreeArm());
        pres.add(new PredicateFree(a));
        pres.add(new PredicateOn(a, b));
        
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
        // Select value
        // *******************************
        
        Block val;
        Block b = pres.get(2).getB();
        
        Predicate p = state.matchPredicate(new PredicateOn(null, b));
        if(p != null){
            val = p.getA();
        }else{
            ArrayList<Block> blocks = state.getAllBlocks();
            if(b != null) blocks.remove(b);
            val = blocks.get(rnd.nextInt(blocks.size()));
        }
        
        // Give value to predicates
        // *******************************
        
        pres.get(1).setA(val);
        pres.get(2).setA(val);
        rmvs.get(0).setA(val);
        adds.get(0).setA(val);
        
    }
    
    private void instanceB(State state){
        // Select value
        // *******************************
        
        ArrayList<Block> blocks = state.getAllBlocks();
        
        Block a = pres.get(1).getA();
        if(a != null) blocks.remove(a);
        
        Block val = blocks.get(rnd.nextInt(blocks.size()));
        
        // Give value to predicates
        // *******************************
        
        pres.get(2).setB(val);
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
        Block a = this.pres.get(2).getA();
        Block b = this.pres.get(2).getB();
        return "unstack(" + (a != null ? a.getName() : "undef") + ", " + (b != null ? b.getName() : "undef") + ")";
    }
}
