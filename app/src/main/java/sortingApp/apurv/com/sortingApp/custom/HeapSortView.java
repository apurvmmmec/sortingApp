package sortingApp.apurv.com.sortingApp.custom;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import sortingApp.apurv.com.sortingApp.R;
import sortingApp.apurv.com.sortingApp.animation.AnimatorPath;
import sortingApp.apurv.com.sortingApp.animation.PathEvaluator;
import sortingApp.apurv.com.sortingApp.animation.PathPoint;

public class HeapSortView extends LinearLayout {


    private MenuItemView[] mMenuItems;
    private int mMenuItemCount;
    private Context mContext;
    private int mWidth = 0;
    private int iconSize, iconSpacing;

    private int animateItemIndex=-1;
    private int animateItemIndexRev=-1;
    private Integer[]  unsortedList;
    private List<Integer[]> mAnimOrder = new ArrayList<Integer[]>();

    public HeapSortView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    public HeapSortView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context;
        init();
    }

    public HeapSortView(Context context) {
        super(context);
        this.mContext = context;
        init();
    }
    public HeapSortView(Context context, Integer[] unsortedArr) {
        super(context);
        this.mContext = context;
        unsortedList = unsortedArr;
        init();
    }

    private void init() {

        LayoutParams LLParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        setLayoutParams(LLParams);

        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        mWidth = size.x / 2;
        mAnimOrder = new ArrayList<>();
    }

    public void setCount(int count) {
        this.mMenuItemCount = count;
        iconSpacing = (int) ( mWidth/(count+1));
        iconSize = (int)(iconSpacing);
        mMenuItems = new MenuItemView[mMenuItemCount];
        initMenus();
    }

    public void initMenus() {
        LayoutParams mParams = new LayoutParams(iconSize, iconSize);
        mParams.gravity = Gravity.CENTER_VERTICAL;
        mParams.setMargins(iconSpacing, 0, 0, 0);


        for (int i = 0; i < mMenuItemCount; i++) {
            mMenuItems[i] = new MenuItemView(mContext);
            mMenuItems[i].setLayoutParams(mParams);
            mMenuItems[i].index = i;
            mMenuItems[i].setBackground(new ColorDrawable(mContext.getResources().getColor(R.color.colorAccent)));
            mMenuItems[i].setText(unsortedList[i]+"");
            mMenuItems[i].setTextSize(12);

            mMenuItems[i].setGravity(Gravity.CENTER);
            addView(mMenuItems[i]);
        }
    }

    public void  animateItem(final int fromidx, final int toidx){
        animateItemIndex = fromidx;
        AnimatorPath path = new AnimatorPath();
        float x1= mMenuItems[fromidx].getTranslationX();
        float x2 = mMenuItems[toidx].getTranslationX();
        int destX = (toidx-fromidx)*(iconSpacing+iconSize)+(int)x1;
        int animHeight = toidx >fromidx ?-200 : 200;

        path.moveTo(x1, 0);
        path.curveTo(x1, 0, (destX+x1) / 2, animHeight, destX, 0);
        ObjectAnimator anim = ObjectAnimator.ofObject(this, "itemLoc",
                new PathEvaluator(), path.getPoints().toArray());
        anim.setInterpolator(new AccelerateInterpolator());
        anim.setDuration(1000);

        int destX2 = (-1*(toidx-fromidx)*(iconSpacing+iconSize)+(int)x2);

        animateItemIndexRev = toidx;
        AnimatorPath path2 = new AnimatorPath();
        path2.moveTo(x2, 0);
        path2.curveTo(x2, 0, (destX2+x2) / 2, -animHeight, destX2, 0);
        final ObjectAnimator anim2 = ObjectAnimator.ofObject(this, "itemLocrev",
                new PathEvaluator(), path2.getPoints().toArray());
        anim2.setInterpolator(new AccelerateInterpolator());
        anim2.setDuration(1000);


        anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                anim2.start();
            }

            @Override
            public void onAnimationEnd(Animator animation) {

                swapImageView(fromidx, toidx);
                if (mAnimOrder.size() > 0) {
                    Integer[] pair = mAnimOrder.remove(0);
                    animateItem(pair[0].intValue(), pair[1].intValue());
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        anim.start();
    }

    public void setItemLoc(PathPoint newLoc) {
        mMenuItems[animateItemIndex].setTranslationX(newLoc.mX);
        mMenuItems[animateItemIndex].setTranslationY(newLoc.mY);
    }

    public void setItemLocrev(PathPoint newLoc) {
        mMenuItems[animateItemIndexRev].setTranslationX(newLoc.mX);
        mMenuItems[animateItemIndexRev].setTranslationY(newLoc.mY);
    }




    public void startAnimation(){
        Integer[] pair= mAnimOrder.remove(0);
        animateItem(pair[0].intValue(), pair[1].intValue());
    }

    private void swapImageView(int x, int y){
        MenuItemView tmp = mMenuItems[x];
        mMenuItems[x] = mMenuItems[y];
        mMenuItems[y] = tmp;
    }

    public void addAnimationPair(int x ,int y){
        mAnimOrder.add(new Integer[]{x,y});
    }
}
