package com.example.session1.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class AssetGroups implements Parcelable {

    public static final Creator<AssetGroups> CREATOR = new Creator<AssetGroups>() {
        @Override
        public AssetGroups createFromParcel(Parcel in) {
            return new AssetGroups(in);
        }

        @Override
        public AssetGroups[] newArray(int size) {
            return new AssetGroups[size];
        }
    };
    private long id;
    private String name;

    public AssetGroups(long id, String name) {
        this.id = id;
        this.name = name;
    }

    protected AssetGroups(Parcel in) {
        id = in.readInt();
        name = in.readString();
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }
}
