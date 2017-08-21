package br.com.luisfelipeas5.givemedetails.model.model.movie;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.content.ContentValues;

@Entity(tableName = MovieLove.TABLE_NAME, foreignKeys = @ForeignKey(entity = MovieTMDb.class,
        parentColumns = "id", childColumns = "movie_id"))
public class MovieLove {
    public static final String TABLE_NAME = "movieLove";
    public static final String COLUMN_MOVIE_ID = "movie_id";
    public static final String COLUMN_IS_LOVED = "is_loved";

    @PrimaryKey
    @ColumnInfo(name = COLUMN_MOVIE_ID)
    private String movieId;
    @ColumnInfo(name = COLUMN_IS_LOVED)
    private boolean isLoved;

    public String getMovieId() {
        return movieId;
    }

    public boolean isLoved() {
        return isLoved;
    }

    public void setMovieId(String movieId) {

        this.movieId = movieId;
    }

    public void setLoved(boolean loved) {
        isLoved = loved;
    }

    public static ContentValues toContentValues(MovieLove movieLove) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_MOVIE_ID, movieLove.getMovieId());
        contentValues.put(COLUMN_IS_LOVED, movieLove.isLoved());
        return contentValues;
    }

    public static MovieLove fromContentValues(ContentValues values) {
        MovieLove movieLove = new MovieLove();
        movieLove.setLoved(values.getAsBoolean(COLUMN_IS_LOVED));
        movieLove.setMovieId(values.getAsString(COLUMN_MOVIE_ID));
        return movieLove;
    }
}
