package com.vbhook2.movies;

/**
 * Created by vbhook2 on 3/26/17.
 */

public class MovieCollection {
    //necessary for the getResults method, which converts the json data into a usable array
    private Movie[] results;

    /**
     * Takes the json data and gives us what is stored under the key "results",
     * which, in this case, is an array of movies
     * @return Array of MovieObjects results
     */
    public Movie[] getResults() {
        return results;
    }
}
