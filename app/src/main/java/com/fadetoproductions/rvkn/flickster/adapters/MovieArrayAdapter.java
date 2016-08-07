package com.fadetoproductions.rvkn.flickster.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fadetoproductions.rvkn.flickster.MovieActivity;
import com.fadetoproductions.rvkn.flickster.R;
import com.fadetoproductions.rvkn.flickster.fragments.DetailsFragment;
import com.fadetoproductions.rvkn.flickster.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by rnewton on 8/3/16.
 */
public class MovieArrayAdapter extends ArrayAdapter<Movie> {

    int GOOD_MOVIE_RATING = 6;

    public enum MovieItemType {
        AVERAGE, GOOD
    }

    private static class ViewHolder {
        TextView tvTitle;
        TextView tvOverview;
        ImageView ivImage;
    }

    public MovieArrayAdapter(Context context, List<Movie> movies) {
        super(context, R.layout.item_movie, movies);
    }

    @Override
    public int getItemViewType(int position) {
        Movie movie = getItem(position);
        return movie.isHighlyRated() ? MovieItemType.GOOD.ordinal() : MovieItemType.AVERAGE.ordinal();
    }

    @Override public int getViewTypeCount() {
        return MovieItemType.values().length;
    }

    private View getInflatedLayoutForType(int type) {
        if (type == MovieItemType.AVERAGE.ordinal()) {
            return LayoutInflater.from(getContext()).inflate(R.layout.item_movie, null);
        }
        return LayoutInflater.from(getContext()).inflate(R.layout.popular_movie_item, null);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Movie movie = getItem(position);

        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();

            int type = getItemViewType(position);
            convertView = getInflatedLayoutForType(type);

            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
            viewHolder.tvOverview = (TextView) convertView.findViewById(R.id.tvOverview);
            viewHolder.ivImage = (ImageView) convertView.findViewById(R.id.ivMovieImage);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tvTitle.setText(movie.getOriginalTitle());
        viewHolder.tvOverview.setText(movie.getOverview());
        String imageUrl = selectImageBasedOnOrientationAndRating(movie);

        Picasso.with(getContext())
                .load(imageUrl)
                .placeholder(R.drawable.camera)
                .into(viewHolder.ivImage);


        viewHolder.tvTitle.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMovieFragment(movie);
            }
        });


        return convertView;
    }

    private void showMovieFragment(Movie movie) {
        MovieActivity context = (MovieActivity) getContext();
        DetailsFragment detailsFragment = DetailsFragment.newInstance(movie);
        FragmentManager fm = context.getSupportFragmentManager();
        detailsFragment.show(fm, "details_fragment");
    }


    private String selectImageBasedOnOrientationAndRating(Movie movie) {
        if (movie.isHighlyRated()) {
            // We always want the background for highly rated movies
            return movie.getBackdropPath();
        }

        int orientation = getContext().getResources().getConfiguration().orientation;
        return (orientation == Configuration.ORIENTATION_PORTRAIT) ? movie.getPosterPath() : movie.getBackdropPath();
    }

}
