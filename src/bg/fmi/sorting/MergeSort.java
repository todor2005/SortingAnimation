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
public class MergeSort extends Sort {

    private int[] array;
    private int[] tempMergArr;
    private int length;

    @Override
    public void sort(int inputArr[]) {

        if (checkIfSorted(inputArr)) {
            if (onSortFinished != null) {
                onSortFinished.sortFinished();
            }
            return;
        }

        t = new Thread(() -> {
            this.array = inputArr;
            this.length = inputArr.length;
            this.tempMergArr = new int[length];
            doMergeSort(0, length - 1);

            if (onSortFinished != null) {
                onSortFinished.sortFinished();
            }
        });

        t.start();
    }

    private void doMergeSort(int lowerIndex, int higherIndex) {

        if (lowerIndex < higherIndex) {
            int middle = lowerIndex + (higherIndex - lowerIndex) / 2;
            // Below step sorts the left side of the array
            doMergeSort(lowerIndex, middle);
            // Below step sorts the right side of the array
            doMergeSort(middle + 1, higherIndex);
            // Now merge both sides
            mergeParts(lowerIndex, middle, higherIndex);
        }
    }

    private void mergeParts(int lowerIndex, int middle, int higherIndex) {

        for (int i = lowerIndex; i <= higherIndex; i++) {
            tempMergArr[i] = array[i];
        }
        int i = lowerIndex;
        int j = middle + 1;
        int k = lowerIndex;
        while (i <= middle && j <= higherIndex) {
            if (tempMergArr[i] <= tempMergArr[j]) {
                array[k] = tempMergArr[i];
                i++;
            } else {
                array[k] = tempMergArr[j];
                j++;
            }
            k++;

            ArrayList<Integer> red_column = new ArrayList<>();
            ArrayList<Integer> green_column = new ArrayList<>();

            green_column.add(j);

            red_column.add(k);

            SwingUtilities.invokeLater(() -> {
                if (onIteration != null) {
                    onIteration.iteration(array, red_column, green_column);
                }
            });

            try {
                Thread.sleep(speed);
            } catch (InterruptedException ex) {
            }
        }
        while (i <= middle) {
            array[k] = tempMergArr[i];
            k++;
            i++;

            ArrayList<Integer> red_column = new ArrayList<>();
            ArrayList<Integer> green_column = new ArrayList<>();

            red_column.add(k);

            SwingUtilities.invokeLater(() -> {
                if (onIteration != null) {
                    onIteration.iteration(array, red_column, green_column);
                }
            });

            try {
                Thread.sleep(speed);
            } catch (InterruptedException ex) {
            }
        }

    }
}
