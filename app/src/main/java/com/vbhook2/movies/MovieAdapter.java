package com.vbhook2.movies;

/**
 * Created by vbhook2 on 3/26/17.
 */

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

/**
 * Taken from Zilles
 *
 * This fills the RecyclerView with the NewsArticle data that we loaded.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    public static final String TITLE = "TITLE";
    public static final String RELEASE_DATE = "RELEASE_DATE";
    public static final String OVERVIEW = "OVERVIEW";
    public static final String IMG_URL = "IMG_URL";
    public static final String SEARCH_QUERY = "SEARCH_QUERY";
    public static final String POPULARITY = "POPULARITY";
    public static final String VOTE_AVERAGE = "VOTE_AVERAGE";
    ArrayList<Movie> movies;

    public MovieAdapter(ArrayList<Movie> movies) {
        this.movies = movies;
    }

    /**
     * This function is called only enough times to cover the screen with views.  After
     * that point, it recycles the views when scrolling is done.
     * @param parent the intended parent object (our RecyclerView)
     * @param viewType unused in our function (enables having different kinds of views in the same RecyclerView)
     * @return the new ViewHolder we allocate
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // a LayoutInflater turns a layout XML resource into a View object.
        final View newsListItem = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.news_list_item, parent, false);
        return new ViewHolder(newsListItem);
    }

    /**
     * This function gets called each time a ViewHolder needs to hold data for a different
     * position in the list.  We don't need to create any views (because we're recycling), but
     * we do need to update the contents in the views.
     * @param holder the ViewHolder that knows about the Views we need to update
     * @param position the index into the array of NewsArticles
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Movie movie = movies.get(position);

        holder.titleView.setText(movie.getTitle());
        holder.releaseDateView.setText( movie.getReleaseDate());
        Picasso.with(holder.imageView.getContext())
                .load(movie.getPosterPath()).into(holder.imageView);

        // Attach a click listener that launches a new Activity
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code for launching an Explicit Intent to go to another Activity in
                // the same App.
                Intent intent = new Intent(v.getContext(), DetailActivity.class);

                Movie movie = movies.get(position);
                //Collect the attributes of the selected movie and pass them into an intent
                //We will then send this attributes to the DetailActivity class for assignments
                intent.putExtra(TITLE, movie.getTitle());
                intent.putExtra(RELEASE_DATE, movie.getReleaseDate());
                intent.putExtra(POPULARITY, movie.getPopularity());
                intent.putExtra(OVERVIEW, movie.getOverview());
                intent.putExtra(SEARCH_QUERY, movie.getSearch_query());
                intent.putExtra(VOTE_AVERAGE, movie.getVote_average());
                intent.putExtra(IMG_URL, movie.getPosterPath());

                v.getContext().startActivity(intent);
            }
        });
    }

    /**
     * RecyclerView wants to know how many list items there are, so it knows when it gets to the
     * end of the list and should stop scrolling.
     * @return the number of NewsArticles in the array.
     */
    @Override
    public int getItemCount() {
        return movies.size();
    }

    /**
     * A ViewHolder class for our adapter that 'caches' the references to the
     * subviews, so we don't have to look them up each time.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public TextView titleView;
        public TextView releaseDateView;
        public ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            titleView = (TextView) itemView.findViewById(R.id.titleTextView);
            releaseDateView = (TextView) itemView.findViewById(R.id.releaseDateTextView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }
}