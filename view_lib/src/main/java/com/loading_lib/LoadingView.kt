package com.loading_lib


import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.support.annotation.ColorInt
import android.view.LayoutInflater
import android.widget.ProgressBar
import android.widget.TextView
import com.example.loading_lib.R

class LoadingView private constructor(context: Activity) : Dialog(context) {

    private var mTextView: TextView? = null
    private var mProgressBar: ProgressBar? = null

    init {
        init(context)
    }

    private fun init(context: Context) {
        val view = LayoutInflater.from(context).inflate(R.layout.loading_layout, null)
        setContentView(view)
        mTextView = view.findViewById(R.id.text_view)
        mProgressBar = view.findViewById(R.id.progress_bar)
        setCanceledOnTouchOutside(true)
        window!!.setBackgroundDrawableResource(android.R.color.transparent)
    }

    fun setProgressBarColor(@ColorInt color: Int) {
        val colorDrawable = ColorDrawable(color)
        mProgressBar!!.indeterminateDrawable = colorDrawable
    }

    fun showLoading(text: String?) {
        mTextView!!.text = if (text == null) text else "正在加载..."
        if (!instance!!.isShowing) {
            instance!!.show()
        }
    }

    fun hideLoading() {
        if (instance != null || instance!!.isShowing) {
            instance!!.dismissLoading()
        }
    }

    fun dismissLoading() {
        instance!!.dismiss()
        instance = null
    }

    companion object {
        private var instance: LoadingView? = null

        fun getInstance(context: Activity): LoadingView? {
            if (instance == null) {
                synchronized(LoadingView::class.java) {
                    if (instance == null) {
                        instance = LoadingView(context)
                    }
                }
            }
            return instance
        }
    }

}
