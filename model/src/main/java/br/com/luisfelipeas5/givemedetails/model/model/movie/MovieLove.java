package br.com.luisfelipeas5.givemedetails.model.model.movie;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = MovieTMDb.class,
        parentColumns = "id", childColumns = "movie_id"))
public class MovieLove {
    @PrimaryKey
    @ColumnInfo(name = "movie_id")
    private String movieId;
    @ColumnInfo(name = "is_loved")
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
}
