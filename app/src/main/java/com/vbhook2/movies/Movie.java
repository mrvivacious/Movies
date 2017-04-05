package com.vbhook2.movies;

/**
 * Created by vbhook2 on 3/26/17.
 */

/**
 * This class is a constructor for the movies we will parse from the json file
 */
public class Movie {
    //desired attributes to collect from json
    private String title;
    private String poster_path;
    private String release_date;
    private String overview;
    private String popularity;
    private String vote_average;

    private String search_query;


    /**
     * Gets title
     *
     * @return String title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets poster_path
     *
     * @return String path to the poster of some movie
     */
    public String getPosterPath() {
        String imageUrl = "https://image.tmdb.org/t/p/w500";
        return imageUrl + poster_path;
    }

    /**
     * Gets date of release
     *
     * @return String release date
     */
    public String getReleaseDate() {
        return release_date;
    }

    /**
     * Gets the overview of the movie
     *
     * @return String overview of selected movie
     */
    public String getOverview() {
        return overview;
    }

    /**
     * Gets the popularity of the movie
     * @return String popularity value
     */
    public String getPopularity() {
        return "" + popularity;
    }

    public String getVote_average() {
        return "" + vote_average;
    }

    /**
     * We take the title of the movie and generate a The Movie Database search request
     * in the form of a url
     * @return String The url for a The Movie Database search query of any movie
     */
    public String getSearch_query() {
        String searchUrl = "https://www.themoviedb.org/search?query=";
        String title = this.getTitle();
        String newTitle = "";
        String plusSymbol = "+";
        char space = ' ';


        int titleLength = title.length();

        int firstIndex = 0;
        for (int addIndex = firstIndex; addIndex < titleLength; addIndex++) {
            //If the character is a space, add a plus symbol. The plus symbol parallels the query if done on the website itself
            if(title.charAt(addIndex) == space) {
                newTitle += plusSymbol;
            }
            //No space, no problem
            else {
                newTitle += title.charAt(addIndex);
            }
        }
        search_query = searchUrl + newTitle;
        return search_query;
    }
}