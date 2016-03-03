package sortingApp.apurv.com.sortingApp.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import sortingApp.apurv.com.sortingApp.R;


public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private Fragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Sorting Visualization");
        getSupportFragmentManager().beginTransaction().replace(R.id.rootview, new WelcomeFragment()).commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.bucketsort:
                mFragment = new BucketSortFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.rootview, mFragment).commit();
                getSupportActionBar().setTitle("Bucket Sort Visualization");
                break;
            case R.id.heapsort:
                mFragment = new HeaptSortFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.rootview, mFragment).commit();
                getSupportActionBar().setTitle("Heap Sort Visualization");

                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
