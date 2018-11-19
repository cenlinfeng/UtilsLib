package com.entity

import android.os.Parcel
import android.os.Parcelable


class PhotoSelectBean : Parcelable {
    var isCheckd: Boolean = false
    var path: String? = null

    constructor(checkd: Boolean, path: String) {
        this.isCheckd = checkd
        this.path = path
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeByte(if (this.isCheckd) 1.toByte() else 0.toByte())
        dest.writeString(this.path)
    }

    protected constructor(`in`: Parcel) {
        this.isCheckd = `in`.readByte().toInt() != 0
        this.path = `in`.readString()
    }

    companion object {


        @JvmField
        val CREATOR: Parcelable.Creator<PhotoSelectBean> = object : Parcelable.Creator<PhotoSelectBean> {
            override fun createFromParcel(source: Parcel): PhotoSelectBean {
                return PhotoSelectBean(source)
            }

            override fun newArray(size: Int): Array<PhotoSelectBean?> {
                return arrayOfNulls(size)
            }
        }
    }
}
