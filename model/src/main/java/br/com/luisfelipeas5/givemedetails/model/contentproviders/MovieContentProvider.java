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

import java.util.LinkedList;
import java.util.List;

import br.com.luisfelipeas5.givemedetails.model.daos.LoveDao;
import br.com.luisfelipeas5.givemedetails.model.daos.MovieDao;
import br.com.luisfelipeas5.givemedetails.model.databases.MovieDatabase;
import br.com.luisfelipeas5.givemedetails.model.model.movie.MovieLove;
import br.com.luisfelipeas5.givemedetails.model.model.movie.MovieTMDb;

public class MovieContentProvider extends ContentProvider implements MovieMvpContentProvider {
    private static MovieDatabase mMovieDatabase;

    public static final String AUTHORITY = "br.com.luifelipeas5.givemedetails.model.contentproviders.provider";

    public static final Uri URI_MOVIE = Uri.parse(
            "content://" + AUTHORITY + "/" + MovieTMDb.TABLE_NAME);
    public static final Uri URI_MOVIE_LOVE = Uri.parse(
            "content://" + AUTHORITY + "/" + MovieLove.TABLE_NAME);

    private static final int CODE_MOVIE_DIR = 100;
    private static final int CODE_MOVIE_ITEM = 101;
    private static final int CODE_MOVIE_LOVE_DIR = 200;
    private static final int CODE_MOVIE_LOVE_ITEM = 201;

    /** The URI matcher. */
    private static final UriMatcher MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        MATCHER.addURI(AUTHORITY, MovieTMDb.TABLE_NAME, CODE_MOVIE_DIR);
        MATCHER.addURI(AUTHORITY, MovieTMDb.TABLE_NAME + "/*", CODE_MOVIE_ITEM);
        MATCHER.addURI(AUTHORITY, MovieLove.TABLE_NAME, CODE_MOVIE_LOVE_DIR);
        MATCHER.addURI(AUTHORITY, MovieLove.TABLE_NAME + "/*", CODE_MOVIE_LOVE_ITEM);
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
    public long insert(MovieTMDb movieTMDb) {
        return insert(mContentResolver, movieTMDb);
    }

    public static long insert(ContentResolver contentResolver, MovieTMDb movieTMDb) {
        ContentValues values = MovieTMDb.toContentValues(movieTMDb);
        Uri itemUri = contentResolver.insert(URI_MOVIE, values);
        return ContentUris.parseId(itemUri);
    }

    public static MovieTMDb getMovieById(ContentResolver contentResolver, String id) {
        Uri uri = Uri.parse(URI_MOVIE.toString() + "/" + id);
        Cursor cursor = contentResolver.query(uri, null, null, null, null);
        if (cursor != null && cursor.moveToNext()) {
            MovieTMDb movieTMDb = MovieTMDb.fromCursor(cursor);
            cursor.close();
            return movieTMDb;
        }
        return null;
    }

    public static int getMovieByIdCount(ContentResolver contentResolver, String id) {
        Uri uri = Uri.parse(URI_MOVIE.toString() + "/" + id);
        Cursor cursor = contentResolver.query(uri, null, null, null, null);
        if (cursor != null) {
            int count = cursor.getCount();
            cursor.close();
            return count;
        }
        return 0;
    }


    @Override
    public Integer getMovieByIdCount(String movieId) {
        return getMovieByIdCount(mContentResolver, movieId);
    }

    @Override
    public List<MovieTMDb> getLoved() {
        return getLoved(mContentResolver);
    }

    public static List<MovieTMDb> getLoved(ContentResolver contentResolver) {
        Cursor cursor = contentResolver.query(URI_MOVIE_LOVE, null, null, null, null);
        List<MovieTMDb> movies = new LinkedList<>();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                MovieTMDb movieTMDb = MovieTMDb.fromCursor(cursor);
                movies.add(movieTMDb);
            }
            cursor.close();
        }
        return movies;
    }

    @Override
    public long insert(MovieLove movieLove) {
        return insert(mContentResolver, movieLove);
    }

    public static long insert(ContentResolver contentResolver, MovieLove movieLove) {
        ContentValues values = MovieLove.toContentValues(movieLove);
        Uri itemUri = contentResolver.insert(URI_MOVIE_LOVE, values);
        return ContentUris.parseId(itemUri);
    }

    @Override
    public Boolean isLoved(String movieId) {
        return isLoved(mContentResolver, movieId);
    }

    public static boolean isLoved(ContentResolver contentResolver, String movieId) {
        Uri uri = Uri.parse(URI_MOVIE_LOVE.toString() + "/" + movieId);
        Cursor cursor = contentResolver.query(uri, null, null, null, null);
        if (cursor != null && cursor.moveToNext()) {
            int isLovedColumnIndex = cursor.getColumnIndex(MovieLove.COLUMN_IS_LOVED);
            Boolean isLoved = cursor.getInt(isLovedColumnIndex) == 1;
            cursor.close();
            return isLoved;
        }
        return false;
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
            case CODE_MOVIE_LOVE_DIR:
                return "vnd.android.cursor.dir/" + AUTHORITY + "." + MovieLove.TABLE_NAME;
            case CODE_MOVIE_LOVE_ITEM:
                return "vnd.android.cursor.item/" + AUTHORITY + "." + MovieLove.TABLE_NAME;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final int code = MATCHER.match(uri);
        Cursor cursor = null;

        if (code == CODE_MOVIE_DIR || code == CODE_MOVIE_ITEM) {
            MovieDao movieDao = mMovieDatabase.getMovieDao();
            if (code == CODE_MOVIE_DIR) {
                cursor = movieDao.getMovies();
            } else {
                String movieId = uri.getLastPathSegment();
                cursor = movieDao.getMovieByIdCursor(movieId);
            }
        } else if (code == CODE_MOVIE_LOVE_DIR || code == CODE_MOVIE_LOVE_ITEM) {
            LoveDao loveDao = mMovieDatabase.getLoveDao();
            if (code == CODE_MOVIE_LOVE_DIR) {
                cursor = loveDao.getLovedCursor(true);
            } else {
                String movieId = uri.getLastPathSegment();
                cursor = loveDao.isLovedCursor(movieId);
            }
        }

        if (cursor != null) {
            final Context context = getContext();
            if (context == null) {
                return null;
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
        final Context context = getContext();
        if (context == null) {
            return null;
        }
        switch (MATCHER.match(uri)) {
            case CODE_MOVIE_DIR:
                final long id = mMovieDatabase.getMovieDao()
                        .insert(MovieTMDb.fromContentValues(values));
                context.getContentResolver().notifyChange(uri, null);
                return ContentUris.withAppendedId(uri, id);

            case CODE_MOVIE_LOVE_DIR:
                final long movieLoveId = mMovieDatabase.getLoveDao()
                        .insert(MovieLove.fromContentValues(values));
                context.getContentResolver().notifyChange(uri, null);
                return ContentUris.withAppendedId(uri, movieLoveId);

            case CODE_MOVIE_LOVE_ITEM:
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
