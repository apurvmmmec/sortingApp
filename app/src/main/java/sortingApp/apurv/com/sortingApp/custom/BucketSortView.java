package sortingApp.apurv.com.sortingApp.custom;

import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.Display;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import sortingApp.apurv.com.sortingApp.R;

public class BucketSortView extends LinearLayout {


    int animateItemIndex = 0;
    int revAnimationIndex=0;
    ArrayList<Integer> unsortedList;
    HashMap<Integer,Integer> itemDestBucket = new HashMap<>();
    HashMap<Integer, Integer> bucketItems = new HashMap<>();
    HashMap<Integer, Integer[]> tempPos = new HashMap<>();

    private MenuItemView[] mMenuItems;
    private int mMenuItemCount;
    private Context mContext;
    private int mWidth = 0;
    private int iconSize;
    private int iconSpacing;
    private Integer[] mSortedArray;

    public BucketSortView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    public BucketSortView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context;
        init();
    }

    public BucketSortView(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    public BucketSortView(Context context, Integer[] unSortedArr) {
        super(context);
        this.mContext = context;
        unsortedList = new ArrayList<Integer>(Arrays.asList(unSortedArr));
        init();
    }

    private void init() {
        //setWillNotDraw(false);
        setOrientation(LinearLayout.HORIZONTAL);
        LayoutParams LLParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        setLayoutParams(LLParams);

        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        mWidth = size.x / 2;
    }

    public void setCount(int count) {
        this.mMenuItemCount = count;
        iconSpacing = (int) (mWidth / (count + 1));
        iconSize = iconSpacing;
        mMenuItems = new MenuItemView[mMenuItemCount];
        initMenus();
    }

    private void initMenus() {
        LayoutParams mParams = new LayoutParams(iconSize, iconSize);
        mParams.setMargins(iconSpacing, 120, 0, 0);

        for (int i = 0; i < mMenuItemCount; i++) {
            mMenuItems[i] = new MenuItemView(mContext);
            mMenuItems[i].setLayoutParams(mParams);
            mMenuItems[i].index = i;

            mMenuItems[i].setBackground(new ColorDrawable(mContext.getResources().getColor(R.color.colorAccent)));
            mMenuItems[i].setText(unsortedList.get(i) + " ");
            mMenuItems[i].setTextSize(12);
            addView(mMenuItems[i]);
        }
    }

    public void moveItemToBucket() {
        int bucketId = itemDestBucket.get(unsortedList.get(animateItemIndex));
        if (bucketItems.get(bucketId) != null) { //Get the count of number of items that have been moved to a buket
            bucketItems.put(bucketId, bucketItems.get(bucketId) + 1);
        } else {
            bucketItems.put(bucketId,1); //If there is no item in bucket , initiate it with 1
        }
        final int destX = (animateItemIndex -bucketId) * (iconSize + iconSpacing);
        final int destY = 600 - (bucketItems.get(bucketId) - 1) * 80;
        TranslateAnimation trans = new TranslateAnimation(0, -destX, 0, destY);
        trans.setFillAfter(true);
        trans.setDuration(1000);

        trans.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (animateItemIndex < mMenuItems.length - 1) {
                    animateItemIndex++;
                    moveItemToBucket();
                } else {
                    moveItemToSortedArray(unsortedList.indexOf(mSortedArray[revAnimationIndex]));
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        Integer[] posArr = new Integer[2];
        posArr[0] = destX;
        posArr[1] = destY;
        tempPos.put(animateItemIndex, posArr);
        mMenuItems[animateItemIndex].startAnimation(trans);
    }




    public void moveItemToSortedArray(int index) {
        int destX = (index -revAnimationIndex)* (iconSpacing+iconSize);
        int destY = 0;
        TranslateAnimation trans = new TranslateAnimation(-tempPos.get(index)[0], -destX, tempPos.get(index)[1], destY);
        trans.setFillAfter(true);
        trans.setDuration(1000);
        trans.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (revAnimationIndex < mSortedArray.length - 1) {
                    revAnimationIndex++;
                    moveItemToSortedArray(unsortedList.indexOf(mSortedArray[revAnimationIndex]));
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mMenuItems[index].startAnimation(trans);
    }

    public void startAnimation(Integer[] sortedArray) {
        mSortedArray = sortedArray;
        moveItemToBucket();
    }

    public void addItemDestBucketToMap(int x, int y){
        itemDestBucket.put(x,y);
    }
}
