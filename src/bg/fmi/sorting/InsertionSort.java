/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bg.fmi.sorting;

import java.util.ArrayList;
import javax.swing.SwingUtilities;

/**
 *
 * @author Todor-PC
 */
public class InsertionSort extends Sort {

    @Override
    public void sort(int[] num) {

        if (checkIfSorted(num)) {
            if (onSortFinished != null) {
                onSortFinished.sortFinished();
            }
            return;
        }

        t = new Thread(() -> {

            int j;                     // the number of items sorted so far
            int key;                // the item to be inserted
            int i;

            for (j = 1; j < num.length; j++) // Start with 1 (not 0)
            {
                key = num[j];
                for (i = j - 1; (i >= 0) && (num[i] > key); i--) // Smaller values are moving up
                {

                    num[i + 1] = num[i];

                    ArrayList<Integer> red_column = new ArrayList<>();
                    ArrayList<Integer> green_column = new ArrayList<>();

                    green_column.add(i);
                    red_column.add(j);

                    SwingUtilities.invokeLater(() -> {
                        if (onIteration != null) {
                            onIteration.iteration(num, red_column, green_column);
                        }
                    });

                    try {
                        Thread.sleep(speed);
                    } catch (InterruptedException ex) {
                    }

                }
                num[i + 1] = key;    // Put the key in its proper location
            }

            if (onSortFinished != null) {
                onSortFinished.sortFinished();
            }
        });

        t.start();

    }

}
