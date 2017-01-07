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
public class ShellSort extends Sort {

    @Override
    public void sort(int[] array) {

        if (checkIfSorted(array)) {
            if (onSortFinished != null) {
                onSortFinished.sortFinished();
            }
            return;
        }

        t = new Thread(() -> {

            int inner, outer;
            int temp;

            int h = 1;
            while (h <= array.length / 3) {
                h = h * 3 + 1;
            }
            while (h > 0) {
                for (outer = h; outer < array.length; outer++) {
                    temp = array[outer];
                    inner = outer;

                    while (inner > h - 1 && array[inner - h] >= temp) {

                        ArrayList<Integer> red_column = new ArrayList<>();
                        ArrayList<Integer> green_column = new ArrayList<>();

                        green_column.add(inner);
                        red_column.add(inner - h);

                        SwingUtilities.invokeLater(() -> {
                            if (onIteration != null) {
                                onIteration.iteration(array, red_column, green_column);
                            }
                        });

                        try {
                            Thread.sleep(speed);
                        } catch (InterruptedException ex) {
                        }

                        array[inner] = array[inner - h];
                        inner -= h;

                    }
                    array[inner] = temp;
                }
                h = (h - 1) / 3;
            }

            if (onSortFinished != null) {
                onSortFinished.sortFinished();
            }
        });

        t.start();
    }

}
