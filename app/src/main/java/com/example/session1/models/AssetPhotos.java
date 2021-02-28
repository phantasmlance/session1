package com.example.session1.models;

import android.net.Uri;

public class AssetPhotos {

    private Uri uri;
    private String name;

    public AssetPhotos(Uri uri, String name) {
        this.uri = uri;
        this.name = name;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
