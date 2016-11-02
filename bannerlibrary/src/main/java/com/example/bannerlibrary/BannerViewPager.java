package com.example.bannerlibrary;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;


class BannerViewPager extends ViewPager {

    private static final int MSG_WHAT = -00001;
    private int SEND_TIME = 5000;
    private int position;

    private boolean canAuto = true;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (canAuto) {
                position = getCurrentItem() + 1;
                setCurrentItem(position);
                sendEmptyMessageDelayed(MSG_WHAT, SEND_TIME);
            }
        }
    };


    public boolean isCanAuto() {
        return canAuto;
    }

    public void setCanAuto(boolean canAuto) {
        this.canAuto = canAuto;
    }


    public BannerViewPager(Context context) {
        this(context, null);
    }

    public BannerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public BannerViewPager startAutoPlay() {
        mHandler.sendEmptyMessageDelayed(MSG_WHAT, SEND_TIME);
        return this;
    }

    public void stopAutoPlay() {
        mHandler.removeMessages(MSG_WHAT);
    }

    public BannerViewPager setTime(int time) {
        SEND_TIME = time;
        return this;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            stopAutoPlay();
        } else if (ev.getAction() == MotionEvent.ACTION_UP) {
            startAutoPlay();
        }
        return super.dispatchTouchEvent(ev);
    }

}
