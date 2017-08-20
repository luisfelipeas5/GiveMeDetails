package br.com.luisfelipeas5.givemedetails.model.contentproviders;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

import br.com.luisfelipeas5.givemedetails.model.daos.MovieDao;
import br.com.luisfelipeas5.givemedetails.model.databases.MovieDatabase;
import br.com.luisfelipeas5.givemedetails.model.model.movie.MovieLove;
import br.com.luisfelipeas5.givemedetails.model.model.movie.MovieTMDb;

public class MovieContentProvider extends ContentProvider implements MovieMvpContentProvider {
    private static MovieDatabase mMovieDatabase;

    public static final String AUTHORITY = "br.com.luifelipeas5.givemedetails.model.contentproviders.provider";

    public static final Uri URI_MOVIE = Uri.parse(
            "content://" + AUTHORITY + "/" + MovieTMDb.TABLE_NAME);

    private static final int CODE_MOVIE_DIR = 100;
    private static final int CODE_MOVIE_ITEM = 101;

    /** The URI matcher. */
    private static final UriMatcher MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        MATCHER.addURI(AUTHORITY, MovieTMDb.TABLE_NAME, CODE_MOVIE_DIR);
        MATCHER.addURI(AUTHORITY, MovieTMDb.TABLE_NAME + "/*", CODE_MOVIE_ITEM);
    }

    private ContentResolver mContentResolver;

    public MovieContentProvider() {
        //It is necessary a default constructor, because it is a ContentProvider
    }

    public MovieContentProvider(MovieDatabase movieDatabase, Context context) {
        mMovieDatabase = movieDatabase;
        mContentResolver = context.getContentResolver();
    }

    public static void setMovieDatabase(MovieDatabase movieDatabase) {
        MovieContentProvider.mMovieDatabase = movieDatabase;
    }

    @Override
    public Boolean isLoved(String movieId) {
        return null;
    }

    @Override
    public Integer getMovieByIdCount(String movieId) {
        return null;
    }

    @Override
    public long insert(MovieTMDb movieTMDb) {
        return insert(mContentResolver, movieTMDb);
    }

    public static long insert(ContentResolver contentResolver, MovieTMDb movieTMDb) {
        ContentValues values = MovieTMDb.toContentValues(movieTMDb);
        Uri itemUri = contentResolver.insert(URI_MOVIE, values);
        return ContentUris.parseId(itemUri);
    }

    @Override
    public List<MovieTMDb> getLoved() {
        return null;
    }

    @Override
    public long insert(MovieLove movieLove) {
        return 0;
    }

    @Override
    public boolean onCreate() {
        return true;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (MATCHER.match(uri)) {
            case CODE_MOVIE_DIR:
                return "vnd.android.cursor.dir/" + AUTHORITY + "." + MovieTMDb.TABLE_NAME;
            case CODE_MOVIE_ITEM:
                return "vnd.android.cursor.item/" + AUTHORITY + "." + MovieTMDb.TABLE_NAME;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final int code = MATCHER.match(uri);
        if (code == CODE_MOVIE_DIR || code == CODE_MOVIE_ITEM) {
            final Context context = getContext();
            if (context == null) {
                return null;
            }
            MovieDao movieDao = mMovieDatabase.getMovieDao();

            final Cursor cursor;
            if (code == CODE_MOVIE_DIR) {
                cursor = movieDao.getMovies();
            } else {
                String movieId = uri.getLastPathSegment();
                cursor = movieDao.getMovieByIdCursor(movieId);
            }
            cursor.setNotificationUri(context.getContentResolver(), uri);
            return cursor;
        } else {
            throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        switch (MATCHER.match(uri)) {
            case CODE_MOVIE_DIR:
                final Context context = getContext();
                if (context == null) {
                    return null;
                }
                final long id = mMovieDatabase.getMovieDao()
                        .insert(MovieTMDb.fromContentValues(values));
                context.getContentResolver().notifyChange(uri, null);
                return ContentUris.withAppendedId(uri, id);
            case CODE_MOVIE_ITEM:
                throw new IllegalArgumentException("Invalid URI, cannot insert with ID: " + uri);
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection,
                      @Nullable String[] selectionArgs) {
        switch (MATCHER.match(uri)) {
            case CODE_MOVIE_DIR:
                throw new IllegalArgumentException("Invalid URI, cannot update without ID" + uri);
            case CODE_MOVIE_ITEM:
                final Context context = getContext();
                if (context == null) {
                    return 0;
                }
                final int count = mMovieDatabase.getMovieDao()
                        .deleteById(uri.getLastPathSegment());
                context.getContentResolver().notifyChange(uri, null);
                return count;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection,
                      @Nullable String[] selectionArgs) {
        switch (MATCHER.match(uri)) {
            case CODE_MOVIE_DIR:
                throw new IllegalArgumentException("Invalid URI, cannot update without ID" + uri);
            case CODE_MOVIE_ITEM:
                final Context context = getContext();
                if (context == null) {
                    return 0;
                }
                final MovieTMDb cheese = MovieTMDb.fromContentValues(values);
                cheese.setId(uri.getLastPathSegment());
                final int count = mMovieDatabase.getMovieDao()
                        .update(cheese);
                context.getContentResolver().notifyChange(uri, null);
                return count;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }
}
