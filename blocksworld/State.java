package blocksworld;

import java.util.ArrayList;
import predicates.Predicate;
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
        preds = new ArrayList<Predicate>();
        
        for(Predicate pred: s.preds){
            preds.add(pred.clone());
        }
    }
    
    // * ** GETTER METHODS
    // * ******************************************

    public ArrayList<Predicate> getPredicates(){
        return this.preds;
    }
    
    public boolean hasPredicate(Predicate p){
        for(Predicate pred: preds){
            if(pred.equals(p)) return true;
        }
        
        return false;
    }
    
    // * ** SETTER METHODS
    // * ******************************************
    
    public void addPredicate(Predicate pred){
        preds.add(pred);
    }
}
