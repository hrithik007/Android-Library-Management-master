package com.nautanki.loginregapp;

import android.graphics.drawable.Drawable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class QueryUtils {
    public static final String LOG_TAG = QueryUtils.class.getSimpleName();

    private QueryUtils() {
    }

    public static ArrayList<Book> extractBooks(String requestUrl) {
        Log.e("DEBUG", "IN QUERY UTILS");
        // Create an empty ArrayList that we can start adding earthquakes to
        ArrayList<Book> books = new ArrayList<>();

        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            Log.e("DEBUG", "JSON RESPONSE " + jsonResponse);
            JSONObject baseJsonResponse = new JSONObject(jsonResponse);

            if (baseJsonResponse.has("items")) {
                JSONArray bookArray = baseJsonResponse.getJSONArray("items");

                for (int i = 0; i < bookArray.length(); i++) {
                    JSONObject currentBook = bookArray.getJSONObject(i);

                    if (currentBook.has("volumeInfo")) {
                        JSONObject volumeInfo = currentBook.getJSONObject("volumeInfo");

                        String title = "Title not available";
                        if (volumeInfo.has("title")) {
                            title = volumeInfo.optString("title");
                        }

                        String subtitle = "Subtitle not available";
                        if (volumeInfo.has("subtitle")) {
                            subtitle = volumeInfo.optString("subtitle");
                            Log.e(LOG_TAG, "Subtitle");
                        }


                        String authors = "Authors not available";
                        if (volumeInfo.has("authors")) {
                            JSONArray author = volumeInfo.getJSONArray("authors");
                            Log.i("Info", "" + author.length());

                            authors = "";
                            for (int j = 0; j < author.length(); j++) {
                                authors += author.getString(j);
                                if (j != (author.length()) - 1)
                                    authors = authors + ", ";
                            }

                        }

                        Drawable thumbnail = null;
                        if (volumeInfo.has("imageLinks")) {
                            JSONObject imageLinks = volumeInfo.getJSONObject("imageLinks");
                            if (imageLinks.has("smallThumbnail")) {
                                String thumbnailLink = imageLinks.optString("smallThumbnail");
                                thumbnail = LoadImageFromWebOperations(thumbnailLink);
                            }
                        }

                        String previewLink = null;
                        if (volumeInfo.has("previewLink")) {
                            previewLink = volumeInfo.optString("previewLink");
                        }

                        float averageRating = 0.0f;
                        if (volumeInfo.has("averageRating")) {
                            double averageRatingInDouble = volumeInfo.optDouble("averageRating");
                            averageRating = (float) averageRatingInDouble;
                        }

                        Book book = new Book(title, subtitle, authors, thumbnail, previewLink, averageRating);

                        books.add(book);
                    }

                }
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e(LOG_TAG, "Problem parsing the earthquake JSON results", e);
        }

        return books;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e("QueryUtils.java", "Error with creating URL ", e);
        }
        return url;
    }

    private static Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "");
            return d;
        } catch (Exception e) {
            Log.e("Book Adapter", "" + e);
            return null;
        }
    }


}
