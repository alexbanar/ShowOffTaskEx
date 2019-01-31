package android.alex.showofftaskex;

import android.alex.showofftaskex.item_model.MovieItem;
import android.alex.showofftaskex.model_sqlite.DAO;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SplashScreenActivity extends AppCompatActivity
                                  implements MovieDataSource.OnDataSavedListener {
    private static String WELCOME_STRING = "Welcome to our site!";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Toast toast = Toast.makeText(this, WELCOME_STRING, Toast.LENGTH_LONG);
        View toastView = toast.getView(); // This'll return the default View of the Toast.

        /* And now we can get the TextView of the default View of the Toast. */
        TextView toastMessage = (TextView) toastView.findViewById(android.R.id.message);
        toastMessage.setTextSize(25);
        toastMessage.setTextColor(Color.RED);
        toastMessage.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_launcher,
                                                            0, 0, 0);
        toastMessage.setGravity(Gravity.CENTER);
        toastMessage.setCompoundDrawablePadding(16);
        toastView.setBackgroundColor(Color.CYAN);
        toast.show();

        //Toast.makeText(this, WELCOME_STRING, Toast.LENGTH_SHORT).show();
        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        DAO.getInstance(this).deleteAllMovies();

        //Go to given site,download the JSON file.
        //Parse it and save to a DB SQLite table
        MovieDataSource.saveMoviesInSQLite(this, this);
    }

    public void onDataSaved(final Exception e)
    {
        if (e == null) {
            Intent intent = new Intent(this, MovieListActivity.class);
            startActivity(intent);
            //finish();

            /*Thread timer = new Thread(){
                public void run(){
                    try{
                        sleep(3000);
                    }catch (InterruptedException e) {
                        e.printStackTrace();
                    }finally{
                        Intent intent = new Intent(SplashScreenActivity1.this, MovieListActivity.class);
                        startActivity(intent);
                    }
                }
            };
            timer.start();*/
        }
        else {
            Toast.makeText(SplashScreenActivity.this,
                        "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}