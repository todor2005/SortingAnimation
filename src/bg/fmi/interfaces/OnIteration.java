package bg.fmi.interfaces;

import java.util.ArrayList;

/**
 *
 * @author Todor-PC
 */
public interface OnIteration {
    
    public void iteration( int[] numArray , ArrayList<Integer> red_columns, ArrayList<Integer> green_column );
    
}
