package sortingApp.apurv.com.sortingApp.model;

import java.util.ArrayList;
import java.util.List;

import sortingApp.apurv.com.sortingApp.activity.BucketSortFragment;
import sortingApp.apurv.com.sortingApp.custom.Sorter;

/**
 * Created by apurv on 3/1/16.
 */

public class BucketSort implements Sorter {
    private static final int DEFAULT_BUCKET_SIZE = 5;
    private List<List<Integer>> buckets;
    private BucketSortFragment sortObserver;


    public BucketSort(BucketSortFragment observer){
        sortObserver = observer;

    }
    public  Integer[] sort(Integer[] array){
        int bucketSize = DEFAULT_BUCKET_SIZE;
        if (array.length == 0) {
            return null;
        }

        // Determine minimum and maximum values
        Integer minValue = array[0];
        Integer maxValue = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] < minValue) {
                minValue = array[i];
            } else if (array[i] > maxValue) {
                maxValue = array[i];
            }
        }

        // Initialise buckets
        int bucketCount = (maxValue - minValue) / bucketSize + 1;
        buckets = new ArrayList<List<Integer>>(bucketCount);
        for (int i = 0; i < bucketCount; i++) {
            buckets.add(new ArrayList<Integer>());
        }



        // Distribute input array values into buckets
        for (int i = 0; i < array.length; i++) {
            int destBucket = (array[i] - minValue) / bucketSize;
            sortObserver.OnArrayUpdate(array[i], destBucket);
            buckets.get((array[i] - minValue) / bucketSize).add(array[i]);
        }

        // Sort buckets and place back into input array
        int currentIndex = 0;
        for (int i = 0; i < buckets.size(); i++) {
            Integer[] bucketArray = new Integer[buckets.get(i).size()];
            bucketArray = buckets.get(i).toArray(bucketArray);
            insertionSort(bucketArray);
            for (int j = 0; j < bucketArray.length; j++) {
                array[currentIndex++] = bucketArray[j];
            }
            List<Integer> intList = new ArrayList<Integer>();
            for (int index = 0; index < bucketArray.length; index++)
            {
                intList.add(bucketArray[index]);
            }
            buckets.set(i, intList);
        }

        return array;
    }


    public static <T extends Comparable<T>> void insertionSort(T[] array) {
        for (int i = 1; i < array.length; i++) {
            T item = array[i];
            int indexHole = i;
            while (indexHole > 0 && array[indexHole - 1].compareTo(item) > 0) {
                array[indexHole] = array[--indexHole];
            }
            array[indexHole] = item;
        }
    }
}
