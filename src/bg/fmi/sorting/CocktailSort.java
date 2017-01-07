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
public class CocktailSort extends Sort {

    @Override
    public void sort(int[] A) {

        if (checkIfSorted(A)) {
            if (onSortFinished != null) {
                onSortFinished.sortFinished();
            }
            return;
        }

        t = new Thread(() -> {

            boolean swapped;
            do {
                swapped = false;
                for (int i = 0; i <= A.length - 2; i++) {
                    if (A[i] > A[i + 1]) {
                        //test whether the two elements are in the wrong order
                        int temp = A[i];
                        A[i] = A[i + 1];
                        A[i + 1] = temp;
                        swapped = true;

                        ArrayList<Integer> red_column = new ArrayList<>();
                        ArrayList<Integer> green_column = new ArrayList<>();

                        green_column.add(i);
                        red_column.add(i + 1);

                        SwingUtilities.invokeLater(() -> {
                            if (onIteration != null) {
                                onIteration.iteration(A, red_column, green_column);
                            }
                        });

                        try {
                            Thread.sleep(speed);
                        } catch (InterruptedException ex) {
                        }

                    }
                }
                if (!swapped) {
                    //we can exit the outer loop here if no swaps occurred.
                    break;
                }
                swapped = false;
                for (int i = A.length - 2; i >= 0; i--) {
                    if (A[i] > A[i + 1]) {
                        int temp = A[i];
                        A[i] = A[i + 1];
                        A[i + 1] = temp;
                        swapped = true;

                        ArrayList<Integer> red_column = new ArrayList<>();
                        ArrayList<Integer> green_column = new ArrayList<>();

                        green_column.add(i);
                        red_column.add(i + 1);

                        SwingUtilities.invokeLater(() -> {
                            if (onIteration != null) {
                                onIteration.iteration(A, red_column, green_column);
                            }
                        });

                        try {
                            Thread.sleep(speed);
                        } catch (InterruptedException ex) {
                        }

                    }
                }
                //if no elements have been swapped, then the list is sorted
            } while (swapped);

            if (onSortFinished != null) {
                onSortFinished.sortFinished();
            }
        });

        t.start();
    }

}
