package com.loading_lib;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.Constance.Constance;
import com.adapter.PhotoAdapter;
import com.entity.PhotoSelectBean;
import com.example.loading_lib.R;

import java.util.ArrayList;
import java.util.List;

/***
 * 相册选择界面
 */
public class PhotoSelectView extends AppCompatActivity {
    
    private Toolbar mToolBar;
    private GridView mGridView;
    
    private List<String> mName;
    private ArrayList<PhotoSelectBean> mPath;
    private List<String> mInfo;
    
    private PhotoAdapter mPhotoAdapter;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phone_view);
        initView();
        requestPermission();
    }
    
    
    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            //有权限 不用申请了
            getAllImage();
        } else {//无权限
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, Constance.PHOTO_REQUEST);
            } else {
                getAllImage();
            }
        }
    }
    
    private void getAllImage() {
        if (mName == null) {
            mName = new ArrayList<>();
        } else mName.clear();
        if (mPath == null) {
            mPath = new ArrayList<>();
        } else mPath.clear();
        if (mInfo == null) {
            mInfo = new ArrayList<>();
        } else mInfo.clear();
        
        Cursor cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
        while (cursor.moveToNext()) {
            //图片名称
            String disPlayName = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));
            //日期
            byte[] path = cursor.getBlob(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            //获取图片的详细信息
            String desc = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DESCRIPTION));
            mName.add(disPlayName);
            mPath.add(new PhotoSelectBean(false, new String(path, 0, path.length - 1)));
            mInfo.add(desc);
        }
        fillGridView();
    }
    
    /***
     * 填充网格布局
     */
    private void fillGridView() {
        mPhotoAdapter.setPaths(mPath);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("PhotoSelectView", "点击事件");
                clickGridViewItem(position);
            }
        });
        
    }
    
    /**
     * 点击事件
     *
     * @param position
     */
    private void clickGridViewItem(int position) {
        Intent intent = new Intent(this, BigImageView.class);
        intent.putParcelableArrayListExtra("data", mPhotoAdapter.getPaths());
        intent.putExtra("position", position);
        startActivityForResult(intent, Constance.PHOTO_SELECT_REQUEST);
    }
    
    private void initView() {
        mToolBar = (Toolbar) findViewById(R.id.tool_bar);
        mGridView = (GridView) findViewById(R.id.grid_view);
        
        setSupportActionBar(mToolBar);
        
        getSupportActionBar().setTitle("图片选择");
        
        mToolBar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mToolBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                return false;
            }
        });
        
        mPhotoAdapter = new PhotoAdapter(this);
        
        mGridView.setAdapter(mPhotoAdapter);
    }
    
    
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            //通过
            if (requestCode == Constance.PHOTO_REQUEST) {
                getAllImage();
            }
        } else {
            //不通过
            Toast.makeText(this, "请通过权限之后再进入此界面", Toast.LENGTH_SHORT).show();
        }
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constance.PHOTO_SELECT_RESULT && requestCode == Constance.PHOTO_SELECT_REQUEST) {
            //选择图片回来了
            ArrayList<PhotoSelectBean> path = data.getParcelableArrayListExtra("data");
            if (path != null) {
                mPath = path;
            }
            if (data.getIntExtra("position", 0) != 0) {
                mGridView.setVerticalScrollbarPosition(data.getIntExtra("position", 0));
            }
            fillGridView();
        }
    }
}
