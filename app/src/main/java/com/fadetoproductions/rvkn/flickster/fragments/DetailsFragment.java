package com.fadetoproductions.rvkn.flickster.fragments;

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
import android.widget.RatingBar;
import android.widget.TextView;

import com.fadetoproductions.rvkn.flickster.R;
import com.fadetoproductions.rvkn.flickster.models.Movie;

/**
 * Created by rnewton on 8/7/16.
 */

public class DetailsFragment extends DialogFragment {

    Movie movie;
    RatingBar rbRating;
    RatingBar rbPopularity;
    TextView tvDescription;
    TextView tvTitle;

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

}
