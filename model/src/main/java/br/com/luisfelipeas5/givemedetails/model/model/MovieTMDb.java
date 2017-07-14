package br.com.luisfelipeas5.givemedetails.model.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MovieTMDb implements Parcelable, Movie {
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

    private DateFormat dateFormat;

    public MovieTMDb() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    }

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

    public String getId() {
        return id;
    }

    public Date getReleaseDate() {
        Date date = null;
        try {
            if (releaseDate != null) {
                date = dateFormat.parse(releaseDate);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setId(String id) {
        this.id = id;
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
        dest.writeSerializable(this.dateFormat);
    }

    private MovieTMDb(Parcel in) {
        this.poster = in.readString();
        this.title = in.readString();
        this.overview = in.readString();
        this.id = in.readString();
        this.originalTitle = in.readString();
        this.voteAverage = in.readDouble();
        this.voteCount = in.readLong();
        this.popularity = in.readFloat();
        this.releaseDate = in.readString();
        this.dateFormat = (DateFormat) in.readSerializable();
    }

    public static final Creator<MovieTMDb> CREATOR = new Creator<MovieTMDb>() {
        @Override
        public MovieTMDb createFromParcel(Parcel source) {
            return new MovieTMDb(source);
        }

        @Override
        public MovieTMDb[] newArray(int size) {
            return new MovieTMDb[size];
        }
    };
}
