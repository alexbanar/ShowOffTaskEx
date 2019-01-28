package android.alex.showofftaskex;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

public class SplashScreenActivity extends AppCompatActivity {
    private static String WELCOME_STRING = "Welcome to our site!";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Toast.makeText(this, WELCOME_STRING, Toast.LENGTH_SHORT).show();
        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

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

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}