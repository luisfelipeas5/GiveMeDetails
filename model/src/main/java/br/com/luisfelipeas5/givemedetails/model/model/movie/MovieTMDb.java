package br.com.luisfelipeas5.givemedetails.model.model.movie;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Entity(tableName = "movies")
public class MovieTMDb implements Parcelable, Movie {
    @Ignore
    private static final int[] IMAGE_WIDTH_AVAILABLE = {92, 154, 185, 342, 500, 780};
    @Ignore
    private static final String[] IMAGE_PATH_WIDTH_AVAILABLE = {"w92", "w154", "w185", "w342", "w500", "w780", "original"};
    @Ignore
    private static final String IMG_AUTHORITY = "image.tmdb.org";

    @PrimaryKey
    @SerializedName("id")
    private String id;
    @SerializedName("poster_path")
    private String posterSuffix;
    @SerializedName("title")
    private String title;
    @SerializedName("overview")
    private String overview;
    @SerializedName("original_title")
    private String originalTitle;
    @SerializedName("vote_average")
    private Double voteAverage;
    @SerializedName("vote_count")
    private Long voteCount;
    @SerializedName("popularity")
    private Float popularity;
    @SerializedName("release_date")
    private String releaseDate;

    @Ignore
    private DateFormat dateFormat;

    public MovieTMDb() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    }

    @Override
    public String getPoster() {
        return getPoster(IMAGE_WIDTH_AVAILABLE[IMAGE_WIDTH_AVAILABLE.length - 1] + 1);
    }

    @Override
    public String getPoster(int posterWidth) {
        if (posterSuffix == null) {
            return null;
        }
        String measuredPath = null;
        for (int i = 0; i < IMAGE_WIDTH_AVAILABLE.length; i++) {
            int widthAvailable = IMAGE_WIDTH_AVAILABLE[i];
            if (posterWidth <= widthAvailable) {
                measuredPath = IMAGE_PATH_WIDTH_AVAILABLE[i];
                break;
            }
        }
        if (measuredPath == null) {
            measuredPath = IMAGE_PATH_WIDTH_AVAILABLE[IMAGE_PATH_WIDTH_AVAILABLE.length - 1];
        }

        return String.format("http://" + IMG_AUTHORITY + "/t/p/%s%s", measuredPath, posterSuffix);
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getOverview() {
        return overview;
    }

    @Override
    public String getOriginalTitle() {
        return originalTitle;
    }

    @Override
    public Double getVoteAverage() {
        return voteAverage;
    }

    @Override
    public Long getVoteCount() {
        return voteCount;
    }

    @Override
    public Float getPopularity() {
        return popularity;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public Date getReleaseDateAsDate() {
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

    public String getReleaseDate() {
        return releaseDate;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MovieTMDb movieTMDb = (MovieTMDb) o;

        return id != null ? id.equals(movieTMDb.id) : movieTMDb.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public void setPosterSuffix(String posterSuffix) {
        this.posterSuffix = posterSuffix;
    }

    public String getPosterSuffix() {
        return posterSuffix;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public void setVoteCount(Long voteCount) {
        this.voteCount = voteCount;
    }

    public void setPopularity(Float popularity) {
        this.popularity = popularity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.posterSuffix);
        dest.writeString(this.title);
        dest.writeString(this.overview);
        dest.writeString(this.originalTitle);
        dest.writeValue(this.voteAverage);
        dest.writeValue(this.voteCount);
        dest.writeValue(this.popularity);
        dest.writeString(this.releaseDate);
        dest.writeSerializable(this.dateFormat);
    }

    protected MovieTMDb(Parcel in) {
        this.id = in.readString();
        this.posterSuffix = in.readString();
        this.title = in.readString();
        this.overview = in.readString();
        this.originalTitle = in.readString();
        this.voteAverage = (Double) in.readValue(Double.class.getClassLoader());
        this.voteCount = (Long) in.readValue(Long.class.getClassLoader());
        this.popularity = (Float) in.readValue(Float.class.getClassLoader());
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
