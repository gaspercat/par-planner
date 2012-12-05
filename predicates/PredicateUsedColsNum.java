/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package predicates;

import blocksworld.Block;
import java.util.ArrayList;

/**
 *
 * @author gaspercat
 */
public class PredicateUsedColsNum extends Predicate{
    public static final int N_UNDEFINED = 0;
    public static final int N_DECREASED = -1;
    public static final int N_INCREASED = -2; 
    
    private int paramN;
    
    public PredicateUsedColsNum(int n){
        super(Predicate.USED_COLS_NUM);
        this.paramN = n;
    }
    
    protected PredicateUsedColsNum(ArrayList<Object> params){
        super(Predicate.USED_COLS_NUM);
        
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

        this.paramN = i;
    }
    
    private PredicateUsedColsNum(PredicateUsedColsNum pred){
        super((Predicate)pred);
        this.paramN = pred.paramN;
    }
    
    @Override
    public String getTypeName(){
        return "used-cols-num";
    }
    
    @Override
    public boolean isInstanced(){
        return this.paramN > 0;
    }
    
    @Override
    public void setN(int n){
        this.paramN = n;
    }
    
    @Override
    public boolean equals(Predicate pred){
        if(!(pred instanceof PredicateUsedColsNum)) return false;
        
        PredicateUsedColsNum p = (PredicateUsedColsNum)pred;
        boolean ret = true;
        
        ret = ret && (this.paramN == p.paramN);
        
        return ret;
    }
    
    @Override
    public Predicate clone(){
        Predicate ret = new PredicateUsedColsNum(this);
        return ret;
    }
}
