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
public class HeapSort extends Sort {

    @Override
    public void sort(int[] arr) {

        if (checkIfSorted(arr)) {
            if (onSortFinished != null) {
                onSortFinished.sortFinished();
            }
            return;
        }

        t = new Thread(() -> {

            heapify(arr);
            for (int i = N; i > 0; i--) {
                swap(arr, 0, i);
                N = N - 1;
                maxheap(arr, 0);
            }

            if (onSortFinished != null) {
                onSortFinished.sortFinished();
            }
        });

        t.start();

    }

    private static int N;

    /* Function to build a heap */
    public void heapify(int arr[]) {
        N = arr.length - 1;
        for (int i = N / 2; i >= 0; i--) {
            maxheap(arr, i);
        }
    }

    /* Function to swap largest element in heap */
    public void maxheap(int arr[], int i) {
        int left = 2 * i;
        int right = 2 * i + 1;
        int max = i;
        if (left <= N && arr[left] > arr[i]) {
            max = left;
        }
        if (right <= N && arr[right] > arr[max]) {
            max = right;
        }

        if (max != i) {
            swap(arr, i, max);
            maxheap(arr, max);
        }
    }

    /* Function to swap two numbers in an array */
    public void swap(int arr[], int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;

        ArrayList<Integer> red_column = new ArrayList<>();
        ArrayList<Integer> green_column = new ArrayList<>();

        green_column.add(i);
        red_column.add(j);

        SwingUtilities.invokeLater(() -> {
            if (onIteration != null) {
                onIteration.iteration(arr, red_column, green_column);
            }
        });

        try {
            Thread.sleep(speed);
        } catch (InterruptedException ex) {
        }
    }

}
