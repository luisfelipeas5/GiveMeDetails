package br.com.luisfelipeas5.wherewatch.model;

import android.databinding.BindingAdapter;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.gson.annotations.SerializedName;

import br.com.luisfelipeas5.wherewatch.api.WhereWatchApi;

public class Movie implements Parcelable {
    @SerializedName("poster_path")
    private String poster;
    @SerializedName("title")
    private String title;

    private String getPoster() {
        return poster;
    }

    public String getTitle() {
        return title;
    }

    @BindingAdapter("set_movie_thumbnail")
    public static void setImageAsThumbnail(ImageView imageView, Movie movie) {
        String posterUrl = WhereWatchApi.IMG_BASE_URL_THUMBNAIL + movie.getPoster();
        setImage(imageView, movie, posterUrl);
    }

    @BindingAdapter("set_movie")
    public static void setImage(ImageView imageView, Movie movie) {
        String posterUrl = WhereWatchApi.IMG_BASE_URL_ORIGINAL + movie.getPoster();
        setImage(imageView, movie, posterUrl);
    }

    private static void setImage(ImageView imageView, Movie movie, String url) {
        Glide.with(imageView.getContext())
                .load(url)
                .dontAnimate()
                .into(imageView);
        imageView.setContentDescription(movie.getTitle());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.poster);
        dest.writeString(this.title);
    }

    protected Movie(Parcel in) {
        this.poster = in.readString();
        this.title = in.readString();
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
