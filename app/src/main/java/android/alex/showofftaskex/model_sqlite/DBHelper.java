package android.alex.showofftaskex.model_sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
* An sqlite Data Base Helper
*
* create a database
* access the data base
* modify the database as necessary
*/
public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, MoviesContract.DBName, null, MoviesContract.DBversion);
    }

    //will run once, when the database is first created
    //(or may be more one time if all data would be deleted)
    @Override
    public void onCreate(SQLiteDatabase db) {
        //write sql script to create the tables.
        //Execute a single SQL statement that is NOT a SELECT
        //or any other SQL statement that returns data.
        //the SQL statement to be executed. Multiple statements
        //separated by semicolons are not supported.
        db.execSQL("CREATE TABLE " + MoviesContract.TBL_MOVIES + "(" +
                MoviesContract.TBL_MOVIES_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MoviesContract.TBL_MOVIES_COL_TITLE + " TEXT NOT NULL, " +
                MoviesContract.TBL_MOVIES_COL_RELEASE_YEAR + " INTEGER, " +
                MoviesContract.TBL_MOVIES_COL_RATING + " REAL, " +
                MoviesContract.TBL_MOVIES_COL_GENRE1 + " TEXT NOT NULL, " +
                MoviesContract.TBL_MOVIES_COL_GENRE2 + " TEXT," +
                MoviesContract.TBL_MOVIES_COL_GENRE3 + " TEXT, " +
                MoviesContract.TBL_MOVIES_COL_IMAGE + " TEXT NOT NULL" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //modify the database as necessary
    }
    //A Nice Design Pattern for databases:
    public static class MoviesContract {
        /**
         * The Data base Name
         */
        public static final String DBName = "Movies";
        /**
         * The Current Data base version
         */
        public static int DBversion = 2;

        public static String TBL_MOVIES = "Movies";

        public static String TBL_MOVIES_COL_ID = "id";

        public static String TBL_MOVIES_COL_TITLE = "title";

        public static String TBL_MOVIES_COL_RELEASE_YEAR = "releaseYear";

        public static String TBL_MOVIES_COL_RATING = "rating";

        public static String TBL_MOVIES_COL_GENRE1 = "genre1";
        public static String TBL_MOVIES_COL_GENRE2 = "genre2";
        public static String TBL_MOVIES_COL_GENRE3 = "genre3";

        public static String TBL_MOVIES_COL_IMAGE = "image";
     }
}
