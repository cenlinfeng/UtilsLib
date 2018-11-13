package com.loading_lib;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.Constance.Constance;
import com.adapter.ImageViewPagerAdapter;
import com.entity.PhotoSelectBean;
import com.example.loading_lib.R;

import java.util.ArrayList;

public class BigImageView extends AppCompatActivity {
    
    private ArrayList<PhotoSelectBean> data;
    private ImageViewPagerAdapter mAdapter;
    private int position;
    private ViewPager mViewPager;
    private RelativeLayout mRelativeLayout;
    private LinearLayout mLinearLayoutCheck;
    private CheckBox mCheckBox;
    private Toolbar mToolBar;
    private RelativeLayout mRootView;
    private TextView mTvSuccess;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_pager);
        
        initOther();
        initView();
        initEvent();
    }
    
    @SuppressLint("ClickableViewAccessibility")
    private void initEvent() {
        mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int currentItem = mViewPager.getCurrentItem();
                //视图改变
                mAdapter.setChecked(isChecked, currentItem);
                //数据改变
                data.get(currentItem).setCheckd(isChecked);
                
                //上方已经是改变过来的了
                int checkCount = 0;
                for (int i = 0; i < data.size(); i++) {
                    if (data.get(i).isCheckd()) {
                        checkCount++;
                        if (checkCount > 9) {
                            break;
                        }
                    }
                }
                
                if (checkCount > 9) {
                    mCheckBox.setChecked(false);
                    mAdapter.setChecked(false, currentItem);
                    data.get(currentItem).setCheckd(false);
                    Toast.makeText(BigImageView.this, "已经到达选择最大数", Toast.LENGTH_SHORT).show();
                }
            }
        });
        
        mViewPager.setOnTouchListener(new View.OnTouchListener() {
            float startX, startY, endX, endY;
            
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = event.getX();
                        startY = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        endX = event.getX();
                        endY = event.getY();
                        if ((Math.abs(endX - startX) <= 10) && (Math.abs(endY - startY) <= 10)) {
                            //算是点击事件
                            clickViewpager();
                        }
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                }
                return false;
            }
        });
        
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }
            
            @Override
            public void onPageSelected(int i) {
                mCheckBox.setChecked(data.get(i).isCheckd());
            }
            
            @Override
            public void onPageScrollStateChanged(int i) {
            
            }
        });
        
        mTvSuccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //完成，直接返回
                
            }
        });
        
        
    }
    
    private void clickViewpager() {
        mToolBar.setVisibility(mToolBar.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
        mRelativeLayout.setVisibility(mRelativeLayout.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
        if (mToolBar.getVisibility() != mRelativeLayout.getVisibility()) {
            //听toolbar的
            mRelativeLayout.setVisibility(mToolBar.getVisibility());
        }
    }
    
    private void initView() {
        mAdapter = new ImageViewPagerAdapter(this, data);
        
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mRelativeLayout = (RelativeLayout) findViewById(R.id.relative_layout);
        mLinearLayoutCheck = (LinearLayout) findViewById(R.id.linear_layout_check);
        mCheckBox = (CheckBox) findViewById(R.id.check_box);
        mToolBar = (Toolbar) findViewById(R.id.tool_bar);
        mRootView = (RelativeLayout) findViewById(R.id.root_view);
        mTvSuccess = (TextView) findViewById(R.id.tv_success);
        
        mViewPager.setAdapter(mAdapter);
        
        mViewPager.setCurrentItem(position);
        
        mToolBar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultData();
            }
        });
        
        mCheckBox.setChecked(data.get(position).isCheckd());
        
        mToolBar.setVisibility(View.GONE);
        mRelativeLayout.setVisibility(View.GONE);
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            resultData();
        }
        return true;
    }
    
    /**
     * 回传数据
     */
    private void resultData() {
        Intent intent = new Intent();
        intent.putExtra("data", data);
        intent.putExtra("position", mViewPager.getCurrentItem());
        setResult(Constance.PHOTO_SELECT_RESULT, intent);
        finish();
    }
    
    private void initOther() {
        Intent intent = getIntent();
        data = intent.getParcelableArrayListExtra("data");
        position = intent.getIntExtra("position", 0);
    }
}
