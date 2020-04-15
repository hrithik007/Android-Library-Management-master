package com.nautanki.loginregapp;

import android.graphics.drawable.Drawable;

public class Book {
    private String mTitle;
    private String mSubtitle;
    private String mAuthors;
    //private String mThumbnailUrl;
    private String mPreviewLink;
    private float mAverageRating;
    private Drawable mThumbnail;

    public Book(String title, String subtitle, String authors, Drawable thumbnail, String previewLink, float averageRating) {
        mTitle = title;
        mSubtitle = subtitle;
        mAuthors = authors;
        mThumbnail = thumbnail;
        mPreviewLink = previewLink;
        mAverageRating = averageRating;
    }

    public String getmTittle() {
        return mTitle;
    }

    public String getmSubtitle() {
        return mSubtitle;
    }

    public String getmAuthors() {
        return mAuthors;
    }

    public Drawable getmThumbnail() {
        return mThumbnail;
    }

    public float getmAverageRating() {
        return mAverageRating;
    }

    public String getmPreviewLink() {
        return mPreviewLink;
    }
}
