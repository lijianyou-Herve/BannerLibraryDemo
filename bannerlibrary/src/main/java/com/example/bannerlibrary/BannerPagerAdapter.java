package com.example.bannerlibrary;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;


public abstract class BannerPagerAdapter<T> extends PagerAdapter {

    public int size = -1;
    private int position;

    private String TAG = getClass().getSimpleName();
    protected List<T> mData;


    //是否无限循环
    private boolean isLimited = true;
    private boolean clickable = false;


    public BannerPagerAdapter(List<T> mData, boolean isLimited) {
        this.isLimited = isLimited;
        this.mData = mData;
        size = mData.size();
    }


    @Override
    public int getCount() {
        if (isLimited) {
            return Integer.MAX_VALUE;
        }
        return size;
    }

    public boolean isLimited() {
        return isLimited;
    }

    public void setLimited(boolean isLimited) {
        this.isLimited = isLimited;
    }

    public boolean getClickable() {
        return clickable;
    }

    public void setClickable(boolean clickable) {
        this.clickable = clickable;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        position %= size;
        this.position = position;

        View view = setView(container, position);

        container.addView(view);

        return view;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public abstract View setView(ViewGroup container, int position);

    public int getPosition() {
        return position;
    }
}
