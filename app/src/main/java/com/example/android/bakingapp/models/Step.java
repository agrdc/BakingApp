package com.example.android.bakingapp.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lsitec207.neto on 13/11/17.
 */

public class Step implements Parcelable {

    private int id;
    private String shortDescription;
    private String description;
    private String videoURLString;
    private String thumbnailURLString;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoURLString() {
        return videoURLString;
    }

    public void setVideoURLString(String videoURLString) {
        this.videoURLString = videoURLString;
    }

    public String getThumbnailURLString() {
        return thumbnailURLString;
    }

    public void setThumbnailURLString(String thumbnailURLString) {
        this.thumbnailURLString = thumbnailURLString;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.shortDescription);
        dest.writeString(this.description);
        dest.writeString(this.videoURLString);
        dest.writeString(this.thumbnailURLString);
    }

    public Step() {
    }

    protected Step(Parcel in) {
        this.id = in.readInt();
        this.shortDescription = in.readString();
        this.description = in.readString();
        this.videoURLString = in.readString();
        this.thumbnailURLString = in.readString();
    }

    public static final Parcelable.Creator<Step> CREATOR = new Parcelable.Creator<Step>() {
        @Override
        public Step createFromParcel(Parcel source) {
            return new Step(source);
        }

        @Override
        public Step[] newArray(int size) {
            return new Step[size];
        }
    };
}
