package blocksworld;

import java.util.ArrayList;
import predicates.Predicate;
import predicates.PredicateFreeStack;
import operators.Operator;

public class State {
    private ArrayList<Predicate> preds;
    
    // Empty constructor
    public State(){
        preds = new ArrayList<Predicate>();
    }
    
    // Constructor by state modification
    public State(State s, Operator o){
        preds = new ArrayList<Predicate>();
        
        for(Predicate pred: s.preds){
            preds.add(pred.clone());
        }
        
        o.apply(this);
    }
    
    // Copy constructor
    public State(State s){
        this.preds = new ArrayList<Predicate>();
        
        for(Predicate pred: s.preds){
            this.preds.add(pred.clone());
        }
    }
    
    // * ** GETTER METHODS
    // * ******************************************

    public ArrayList<Predicate> getPredicates(){
        return this.preds;
    }
    
    public boolean hasPredicate(Predicate p){
        int nStacks = 0;
        
        for(Predicate pred: this.preds){
            if(pred.equals(p)) return true;
            if(pred.getType() == Predicate.ON_TABLE) nStacks++;
        }
        
        // Check FreeStack predicate if less than three stacks
        if(nStacks < 3) return p instanceof PredicateFreeStack;
        
        return false;
    }
    
    public ArrayList<Predicate> getUnmetConditions(Preconditions conds){
        ArrayList<Predicate> ret = new ArrayList<Predicate>();
        
        for(Predicate pred: conds.getPredicates()){
            boolean found = false;
            if(!hasPredicate(pred)) ret.add(pred);
        }
        
        return ret;
    }
    
    // * ** SETTER METHODS
    // * ******************************************
    
    public void addPredicate(Predicate pred){
        preds.add(pred);
    }
}
