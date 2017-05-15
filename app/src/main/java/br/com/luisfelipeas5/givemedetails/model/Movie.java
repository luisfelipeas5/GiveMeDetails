package br.com.luisfelipeas5.givemedetails.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.luisfelipeas5.givemedetails.controllers.MovieController;

public class Movie implements Parcelable {
    @SerializedName("poster_path")
    private String poster;
    @SerializedName("title")
    private String title;
    @SerializedName("overview")
    private String overview;
    @SerializedName("id")
    private String id;
    @SerializedName("original_title")
    private String originalTitle;
    @SerializedName("vote_average")
    private double voteAverage;
    @SerializedName("vote_count")
    private long voteCount;
    @SerializedName("popularity")
    private float popularity;
    @SerializedName("release_date")
    private String releaseDate;

    public String getPoster() {
        return poster;
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public long getVoteCount() {
        return voteCount;
    }

    public float getPopularity() {
        return popularity;
    }

    public Movie() {
    }

    public String getId() {
        return id;
    }

    public Date getReleaseDate() {
        Date date = null;
        SimpleDateFormat simpleDateFormat = MovieController.getSimpleDateFormat();
        try {
            date = simpleDateFormat.parse(releaseDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.poster);
        dest.writeString(this.title);
        dest.writeString(this.overview);
        dest.writeString(this.id);
        dest.writeString(this.originalTitle);
        dest.writeDouble(this.voteAverage);
        dest.writeLong(this.voteCount);
        dest.writeFloat(this.popularity);
        dest.writeString(this.releaseDate);
    }

    protected Movie(Parcel in) {
        this.poster = in.readString();
        this.title = in.readString();
        this.overview = in.readString();
        this.id = in.readString();
        this.originalTitle = in.readString();
        this.voteAverage = in.readDouble();
        this.voteCount = in.readLong();
        this.popularity = in.readFloat();
        this.releaseDate = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
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
