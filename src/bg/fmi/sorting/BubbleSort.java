package bg.fmi.sorting;

import java.util.ArrayList;
import javax.swing.SwingUtilities;

/**
 *
 * @author Todor-PC
 */
public class BubbleSort extends Sort {

    @Override
    public void sort(int[] numArray) {
        if (checkIfSorted(numArray)) {
            if (onSortFinished != null) {
                onSortFinished.sortFinished();
            }
            return;
        }

        t = new Thread(() -> {

            int n = numArray.length;
            int temp = 0;

            for (int i = 0; i < n; i++) {

                for (int j = 1; j < (n - i); j++) {

                    ArrayList<Integer> red_column = new ArrayList<>();
                    ArrayList<Integer> green_column = new ArrayList<>();

                    green_column.add(j - 1);
                    red_column.add(j);

                    SwingUtilities.invokeLater(() -> {
                        if (onIteration != null) {
                            onIteration.iteration(numArray, red_column, green_column);
                        }
                    });

                    if (numArray[j - 1] > numArray[j]) {
                        temp = numArray[j - 1];
                        numArray[j - 1] = numArray[j];
                        numArray[j] = temp;
                    }

                    try {
                        Thread.sleep(speed);
                    } catch (InterruptedException ex) {
                    }
                }

            }

            if (onSortFinished != null) {
                onSortFinished.sortFinished();
            }
        });

        t.start();

    }
}
