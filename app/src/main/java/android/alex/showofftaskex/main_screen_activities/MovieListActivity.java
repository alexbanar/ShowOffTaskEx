package android.alex.showofftaskex.main_screen_activities;

import android.alex.showofftaskex.manipulation_data.MovieAdapter;
import android.alex.showofftaskex.manipulation_data.MovieDataSource;
import android.alex.showofftaskex.R;
import android.alex.showofftaskex.item_model.MovieItem;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;

public class MovieListActivity extends AppCompatActivity implements MovieDataSource.OnDataArrivedListener {

    // /1) find the  Recycler
    private RecyclerView rvMovies;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //1) find the  Recycler
        rvMovies = (RecyclerView) findViewById(R.id.rvMovies);

        //2) and put it into List of Movie Item
        MovieDataSource.getMoviesFromSQLite(this, this);
    }

    @Override
    public void onDataArrived(final ArrayList<MovieItem> movies) {
        //this.runOnUiThread(new Runnable()
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (movies.size() > 0) {
                    //3) layout manager:
                    linearLayoutManager = new LinearLayoutManager(MovieListActivity.this);

                    //4) init the adapter:
                    MovieAdapter adapter = new MovieAdapter(/*getLayoutInflator(), */
                            MovieListActivity.this, movies);

                    //5) give the layout manager to the recycler.
                    rvMovies.setLayoutManager(linearLayoutManager);

                    //6) give the adapter to the recycler.
                    rvMovies.setAdapter(adapter);
                }
                else {
                    Toast.makeText(MovieListActivity.this,
                                   "The table 'Movies' is empty",
                                    Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //DAO.getInstance(this).deleteAllMovies();
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.Logout) {
            //finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

}