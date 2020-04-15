package com.nautanki.loginregapp;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;

public class BookActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Book>>
{
    private static String REQUEST_URL = "https://www.googleapis.com/books/v1/volumes?q=";
    SearchView searchView;
    TextView emptyView;
    ProgressBar progressBar;
    LoaderManager loaderManager;
    ListView listView;
    BookAdapter bookAdapter;
    private String requestUrl;





    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);


        //Starting loader in onCreate method so as to save state and restore the list back to the previously scrolled position.
        loaderManager = getSupportLoaderManager();

        // The support library for getSupportManager has a bug which can't find the running the loaders on device rotation
        // so it fetches the data again as opposed to the concept of loaders!

        Log.e("DEBUG", "" + loaderManager.hasRunningLoaders());
        if (loaderManager.getLoader(1) != null) {
            Log.e("DEBUG", "LoaderManager");
            loaderManager.initLoader(1, null, BookActivity.this);
        }
        listView = (ListView) findViewById(R.id.list);

        emptyView = (TextView) findViewById(R.id.empty_view);

        searchView = (SearchView) findViewById(R.id.search_view);
        searchView.setFocusable(false);

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);



        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                ImageView imageView=findViewById(R.id.bg);
                Log.e("DEBUG", "IN ONQUERYSUBMIT");
                searchView.clearFocus();
                Log.e("DEBUG", "INTERNET= " + checkInternetConnection());
                if (checkInternetConnection()) {
                    requestUrl = "";

                    /*if (bookAdapter != null) {
                        Log.e("DEBUG", "IF EXECUTED");
                        loaderManager.destroyLoader(1);
                    }*/


                    imageView.setVisibility(View.GONE);
                    TextView text1=findViewById(R.id.text1);
                    TextView text2=findViewById(R.id.text2);
                    text1.setVisibility(View.GONE);
                    text2.setVisibility(View.GONE);
                    emptyView.setVisibility(View.GONE);
                    progressBar.setVisibility(View.VISIBLE);
                    requestUrl = REQUEST_URL + s.replaceAll(" ", "%20");
                    Log.e("DEBUG", "requestUrl= " + requestUrl);

                    loaderManager.restartLoader(1, null, BookActivity.this);

                } else {
                    TextView text1=findViewById(R.id.text1);
                    TextView text2=findViewById(R.id.text2);
                    text1.setVisibility(View.GONE);
                    text2.setVisibility(View.GONE);
                    imageView.setVisibility(View.VISIBLE);
                    emptyView.setText("No Internet Connection");
                    listView.setEmptyView(emptyView);
                    //To show the empty view
                    if (bookAdapter != null)
                        bookAdapter.clear();
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Book currentBook = bookAdapter.getItem(i);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri bookUri = Uri.parse(currentBook.getmPreviewLink());

                // Create a new intent to view the book URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, bookUri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);


            }
        });
        searchView = (SearchView) findViewById(R.id.search_view);
        searchView.setFocusable(false);

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);

        final TextView text1=(TextView)findViewById(R.id.text1);
        final TextView text2=(TextView)findViewById(R.id.text2);
    }

    public void onBookCheck(View view) {
        CheckBox bookcheck=findViewById(R.id.books_checkbox);
        CheckBox authorcheck =findViewById(R.id.author_checkbox);
        if (authorcheck.isChecked())
        {
            authorcheck.setChecked(false);
            REQUEST_URL = "https://www.googleapis.com/books/v1/volumes?q=";
        }

    }

    public void onAuthorCheck(View view)
    {
        CheckBox bookcheck=findViewById(R.id.books_checkbox);
        CheckBox authorcheck =findViewById(R.id.author_checkbox);
        if(bookcheck.isChecked())
        {
            bookcheck.setChecked(false);
            REQUEST_URL=REQUEST_URL+"+inauthor:";

        }
    }

    private void updateUi(ArrayList<Book> books) {
        bookAdapter = new BookAdapter(this, books);
        listView.setAdapter(bookAdapter);
    }

    @NonNull
    @Override
    public Loader<ArrayList<Book>> onCreateLoader(int id, @Nullable Bundle args) {
        Log.e("DEBUG", "IN onCreateLoader");
        return new BookLoader(this, requestUrl);

    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<Book>> loader, ArrayList<Book> books) {
        Log.e("DEBUG", "IN onLoadFinished" + books);

        // If there is no result,and the app hasn't been just opened then set text to the emptyView.
        if ((books == null || books.isEmpty())) {
            TextView text1=findViewById(R.id.text1);
            TextView text2=findViewById(R.id.text2);
            text1.setVisibility(View.GONE);
            text2.setVisibility(View.GONE);
            ImageView imageView=findViewById(R.id.bg);
            imageView.setVisibility(View.VISIBLE);
            emptyView.setText("No Results Found");
            emptyView.setVisibility(View.VISIBLE);
            if (bookAdapter != null)
                bookAdapter.clear();
            listView.setEmptyView(emptyView);
            progressBar.setVisibility(View.GONE);
            // Destroying the loader here again because after the first No result found search the adapter becomes null
            // and thus if adapter!=null statement above in onQueryTextSubmit method does not execute.
            //loaderManager.destroyLoader(1);
            return;
        }

        //making progressbar invisible after the data is fetched.
        progressBar.setVisibility(View.GONE);


        // Update the information displayed to the user.
        updateUi(books);


    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<Book>> loader) {
        Log.e("DEBUG", "IN onLoaderReset");
        //bookAdapter.clear();
    }

    private boolean checkInternetConnection() {
        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);


        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        } else
            return false;
    }

}
