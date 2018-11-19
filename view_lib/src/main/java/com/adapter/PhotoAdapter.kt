package com.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.Toast
import com.Constance.Constance
import com.bumptech.glide.Glide
import com.entity.PhotoSelectBean
import com.example.loading_lib.R
import com.holder.PhotoHolder
import com.icallback.PhotoChangeListener
import com.utils.MaskImageView
import kotlinx.android.synthetic.main.photo_item.view.*
import java.util.*

class PhotoAdapter(private val mContext: Context) : BaseAdapter() {

    var paths: ArrayList<PhotoSelectBean>? = null
        set(paths) {
            field = paths
            notifyDataSetChanged()
        }
    private val mWidth: Int
    private val mHeight: Int
    private val mLineCount = 4
    var photoChangeListener: PhotoChangeListener? = null

    /**
     * 设置选择的个数
     */
    var selectCount = 0

    init {
        val windowManager = mContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        mWidth = windowManager.defaultDisplay.width
        mHeight = windowManager.defaultDisplay.height
    }

    override fun getCount(): Int {
        return if (paths == null) 0 else paths!!.size
    }

    override fun getItem(position: Int): Any {
        return paths!![position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        var convertView = convertView
        var photoHolder: PhotoHolder? = null
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.photo_item, parent, false)
            photoHolder = PhotoHolder()
            photoHolder.mImageView = convertView.image_view
            photoHolder.mCheckBox = convertView.check_box
            val layoutParams = photoHolder.mImageView!!.layoutParams
            layoutParams.width = mWidth / mLineCount
            layoutParams.height = layoutParams.width
            photoHolder.mImageView!!.layoutParams = layoutParams

            convertView.tag = photoHolder
        } else {
            photoHolder = convertView.tag as PhotoHolder
        }
        Glide.with(mContext).load(paths!![position].path).into(photoHolder.mImageView!!)

        photoHolder.mCheckBox!!.setOnCheckedChangeListener { buttonView, isChecked ->
            paths!![position].isCheckd = isChecked
            if (isChecked) {
                selectCount++
                photoChangeListener!!.photoChange(selectCount)
                MaskImageView.maskDarkImg(photoHolder.mImageView!!)
            } else {
                selectCount--
                photoChangeListener!!.photoChange(selectCount)
                MaskImageView.maskBrighten(photoHolder.mImageView!!)
            }
        }


        photoHolder.mCheckBox!!.setOnClickListener { v ->
            val checked = (v as CheckBox).isChecked
            if (checked) {//这是打钩的,判断有没有超过
                if (selectCount > Constance.MAX_IMAGE_SELECT) {
                    v.isChecked = false//被点击的视图变成未打钩的状态，不增加个数
                    Toast.makeText(mContext, "选择数量已经超过最大值", Toast.LENGTH_SHORT).show()
                }
            }
        }

        photoHolder.mCheckBox!!.isChecked = paths!![position].isCheckd
        return convertView
    }
}
