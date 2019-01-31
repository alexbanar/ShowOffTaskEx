package android.alex.showofftaskex.model_sqlite;

import android.alex.showofftaskex.item_model.MovieItem;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Data access Object.
 * access db helper
 */

//Singelton is not thread safe.
//Unless we make it thread safe...Synchronized
//Synchronized is not efficient since 99.9% of time
//the instance is not null.
//To fix this we use double checked locking on our singleton
 public class DAO {
    private static DAO sharedInstance;
    //properties
    private SQLiteDatabase db;
    private Context context;
    private DAO(Context context /*No Instances please*/){
        db = new DBHelper(context).getWritableDatabase();
        this.context = context;
    }

    //Factory method
    public static DAO getInstance(Context context) {
        // if no instances were crested? -> new Instance
        //save this one instance

        //double checked locking on singelton
        if(DAO.sharedInstance == null) {
            synchronized (DAO.class) {
                if(DAO.sharedInstance == null) {
                    DAO.sharedInstance = new DAO(context);
                }
            }
        }
        return DAO.sharedInstance;
    }

    /**
     * add to Movies Table
     **/
    public long addMovieItem(String title, int releaseYear, double rating,
                             String genre1, String genre2, String genre3, String image){
        //maps key value pairs
        ContentValues values = new ContentValues();
        values.put(DBHelper.MoviesContract.TBL_MOVIES_COL_TITLE, title);
        values.put(DBHelper.MoviesContract.TBL_MOVIES_COL_RELEASE_YEAR, releaseYear);
        values.put(DBHelper.MoviesContract.TBL_MOVIES_COL_RATING, rating);
        values.put(DBHelper.MoviesContract.TBL_MOVIES_COL_GENRE1, genre1);
        values.put(DBHelper.MoviesContract.TBL_MOVIES_COL_GENRE2, genre2);
        values.put(DBHelper.MoviesContract.TBL_MOVIES_COL_GENRE3, genre3);
        values.put(DBHelper.MoviesContract.TBL_MOVIES_COL_IMAGE, image);

        //prepared statements(with parameters) to avoid SQL Injection
        long lastInsertedID = db.insert(DBHelper.MoviesContract.TBL_MOVIES, null, values);
        return lastInsertedID;
    }

    public ArrayList<MovieItem> getMovieItems(String orderBy){
        ArrayList<MovieItem> movieItems = new ArrayList<>();

        //Query the given table, returning a {@link Cursor} over the result set.
        //Query does SELECT for us
        /*
        public Cursor query(String table, String[] columns, String selection,
            String[] selectionArgs, String groupBy, String having,
            String orderBy) {
         */
        //the result by the cursor will be ordered by 'releaseYear' in extending order
        Cursor cursor = db.query(DBHelper.MoviesContract.TBL_MOVIES,
            /*for order for fields from cursor list*/
            //equivalent to passing 'null' that will return all columns
            new String[]{DBHelper.MoviesContract.TBL_MOVIES_COL_ID,
                    DBHelper.MoviesContract.TBL_MOVIES_COL_TITLE,
                    DBHelper.MoviesContract.TBL_MOVIES_COL_RELEASE_YEAR,
                    DBHelper.MoviesContract.TBL_MOVIES_COL_RATING,
                    DBHelper.MoviesContract.TBL_MOVIES_COL_GENRE1,
                    DBHelper.MoviesContract.TBL_MOVIES_COL_GENRE2,
                    DBHelper.MoviesContract.TBL_MOVIES_COL_GENRE3,
                    DBHelper.MoviesContract.TBL_MOVIES_COL_IMAGE
                    },
            null,
            null,
            null,
            null,
            //String that is passed as a parameter and has to be used as the
            //field for ordering movies(in extending order for now)
                orderBy
             );
        //cursor need to go to the last row in data set for reversing
        //the order of release year in rows to be from new to old (descending order)
        if((cursor != null) && (cursor.moveToLast())){
            //another way for order for fields from cursor list
            int idIDx = cursor.getColumnIndex(DBHelper.MoviesContract.TBL_MOVIES_COL_ID);
            int titleIDx = cursor.getColumnIndex(DBHelper.MoviesContract.TBL_MOVIES_COL_TITLE);
            int releaseYearIDx = cursor.getColumnIndex(DBHelper.MoviesContract.TBL_MOVIES_COL_RELEASE_YEAR);
            int ratingIDx = cursor.getColumnIndex(DBHelper.MoviesContract.TBL_MOVIES_COL_RATING);
            int genre1IDx = cursor.getColumnIndex(DBHelper.MoviesContract.TBL_MOVIES_COL_GENRE1);
            int genre2IDx = cursor.getColumnIndex(DBHelper.MoviesContract.TBL_MOVIES_COL_GENRE2);
            int genre3Dx = cursor.getColumnIndex(DBHelper.MoviesContract.TBL_MOVIES_COL_GENRE3);
            int imageDx = cursor.getColumnIndex(DBHelper.MoviesContract.TBL_MOVIES_COL_IMAGE);


            do {
                int id = cursor.getInt(idIDx);
                String title = cursor.getString(titleIDx);
                int releaseYear = cursor.getInt(releaseYearIDx);
                double rating = cursor.getDouble(ratingIDx);
                String genre1 = cursor.getString(genre1IDx);
                String genre2 = cursor.getString(genre2IDx);
                String genre3 = cursor.getString(genre3Dx);
                String image = cursor.getString(imageDx);

                ArrayList<String> genresArray = new ArrayList<>();
                genresArray.add(genre1);
                if(genre2 != null)
                {
                    genresArray.add(genre2);
                }
                if(genre3 != null)
                {
                    genresArray.add(genre3);
                }

                MovieItem movieItem = new MovieItem(id, title, releaseYear, rating, genresArray, image);
                movieItems.add(movieItem);

            } //The cursor has to move to previous member in data set for achieving the order
             //of release year in rows to be from new to old (descending order)
            while(cursor.moveToPrevious());
        }
        try {
            cursor.close();
        } catch(NullPointerException e) {
            Toast.makeText(context, "Can not close the cursor", Toast.LENGTH_SHORT).show();
        }

        return movieItems;
    }

    public void deleteAllMovies() {
        db.delete(DBHelper.MoviesContract.TBL_MOVIES, null, null);
    }

    public void updateMovies(long movieID, String title, int releaseYear, double rating,
                           String genre1, String genre2, String genre3, String image) {
        //maps key value pairs
        ContentValues values = new ContentValues();
        values.put(DBHelper.MoviesContract.TBL_MOVIES_COL_TITLE, title);
        values.put(DBHelper.MoviesContract.TBL_MOVIES_COL_RELEASE_YEAR, releaseYear);
        values.put(DBHelper.MoviesContract.TBL_MOVIES_COL_RATING, rating);
        values.put(DBHelper.MoviesContract.TBL_MOVIES_COL_GENRE1, genre1);
        values.put(DBHelper.MoviesContract.TBL_MOVIES_COL_GENRE2, genre2);
        values.put(DBHelper.MoviesContract.TBL_MOVIES_COL_GENRE3, genre3);
        values.put(DBHelper.MoviesContract.TBL_MOVIES_COL_IMAGE, image);

        //prepared statements to avoid SQL Injection
        db.update(
                DBHelper.MoviesContract.TBL_MOVIES,
                values,
                //DBHelper.TodosContract.TBL_MOVIES_COL_ID + " = ?",
                DBHelper.MoviesContract.TBL_MOVIES_COL_ID + " = " + movieID,
                //new String[]{String.valueOf(todoID)
                null
                );
    }

    public void deleteMovies(int id) {
        db.delete(DBHelper.MoviesContract.TBL_MOVIES,
                DBHelper.MoviesContract.TBL_MOVIES_COL_ID + " = ?",
                new String[]{String.valueOf(id)}
                );
    }
}
