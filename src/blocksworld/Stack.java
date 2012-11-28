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
public class Stack {
    private ArrayList<Block> blocks;
    
    // Empty stack constructor
    public Stack(){
        this.blocks = new ArrayList<Block>();
    }
    
    // Clone constructor
    public Stack(Stack stack){
        this.blocks = (ArrayList<Block>)stack.blocks.clone();
    }
    
    // * ** SETTER METHODS
    // * ******************************************
    
    public void clear(){
        this.blocks.clear();
    }
    
    public Stack clone(){
        return new Stack(this);
    }
    
    public void stackBlock(Block block){
        this.blocks.add(block);
    }
    
    public Block unstackBlock(){
        Block ret = this.blocks.remove(this.blocks.size()-1);
        return ret;
    }
}
