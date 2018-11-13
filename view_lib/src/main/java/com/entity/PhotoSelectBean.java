package com.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class PhotoSelectBean implements Parcelable {
    private boolean checkd;
    private String path;
    
    public PhotoSelectBean(boolean checkd, String path) {
        this.checkd = checkd;
        this.path = path;
    }
    
    public boolean isCheckd() {
        return checkd;
    }
    
    public void setCheckd(boolean checkd) {
        this.checkd = checkd;
    }
    
    public String getPath() {
        return path;
    }
    
    public void setPath(String path) {
        this.path = path;
    }
    
    @Override
    public int describeContents() {
        return 0;
    }
    
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.checkd ? (byte) 1 : (byte) 0);
        dest.writeString(this.path);
    }
    
    protected PhotoSelectBean(Parcel in) {
        this.checkd = in.readByte() != 0;
        this.path = in.readString();
    }
    
    public static final Parcelable.Creator<PhotoSelectBean> CREATOR = new Parcelable.Creator<PhotoSelectBean>() {
        @Override
        public PhotoSelectBean createFromParcel(Parcel source) {
            return new PhotoSelectBean(source);
        }
        
        @Override
        public PhotoSelectBean[] newArray(int size) {
            return new PhotoSelectBean[size];
        }
    };
}
