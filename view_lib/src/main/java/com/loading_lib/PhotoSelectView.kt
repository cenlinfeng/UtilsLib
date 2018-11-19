package com.loading_lib

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.Window
import android.widget.AdapterView
import android.widget.Toast
import com.Constance.Constance
import com.adapter.PhotoAdapter
import com.entity.PhotoSelectBean
import com.example.loading_lib.R
import com.icallback.PhotoChangeListener
import com.utils.NotCallBackException
import com.utils.PhotoSelect
import kotlinx.android.synthetic.main.phone_view.*
import java.util.*

/***
 * 相册选择界面
 */
class PhotoSelectView : AppCompatActivity() {
    private var mName: MutableList<String>? = null
    private var mPath: ArrayList<PhotoSelectBean>? = null
    private var mInfo: MutableList<String>? = null
    private var mPhotoAdapter: PhotoAdapter? = null
    private var max_select: Int = 9

    override fun onCreate(savedInstanceState: Bundle?) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.phone_view)
        initOther()
        initView()
        requestPermission()
    }

    private fun initOther() {
        max_select = intent.extras.getInt("max_select")
    }


    private fun requestPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            //有权限 不用申请了
            getAllImage()
        } else {//无权限
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), Constance.PHOTO_REQUEST)
            } else {
                getAllImage()
            }
        }
    }

    private fun getAllImage() {
        if (mName == null) {
            mName = ArrayList()
        } else
            mName!!.clear()
        if (mPath == null) {
            mPath = ArrayList()
        } else
            mPath!!.clear()
        if (mInfo == null) {
            mInfo = ArrayList()
        } else
            mInfo!!.clear()

        val cursor = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, null)
        while (cursor!!.moveToNext()) {
            //图片名称
            val disPlayName = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME))
            //日期
            val path = cursor.getBlob(cursor.getColumnIndex(MediaStore.Images.Media.DATA))
            //获取图片的详细信息
            val desc = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DESCRIPTION))
            mName!!.add(disPlayName)
            mPath!!.add(PhotoSelectBean(false, String(path, 0, path.size - 1)))
            mInfo!!.add(desc ?: "")

        }
        fillGridView()
    }

    /***
     * 填充网格布局
     */
    private fun fillGridView() {
        mPhotoAdapter!!.paths = mPath
        grid_view.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            clickGridViewItem(position)
        }

    }

    /**
     * 点击事件
     *
     * @param position
     */
    private fun clickGridViewItem(position: Int) {
        val intent = Intent(this, BigImageView::class.java)
        intent.putParcelableArrayListExtra("data", mPhotoAdapter!!.paths)
        intent.putExtra("position", position)
        startActivityForResult(intent, Constance.PHOTO_SELECT_REQUEST)
    }

    private fun initView() {
        setSupportActionBar(tool_bar)
        supportActionBar!!.title = "图片选择"
        tool_bar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp)
        tool_bar.setNavigationOnClickListener { finish() }
        tool_bar.setOnMenuItemClickListener { false }
        mPhotoAdapter = PhotoAdapter(this)

        grid_view.adapter = mPhotoAdapter


        tool_bar.inflateMenu(R.menu.menu)

        tool_bar.setOnMenuItemClickListener { menuItem ->
            if (menuItem.itemId == R.id.tv_success) {
                //提交
                commitPhoto()
            }
            false
        }

        mPhotoAdapter!!.photoChangeListener = object : PhotoChangeListener {
            override fun photoChange(count: Int) {
                if (count != 0 && count > 0) {
                    val item = tool_bar.menu.findItem(R.id.tv_success)
                    item.title = "完成（$count/$max_select）"
                    item.isEnabled = true
                } else {
                    val item = tool_bar.menu.findItem(R.id.tv_success)
                    item.isEnabled = false
                    item.title = "完成"
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        menu.findItem(R.id.tv_success).isEnabled = false
        return true
    }

    private fun commitPhoto() {
        val photos = ArrayList<String>()
        if (mPhotoAdapter != null) {
            val paths = mPhotoAdapter!!.paths
            for (i in paths!!.indices) {
                if (paths[i].isCheckd) {
                    photos.add(paths[i].path!!)
                }
            }
        }
        val callBack = PhotoSelect.getInstance()!!.callBack
        if (callBack != null) {
            callBack.callBackSuccess(photos)
            finish()
        } else {
            throw NotCallBackException("not has callback !!!")
        }
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            //通过
            if (requestCode == Constance.PHOTO_REQUEST) {
                getAllImage()
            }
        } else {
            //不通过
            PhotoSelect.getInstance()!!.callBack!!.callBackFailure("not android.permission.READ_EXTERNAL_STORAGE permission!")

            Toast.makeText(this, "请通过权限之后再进入此界面", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Constance.PHOTO_SELECT_RESULT && requestCode == Constance.PHOTO_SELECT_REQUEST) {
            //选择图片回来了
            val path = data!!.getParcelableArrayListExtra<PhotoSelectBean>("data")
            if (path != null) {
                mPath = path
            }
            if (data.getIntExtra("position", 0) != 0) {
                grid_view.verticalScrollbarPosition = data.getIntExtra("position", 0)
            }
            fillGridView()
        }
    }
}
