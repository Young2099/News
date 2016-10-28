package com.demo.panguso.mvp_mode.mvp.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${yangfang} on 2016/10/28.
 */

public class PhotoDetail implements Parcelable {
    private String title;
    private List<Picture> pictures = new ArrayList<>();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Picture> getPictures() {
        return pictures;
    }

    public void setPictures(List<Picture> pictures) {
        this.pictures = pictures;
    }

    public static class Picture implements Parcelable {
        private String title;
        private String imgSrc;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImgSrc() {
            return imgSrc;
        }

        public void setImgSrc(String imgSrc) {
            this.imgSrc = imgSrc;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        /**
         * 将对象序列化成一个Parcle对象
         *
         * @param parcel
         * @param i
         */
        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(this.title);
            parcel.writeString(this.imgSrc);
        }

        public Picture() {
        }

        protected Picture(Parcel in) {
            this.title = in.readString();
            this.imgSrc = in.readString();
        }

        public static final Creator<Picture> CREATOR = new Creator<Picture>() {
            @Override
            public Picture createFromParcel(Parcel parcel) {
                return new Picture(parcel);
            }

            @Override
            public Picture[] newArray(int i) {
                return new Picture[i];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.title);
        parcel.writeList(this.pictures);
    }

    public PhotoDetail() {
    }

    public PhotoDetail(Parcel parcel) {
        this.title = parcel.readString();
        this.pictures = new ArrayList<>();
        parcel.readList(this.pictures, Picture.class.getClassLoader());
    }

    public static final Parcelable.Creator<PhotoDetail> CREATOR = new Creator<PhotoDetail>() {
        @Override
        public PhotoDetail createFromParcel(Parcel parcel) {
            return new PhotoDetail(parcel);
        }

        @Override
        public PhotoDetail[] newArray(int i) {
            return new PhotoDetail[i];
        }
    };
}
