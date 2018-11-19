package com.utils

/**
 * 选择图片设置类
 */
class PhotoSelectOption {

    constructor(gifShow: Boolean, maxSelect: Int) {
        this.gifShow = gifShow
        this.maxSelect = maxSelect
    }

    constructor()

    /*** 是否显示gif图片  默认显示 */
    var gifShow = true
    /*** 最大选择数量 默认9张  */
    var maxSelect = 9
}
