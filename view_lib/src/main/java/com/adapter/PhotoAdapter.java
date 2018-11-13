package com.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.Constance.Constance;
import com.bumptech.glide.Glide;
import com.entity.PhotoSelectBean;
import com.example.loading_lib.R;
import com.holder.PhotoHolder;

import java.util.ArrayList;

public class PhotoAdapter extends BaseAdapter {
    
    private ArrayList<PhotoSelectBean> mPaths;
    private Context mContext;
    private int mWidth;
    private int mHeight;
    private int mLineCount = 4;
    private int mSelectCount = 0;
    
    public PhotoAdapter(Context context) {
        mContext = context;
        WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        mWidth = windowManager.getDefaultDisplay().getWidth();
        mHeight = windowManager.getDefaultDisplay().getHeight();
    }
    
    public void setPaths(ArrayList<PhotoSelectBean> paths) {
        mPaths = paths;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        PhotoHolder photoHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.photo_item, parent, false);
            photoHolder = new PhotoHolder();
            photoHolder.mImageView = convertView.findViewById(R.id.image_view);
            photoHolder.mCheckBox = convertView.findViewById(R.id.check_box);
            ViewGroup.LayoutParams layoutParams = photoHolder.mImageView.getLayoutParams();
            
            layoutParams.width = mWidth / (mLineCount);
            layoutParams.height = layoutParams.width;
            photoHolder.mImageView.setLayoutParams(layoutParams);
            
            convertView.setTag(photoHolder);
        } else {
            photoHolder = (PhotoHolder) convertView.getTag();
        }
        Glide.with(mContext).load(mPaths.get(position).getPath()).into(photoHolder.mImageView);
        photoHolder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                mSelectCount++;
                if (mSelectCount >= Constance.MAX_IMAGE_SELECT && isChecked) {
                    mSelectCount++;
                    mPaths.get(position).setCheckd(false);
                    notifyDataSetChanged();
                    Toast.makeText(mContext, "选择数量已经超过最大值", Toast.LENGTH_SHORT).show();
                } else {
                    mPaths.get(position).setCheckd(isChecked);
                    if (isChecked) {
                        mSelectCount++;
                    } else {
                        mSelectCount--;
                    }
                }
                Log.d("PhotoAdapter", "isChecked = " + isChecked);
                Log.d("PhotoAdapter", "mSelectCount = " + mSelectCount);
            }
        });
        photoHolder.mCheckBox.setChecked(mPaths.get(position).isCheckd());
        return convertView;
    }
    
    public ArrayList<PhotoSelectBean> getPaths() {
        return mPaths;
    }
    
    
    /**
     * 设置选择的个数
     */
    public void setSelectCount(int count) {
        mSelectCount = count;
    }
    
    public int getSelectCount() {
        return mSelectCount;
    }
}
