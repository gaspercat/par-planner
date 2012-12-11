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
    ArrayList<Block> instanceA = null;
    ArrayList<Block> instanceB = null;
    ArrayList<Block> blacklistA = null;
    ArrayList<Block> blacklistB = null;
    
    public OperatorStack(Block a, Block b){
        super(Operator.STACK);

        // Add preconditions
        pres.add(new PredicateHeavier(b, a));
        pres.add(new PredicatePickedUp(a));
        pres.add(new PredicateFree(b));
        
        // Add deletions
        rmvs.add(new PredicatePickedUp(a));
        rmvs.add(new PredicateFree(b));
        
        // Add additions
        adds.add(new PredicateOn(a, b));
        adds.add(new PredicateFreeArm());
    }
    
    public OperatorStack(Block a, Block b, ArrayList<Block> blacklistA, ArrayList<Block> blacklistB){
        super(Operator.STACK);

        // Add preconditions
        pres.add(new PredicateHeavier(b, a));
        pres.add(new PredicatePickedUp(a));
        pres.add(new PredicateFree(b));
        
        // Add deletions
        rmvs.add(new PredicatePickedUp(a));
        rmvs.add(new PredicateFree(b));
        
        // Add additions
        adds.add(new PredicateOn(a, b));
        adds.add(new PredicateFreeArm());
        
        // Set blacklists
        this.blacklistA = blacklistA;
        this.blacklistB = blacklistB;
    }
    
    public OperatorStack(OperatorStack op){
        super(op);
    }
    
    @Override
    public Block getA(){
        return pres.get(1).getA();
    }
    
    @Override
    public Block getB(){
        return pres.get(2).getA();
    }
    
    @Override
    public boolean canBeInstanced(){
        if(instanceA == null && instanceB == null) return false;
        if(instanceA != null && instanceA.isEmpty()) return false;
        if(instanceB != null && instanceB.isEmpty()) return false;
        
        if(instanceA != null) setA(null);
        if(instanceB != null) setB(null);
        
        return true;
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
        Block val;
        
        // Instance values
        // *******************************
        
        if(instanceA == null){
            instanceA = new ArrayList<Block>();
            instanceA.addAll(state.getAllBlocks());
            
            Block b = pres.get(2).getA();
            if(b != null){
                // Remove a from possible options
                instanceA.remove(b);
                
                // Remove elements heavier than b
                ArrayList<Predicate> preds = state.matchPredicates(new PredicateHeavier(null, b));
                for(Predicate p: preds) instanceA.remove(p.getA());
                
                // Remove blacklisted elements
                instanceA.removeAll(this.blacklistA);
            }
        }
        
        // Select value
        // *******************************
        
        val = instanceA.remove(rnd.nextInt(instanceA.size()));
        setA(val);
    }
    
    private void instanceB(State state){
        Block val;
        
        // Instance values
        // *******************************
        
        if(instanceB == null){
            ArrayList<Predicate> preds;
            
            instanceB = new ArrayList<Block>();
            preds = state.matchPredicates(new PredicateFree(null));
            for(Predicate tp: preds) instanceB.add(tp.getA());
            //instanceB.addAll(state.getAllBlocks());
            
            Block a = pres.get(1).getA();
            if(a != null){
                // Remove a from possible options
                instanceB.remove(a);  
                
                // Remove elements lighter than a
                preds = state.matchPredicates(new PredicateHeavier(a, null));
                for(Predicate p: preds) instanceB.remove(p.getB());
                
                // Remove blacklisted elements
                instanceB.removeAll(this.blacklistB);
                
                // If no element left, add elements heavier (even the ones not on top)
                if(instanceB.isEmpty()){
                    preds = state.matchPredicates(new PredicateHeavier(null, a));
                    for(Predicate p: preds) instanceB.add(p.getA());
                }
            }
        }
        
        // Select value
        // *******************************
        
        val = instanceB.get(rnd.nextInt(instanceB.size()));
        setB(val);
    }
    
    @Override
    public Operator clone(){
        OperatorStack ret = new OperatorStack(this); 
        return ret;
    }
    
    @Override
    public String toString(){
        Block a = this.pres.get(1).getA();
        Block b = this.pres.get(2).getA();
        return "stack(" + (a != null ? a.getName() : "undef") + ", " + (b != null ? b.getName() : "undef") + ")";

    }
    
    private void setA(Block val){
        pres.get(1).setA(val);
        pres.get(0).setB(val);
        rmvs.get(0).setA(val);
        adds.get(0).setA(val);
    }
    
    private void setB(Block val){
        pres.get(2).setA(val);
        pres.get(0).setA(val);
        rmvs.get(1).setA(val);
        adds.get(0).setB(val);
    }
}