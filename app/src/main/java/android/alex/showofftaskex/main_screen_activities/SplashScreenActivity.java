package android.alex.showofftaskex.main_screen_activities;

import android.alex.showofftaskex.manipulation_data.MovieDataSource;
import android.alex.showofftaskex.R;
import android.alex.showofftaskex.model_sqlite.DAO;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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

        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        } catch(NullPointerException e) {
            Toast.makeText(this,
                    "There is problem with the screen arrow back to go to back screen",
                    Toast.LENGTH_SHORT).show();
        }

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        DAO.getInstance(this).deleteAllMovies();

        //Go to given site,download the JSON file.
        //Parse it and save to a DB SQLite table
        MovieDataSource.saveMoviesInSQLite(this, this);
    }

    public void onDataSaved(final Exception e)
    {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (e == null) {
                    Intent intent = new Intent(SplashScreenActivity.this,
                                                MovieListActivity.class);
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
                } else {
                    Toast.makeText(SplashScreenActivity.this,
                            "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}