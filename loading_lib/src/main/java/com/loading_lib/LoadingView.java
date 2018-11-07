package com.loading_lib;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.ColorInt;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.loading_lib.R;

public class LoadingView extends Dialog {
    
    private TextView mTextView;
    private static LoadingView instance;
    private ProgressBar mProgressBar;
    
    public static LoadingView getInstance(Activity context) {
        if (instance == null) {
            synchronized (LoadingView.class) {
                if (instance == null) {
                    instance = new LoadingView(context);
                }
            }
        }
        return instance;
    }
    
    private LoadingView(Activity context) {
        super(context);
        init(context);
    }
    
    private void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.loading_layout, null);
        setContentView(view);
        mTextView = view.findViewById(R.id.text_view);
        mProgressBar = view.findViewById(R.id.progress_bar);
        setCanceledOnTouchOutside(true);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }
    
    public void setProgressBarColor(@ColorInt int color){
        ColorDrawable colorDrawable = new ColorDrawable(color);
        mProgressBar.setIndeterminateDrawable(colorDrawable);
    }
    
    public void showLoading(String text) {
        mTextView.setText(text == null ? text : "正在加载...");
        if (!instance.isShowing()) {
            instance.show();
        }
    }
    
    public void hideLoading() {
        if (instance != null || instance.isShowing()) {
            instance.dismissLoading();
        }
    }
    
    public void dismissLoading() {
        instance.dismiss();
        instance = null;
    }
    
}
