package com.utils

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.icallback.PhotoSelectCallBack
import com.loading_lib.PhotoSelectView

/***
 * 选择图片工具类，从这里启动
 */
class PhotoSelect private constructor() {

    var callBack: PhotoSelectCallBack? = null

    fun openSelect(activity: Activity, callBack: PhotoSelectCallBack, option: PhotoSelectOption) {

        this.callBack = callBack
        var intent = Intent(activity, PhotoSelectView::class.java)
        var bundle = Bundle()
        bundle.putBoolean("is_show_gif", option.gifShow)
        bundle.putInt("max_select", option.maxSelect)
        intent.putExtras(bundle)
        activity.startActivity(intent)
    }

    companion object {
        private var instance: PhotoSelect? = null
        fun getInstance(): PhotoSelect? {
            if (instance == null) {
                synchronized(PhotoSelect::class.java) {
                    if (instance == null) {
                        instance = PhotoSelect()
                    }
                }
            }
            return instance
        }
    }

}
