/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package blocksworld;

import java.util.ArrayList;

/**
 *
 * @author gaspercat
 */
public class Operator {
    public static final int PICK_UP = 1;
    public static final int LEAVE = 2;
    public static final int UNSTACK = 3;
    public static final int STACK = 4;
    
    public class Preconditions {
        ArrayList<Predicate> preconds;
        
        public Preconditions(){
            preconds = new ArrayList<Predicate>();
        }
        
        public void addPredicate(Predicate pred){
            preconds.add(pred);
        }
        
        public ArrayList<Predicate> getPredicates(){
            return preconds;
        }
    }
    
    int type;
    Preconditions preconds;
    ArrayList<Predicate> remove;
    ArrayList<Predicate> add;
    
    public Operator(int type, ArrayList<Block> params){
        this.preconds = new Preconditions();
        this.remove = new ArrayList<Predicate>();
        this.add = new ArrayList<Predicate>();
        
        switch(type){
            case PICK_UP:
                create_pick_up(params);
                break;
            case LEAVE:
                create_leave(params);
                break;
            case UNSTACK:
                create_unstack(params);
                break;
            case STACK:
                create_stack(params);
                break;
        }
    }
    
    private void create_pick_up(ArrayList<Block> params){
        ArrayList<Object> pars = new ArrayList<Object>();
        type = PICK_UP;
        
        // Add preconditions
        // **************************
        
        pars.clear();
        pars.add(new Integer(Predicate.N_VALUE));
        preconds.addPredicate(new Predicate(Predicate.USED_COLS_NUM, pars));
        
        pars.clear();
        pars.add(params.get(0));
        preconds.addPredicate(new Predicate(Predicate.ON_TABLE, pars));
        
        pars.clear();
        preconds.addPredicate(new Predicate(Predicate.FREE_ARM, pars));
        
        pars.clear();
        pars.add(params.get(0));
        preconds.addPredicate(new Predicate(Predicate.FREE, pars));
        
        // Add deletions
        // **************************
        
        pars.clear();
        pars.add(params.get(0));
        remove.add(new Predicate(Predicate.ON_TABLE, pars));
        
        pars.clear();
        remove.add(new Predicate(Predicate.FREE_ARM, pars));
        
        pars.clear();
        pars.add(new Integer(Predicate.N_VALUE));
        remove.add(new Predicate(Predicate.USED_COLS_NUM, pars));
        
        // Add additions
        // **************************
        
        pars.clear();
        pars.add(params.get(0));
        add.add(new Predicate(Predicate.PICKED_UP, pars));
        
        pars.clear();
        pars.add(new Integer(Predicate.N_DECREASED));
        add.add(new Predicate(Predicate.USED_COLS_NUM, pars));
    }
    
    private void create_leave(ArrayList<Block> params){
        ArrayList<Object> pars = new ArrayList<Object>();
        type = LEAVE;
        
        // Add preconditions
        // **************************
        
        pars.clear();
        pars.add(params.get(0));
        preconds.addPredicate(new Predicate(Predicate.PICKED_UP, pars));
        
        pars.clear();
        pars.add(new Integer(Predicate.N_VALUE));
        preconds.addPredicate(new Predicate(Predicate.USED_COLS_NUM, pars));
        
        // Add deletions
        // **************************
        
        pars.clear();
        pars.add(params.get(0));
        remove.add(new Predicate(Predicate.PICKED_UP, pars));
        
        pars.clear();
        pars.add(new Integer(Predicate.N_VALUE));
        remove.add(new Predicate(Predicate.USED_COLS_NUM, pars));
        
        // Add additions
        // **************************
        
        pars.clear();
        pars.add(params.get(0));
        add.add(new Predicate(Predicate.ON_TABLE, pars));
        
        pars.clear();
        pars.add(new Integer(Predicate.N_INCREASED));
        add.add(new Predicate(Predicate.USED_COLS_NUM, pars));
        
        pars.clear();
        add.add(new Predicate(Predicate.FREE_ARM, pars));
    }
    
    private void create_unstack(ArrayList<Block> params){
        ArrayList<Object> pars = new ArrayList<Object>();
        type = UNSTACK;
        
        // Add preconditions
        // **************************
        
        pars.clear();
        pars.add(params.get(0));
        pars.add(params.get(1));
        preconds.addPredicate(new Predicate(Predicate.ON, pars));
        
        pars.clear();
        pars.add(params.get(0));
        preconds.addPredicate(new Predicate(Predicate.FREE, pars));
        
        pars.clear();
        preconds.addPredicate(new Predicate(Predicate.FREE_ARM, pars));
        
        // Add deletions
        // **************************
        
        pars.clear();
        pars.add(params.get(0));
        pars.add(params.get(1));
        remove.add(new Predicate(Predicate.ON, pars));
        
        pars.clear();
        remove.add(new Predicate(Predicate.FREE_ARM, pars));
        
        // Add additions
        // **************************
        
        pars.clear();
        pars.add(params.get(0));
        add.add(new Predicate(Predicate.PICKED_UP, pars));
        
        pars.clear();
        pars.add(params.get(1));
        add.add(new Predicate(Predicate.FREE, pars));
    }
    
    private void create_stack(ArrayList<Block> params){
        ArrayList<Object> pars = new ArrayList<Object>();
        type = STACK;
        
        // Add preconditions
        // **************************
        
        pars.clear();
        pars.add(params.get(0));
        preconds.addPredicate(new Predicate(Predicate.PICKED_UP, pars));
        
        pars.clear();
        pars.add(params.get(1));
        preconds.addPredicate(new Predicate(Predicate.FREE, pars));
        
        pars.clear();
        pars.add(params.get(0));
        pars.add(params.get(1));
        preconds.addPredicate(new Predicate(Predicate.HEAVIER, pars));
        
        // Add deletions
        // **************************
        
        pars.clear();
        pars.add(params.get(0));
        remove.add(new Predicate(Predicate.PICKED_UP, pars));
        
        pars.clear();
        pars.add(params.get(1));
        remove.add(new Predicate(Predicate.FREE, pars));
        
        // Add additions
        // **************************
        
        pars.clear();
        pars.add(params.get(0));
        pars.add(params.get(1));
        add.add(new Predicate(Predicate.ON, pars));
        
        pars.clear();
        add.add(new Predicate(Predicate.FREE_ARM, pars));
    }
    
    
    // * ** OPERATORS
    // * ******************************************
    
    public void apply(State s){
        
    }
}
