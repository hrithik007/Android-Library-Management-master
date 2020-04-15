package com.nautanki.loginregapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

public class BookAdapter extends ArrayAdapter<Book> {

    public BookAdapter(Context context, List<Book> books) {
        super(context, 0, books);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Log.e("DEBUG","IN BOOK ADAPTER");

        // Check if there is an existing list item view (called convertView) that we can reuse,
        // otherwise, if convertView is null, then inflate a new list item layout.
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.book_list_item, parent, false);
        }

        Book currentBook = getItem(position);

        ImageView thumbnail = (ImageView) listItemView.findViewById(R.id.thumbnail);
        thumbnail.setImageDrawable(currentBook.getmThumbnail());


        TextView title = (TextView) listItemView.findViewById(R.id.title);
        title.setText(currentBook.getmTittle());

        TextView subtitle = (TextView) listItemView.findViewById(R.id.subtitle);
        subtitle.setText(currentBook.getmSubtitle());

        TextView author = (TextView) listItemView.findViewById(R.id.author);
        author.setText(currentBook.getmAuthors());

        RatingBar ratingBar = (RatingBar) listItemView.findViewById(R.id.rating_bar);
        ratingBar.setRating(currentBook.getmAverageRating());

        // Return the list item view that is now showing the appropriate data
        return listItemView;


    }
}
