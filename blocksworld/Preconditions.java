package blocksworld;

import java.util.ArrayList;
import predicates.Predicate;

public class Preconditions {
    ArrayList<Predicate> preconds;

    public Preconditions(){
        this.preconds = new ArrayList<Predicate>();
    }
    
    public Preconditions(ArrayList<Predicate> preds){
        this.preconds = preds;
    }

    public void addPredicate(Predicate pred){
        this.preconds.add(pred);
    }

    public ArrayList<Predicate> getPredicates(){
        return this.preconds;
    }
}