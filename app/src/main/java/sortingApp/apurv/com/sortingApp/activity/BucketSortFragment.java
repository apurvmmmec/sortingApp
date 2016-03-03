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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import sortingApp.apurv.com.sortingApp.R;
import sortingApp.apurv.com.sortingApp.custom.BucketSortView;
import sortingApp.apurv.com.sortingApp.custom.SortObserver;
import sortingApp.apurv.com.sortingApp.custom.Sorter;
import sortingApp.apurv.com.sortingApp.model.BucketSort;


public class BucketSortFragment extends Fragment implements SortObserver {

    BucketSortView mBucketSortView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        final RelativeLayout mLayout = (RelativeLayout) rootView.findViewById(R.id.fragment);

        WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int mWidth = size.x;

        final Integer[] unSortedArray = {8, 4, 2, 5, 7, 1, 0};

        mBucketSortView = new BucketSortView(getActivity(), unSortedArray);
        LinearLayout.LayoutParams mParams2 = new LinearLayout.LayoutParams(mWidth, 1000);
        mParams2.setMargins(0, 0, 0, 0);
        mBucketSortView.setLayoutParams(mParams2);
        mBucketSortView.setBackgroundColor(getResources().getColor(android.R.color.white));
        mBucketSortView.setCount(unSortedArray.length);

        new AlertDialog.Builder(getActivity())
                .setTitle("Bucket Sort")
                .setMessage("Bucket sort Animation Algorithm:" + "\n" +
                        "1- Iterate through each item in array and move it to bucket" + "\n" +
                        "2- Sort items in bucket" + "\n" +
                        "3- Move items from sorted buckets to array")
                .setPositiveButton("View Animation", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        mLayout.addView(mBucketSortView);
                        Sorter bucketSort = new BucketSort(BucketSortFragment.this);

                        Integer[] sortedArray = bucketSort.sort(unSortedArray);
                        mBucketSortView.startAnimation(sortedArray);

                    }
                })

                .show();

        return rootView;
    }

    @Override
    public void OnArrayUpdate(int x, int y) {
        mBucketSortView.addItemDestBucketToMap(x, y);
    }
}
