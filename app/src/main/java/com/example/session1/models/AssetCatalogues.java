package com.example.session1.models;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class AssetCatalogues implements Parcelable {

    public static final Creator<AssetCatalogues> CREATOR = new Creator<AssetCatalogues>() {
        @Override
        public AssetCatalogues createFromParcel(Parcel in) {
            return new AssetCatalogues(in);
        }

        @Override
        public AssetCatalogues[] newArray(int size) {
            return new AssetCatalogues[size];
        }
    };
    private String assetname, departmentname, assetsn;
    private Bitmap assetphoto;

    public AssetCatalogues(String assetname, String departmentname, String assetsn, Bitmap assetphoto) {
        this.assetname = assetname;
        this.departmentname = departmentname;
        this.assetsn = assetsn;
        this.assetphoto = assetphoto;
    }

    protected AssetCatalogues(Parcel in) {
        assetname = in.readString();
        departmentname = in.readString();
        assetsn = in.readString();
    }

    public Bitmap getAssetphoto() {
        return assetphoto;
    }

    public void setAssetphoto(Bitmap assetphoto) {
        this.assetphoto = assetphoto;
    }

    public String getAssetname() {
        return assetname;
    }

    public void setAssetname(String assetname) {
        this.assetname = assetname;
    }

    public String getDepartmentname() {
        return departmentname;
    }

    public void setDepartmentname(String departmentname) {
        this.departmentname = departmentname;
    }

    public String getAssetsn() {
        return assetsn;
    }

    public void setAssetsn(String assetsn) {
        this.assetsn = assetsn;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(assetname);
        dest.writeString(departmentname);
        dest.writeString(assetsn);
    }

    @NonNull
    @Override
    public String toString() {
        return assetname;
    }
}
