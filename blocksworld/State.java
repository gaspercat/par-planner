package blocksworld;

import java.util.ArrayList;
import predicates.Predicate;
import predicates.PredicateFreeStack;
import predicates.PredicateUsedColsNum;
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
            if(!hasPredicate(pred)) ret.add(pred);
        }
        
        return ret;
    }
    
    // * ** SETTER METHODS
    // * ******************************************
    
    public void addPredicate(Predicate pred){
        preds.add(pred);
    }
    
    // Method to set the number of used columns
    public void setUsedColsNum(){
        // Count number of used columns
        int nCols = 0;
        for(Predicate p: this.preds){
            if(p.getType() == Predicate.ON_TABLE) nCols++;
        }
        
        // Remove used columns predicate if present & invalid
        for(Predicate p: this.preds){
            if(p.getType() == Predicate.USED_COLS_NUM){
                if(p.getN() == nCols) return;
                this.preds.remove(p);
                break;
            }
        }
        
        // Add new UsedColsNum
        this.preds.add(new PredicateUsedColsNum(nCols));
    }
}
