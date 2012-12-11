package blocksworld;

import java.util.ArrayList;
import java.util.Random;
import predicates.Predicate;

public class Preconditions {
    private static Random rnd = new Random();
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
        /*ArrayList<Predicate> bef = (ArrayList<Predicate>)this.preconds.clone();
        ArrayList<Predicate> ret = new ArrayList<Predicate>();
        
        while(!bef.isEmpty()){
            Predicate p = bef.remove(rnd.nextInt(bef.size()));
            ret.add(p);
        }
        
        return ret;*/
        return this.preconds;
    }
}