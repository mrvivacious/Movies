package com.vbhook2.movies;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String urlString = "https://api.themoviedb.org/3/movie/popular?api_key=";
    public static final String page = "&page=";

    private RecyclerView mRecyclerView;
    private ArrayList<Movie> mMovies = new ArrayList<>();
    private MovieAdapter mMovieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        // Initially, mMovies is an empty ArrayList.  We populate it with MovieAsyncTask.
        mMovieAdapter = new MovieAdapter(mMovies);
        mRecyclerView.setAdapter(mMovieAdapter);

        // Construct URL and request data...
        try {
            URL[] url = {  new URL(urlString + MovieApi.API_KEY + page + "1"),
                           new URL(urlString + MovieApi.API_KEY + page + "2"),
                           new URL(urlString + MovieApi.API_KEY + page + "3"),
                           new URL(urlString + MovieApi.API_KEY + page + "4"),
                           new URL(urlString + MovieApi.API_KEY + page + "5"),
                           new URL(urlString + MovieApi.API_KEY + page + "6"),
                           new URL(urlString + MovieApi.API_KEY + page + "7"),};

            // Fetch the news on a background thread; it will populate mMovies
            //iterate through the array of urls to fetch x >= 100 movies
            int firstUrl = 0;
            int urlLength = url.length;
            for (int urlIndex = firstUrl; urlIndex < urlLength; urlIndex++) {
                 new MovieAsyncTask(this).execute(url[urlIndex]);
            }
        } catch (MalformedURLException e) {
            // OK to dump stack trace here.  Should never fire once we've debugged app.
            e.printStackTrace();
        }
    }

    /**
     * Taken from Zilles
     *
     * This class (1) takes a URL, (2) makes an HTTP request, (3) parses the resulting JSON
     * into a MovieCollection, and (4) returns the array of Movies.
     *
     * This class requires a Context in its constructor so that we can make Toasts.
     */
    public class MovieAsyncTask extends AsyncTask<URL, Void, Movie[]> {

        Context context;

        public MovieAsyncTask(Context context) {
            this.context = context;
        }

        // This function is done on the background thread.
        @Override
        protected Movie[] doInBackground(URL... params) {

            try {
                URL url = params[0];
                URLConnection connection = url.openConnection();
                connection.connect();

                InputStream inStream = connection.getInputStream();
                InputStreamReader inStreamReader = new InputStreamReader(inStream, Charset.forName("UTF-8"));

                Gson gson = new Gson();
                MovieCollection movieCollection = gson.fromJson(inStreamReader, MovieCollection.class);
                return movieCollection.getResults();

            } catch (IOException e) {
                Log.d("NewsAsyncTask", "failed to get data from network", e);
                return null;
            }
        }

        // This code is run on the UI thread
        @Override
        protected void onPostExecute(Movie[] movies) {

            // Pop up a Toast if we failed to get data.
            if (movies == null) {
                Toast.makeText(context, "Failed to get network data", Toast.LENGTH_LONG).show();
                return;
            }

            //Populate screen with collected movies
            for (Movie movie : movies) {
                Log.d("MOVIES", movie.getTitle());
                mMovies.add(movie);
            }

            // Poke mMovieAdapter to let it know that its data changed.
            mMovieAdapter.notifyDataSetChanged();
        }
    }
}
