package com.fadetoproductions.rvkn.flickster.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fadetoproductions.rvkn.flickster.R;
import com.fadetoproductions.rvkn.flickster.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

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
        super(context, android.R.layout.simple_list_item_1, movies);
    }

    @Override
    public int getItemViewType(int position) {
        double voteAverage = getItem(position).getVoteAverage();
        if (voteAverage > GOOD_MOVIE_RATING) {
            return MovieItemType.GOOD.ordinal();
        }
        return MovieItemType.AVERAGE.ordinal();
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
        Movie movie = getItem(position);

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
        String imageUrl = selectImageBasedOnOrientation(movie);
        Picasso.with(getContext())
                .load(imageUrl)
                .placeholder(R.drawable.movie_icon_2)
                .transform(new RoundedCornersTransformation(10, 10))
                .into(viewHolder.ivImage);
        return convertView;
    }

    private String selectImageBasedOnOrientation(Movie movie) {
        int orientation = getContext().getResources().getConfiguration().orientation;
        String imageUrl = (orientation == Configuration.ORIENTATION_PORTRAIT) ? movie.getPosterPath() : movie.getBackdropPath();
        return imageUrl;
    }

}
