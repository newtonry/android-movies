package com.fadetoproductions.rvkn.flickster.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by rnewton on 8/3/16.
 */
public class Movie  {

    int GOOD_MOVIE_RATING = 6;

    public String getBackdropPath() {
        return String.format("https://image.tmdb.org/t/p/w684/%s", backdropPath);
    }

    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", posterPath);
    }

    public String getOriginalTitle() {
        return originalTitle;
    }
    public String getOverview() {
        return overview;
    }
    public double getPopularity() { return popularity; }
    public double getVoteAverage() { return voteAverage; }
    public int getId() { return id; }

    int id;
    String backdropPath;
    String posterPath;
    String originalTitle;
    String overview;
    double popularity;
    double voteAverage;

    public Movie(JSONObject jsonObject) throws JSONException {
        this.id = jsonObject.getInt("id");
        this.backdropPath = jsonObject.getString("backdrop_path");
        this.posterPath = jsonObject.getString("poster_path");
        this.originalTitle = jsonObject.getString("original_title");
        this.overview = jsonObject.getString("overview");
        this.popularity = jsonObject.getDouble("popularity");
        this.voteAverage = jsonObject.getDouble("vote_average");

    }

    public Boolean isHighlyRated() {
        return voteAverage > GOOD_MOVIE_RATING;

    }


    public static ArrayList<Movie> fromJSONArray(JSONArray jsonArray) {
        ArrayList<Movie> movies = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            Movie movie = null;
            try {
                movie = new Movie(jsonArray.getJSONObject(i));
                movies.add(movie);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return movies;
    }

}
