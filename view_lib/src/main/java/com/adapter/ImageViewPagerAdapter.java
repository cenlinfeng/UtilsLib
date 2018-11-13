package com.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.entity.PhotoSelectBean;
import com.example.loading_lib.R;

import java.util.ArrayList;

public class ImageViewPagerAdapter extends PagerAdapter {
    
    private Context mContext;
    private ArrayList<PhotoSelectBean> mData;
    
    public ImageViewPagerAdapter(Context context, ArrayList<PhotoSelectBean> data) {
        mContext = context;
        mData = data;
    }
    
    public ArrayList<PhotoSelectBean> getData() {
        return mData;
    }
    
    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }
    
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }
    
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//        super.destroyItem(container, position, object);
        container.removeView((View) object);
    }
    
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.big_image_view, null);
        ImageView imageView = view.findViewById(R.id.image_view);
        Glide.with(mContext).load(mData.get(position).getPath()).into(imageView);
        container.addView(view);
        return view;
    }
    
    public void setChecked(boolean checked, int position) {
        mData.get(position).setCheckd(checked);
    }
}
