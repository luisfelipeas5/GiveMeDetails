package br.com.luisfelipeas5.givemedetails.controllers;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import br.com.luisfelipeas5.givemedetails.R;
import br.com.luisfelipeas5.givemedetails.api.GiveMeDetailsApi;
import br.com.luisfelipeas5.givemedetails.model.Movie;

public class MovieController {

    @BindingAdapter("releaseDate")
    public static void setReleaseDate(TextView textView, Movie movie) {
        if (movie != null) {
            Date releaseDate = movie.getReleaseDate();
            if (releaseDate != null) {
                Context context = textView.getContext();
                String dateFormatPattern = context.getString(R.string.date_format_pattern);
                SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatPattern, Locale.getDefault());
                textView.setText(context.getString(R.string.release_date, dateFormat.format(releaseDate)));
            }
        }
    }

    @BindingAdapter("set_movie_poster")
    public static void setImage(final ImageView imageView, final Movie movie) {
        ViewTreeObserver vto = imageView.getViewTreeObserver();
        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            public boolean onPreDraw() {
                imageView.getViewTreeObserver().removeOnPreDrawListener(this);
                int measuredWidth = imageView.getMeasuredWidth();

                String posterUrl = GiveMeDetailsApi.getImgUrlThumbnail(measuredWidth) + movie.getPoster();
                setImage(imageView, movie, posterUrl);
                return true;
            }
        });
    }

    private static void setImage(ImageView imageView, Movie movie, String url) {
        Glide.with(imageView.getContext())
                .load(url)
                .dontAnimate()
                .into(imageView);
        imageView.setContentDescription(movie.getTitle());
    }

    public static SimpleDateFormat getSimpleDateFormat() {
        return new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    }

}
