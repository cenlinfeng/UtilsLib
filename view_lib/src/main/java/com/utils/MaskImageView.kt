package com.utils

import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.widget.ImageView

object MaskImageView {
    fun maskDarkImg(imageView: ImageView) {
        val brightness = -50 //RGB偏移量，变暗为负数
        val matrix = ColorMatrix()
        matrix.set(floatArrayOf(1f, 0f, 0f, 0f, brightness.toFloat(), 0f, 1f, 0f, 0f, brightness.toFloat(), 0f, 0f, 1f, 0f, brightness.toFloat(), 0f, 0f, 0f, 1f, 0f))
        val cmcf = ColorMatrixColorFilter(matrix)
        imageView.setColorFilter(cmcf) //imageView为显示图片的View。
    }

    fun maskBrighten(imageView: ImageView) {
        val brightness = 0 //RGB偏移量，变暗为负数
        val matrix = ColorMatrix()
        matrix.set(floatArrayOf(1f, 0f, 0f, 0f, brightness.toFloat(), 0f, 1f, 0f, 0f, brightness.toFloat(), 0f, 0f, 1f, 0f, brightness.toFloat(), 0f, 0f, 0f, 1f, 0f))
        val cmcf = ColorMatrixColorFilter(matrix)
        imageView.setColorFilter(cmcf) //imageView为显示图片的View。
    }

}