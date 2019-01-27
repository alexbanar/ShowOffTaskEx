package android.alex.showofftaskex;


import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


public class MovieDataSource {

    public interface OnDataArrivedListener{
        void onDataArrived(ArrayList<MovieItem> movies, Exception e);
    }

    public static void getMovies(final OnDataArrivedListener listener, final Context context){
        Thread movieThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //Uses-permission INTERNET in manifest
                    String json = StreamIO.readWebSite(
                            "https://api.androidhive.info/json/movies.json#");
                    //InputStream in = context.getAssets().open("movies.txt");
                    //String json = StreamIO.read(in);
                    ArrayList<MovieItem> movies = parse(json);
                    listener.onDataArrived(movies, null);
                } catch (IOException | JSONException e) {
                    listener.onDataArrived(null, e);
                }
            }
        });
        movieThread.start();
    }

    private static ArrayList<MovieItem> parse(String json) throws JSONException {
        //code that runs in the background
        //Uses-permission INTERNET in manifest
        ArrayList<MovieItem> movies = new ArrayList<>();
        //Decide if JSONObject Or JSONArray;
        JSONArray moviesArray = new JSONArray(json);
        for (int i = 0; i < moviesArray.length(); i++) {
            ArrayList<String> genres = new ArrayList<>();
            JSONObject movieObject = moviesArray.getJSONObject(i);
            String title = movieObject.getString("title");
            String image = movieObject.getString("image");
            int releaseYear = movieObject.getInt("releaseYear");
            double rating = movieObject.getDouble("rating");
            JSONArray genresArray = movieObject.getJSONArray("genre");
            for (int j = 0; j < genresArray.length(); j++) {
                String genre = genresArray.getString(j);
                genres.add(genre);
            }
            MovieItem movieItem = new MovieItem(title, releaseYear, rating, genres, image);
            movies.add(movieItem);
        }

        return movies;
    }
}