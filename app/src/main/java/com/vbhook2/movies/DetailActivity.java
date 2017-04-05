package com.vbhook2.movies;

/**
 * Created by vbhook2 on 4/4/17.
 */


import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class DetailActivity extends AppCompatActivity {

    private TextView mTitleTextView;
    private TextView mReleaseDateTextView;
    private TextView mPopularityTextView;
    private TextView mVoteAverageTextView;
    private TextView mOverviewTextView;
    private TextView mSearchQueryTextView;
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // get references to the views.
        mTitleTextView = (TextView) findViewById(R.id.titleTextView);
        mReleaseDateTextView = (TextView) findViewById(R.id.releaseDateTextView);
        mPopularityTextView = (TextView) findViewById(R.id.popularityTextView);
        mVoteAverageTextView = (TextView) findViewById(R.id.voteAverageTextView);
        mOverviewTextView = (TextView) findViewById(R.id.overviewTextView);
        mSearchQueryTextView = (TextView) findViewById(R.id.searchQueryTextView);
        mImageView = (ImageView) findViewById(R.id.imageView);

        //Get the corresponding intent attributes from MovieAdapter
        final String title = getIntent().getStringExtra(MovieAdapter.TITLE);
        final String releaseDate = getIntent().getStringExtra(MovieAdapter.RELEASE_DATE);
        final String popularity = getIntent().getStringExtra(MovieAdapter.POPULARITY);
        final String voteAverage = getIntent().getStringExtra(MovieAdapter.VOTE_AVERAGE);
        final String overview = getIntent().getStringExtra(MovieAdapter.OVERVIEW);
        final String searchQuery = getIntent().getStringExtra(MovieAdapter.SEARCH_QUERY);
        final String imgURL = getIntent().getStringExtra(MovieAdapter.IMG_URL);

        //Set the collected attributes to their corresponding views
        mTitleTextView.setText(title);
        mReleaseDateTextView.setText(releaseDate);
        mPopularityTextView.setText(popularity);
        mVoteAverageTextView.setText(voteAverage);
        mOverviewTextView.setText(overview);
        mSearchQueryTextView.setText(searchQuery);
        Picasso.with(mImageView.getContext()).load(imgURL).into(mImageView);

        /**
         * This handy part is a button which allows you to share the title of the current movie and
         * a link to its search query. The information is collected as a text body, and can be
         * sent through any app which supports sharing of text. So,
         * snapchat, facebook, text messaging, email, etc
         */
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent share = new Intent(Intent.ACTION_SEND);
                    //You can now share the message through multiple text-sharing apps
                    share.setType("text/plain");
                    //Text's subject
                    share.putExtra(Intent.EXTRA_SUBJECT,
                            "Here's a movie I'm interested in!");
                    //Text's greeting text
                    share.putExtra(Intent.EXTRA_TEXT, title + ":\n" + searchQuery);

                    startActivity(Intent.createChooser(share, "Share:"));
            }
        });
    }
}
