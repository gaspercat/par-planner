/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package blocksworld;

/**
 *
 * @author gaspercat
 */
public class Block {
    private String name;

    // * ** CONSTRUCTORS
    // * ******************************************
    
    public Block(String name){
        this.name = name;
    }
    
    // * ** GETTERS
    // * ******************************************
    
    public String getName(){
        return this.name;
    }
}
