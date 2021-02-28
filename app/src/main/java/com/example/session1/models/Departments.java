package com.example.session1.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Departments implements Parcelable {

    public static final Creator<Departments> CREATOR = new Creator<Departments>() {
        @Override
        public Departments createFromParcel(Parcel in) {
            return new Departments(in);
        }

        @Override
        public Departments[] newArray(int size) {
            return new Departments[size];
        }
    };
    private long id;
    private String name;

    public Departments(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Departments(Parcel in) {
        id = in.readInt();
        name = in.readString();
    }

    public Departments() {

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
