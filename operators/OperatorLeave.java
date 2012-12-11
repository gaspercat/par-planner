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
    private ArrayList<Block> instanceA = null;
    
    public OperatorLeave(Block a){
        super(Operator.LEAVE);
        
        // Add preconditions
        pres.add(new PredicateUsedColsNum(PredicateUsedColsNum.N_UNDEFINED));
        pres.add(new PredicatePickedUp(a));
        pres.add(new PredicateFreeStack());
        
        // Add deletions
        rmvs.add(new PredicatePickedUp(a));
        rmvs.add(new PredicateUsedColsNum(PredicateUsedColsNum.N_UNDEFINED));
        
        // Add additions
        adds.add(new PredicateOnTable(a));
        adds.add(new PredicateUsedColsNum(PredicateUsedColsNum.N_INCREASED));
        adds.add(new PredicateFreeArm());
    }
    
    public OperatorLeave(OperatorLeave op){
        super(op);
    }
    
    @Override
    public boolean hasInstancesLeft(){
        if(instanceA == null || instanceA.isEmpty()) return false;
        
        setA(null);
        setN(0);
        
        return true;
    }
    
    @Override
    public void instanceValues(Predicate pred, State state){
        if(pred instanceof PredicatePickedUp){
            instanceA(state);
        }else if(pred instanceof PredicateUsedColsNum){
            instanceN(state);
        }
    }
    
    private void instanceA(State state){
        Block val;
        
        // Initialize values
        // *******************************
        
        if(instanceA == null){
            instanceA = new ArrayList<Block>();
            
            Predicate p = state.getPredicate(Predicate.PICKED_UP);
            if(p != null){
                instanceA.add(p.getA());
            }
            
            ArrayList<Block> blocks = state.getAllBlocks();
            blocks.remove(p.getA());
            instanceA.addAll(blocks);
        }
        
        // Select value
        // *******************************
        
        val = instanceA.remove(rnd.nextInt(instanceA.size()));
        setA(val);
    }
    
    private void instanceN(State state){
        int val = state.getNumColumns();
        setN(val);
    }
    
    @Override
    public Operator clone(){
        OperatorLeave ret = new OperatorLeave(this); 
        return ret;
    }
    
    @Override
    public String toString(){
        Block a = this.pres.get(1).getA();
        return "leave(" + (a == null ? "undef" : a.getName()) + ")";
    }
    
    private void setA(Block val){
        pres.get(1).setA(val);
        rmvs.get(0).setA(val);
        adds.get(0).setA(val);
    }
    
    private void setN(int val){
        pres.get(0).setN(val);
        rmvs.get(1).setN(val);
        adds.get(1).setN(val+1);
    }
}
