package com.loading_lib

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.Toast
import com.Constance.Constance
import com.adapter.ImageViewPagerAdapter
import com.entity.PhotoSelectBean
import com.example.loading_lib.R

import java.util.*

class BigImageView : AppCompatActivity() {

    private var data: ArrayList<PhotoSelectBean>? = null
    private var mAdapter: ImageViewPagerAdapter? = null
    private var position: Int = 0
    private var mViewPager: ViewPager? = null
    private var mRelativeLayout: RelativeLayout? = null
    private var mLinearLayoutCheck: LinearLayout? = null
    private var mCheckBox: CheckBox? = null
    private var mToolBar: Toolbar? = null
    private var mRootView: RelativeLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_pager)

        initOther()
        initView()
        initEvent()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initEvent() {
        mCheckBox!!.setOnCheckedChangeListener { buttonView, isChecked ->
            val currentItem = mViewPager!!.currentItem
            //视图改变
            mAdapter!!.setChecked(isChecked, currentItem)
            //数据改变
            data!![currentItem].isCheckd = isChecked

            //上方已经是改变过来的了
            var checkCount = 0
            for (i in data!!.indices) {
                if (data!![i].isCheckd) {
                    checkCount++
                    if (checkCount > 9) {
                        break
                    }
                }
            }

            if (checkCount > 9) {
                mCheckBox!!.isChecked = false
                mAdapter!!.setChecked(false, currentItem)
                data!![currentItem].isCheckd = false
                Toast.makeText(this@BigImageView, "已经到达选择最大数", Toast.LENGTH_SHORT).show()
            }
        }

        mViewPager!!.setOnTouchListener(object : View.OnTouchListener {
            internal var startX: Float = 0.toFloat()
            internal var startY: Float = 0.toFloat()
            internal var endX: Float = 0.toFloat()
            internal var endY: Float = 0.toFloat()

            override fun onTouch(v: View, event: MotionEvent): Boolean {
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        startX = event.x
                        startY = event.y
                    }
                    MotionEvent.ACTION_UP -> {
                        endX = event.x
                        endY = event.y
                        if (Math.abs(endX - startX) <= 10 && Math.abs(endY - startY) <= 10) {
                            //算是点击事件
                            clickViewpager()
                        }
                    }
                    MotionEvent.ACTION_MOVE -> {
                    }
                }
                return false
            }
        })

        mViewPager!!.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(i: Int, v: Float, i1: Int) {}

            override fun onPageSelected(i: Int) {
                mCheckBox!!.isChecked = data!![i].isCheckd
            }

            override fun onPageScrollStateChanged(i: Int) {

            }
        })
    }

    private fun clickViewpager() {
        mToolBar!!.visibility = if (mToolBar!!.visibility == View.VISIBLE) View.GONE else View.VISIBLE
        mRelativeLayout!!.visibility = if (mRelativeLayout!!.visibility == View.VISIBLE) View.GONE else View.VISIBLE
        if (mToolBar!!.visibility != mRelativeLayout!!.visibility) {
            //听toolbar的
            mRelativeLayout!!.visibility = mToolBar!!.visibility
        }
    }

    private fun initView() {
        mAdapter = ImageViewPagerAdapter(this, data)

        mViewPager = findViewById<View>(R.id.view_pager) as ViewPager
        mRelativeLayout = findViewById<View>(R.id.relative_layout) as RelativeLayout
        mLinearLayoutCheck = findViewById<View>(R.id.linear_layout_check) as LinearLayout
        mCheckBox = findViewById<View>(R.id.check_box) as CheckBox
        mToolBar = findViewById<View>(R.id.tool_bar) as Toolbar
        mRootView = findViewById<View>(R.id.root_view) as RelativeLayout

        mViewPager!!.adapter = mAdapter

        mViewPager!!.currentItem = position

        mToolBar!!.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp)
        mToolBar!!.setNavigationOnClickListener { resultData() }

        mCheckBox!!.isChecked = data!![position].isCheckd

        mToolBar!!.visibility = View.GONE
        mRelativeLayout!!.visibility = View.GONE

    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            resultData()
        }
        return true
    }

    /**
     * 回传数据
     */
    private fun resultData() {
        val intent = Intent()
        intent.putExtra("data", data)
        intent.putExtra("position", mViewPager!!.currentItem)
        setResult(Constance.PHOTO_SELECT_RESULT, intent)
        finish()
    }

    private fun initOther() {
        val intent = intent
        data = intent.getParcelableArrayListExtra("data")
        position = intent.getIntExtra("position", 0)
    }


}
