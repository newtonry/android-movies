package com.fadetoproductions.rvkn.flickster.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.fadetoproductions.rvkn.flickster.QuickPlayActivity;
import com.fadetoproductions.rvkn.flickster.R;
import com.fadetoproductions.rvkn.flickster.clients.MovieClient;
import com.fadetoproductions.rvkn.flickster.models.Movie;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by rnewton on 8/7/16.
 */

public class DetailsFragment extends DialogFragment {

    Movie movie;
    RatingBar rbRating;
    RatingBar rbPopularity;
    TextView tvDescription;
    TextView tvTitle;
    ImageView ivMovieImage;
    ImageView ivPlayTrailer;
    String trailerSource;


    public static  DetailsFragment newInstance(Movie movie) {
        DetailsFragment fragment = new DetailsFragment();
        fragment.movie = movie;
        return fragment;
    }

    // The onCreateView method is called when Fragment should create its View object hierarchy,
    // either dynamically or via XML layout inflation.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return inflater.inflate(R.layout.details_fragment, parent, false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        rbRating = (RatingBar) view.findViewById(R.id.rbRating);
        rbRating.setRating((float) (movie.getVoteAverage() / 2.0));  // Assumes the max rating is 10
        rbPopularity = (RatingBar) view.findViewById(R.id.rbPopularity);
        rbPopularity.setRating((float) (movie.getPopularity() * 5.0 / 35.0));  // I'm just making these numbers up here
        setStarColors(rbRating);
        setStarColors(rbPopularity);

        tvDescription = (TextView) view.findViewById(R.id.tvDescription);
        tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        tvTitle.setText(movie.getOriginalTitle());
        tvDescription.setText(movie.getOverview());

        ivPlayTrailer = (ImageView) view.findViewById(R.id.ivPlayTrailer);
        ivPlayTrailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadTrailer();
            }
        });

        ivMovieImage = (ImageView) view.findViewById(R.id.ivMovieImage);
        Picasso.with(getContext())
                .load(movie.getBackdropPath())
                .placeholder(R.drawable.camera)
                .transform(new RoundedCornersTransformation(10, 10))
                .into(ivMovieImage);

        setUpTrailer();
    }

    @Override
    public void onResume() {
        // Store access variables for window and blank point
        Window window = getDialog().getWindow();
        Point size = new Point();
        // Store dimensions of the screen in `size`
        Display display = window.getWindowManager().getDefaultDisplay();
        display.getSize(size);
        // Set the width of the dialog proportional to 75% of the screen width
        window.setLayout((int) (size.x * 0.8), (int) (size.y * 0.5));
        window.setGravity(Gravity.CENTER);
        // Call super onResume after sizing
        super.onResume();
    }

    private void setStarColors(RatingBar ratingBar) {
        LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);
    }

    private void setUpTrailer() {
        MovieClient movieClient = new MovieClient();
        movieClient.setMovieClientListener(new MovieClient.MovieClientListener() {
            @Override
            public void onFetchAllMoviesSuccess(int statusCode, Header[] headers, JSONObject response) {
                // Is there a way to avoid having to set all of these?
            }

            @Override
            public void onFetchAllMoviesFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                // Is there a way to avoid having to set all of these?
            }

            @Override
            public void onFetchTrailerSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray youtubeVideos = response.getJSONArray("youtube");
                    if (youtubeVideos.length() > 0) {
                        JSONObject firstTrailer = youtubeVideos.getJSONObject(0);
                        trailerSource = firstTrailer.getString("source");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFetchTrailerFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
            }
        });

        movieClient.fetchTrailerForMovie(movie);
    }

    private void loadTrailer() {
        Intent launchQuickPlayActivity = new Intent(getActivity(), QuickPlayActivity.class);
        launchQuickPlayActivity.putExtra("source", trailerSource);
        startActivityForResult(launchQuickPlayActivity, 100);
    }
}
