package com.example.bannerlibrary;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;


public class Banner extends FrameLayout {


    public static final int CENTER = 1;
    public static final int RIGHT = 5;

    private Context mContext;

    private int[] mDot = new int[2];

    private LinearLayout mDotGroup;

    private FrameLayout mFrameLayout;

    private View mSelectedDot;
    private BannerViewPager mPager;
    private BannerPagerAdapter mAdapter;
    private String TAG = getClass().getSimpleName();
    private boolean showDot = false;
    /*是否无限轮播，循环*/
    private boolean isLimited = true;

    private OnPageChangeListener onPageChangeListener;

    public Banner(Context context) {
        this(context, null);
    }

    public Banner(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Banner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init() {

        /*添加ViewPager*/
        mPager = new BannerViewPager(mContext);

        FrameLayout.LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        addView(mPager, layoutParams);

        mFrameLayout = new FrameLayout(mContext);

        mDotGroup = new LinearLayout(mContext);

        mDotGroup.setOrientation(LinearLayout.HORIZONTAL);

        mDotGroup.setGravity(CENTER | Gravity.BOTTOM);

        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);

        mFrameLayout.addView(mDotGroup, params);

        addView(mFrameLayout, params);


    }

    public boolean isLimited() {
        return isLimited;
    }

    public void setLimited(boolean isLimited) {
        this.isLimited = isLimited;
    }

    public void setAdapter(final BannerPagerAdapter adapter) {
        mAdapter = adapter;
        mAdapter.setLimited(isLimited());
        mPager.setAdapter(mAdapter);


        LinearLayout.LayoutParams dotParams =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
        float density = mContext.getResources().getDisplayMetrics().density;

        dotParams.rightMargin = (int) (6 * density);
        int size = mAdapter.size;
        for (int i = 0; i < size; i++) {
            ImageView iv = new ImageView(mContext);
            iv.setImageDrawable(mContext.getResources().getDrawable(mDot[1]));
            iv.setLayoutParams(dotParams);
            mDotGroup.addView(iv);
        }

        mDotGroup.setPadding(0, 0, 10, (int) (density * 10));

        post(new Runnable() {
            @Override
            public void run() {
                //动态的小图标
                ImageView iv = new ImageView(mContext);
                float density = mContext.getResources().getDisplayMetrics().density;

                iv.setImageDrawable(mContext.getResources().getDrawable(mDot[0]));
                LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.leftMargin = (int) mDotGroup.getChildAt(0).getX();
                params.gravity = Gravity.BOTTOM;
                params.bottomMargin = (int) (10 * density);
                mFrameLayout.addView(iv, params);
                mSelectedDot = mFrameLayout.getChildAt(1);
            }
        });


        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (onPageChangeListener != null) {
                    onPageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
                }
                position %= mAdapter.size;

                if (mSelectedDot != null) {
                    float dx = mDotGroup.getChildAt(1).getX() - mDotGroup.getChildAt(0).getX();
                    float startX = position * dx;
                    float scrollX = positionOffset * dx;

                    float selectX = mSelectedDot.getX();
                    float tranX = startX + scrollX;
                    if (position == mAdapter.size - 1) {

                        boolean scrollable = (selectX + tranX) < mDotGroup.getChildAt(mAdapter.size - 1).getX();
                        if (scrollable) {
                            mSelectedDot.setTranslationX(tranX);
                        } else {
                            //doNothing
                        }
                    } else {
                        mSelectedDot.setTranslationX(tranX);
                    }
                }
            }

            @Override
            public void onPageSelected(int position) {
                if (onPageChangeListener != null) {
                    onPageChangeListener.onPageSelected(position);
                }

                position %= mAdapter.size;

                if (mSelectedDot != null) {
                    float dx = mDotGroup.getChildAt(1).getX() - mDotGroup.getChildAt(0).getX();
                    mSelectedDot.setTranslationX(position * dx);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (onPageChangeListener != null) {
                    onPageChangeListener.onPageScrollStateChanged(state);
                }
            }
        });

    }


    public void setOnPageChangeListener(Banner.OnPageChangeListener onPageChangeListener) {
        this.onPageChangeListener = onPageChangeListener;
    }


    public void startAutoPlay() {
        mPager.startAutoPlay();
    }

    public void stopAutoPlay() {
        mPager.stopAutoPlay();
    }

    public void setTime(int time) {
        mPager.setTime(time);

    }

    public void canAuto(boolean canAuto) {
        mPager.setCanAuto(canAuto);

    }

    public void setDot(int selectIcon, int unSelectIcon) {
        showDot = true;
        mDot[0] = selectIcon;
        mDot[1] = unSelectIcon;

    }

    public boolean hideShowDot() {
        return showDot;
    }

    public void showDot(boolean showDot) {
        if (showDot) {
            this.showDot = true;
            mFrameLayout.setVisibility(VISIBLE);
        } else {
            this.showDot = false;
            mFrameLayout.setVisibility(GONE);
        }
    }


    public void setDotGravity(int gravity) {
        mDotGroup.setGravity(gravity | Gravity.BOTTOM);
        float density = mContext.getResources().getDisplayMetrics().density;
        if (gravity == CENTER) {
            mDotGroup.setPadding(0, 0, 0, (int) (density * 10));
        } else {
            mDotGroup.setPadding(0, 0, 10, (int) (density * 10));
        }
    }

    //和ViewPager的OnPageChangeListener 相同
    public interface OnPageChangeListener {

        /**
         * This method will be invoked when the current page is scrolled, either as part
         * of a programmatically initiated smooth scroll or a user initiated touch scroll.
         *
         * @param position             Position index of the first page currently being displayed.
         *                             Page position+1 will be visible if positionOffset is nonzero.
         * @param positionOffset       Value from [0, 1) indicating the offset from the page at position.
         * @param positionOffsetPixels Value in pixels indicating the offset from position.
         */
        void onPageScrolled(int position, float positionOffset, int positionOffsetPixels);

        /**
         * This method will be invoked when a new page becomes selected. Animation is not
         * necessarily complete.
         *
         * @param position Position index of the new selected page.
         */
        void onPageSelected(int position);

        /**
         * Called when the scroll state changes. Useful for discovering when the user
         * begins dragging, when the pager is automatically settling to the current page,
         * or when it is fully stopped/idle.
         */
        void onPageScrollStateChanged(int state);
    }

}
