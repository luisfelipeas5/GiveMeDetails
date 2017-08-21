package br.com.luisfelipeas5.givemedetails.model.model.movie;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.content.ContentValues;
import android.database.Cursor;

import com.google.gson.annotations.SerializedName;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Entity(tableName = MovieTMDb.TABLE_NAME)
public class MovieTMDb implements Movie {
    public static final String TABLE_NAME = "movies";
    public static final String COLUMN_ID = "id";
    private static final String COLUMN_POSTER_SUFFIX = "poster_suffix";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_OVERVIEW = "overview";
    private static final String COLUMN_ORIGINAL_TITLE = "original_title";
    private static final String COLUMN_VOTE_AVERAGE = "vote_average";
    private static final String COLUMN_VOTE_COUNT = "vote_count";
    private static final String COLUMN_POPULARITY = "popularity";
    private static final String COLUMN_RELEASE_DATE = "release_date";

    @Ignore
    private static final int[] IMAGE_WIDTH_AVAILABLE = {92, 154, 185, 342, 500, 780};
    @Ignore
    private static final String[] IMAGE_PATH_WIDTH_AVAILABLE = {"w92", "w154", "w185", "w342", "w500", "w780", "original"};
    @Ignore
    private static final String IMG_AUTHORITY = "image.tmdb.org";

    @PrimaryKey
    @ColumnInfo(name = COLUMN_ID)
    @SerializedName("id")
    private String id;

    @ColumnInfo(name = COLUMN_POSTER_SUFFIX)
    @SerializedName("poster_path")
    private String posterSuffix;

    @ColumnInfo(name = COLUMN_TITLE)
    @SerializedName("title")
    private String title;

    @ColumnInfo(name = COLUMN_OVERVIEW)
    @SerializedName("overview")
    private String overview;

    @ColumnInfo(name = COLUMN_ORIGINAL_TITLE)
    @SerializedName("original_title")
    private String originalTitle;

    @ColumnInfo(name = COLUMN_VOTE_AVERAGE)
    @SerializedName("vote_average")
    private Double voteAverage;

    @ColumnInfo(name = COLUMN_VOTE_COUNT)
    @SerializedName("vote_count")
    private Long voteCount;

    @ColumnInfo(name = COLUMN_POPULARITY)
    @SerializedName("popularity")
    private Float popularity;

    @ColumnInfo(name = COLUMN_RELEASE_DATE)
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

    public static MovieTMDb fromContentValues(ContentValues values) {
        MovieTMDb movieTMDb = new MovieTMDb();

        movieTMDb.setId(values.getAsString(COLUMN_ID));
        movieTMDb.setPosterSuffix(values.getAsString(COLUMN_POSTER_SUFFIX));
        movieTMDb.setTitle(values.getAsString(COLUMN_TITLE));
        movieTMDb.setOverview(values.getAsString(COLUMN_OVERVIEW));
        movieTMDb.setOriginalTitle(values.getAsString(COLUMN_ORIGINAL_TITLE));
        movieTMDb.setVoteAverage(values.getAsDouble(COLUMN_VOTE_AVERAGE));
        movieTMDb.setVoteCount(values.getAsLong(COLUMN_VOTE_COUNT));
        movieTMDb.setPopularity(values.getAsFloat(COLUMN_POPULARITY));
        movieTMDb.setReleaseDate(values.getAsString(COLUMN_RELEASE_DATE));

        return movieTMDb;
    }

    public static ContentValues toContentValues(MovieTMDb movieTMDb) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_ID, movieTMDb.getId());
        contentValues.put(COLUMN_POSTER_SUFFIX, movieTMDb.getPosterSuffix());
        contentValues.put(COLUMN_TITLE, movieTMDb.getTitle());
        contentValues.put(COLUMN_OVERVIEW, movieTMDb.getOverview());
        contentValues.put(COLUMN_ORIGINAL_TITLE, movieTMDb.getOriginalTitle());
        contentValues.put(COLUMN_VOTE_AVERAGE, movieTMDb.getVoteAverage());
        contentValues.put(COLUMN_VOTE_COUNT, movieTMDb.getPopularity());
        contentValues.put(COLUMN_RELEASE_DATE, movieTMDb.getReleaseDate());

        return contentValues;
    }

    public static MovieTMDb fromCursor(Cursor cursor) {
        MovieTMDb movieTMDb = new MovieTMDb();

        movieTMDb.setId(cursor.getString(cursor.getColumnIndex(COLUMN_ID)));
        movieTMDb.setPosterSuffix(cursor.getString(cursor.getColumnIndex(COLUMN_POSTER_SUFFIX)));
        movieTMDb.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_OVERVIEW)));
        movieTMDb.setOriginalTitle(cursor.getString(cursor.getColumnIndex(COLUMN_ORIGINAL_TITLE)));
        movieTMDb.setVoteAverage(cursor.getDouble(cursor.getColumnIndex(COLUMN_VOTE_AVERAGE)));
        movieTMDb.setVoteCount(cursor.getLong(cursor.getColumnIndex(COLUMN_VOTE_COUNT)));
        movieTMDb.setPopularity(cursor.getFloat(cursor.getColumnIndex(COLUMN_POPULARITY)));
        movieTMDb.setReleaseDate(cursor.getString(cursor.getColumnIndex(COLUMN_RELEASE_DATE)));

        return movieTMDb;
    }
}
