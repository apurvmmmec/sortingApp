package sortingApp.apurv.com.sortingApp.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import sortingApp.apurv.com.sortingApp.R;
import sortingApp.apurv.com.sortingApp.custom.HeapSortView;
import sortingApp.apurv.com.sortingApp.custom.SortObserver;
import sortingApp.apurv.com.sortingApp.custom.Sorter;
import sortingApp.apurv.com.sortingApp.model.HeapSort;


/**
 * A placeholder fragment containing a simple view.
 */
public class HeaptSortFragment extends Fragment implements SortObserver {

    HeapSortView mHeapSortView;
    Integer[] mSortedArray;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        final RelativeLayout mLayout = (RelativeLayout)rootView.findViewById(R.id.fragment);


        WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int mWidth = size.x ;
        int mHeight = size.y;

        final Integer[] unSortedArr = {8,4,2,5,7,1,0};
        mHeapSortView = new HeapSortView(getActivity(), unSortedArr);
        RelativeLayout.LayoutParams mParams = new RelativeLayout.LayoutParams(mWidth, mHeight);
        mParams.setMargins(0, 0, 0, 0);
        mHeapSortView.setLayoutParams(mParams);
        mHeapSortView.setBackgroundColor(getResources().getColor(android.R.color.white));
        mHeapSortView.setCount(unSortedArr.length);
        new AlertDialog.Builder(getActivity())
                .setTitle("Heap Sort")
                .setMessage("Heap sort Animation Algorithm:" + "\n" +
                        "1- Heapify the array i.e puts the array into heap order" + "\n" +
                        "2- Repeatedly extract the maximum from heap and move to last of Array" + "\n")
                .setPositiveButton("View Animation", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        mLayout.addView(mHeapSortView);
                        Sorter bucketSort = new HeapSort(HeaptSortFragment.this);

                        Integer[] sortedArray = bucketSort.sort(unSortedArr);
                        mHeapSortView.startAnimation();
                    }
                })

                .show();

        return rootView;
    }


    @Override
    public void OnArrayUpdate(int x, int y) {
        mHeapSortView.addAnimationPair(x,y);
    }
}
