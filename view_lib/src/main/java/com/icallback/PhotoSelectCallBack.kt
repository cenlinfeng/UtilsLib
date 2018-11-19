package com.icallback

import java.util.*

abstract class PhotoSelectCallBack {
    abstract fun callBackSuccess(selectPath: ArrayList<String>)

    /**
     * 钩子方法
     * 回调错误
     *
     * @param errMsg 错误信息
     */
    abstract fun callBackFailure(errMsg: String)

    /**
     * 钩子方法
     * 选择取消
     */
    open fun callBackCancel() {

    }

    /**
     * 钩子方法
     * 选择完成
     */
    fun callBackComplete() {

    }
}
