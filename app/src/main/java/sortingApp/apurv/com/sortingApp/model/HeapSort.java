package sortingApp.apurv.com.sortingApp.model;/*
 * Java Program to Implement Heap Sort
 */

import sortingApp.apurv.com.sortingApp.activity.HeaptSortFragment;
import sortingApp.apurv.com.sortingApp.custom.Sorter;

/* Class HeapSort */
public class HeapSort implements Sorter
{
    private static int N;
    private HeaptSortFragment sortObserver;

    public HeapSort(HeaptSortFragment observer){
        sortObserver = observer;

    }
    /* Sort Function */
    public  Integer[] sort(Integer arr[])
    {
        heapify(arr);
        for (int i = N; i > 0; i--)
        {
            swap(arr,0, i);
            N = N-1;
            maxheap(arr, 0);
        }
        return arr;
    }
    /* Function to build a heap */
    private  void heapify(Integer arr[])
    {
        N = arr.length-1;
        for (int i = N/2; i >= 0; i--)
            maxheap(arr, i);
    }
    /* Function to swap largest element in heap */
    private  void maxheap(Integer arr[], int i)
    {
        int left = 2*i ;
        int right = 2*i + 1;
        int max = i;
        if (left <= N && arr[left] > arr[i])
            max = left;
        if (right <= N && arr[right] > arr[max])
            max = right;

        if (max != i)
        {
            swap(arr, i, max);
            maxheap(arr, max);
        }
    }
    /* Function to swap two numbers in an array */
    private   void swap(Integer arr[], int i, int j)
    {
        sortObserver.OnArrayUpdate(i,j);
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

}