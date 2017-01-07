package bg.fmi.sorting;

import bg.fmi.interfaces.OnIteration;
import bg.fmi.interfaces.OnSortFinished;

/**
 *
 * @author Todor-PC
 */
public abstract class Sort {

    public abstract void sort(int[] numArray);
    
    protected Thread t;
    
    public void stopSorting() {
        if (t != null) {
            if (t.isAlive()) {
                t.stop();
            }
        }

        if (onSortFinished != null) {
            onSortFinished.sortFinished();
        }
    }

    protected boolean checkIfSorted(int[] numArray) {
        boolean sorted = true;

        for (int i = 0; i < numArray.length - 1; i++) {
            if (numArray[i + 1] < numArray[i]) {
                sorted = false;
                break;
            }
        }

        return sorted;
    }

    protected int speed = 20;

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    protected OnIteration onIteration;

    public void setOnIteration(OnIteration onIteration) {
        this.onIteration = onIteration;
    }

    protected OnSortFinished onSortFinished;

    public void setOnSortFinished(OnSortFinished onSortFinished) {
        this.onSortFinished = onSortFinished;
    }

}
