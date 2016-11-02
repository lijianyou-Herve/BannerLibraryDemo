package com.example.herve.bannerlibrarydemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.RelativeLayout;

import com.example.bannerlibrary.Banner;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private RelativeLayout activityMain;
    private Banner bannerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activityMain = (RelativeLayout) findViewById(R.id.activity_main);
        bannerView = (Banner) findViewById(R.id.banner_view);

        ArrayList<String> data = new ArrayList<>();

        for (int i = 0; i < 4; i++) {

            data.add("TAB" + i);
        }


        BannerTestAdapter bannerTestAdapter = new BannerTestAdapter(this, data, false);

//
        bannerView.setDot(R.drawable.btn_radio_on_holo_dark, R.drawable.btn_radio_on_disabled_holo_dark);
        bannerView.setAdapter(bannerTestAdapter);
        bannerView.setDotGravity(Gravity.CENTER);
        bannerView.canAuto(true);
        bannerView.startAutoPlay();
        bannerView.showDot(false);


        //测试
    }

}
