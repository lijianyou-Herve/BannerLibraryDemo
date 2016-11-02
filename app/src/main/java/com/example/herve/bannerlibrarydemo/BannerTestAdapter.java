package com.example.herve.bannerlibrarydemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.bannerlibrary.BannerPagerAdapter;

import java.util.List;

/**
 * Created           :Herve on 2016/10/27.
 *
 * @ Author          :Herve
 * @ e-mail          :lijianyou.herve@gmail.com
 * @ LastEdit        :2016/10/27
 * @ projectName     :BannerLibraryDemo
 * @ version
 */
public class BannerTestAdapter extends BannerPagerAdapter {


    private Context mContext;

    public BannerTestAdapter(Context context, List mData, boolean isLimited) {
        super(mData, isLimited);
        this.mContext = context;

    }

    @Override
    public View setView(ViewGroup container, final int position) {


        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_banner, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.iv_banner_item);

        if (position % 2 == 0) {
            imageView.setImageResource(R.drawable.welcome);
        } else {
            imageView.setImageResource(R.drawable.welcome2);
        }

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(mContext, "点击了" + position, Toast.LENGTH_SHORT).show();

            }
        });


        return itemView;
    }
}
