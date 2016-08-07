package com.fadetoproductions.rvkn.flickster;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.fadetoproductions.rvkn.flickster.adapters.MovieArrayAdapter;
import com.fadetoproductions.rvkn.flickster.clients.MovieClient;
import com.fadetoproductions.rvkn.flickster.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MovieActivity extends AppCompatActivity {
    private SwipeRefreshLayout swipeContainer;


    ArrayList<Movie> movies;
    MovieArrayAdapter movieAdapter;
    ListView lvItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        lvItems = (ListView) findViewById(R.id.lvMovies);
        movies = new ArrayList<>();
        movieAdapter = new MovieArrayAdapter(this, movies);
        lvItems.setAdapter(movieAdapter);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchAndSetMovies();
            }
        });

        fetchAndSetMovies();



    }

    private void fetchAndSetMovies() {
        MovieClient movieClient = new MovieClient();
        movieClient.setMovieClientListener(new MovieClient.MovieClientListener() {
            @Override
            public void onFetchAllMoviesSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray movieJsonResults;
                try {
                    movieJsonResults = response.getJSONArray("results");
                    movies.addAll(Movie.fromJSONArray(movieJsonResults));
                    movieAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                swipeContainer.setRefreshing(false);
            }
            @Override
            public void onFetchAllMoviesFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                swipeContainer.setRefreshing(false);
            }

            @Override
            public void onFetchTrailerSuccess(int statusCode, Header[] headers, JSONObject response) {

            }

            @Override
            public void onFetchTrailerFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }
        });

        movieClient.fetchAllMovies();
    }

}
