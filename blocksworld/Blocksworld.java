package blocksworld;

// Load imports for file reading
import java.io.File;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

import java.util.regex.Matcher;
import operators.Operator;
import predicates.Predicate;

public class Blocksworld {
    private static ArrayList<Block> blocks;
    private static State state_initial;
    private static State state_goal;
    
    public static void main(String[] args) {
        // Initialize blocks list
        blocks = new ArrayList<Block>();
        
        // Create initial and final states
        state_initial = new State();
        state_goal = new State();
        
        // Load initial state of the algorithm
        if(args.length == 0){
            System.out.println();
        }else if(args.length > 0){
            for(String arg: args){
                if(!parse_file(arg)) return;
            }
        }
        
        // Launch algorithm
        execute(state_initial, state_goal);
    }
    
    private static void execute(State initial, State goal){
        Algorithm alg = new Algorithm();
        alg.run(initial, goal);
        
        // Show result
        ArrayList<Operator> plan = alg.getPlan();
        for(Operator op: plan){
            System.out.println(plan.toString());
        }
    }
    
    // * ** INITIAL STATE FILE LOADER
    // * ******************************************
    
    private static boolean parse_file(String fpath){
        String input = read_file(fpath);
        if(input == null) return false;
        
        int op = 0;
        Predicate pred;
        
        // Format input
        input = input.replaceAll("\n", "");  
        input = input.replaceAll("\r", "");
        input = input.replaceAll(" ", "");
        input = input.toLowerCase();
        
        // Parse file statements
        Matcher m = Pattern.compile("([a-z0-9\\-(),_=]+);").matcher(input);
        while(m.find()){
            String elem = m.group(1);
            
            String[] subparts = elem.split("=");
            // If equality, set operator
            if(subparts.length == 2){
                if(subparts[0].equals("blocks")){
                    op = 1;
                }else if(subparts[0].equals("initial_state")){
                    op = 2;
                }else if(subparts[0].equals("goal_state")){
                    op = 3;
                }
                
                subparts[0] = subparts[1];
            }
            
            // Parse element of operator
            switch(op){
                case 1:
                    parse_blocks(subparts[0]);
                    break;
                    
                case 2:
                    pred = parse_predicate(subparts[0]);
                    if(pred == null) return false;
                    state_initial.addPredicate(pred);
                    break;
                    
                case 3: 
                    pred = parse_predicate(subparts[0]);
                    if(pred == null) return false;
                    state_goal.addPredicate(pred);
                    break;
            }
        }
        
        // Set number of used columns on states
        state_initial.setUsedColsNum();
        state_goal.setUsedColsNum();
        
        return true;
    }
    
    private static void parse_blocks(String bcks){
        String[] bl_names = bcks.split(",");
        for(String bl: bl_names){
            blocks.add(new Block(bl));
        }
    }
    
    private static Predicate parse_predicate(String pred){
        ArrayList<Object> params = new ArrayList<Object>();
        String predicate;
        
        String[] parts = pred.split("[(]");
        predicate = parts[0];
        
        if(parts.length > 1){
            parts = parts[1].replace(")", "").split(",");
            for(String par: parts){
                if(par.matches("\\d+")){
                    params.add(new Integer(Integer.parseInt(par)));
                }else{
                    Block bl = getBlock(par);
                    if(bl == null){
                        System.out.println("ERROR: Block '" + par + "' not defined");
                        return null;
                    }
                    
                    params.add(bl);
                }
            }
        }
        
        Predicate ret = Predicate.create(predicate, params);
        return (ret.isValid()) ? ret : null;
    }
    
    private static String read_file(String fpath){
        File file = new File(fpath);
        Reader reader;
        
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            
            char[] buffer = new char[1024];
            reader.read(buffer);
            reader.close();
            
            return new String(buffer);
        } catch (FileNotFoundException ex) {
            System.out.println("ERROR: Input file '" + fpath + "' not found");
        } catch (IOException ex){
            System.out.println("ERROR: There was an I/O problem reading the file");
        }
        
        return null;
    }
    
    // * ** HELPER METHODS
    // * ******************************************
    
    private static Block getBlock(String name){
        for(Block bl: blocks){
            if(bl.getName().equals(name)) return bl;
        }
        
        return null;
    }
}
