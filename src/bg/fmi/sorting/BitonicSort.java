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
public class BitonicSort extends Sort {

    @Override
    public void sort(int[] b) {

        if (checkIfSorted(b)) {
            if (onSortFinished != null) {
                onSortFinished.sortFinished();
            }
            return;
        }

        t = new Thread(() -> {

            this.a = b;
            bitonicSort(0, b.length, ASCENDING);

            if (onSortFinished != null) {
                onSortFinished.sortFinished();
            }
        });

        t.start();

    }

    private final static boolean ASCENDING = true, DESCENDING = false;
    public int[] a;

//    /**
//     * When called with parameters lo = 0, cnt = a.length() and dir = ASCENDING,
//     * procedure bitonicSort sorts the whole array a.
//     *
//     */
//    private BitonicSort(int b[])
//    {
//        this.a = b;
//        bitonicSort(0, a.length, ASCENDING);
//    }
    /**
     * Procedure bitonicSort first produces a bitonic sequence by recursively
     * sorting its two halves in opposite directions, and then calls
     * bitonicMerge.
     *
     */
    private void bitonicSort(int lo, int cnt, boolean dir) {
        if (cnt > 1) {
            int k = cnt / 2;
            bitonicSort(lo, k, ASCENDING);
            bitonicSort(lo + k, k, DESCENDING);
            bitonicMerge(lo, cnt, dir);
        }
    }

    /**
     * The procedure bitonicMerge recursively sorts a bitonic sequence in
     * ascending order, if dir = ASCENDING, and in descending order otherwise.
     * The sequence to be sorted starts at index position lo, the number of
     * elements is cnt.
     *
     */
    private void bitonicMerge(int lo, int cnt, boolean dir) {
        if (cnt > 1) {
            int k = cnt / 2;
            int i;
            for (i = lo; i < lo + k; i++) {
                compare(i, i + k, dir);
            }
            bitonicMerge(lo, k, dir);
            bitonicMerge(lo + k, k, dir);
        }
    }

    /**
     * A comparator is modelled by the procedure compare, where the parameter
     * dir indicates the sorting direction. If dir is ASCENDING and a[i] > a[j]
     * is true or dir is DESCENDING and a[i] > a[j] is false then a[i] and a[j]
     * are interchanged.
     *
     */
    private void compare(int i, int j, boolean dir) {
        if (dir == (a[i] > a[j])) {
            int h = a[i];
            a[i] = a[j];
            a[j] = h;
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

}
