package sortingApp.apurv.com.sortingApp.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import sortingApp.apurv.com.sortingApp.R;


public class WelcomeFragment extends Fragment {


    public WelcomeFragment() {
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_welcome, container, false);

    }

    public void launchBucketSort(View v){
        getFragmentManager().beginTransaction().replace(R.id.rootview, new BucketSortFragment()).commit();

    }








}
