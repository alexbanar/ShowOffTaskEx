package android.alex.showofftaskex;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder>{
    //RecyclerView.Adapter<SongViewHolder>
    //need:
    //data to know the count. to do binding
    //inflater: to create the view for the viewholder
    //context: to use explicit intents, toasts, and so on.
    private LayoutInflater inflater;
    private Context context;
    private List<MovieItem> data;

    //alt+insert -> Constructor.
    public MovieAdapter(Context context, ArrayList<MovieItem> data) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        //this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.data = data;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.movie_item, parent, false);
        return new MovieViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(final MovieViewHolder holder, int position) {
        MovieItem movie = data.get(position);
        //circular list show
        //MovieItem movie = data.get(position % getActualItemCount());

        //data binding
        //tvTitle.setText(...)
        holder.tvTitle.setText(movie.getTitle());
        holder.tvTitle.post(new Runnable() {
            @Override
            public void run() {
                int lineCount = holder.tvTitle.getLineCount();
                if (lineCount == 1) {
                    String tvTitleString = holder.tvTitle.getText().toString();
                    holder.tvTitle.setText(String.format("%s\n", tvTitleString));
                }
            }
        });

        holder.tvReleaseYear.setText(String.format(Locale.US, "%d", movie.getReleaseYear()));
        holder.tvRating.setText(String.format(Locale.US, "%.1f", movie.getRating()));

        ArrayList<String> genresList = movie.getGenres();
        int genresListSize =  genresList.size();

        if(genresListSize == 1) {
            holder.tvGenre1.setText(genresList.get(0));
            holder.tvGenre2.setText("             ");
            holder.tvGenre3.setText("             ");
            //holder.tvGenre2.setVisibility(View.GONE);
            //holder.tvGenre3.setVisibility(View.GONE);
        }
        if(genresListSize == 2) {
            holder.tvGenre1.setText(genresList.get(0));
            holder.tvGenre2.setText(genresList.get(1));
            holder.tvGenre3.setText("             ");
            //holder.tvGenre3.setVisibility(View.GONE);
        }
        if(genresListSize == 3) {
            holder.tvGenre1.setText(genresList.get(0));
            holder.tvGenre2.setText(genresList.get(1));
            holder.tvGenre3.setText(genresList.get(2));
        }

        Picasso.with(context).load(movie.getImage()).into(holder.ivThumbnail);
    }

    @Override
    public int getItemCount() {
        //how many items? data.size!
        return data.size();
    }


     //No One needs the viewHolder but the Adapter.
     public class MovieViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvTitle;
        private final TextView tvReleaseYear;
        private final TextView tvRating;
        private final TextView tvGenre1;
        private final TextView tvGenre2;
        private final TextView tvGenre3;
        private final ImageView ivThumbnail;

        public MovieViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvReleaseYear = (TextView) itemView.findViewById(R.id.tvReleaseYear);
            tvRating = (TextView) itemView.findViewById(R.id.tvRating);
            tvGenre1 = (TextView) itemView.findViewById(R.id.tvGenre1);
            tvGenre2 = (TextView) itemView.findViewById(R.id.tvGenre2);
            tvGenre3 = (TextView) itemView.findViewById(R.id.tvGenre3);
            ivThumbnail = (ImageView) itemView.findViewById(R.id.ivThumbnail);

            itemView.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     // method of this RecyclerView.ViewHolder object
                     // after click on the appropriate itemView object

                     int position = getAdapterPosition();
                     MovieItem movieItem = data.get(position);

                     Intent intent = new Intent(context, DetailsActivity.class);
                     intent.putExtra("Movie", movieItem);
                     context.startActivity(intent);
                 }
             });
        }
     }
}
