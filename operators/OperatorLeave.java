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
    public OperatorLeave(Block a){
        super(Operator.LEAVE);
        
        // Add preconditions
        pres.add(new PredicatePickedUp(a));
        pres.add(new PredicateUsedColsNum(PredicateUsedColsNum.N_UNDEFINED));
        
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
    public void instanceValues(Predicate pred, State state){
        instanceA(state);
    }
    
    private void instanceA(State state){
        Block val = null;
        
        // TODO: Select value
        // *******************************
        
        // Give value to predicates
        // *******************************
        
        pres.get(0).setA(val);
        rmvs.get(0).setA(val);
        adds.get(0).setA(val);
    }
    
    @Override
    public Operator clone(){
        OperatorLeave ret = new OperatorLeave(this); 
        return ret;
    }
    
    @Override
    public String toString(){
        Block a = this.pres.get(0).getA();
        return "leave(" + a.getName() + ")";
    }
}
