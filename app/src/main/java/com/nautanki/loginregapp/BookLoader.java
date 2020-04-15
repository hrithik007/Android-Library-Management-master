package com.nautanki.loginregapp;


import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.util.ArrayList;

public class BookLoader extends AsyncTaskLoader<ArrayList<Book>> {
    ArrayList<Book> books;
    private String mUrl;

    public BookLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        Log.e("DEBUG", "IN onStartLoading");

        books = new ArrayList<>();
        forceLoad();
    }

    @Override
    public ArrayList<Book> loadInBackground() {
        Log.e("DEBUG", "IN loadInBackground " + mUrl);

        // Don't perform the request if there are no URLs, or the first URL is null.
        if (mUrl == null) {
            return null;
        }

        // Perform the HTTP request for book data and process the response.
        // Get the list of books from {@link QueryUtils}

        books = QueryUtils.extractBooks(mUrl);
        if (books.isEmpty())
            Log.e("DEBUG", "Book is null");

        return books;
    }

}
