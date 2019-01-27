package android.alex.showofftaskex;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MovieDataSource.OnDataArrivedListener {

    // /1) find the  Recycler
    private RecyclerView rvMovies;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //1) find the  Recycler
        rvMovies = (RecyclerView) findViewById(R.id.rvMovies);

        //2) Take data from the file and put it into List of Movie Items
        MovieDataSource.getMovies(this, this);
    }

    @Override
    public void onDataArrived(final ArrayList<MovieItem> movies, final Exception e) {
        //this.runOnUiThread(new Runnable()
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (e == null) {
                    //3) layout manager:
                    linearLayoutManager = new LinearLayoutManager(MainActivity.this);

                    //4) init the adapter:
                    MovieAdapter adapter = new MovieAdapter(/*getLayoutInflator(), */
                            MainActivity.this, movies);

                    //5) give the layout manager to the recycler.
                    rvMovies.setLayoutManager(linearLayoutManager);

                    //6) give the adapter to the recycler.
                    rvMovies.setAdapter(adapter);
                }
                else {
                    Toast.makeText(MainActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });
    }
}