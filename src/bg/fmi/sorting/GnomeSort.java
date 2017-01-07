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
public class GnomeSort extends Sort {

    @Override
    public void sort(int[] a) {

        if (checkIfSorted(a)) {
            if (onSortFinished != null) {
                onSortFinished.sortFinished();
            }
            return;
        }

        t = new Thread(() -> {

            int i = 1;
            int j = 2;

            while (i < a.length) {
                if (a[i - 1] <= a[i]) {
                    i = j;
                    j++;
                } else {
                    int tmp = a[i - 1];
                    a[i - 1] = a[i];
                    a[i--] = tmp;
                    i = (i == 0) ? j++ : i;
                }

                ArrayList<Integer> red_column = new ArrayList<>();
                ArrayList<Integer> green_column = new ArrayList<>();

                green_column.add(i);
                red_column.add(j);

                SwingUtilities.invokeLater(() -> {
                    if (onIteration != null) {
                        onIteration.iteration(a, red_column, green_column);
                    }
                });

                try {
                    Thread.sleep(speed);
                } catch (InterruptedException ex) {
                }

            }

            if (onSortFinished != null) {
                onSortFinished.sortFinished();
            }
        });

        t.start();
    }

}
