package blocksworld;

import java.util.ArrayList;
import java.util.Collections;
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

    public Predicate getPredicate(int type){
        for(Predicate p: this.preds){
            if(p.getType() == type) return p;
        }
        
        return null;
    }
    
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
    
    public int getNumColumns(){
        int ret = 0;
        
        for(Predicate pred: this.preds){
            if(pred.getType() == Predicate.ON_TABLE) ret++;
        }
        
        return ret;
    }
    
    public ArrayList<Block> getAllBlocks(){
        ArrayList<Block> ret = new ArrayList<Block>();
        
        for(Predicate p: this.preds){
            if(p.getType() == Predicate.ON || p.getType() == Predicate.ON_TABLE){
                ret.add(p.getA());
            }
        }
        
        return ret;
    }
    
    public Predicate matchPredicate(Predicate pred){
        for(Predicate p: this.preds){
            if(p.matches(pred)) return p;
        }
        
        return null;
    }
    
    public ArrayList<Predicate> matchPredicates(Predicate pred){
        ArrayList<Predicate> ret = new ArrayList<Predicate>();
        
        for(Predicate p: this.preds){
            if(p.matches(pred)) ret.add(p);
        }
        
        return ret;
    }
    
    // * ** SETTER METHODS
    // * ******************************************
    
    public void addPredicate(Predicate pred){
        preds.add(pred);
    }
    
    // Method to set the number of used columns
    public void sortPredicates(boolean setColsNum){
        if(setColsNum){
            // Count number of used columns
            int nCols = 0;
            for(Predicate p: this.preds){
                if(p.getType() == Predicate.ON_TABLE) nCols++;
            }

            // Remove used columns predicate if present
            for(Predicate p: this.preds){
                if(p.getType() == Predicate.USED_COLS_NUM){
                    this.preds.remove(p);
                    break;
                }
            }

            // Add new UsedColsNum
            this.preds.add(0, new PredicateUsedColsNum(nCols));
        }
        
        // Sort predicates
        Collections.sort(this.preds, Collections.reverseOrder());
    }
    
    // * ** OPERATORS
    // * ******************************************
    
    @Override
    public State clone(){
        return new State(this);
    }
    
    public boolean equals(State s){
        if(s.preds.size() != this.preds.size()) return false;
        
        for(Predicate p: this.preds){
            boolean found = false;
            for(Predicate p2: s.preds){
                if(p.equals(p2)){
                    found = true;
                    break;
                }
            }
            
            if(!found) return false;
        }
        
        return true;
    }
    
    @Override
    public String toString(){
        String ret = "";
        
        for(Predicate p: this.preds){
            ret += p.toString() + " ";
        }
        
        return ret;
    }
}
