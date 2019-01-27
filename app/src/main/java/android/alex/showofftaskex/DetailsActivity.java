package android.alex.showofftaskex;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;

public class DetailsActivity extends AppCompatActivity {
    private TextView tvbTitle;
    private TextView tvbReleaseYear;
    private TextView tvbRating;
    private  TextView tvbGenre1;
    private  TextView tvbGenre2;
    private TextView tvbGenre3;
    private ImageView ivbPicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        MovieItem movieItem = intent.getParcelableExtra("Movie");

        tvbTitle = (TextView) findViewById(R.id.tvbTitle);
        tvbReleaseYear = (TextView) findViewById(R.id.tvbReleaseYear);
        tvbRating = (TextView) findViewById(R.id.tvbRating);
        tvbGenre1 = (TextView) findViewById(R.id.tvbGenre1);
        tvbGenre2 = (TextView) findViewById(R.id.tvbGenre2);
        tvbGenre3 = (TextView) findViewById(R.id.tvbGenre3);
        ivbPicture = (ImageView) findViewById(R.id.ivbPicture);

        tvbTitle.setText(movieItem.getTitle());
        tvbReleaseYear.setText("Year: " + String.format(Locale.US, "%d", movieItem.getReleaseYear()));
        tvbRating.setText("Rating: " + String.format(Locale.US, "%.1f", movieItem.getRating()));

        ArrayList<String> genresList = movieItem.getGenres();
        int genresListSize =  genresList.size();

        if(genresListSize == 1) {
            tvbGenre1.setText(genresList.get(0));
            tvbGenre2.setText("             ");
            tvbGenre3.setText("             ");
            //tvbGenre2.setVisibility(View.GONE);
            //tvbGenre3.setVisibility(View.GONE);
        }
        if(genresListSize == 2) {
            tvbGenre1.setText(genresList.get(0));
            tvbGenre2.setText(genresList.get(1));
            tvbGenre3.setText("             ");
            //tvbGenre3.setVisibility(View.GONE);
        }
        if(genresListSize == 3) {
            tvbGenre1.setText(genresList.get(0));
            tvbGenre2.setText(genresList.get(1));
            tvbGenre3.setText(genresList.get(2));
        }

        Picasso.with(this).load(movieItem.getImage()).into(ivbPicture);
    }

}
