/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bg.fmi.sorting;

import java.util.ArrayList;
import java.util.List;
import javax.swing.SwingUtilities;

/**
 *
 * @author Todor-PC
 */
public class RadixSort extends Sort {

    @Override
    public void sort(int[] input) {

        if (checkIfSorted(input)) {
            if (onSortFinished != null) {
                onSortFinished.sortFinished();
            }
            return;
        }

        t = new Thread(() -> {

            final int RADIX = 10;
            // declare and initialize bucket[]
            List<Integer>[] bucket = new ArrayList[RADIX];
            for (int i = 0; i < bucket.length; i++) {
                bucket[i] = new ArrayList<Integer>();
            }

            // sort
            boolean maxLength = false;
            int tmp = -1, placement = 1;
            while (!maxLength) {
                maxLength = true;
                // split input between lists
                for (Integer i : input) {
                    tmp = i / placement;
                    bucket[tmp % RADIX].add(i);
                    if (maxLength && tmp > 0) {
                        maxLength = false;
                    }
                }
                // empty lists into input array
                int a = 0;
                for (int b = 0; b < RADIX; b++) {
                    for (Integer i : bucket[b]) {

                        input[a++] = i;

                        ArrayList<Integer> red_column = new ArrayList<>();
                        ArrayList<Integer> green_column = new ArrayList<>();

//                        green_column.add(a);
                    red_column.add(a);

                        SwingUtilities.invokeLater(() -> {
                            if (onIteration != null) {
                                onIteration.iteration(input, red_column, green_column);
                            }
                        });

                        try {
                            Thread.sleep(speed);
                        } catch (InterruptedException ex) {
                        }
                    }
                    bucket[b].clear();
                }
                // move to next digit
                placement *= RADIX;
            }

            if (onSortFinished != null) {
                onSortFinished.sortFinished();
            }
        });

        t.start();

    }
}
