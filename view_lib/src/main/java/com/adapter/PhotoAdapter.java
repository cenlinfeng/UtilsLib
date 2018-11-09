package com.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;

import com.bumptech.glide.Glide;
import com.example.loading_lib.R;
import com.holder.PhotoHolder;

import java.util.List;

public class PhotoAdapter extends BaseAdapter {
    
    private List<String> mPaths;
    private Context mContext;
    private int mWidth;
    private int mHeight;
    private int mLineCount = 4;
    
    public PhotoAdapter(Context context) {
        mContext = context;
        WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        mWidth = windowManager.getDefaultDisplay().getWidth();
        mHeight = windowManager.getDefaultDisplay().getHeight();
    }
    
    public void setPaths(List<String> paths) {
        mPaths = paths;
        for (int i = 0; i < mPaths.size(); i++) {
            Log.d("PhotoAdapter", mPaths.get(i));
        }
        notifyDataSetChanged();
    }
    
    @Override
    public int getCount() {
        return mPaths == null ? 0 : mPaths.size();
    }
    
    @Override
    public Object getItem(int position) {
        return mPaths.get(position);
    }
    
    @Override
    public long getItemId(int position) {
        return position;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PhotoHolder photoHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.photo_item, parent, false);
            photoHolder = new PhotoHolder();
            photoHolder.mImageView = convertView.findViewById(R.id.image_view);
            ViewGroup.LayoutParams layoutParams = photoHolder.mImageView.getLayoutParams();
            
            layoutParams.width = mWidth / (mLineCount);
            layoutParams.height = layoutParams.width;
            photoHolder.mImageView.setLayoutParams(layoutParams);
            
            convertView.setTag(photoHolder);
        } else {
            photoHolder = (PhotoHolder) convertView.getTag();
        }
        
        Glide.with(mContext).load(mPaths.get(position)).into(photoHolder.mImageView);
        
        return convertView;
    }
    
    
}
