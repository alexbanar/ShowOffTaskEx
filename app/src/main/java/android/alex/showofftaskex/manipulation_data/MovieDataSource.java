package android.alex.showofftaskex.manipulation_data;


import android.alex.showofftaskex.StreamIO;
import android.alex.showofftaskex.item_model.MovieItem;
import android.alex.showofftaskex.model_sqlite.DAO;
import android.alex.showofftaskex.model_sqlite.DBHelper;
import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MovieDataSource {

    public interface OnDataSavedListener{
        void onDataSaved(Exception e);
    }
    public interface OnDataArrivedListener{
        void onDataArrived(ArrayList<MovieItem> movies);
    }

    private final static String SITE_DATA_ADDRESS = "https://api.androidhive.info/json/movies.json#" ;

    private static DAO dao;

    public static void saveMoviesInSQLite(final OnDataSavedListener listener, final Context context){
        Thread movieThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //Uses-permission INTERNET in manifest
                    String json = StreamIO.readWebSite(SITE_DATA_ADDRESS);
                    //InputStream in = context.getAssets().open("movies.txt");
                    //String json = StreamIO.read(in);
                    parseAndSave(json, context);
                    listener.onDataSaved(null);
                } catch (IOException | JSONException e) {
                    listener.onDataSaved(e);
                }
            }
        });
        movieThread.start();
    }

    private static void parseAndSave(String json, Context context) throws JSONException {
        //code that runs in the background
        //Uses-permission INTERNET in manifest
        //Create
        dao = DAO.getInstance(context);
        ArrayList<MovieItem> movies = new ArrayList<>();
        //Decide if JSONObject Or JSONArray;
        JSONArray moviesArray = new JSONArray(json);
        for (int i = 0; i < moviesArray.length(); i++) {
            JSONObject movieObject = moviesArray.getJSONObject(i);
            String title = movieObject.getString("title");
            String image = movieObject.getString("image");
            int releaseYear = movieObject.getInt("releaseYear");
            double rating = movieObject.getDouble("rating");
            JSONArray genresArray = movieObject.getJSONArray("genre");

            String genre1 = null;
            String genre2 = null;
            String genre3 = null;
            if (genresArray.length() == 1) {
                genre1 = genresArray.getString(0);
            }
            if (genresArray.length() == 2) {
                genre1 = genresArray.getString(0);
                genre2 = genresArray.getString(1);
                ;
            }
            if (genresArray.length() == 3) {
                genre1 = genresArray.getString(0);
                genre2 = genresArray.getString(1);
                genre3 = genresArray.getString(2);
            }

            dao.addMovieItem(title, releaseYear, rating, genre1, genre2, genre3, image);
        }
    }

    public static void getMoviesFromSQLite(final OnDataArrivedListener listener, final Context context){
        Thread movieThread = new Thread(new Runnable() {
            @Override
            public void run() {
                dao = DAO.getInstance(context);
                ArrayList<MovieItem> movies =
                        dao.getMovieItems(DBHelper.MoviesContract.TBL_MOVIES_COL_RELEASE_YEAR);
                listener.onDataArrived(movies);
            }
        });
        movieThread.start();
    }
}