package bg.fmi;

import bg.fmi.interfaces.OnIteration;
import bg.fmi.interfaces.OnRepaintListener;
import bg.fmi.sorting.BubbleSort;
import bg.fmi.interfaces.OnSortFinished;
import bg.fmi.sorting.BitonicSort;
import bg.fmi.sorting.CocktailSort;
import bg.fmi.sorting.GnomeSort;
import bg.fmi.sorting.HeapSort;
import bg.fmi.sorting.InsertionSort;
import bg.fmi.sorting.MergeSort;
import bg.fmi.sorting.QuickSort;
import bg.fmi.sorting.RadixSort;
import bg.fmi.sorting.SelectionSort;
import bg.fmi.sorting.ShellSort;
import bg.fmi.sorting.Sort;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JPanel;

/**
 *
 * @author Todor-PC
 */
public class AnimationPanel extends JPanel {

    public AnimationPanel() {
        init();
    }

    public void init() {
        setBackground(new Color(128, 222, 234));

        makeRandom(50);

    }

    public void makeRandom(int lenght) {

        ArrayList<Integer> array = new ArrayList<>();
        for (int i = 0; i < lenght; i++) {
            array.add(i + 1);
        }

        Collections.shuffle(array);

        numArray = new int[array.size()];
        for (int i = 0; i < array.size(); i++) {
            numArray[i] = array.get(i);
        }

        repaint();
    }

    private Sort sorter = null;

    public void startBubbleSotr() {
        sorter = new BubbleSort();
        startSorting();
    }

    public void startSelectionSotr() {
        sorter = new SelectionSort();
        startSorting();
    }

    public void startInsertionSotr() {
        sorter = new InsertionSort();
        startSorting();
    }

    public void startShellSotr() {
        sorter = new ShellSort();
        startSorting();
    }

    public void startHeapSort() {
        sorter = new HeapSort();
        startSorting();
    }

    public void startMergeSotr() {
        sorter = new MergeSort();
        startSorting();
    }

    public void startQuickSotr() {
        sorter = new QuickSort();
        startSorting();
    }
    
    public void startCocktailSotr() {
        sorter = new CocktailSort();
        startSorting();
    }
    
    public void startRadixSotr() {
        sorter = new RadixSort();
        startSorting();
    }
        
    public void startBitonicSotr() {
        sorter = new BitonicSort();
        startSorting();
    }
    
    public void startGnomeSotr(){
        sorter = new GnomeSort();
        startSorting();
    }

    public void stopSorting( boolean stop ) {
        if( stop ){
            sorter.stopSorting();
        }
        sorter = null;
        red_column.clear();
        green_column.clear();
        repaint();
        if (onSortFinished != null) {
            onSortFinished.sortFinished();
        }
    }

    private int counter = 0;

    private void startSorting() {

        counter = 0;

        sorter.setSpeed(speed);
        sorter.setOnIteration((int[] array, ArrayList<Integer> red_columns, ArrayList<Integer> green_columns) -> {
            numArray = array;
            red_column = red_columns;
            green_column = green_columns;
            counter++;

            if (onRepaintListener != null) {
                onRepaintListener.onRepaint(counter);
            }

            repaint();
        });
        sorter.setOnSortFinished(() -> {
            stopSorting( false );
        });
        sorter.sort(numArray);
    }

    private OnRepaintListener onRepaintListener;

    public void setOnRepaintListener(OnRepaintListener onRepaintListener) {
        this.onRepaintListener = onRepaintListener;
    }

    private OnSortFinished onSortFinished;

    public void setOnSortFinished(OnSortFinished onSortFinished) {
        this.onSortFinished = onSortFinished;
    }

    private ArrayList<Integer> red_column = new ArrayList<>();
    private ArrayList<Integer> green_column = new ArrayList<>();

    private int speed;

    public void setSpeed(int speed) {
        this.speed = speed;
        if (sorter != null) {
            sorter.setSpeed(speed);
        }
    }

    private int[] numArray = new int[0];

    private int calculateHeight(int index) {
        double h = getHeight();
        double l = numArray.length;
        double value = numArray[index];
        double d = (h / l) * value;

        int height = (int) d;

        return height;
    }

    private int calcualteWidth() {

        double w = getWidth();
        double l = numArray.length;

        double d = w / l;

        int width = (int) d;

        return width;

    }
    
    private Color getColor( int index ){
        double d = index;
        double l = numArray.length;
        double koef = l /d;
        int c = (int)(255d/koef);
        Color color = new Color(c, 255, 0);
        return color;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        for (int i = 0; i < numArray.length; i++) {

            int height = calculateHeight(i);
            int width = calcualteWidth();

            if (red_column.contains(i)) {
                g.setColor(new Color(229, 115, 115));//red
            } else if (green_column.contains(i)) {
                g.setColor(new Color(76, 175, 80));//green
            } else {
                Color c = getColor( numArray[i] );
                g.setColor(c);
//                g.setColor(new Color(255, 241, 118));//yellow
            }

            g.fillRect(i * width, getHeight()-height, width - 2, height);

        }

    }

}
