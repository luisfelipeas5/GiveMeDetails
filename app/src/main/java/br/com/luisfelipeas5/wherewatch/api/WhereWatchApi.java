package br.com.luisfelipeas5.wherewatch.api;

import android.content.Context;
import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

import br.com.luisfelipeas5.wherewatch.R;
import br.com.luisfelipeas5.wherewatch.api.responsebodies.MoviesResponseBody;
import br.com.luisfelipeas5.wherewatch.api.tasks.GetMovieTask;
import br.com.luisfelipeas5.wherewatch.api.tasks.GetMoviesTask;
import br.com.luisfelipeas5.wherewatch.model.Movie;

public class WhereWatchApi {
    public static final String IMG_BASE_URL_THUMBNAIL = "http://image.tmdb.org/t/p/w185";
    public static final String IMG_BASE_URL_ORIGINAL = "http://image.tmdb.org/t/p/original";
    private static final String AUTHORITY = "api.themoviedb.org";

    private static final String GET_MOVIES_POPULAR_PATH = "3/movie/popular";
    private static final String GET_MOVIES_TOP_RATED_PATH = "3/movie/top_rated";
    private static final String GET_MOVIE_PATH = "3/movie";

    private static final String KEY_QUERY_API_KEY = "api_key";

    public interface Callback<T> {
        void onResult(T movies);
    }

    public static GetMoviesTask getPopularMovies(Context context, final Callback<MoviesResponseBody> callback) {
        Uri.Builder builder = getDefaultBuilder(context)
                .path(GET_MOVIES_POPULAR_PATH);

        GetMoviesTask task = new GetMoviesTask(builder, callback);
        task.execute();
        return task;
    }

    public static GetMoviesTask getTopRatedMovies(Context context, final Callback<MoviesResponseBody> callback) {
        Uri.Builder builder = getDefaultBuilder(context)
                .path(GET_MOVIES_TOP_RATED_PATH);

        GetMoviesTask task = new GetMoviesTask(builder, callback);
        task.execute();
        return task;
    }

    public static GetMovieTask getMovie(Context context, Movie movie, Callback<Movie> callback) {
        Uri.Builder builder = getDefaultBuilder(context)
                .path(GET_MOVIE_PATH)
                .appendPath(movie.getId());

        GetMovieTask task = new GetMovieTask(builder, callback);
        task.execute();
        return task;
    }

    private static Uri.Builder getDefaultBuilder(Context context) {
        return new Uri.Builder()
                .scheme("https")
                .appendQueryParameter(KEY_QUERY_API_KEY, context.getString(R.string.the_movie_db_api_key))
                .authority(AUTHORITY);
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
