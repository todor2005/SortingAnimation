package bg.fmi.sorting;

import java.util.ArrayList;
import javax.swing.SwingUtilities;

/**
 *
 * @author Todor-PC
 */
public class SelectionSort extends Sort {

    @Override
    public void sort(int[] arr) {

        if (checkIfSorted(arr)) {
            if (onSortFinished != null) {
                onSortFinished.sortFinished();
            }
            return;
        }

        t = new Thread(() -> {
            for (int i = 0; i < arr.length - 1; i++) {
                int index = i;
                for (int j = i + 1; j < arr.length; j++) {
                    if (arr[j] < arr[index]) {
                        index = j;
                    }

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

                int smallerNumber = arr[index];
                arr[index] = arr[i];
                arr[i] = smallerNumber;

            }

            if (onSortFinished != null) {
                onSortFinished.sortFinished();
            }
        });

        t.start();

    }
}
