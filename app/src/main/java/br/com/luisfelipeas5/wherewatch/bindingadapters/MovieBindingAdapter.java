package br.com.luisfelipeas5.wherewatch.bindingadapters;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import br.com.luisfelipeas5.wherewatch.R;
import br.com.luisfelipeas5.wherewatch.model.Movie;

public class MovieBindingAdapter {

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

}
