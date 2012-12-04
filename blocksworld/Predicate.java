/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package blocksworld;

import java.util.ArrayList;

public class Predicate {
    public static final int ON_TABLE = 1;
    public static final int ON = 2;
    public static final int FREE = 3;
    public static final int FREE_ARM = 4;
    public static final int PICKED_UP = 5;
    public static final int USED_COLS_NUM = 6;
    public static final int HEAVIER = 7;
    
    public static final int N_VALUE = 0;
    public static final int N_DECREASED = -1;
    public static final int N_INCREASED = -2;
    
    private static final String predicate_names[] = {
        "on-table",
        "on",
        "free",
        "free-arm",
        "picked-up",
        "used-cols-num",
        "heavier"
    };
    
    
    int type;
    Object param1;
    Object param2;
    private boolean is_valid;
    
    // * ** INSTANCE INITIALIZATION
    // * ******************************************
    
    public Predicate(String pred, ArrayList<Object> params){
        is_valid = false;
        
        if(pred.equals("on-table")){
            set_on_table(params);
        }else if(pred.equals("on")){
            set_on(params);
        }else if(pred.equals("free")){
            set_free(params);
        }else if(pred.equals("free-arm")){
            set_free_arm(params);
        }else if(pred.equals("picked-up")){
            set_picked_up(params);
        }else if(pred.equals("used-cols-num")){
            set_used_cols_num(params);
        }else if(pred.equals("heavier")){
            set_heavier(params);
        }else{
            System.out.println("ERROR: Statement '" + pred + "' is not recognized");
        }
    }
    
    public Predicate(int pred, ArrayList<Object> params){
        is_valid = false;
        
        switch(pred){
            case ON_TABLE:
                set_on_table(params);
                break;   
            case ON:
                set_on(params);
                break;
            case FREE:
                set_free(params);
                break;
            case FREE_ARM:
                set_free_arm(params);
                break;
            case PICKED_UP:
                set_picked_up(params);
                break;
            case USED_COLS_NUM:
                set_used_cols_num(params);
                break;
            case HEAVIER:
                set_heavier(params);
                break;
            default:
                System.out.println("ERROR: Statement '" + pred + "' is not recognized");
                break;
        }
    }
    
    public Predicate(Predicate p){
        this.type = p.type;
        this.param1 = p.param1;
        this.param2 = p.param2;
        this.is_valid = p.is_valid;
    }
    
    private void set_on_table(ArrayList<Object> params){
        if(params.size() != 1){
            System.out.println("ERROR: Wrong number of parameters for 'on-table' predicate (must have one)");
            return;
        }

        if(!(params.get(0) instanceof Block)){
            System.out.println("ERROR: First parameter for 'on-table' predicate must be a block");
            return;
        }

        this.type = ON_TABLE;
        this.param1 = params.get(0);
        is_valid = true;
    }
    
    private void set_on(ArrayList<Object> params){
        if(params.size() != 2){
            System.out.println("ERROR: Wrong number of parameters for 'on' predicate (must have two)");
            return;
        }

        if(!(params.get(0) instanceof Block)){
            System.out.println("ERROR: First parameter for 'on' predicate must be a block");
            return;
        }

        if(!(params.get(1) instanceof Block)){
            System.out.println("ERROR: Second parameter for 'on' predicate must be a block");
            return;
        }

        this.type = ON;
        this.param1 = params.get(0);
        this.param1 = params.get(1);
        is_valid = true;
    }
    
    private void set_free(ArrayList<Object> params){
        if(params.size() != 1){
            System.out.println("ERROR: Wrong number of parameters for 'free' predicate (must have one)");
            return;
        }

        if(!(params.get(0) instanceof Block)){
            System.out.println("ERROR: First parameter for 'free' predicate must be a block");
            return;
        }

        this.type = FREE;
        this.param1 = params.get(0);
        is_valid = true;
    }
    
    private void set_free_arm(ArrayList<Object> params){
        if(params.size() != 0){
            System.out.println("ERROR: Wrong number of parameters for 'free-arm' predicate (must have none)");
            return;
        }

        this.type = FREE_ARM;
        is_valid = true;
    }
    
    private void set_picked_up(ArrayList<Object> params){
        if(params.size() != 1){
            System.out.println("ERROR: Wrong number of parameters for 'picked-up' predicate (must have one)");
            return;
        }

        if(!(params.get(0) instanceof Block)){
            System.out.println("ERROR: First parameter for 'picked-up' predicate must be a block");
            return;
        }

        this.type = PICKED_UP;
        this.param1 = params.get(0);
        is_valid = true;
    }
    
    private void set_used_cols_num(ArrayList<Object> params){
        if(params.size() != 1){
            System.out.println("ERROR: Wrong number of parameters for 'used-cols-num' predicate (must have one)");
            return;
        }

        if(!(params.get(0) instanceof Integer)){
            System.out.println("ERROR: First parameter for 'used-cols-num' predicate must be an integer");
            return;
        }
        
        int i = ((Integer)params.get(0)).intValue();
        if(i<-1 || i>3){
            System.out.println("ERROR: Number of used columns must be between -1 and 3");
            return;
        }

        this.type = USED_COLS_NUM;
        this.param1 = params.get(0);
        is_valid = true;
    }
    
    private void set_heavier(ArrayList<Object> params){
        if(params.size() != 2){
            System.out.println("ERROR: Wrong number of parameters for 'heavier' predicate (must have two)");
            return;
        }

        if(!(params.get(0) instanceof Block)){
            System.out.println("ERROR: First parameter for 'heavier' predicate must be a block");
            return;
        }

        if(!(params.get(1) instanceof Block)){
            System.out.println("ERROR: Second parameter for 'heavier' predicate must be a block");
            return;
        }

        this.type = HEAVIER;
        this.param1 = params.get(0);
        this.param1 = params.get(1);
        is_valid = true;
    }
    
    // * ** GETTER METHODS
    // * ******************************************
    
    public int getType(){
        return this.type;
    }
    
    public String getTypeName(){
        if(this.type <= 0) return null;
        return predicate_names[this.type - 1];
    }
    
    // * ** OPERATORS
    // * ******************************************
    
    public Predicate clone(){
        return new Predicate(this);
    }
    
    public boolean equals(Predicate p){
        if(this.type != p.type) return false;
        
        if(this.param1 == null && p.param1 != null) return false;  
        if(this.param1 instanceof Predicate){
            if(this.param1 != p.param1) return false;
        }else{
            int v1 = ((Integer)this.param1).intValue();
            int v2 = ((Integer)p.param1).intValue();
            if(v1 != v2) return false;
        }
        
        if(this.param2 == null && p.param2 != null) return false;  
        if(this.param2 instanceof Predicate){
            if(this.param2 != p.param2) return false;
        }else{
            int v1 = ((Integer)this.param2).intValue();
            int v2 = ((Integer)p.param2).intValue();
            if(v1 != v2) return false;
        }
        
        return true;
    }
    
    public boolean isValid(){
        return is_valid;
    }
    
    public boolean isInstanced(){
        if(type != USED_COLS_NUM) return true;
        int v = ((Integer)param1).intValue();
        
        if(v < 1) return false;
        return true;
    }
}
