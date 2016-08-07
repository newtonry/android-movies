package com.fadetoproductions.rvkn.flickster.clients;

import com.fadetoproductions.rvkn.flickster.models.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by rnewton on 8/7/16.
 */



public class MovieClient {

    public interface MovieClientListener {
        void onFetchAllMoviesSuccess(int statusCode, Header[] headers, JSONObject response);
        void onFetchAllMoviesFailure(int statusCode, Header[] headers, String responseString, Throwable throwable);
    }

    private MovieClientListener listener;
    private String API_KEY = "?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";


    public MovieClient() {
        this.listener = null;
    }

    public void setMovieClientListener(MovieClientListener listener) {
        this.listener = listener;
    }


    public void fetchAllMovies() {
        String url = "https://api.themoviedb.org/3/movie/now_playing".concat(API_KEY);
        AsyncHttpClient client = new AsyncHttpClient();


        client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                listener.onFetchAllMoviesSuccess(statusCode, headers, response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                listener.onFetchAllMoviesFailure(statusCode, headers, responseString, throwable);
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }

    public void fetchTrailerForMovie(Movie movie) {



    }


}
