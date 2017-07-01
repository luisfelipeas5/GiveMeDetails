package br.com.luisfelipeas5.givemedetails.api;

import android.content.Context;
import android.net.Uri;

import br.com.luisfelipeas5.givemedetails.R;
import br.com.luisfelipeas5.givemedetails.model.model.MoviesResponseBody;
import br.com.luisfelipeas5.givemedetails.api.tasks.GetMovieTask;
import br.com.luisfelipeas5.givemedetails.api.tasks.GetMoviesTask;
import br.com.luisfelipeas5.givemedetails.model.model.Movie;

public class GiveMeDetailsApi {
    private static final int[] IMAGE_WIDTH_AVAILABLE = {92, 154, 185, 342, 500, 780};
    private static final String[] IMAGE_PATH_WIDTH_AVAILABLE = {"w92", "w154", "w185", "w342", "w500", "w780", "original"};

    private static final String AUTHORITY = "api.themoviedb.org";
    private static final String IMG_AUTHORITY = "image.tmdb.org";

    private static final String GET_MOVIES_POPULAR_PATH = "3/movie/popular";
    private static final String GET_MOVIES_TOP_RATED_PATH = "3/movie/top_rated";
    private static final String GET_MOVIE_PATH = "3/movie";

    private static final String KEY_QUERY_API_KEY = "api_key";

    public interface Callback<T> {
        void onResult(T movies);
        void onError();
    }

    public static String getImgUrlThumbnail(int measuredWidth) {
        String measuredPath = null;
        for (int i = 0; i < IMAGE_WIDTH_AVAILABLE.length; i++) {
            int widthAvailable = IMAGE_WIDTH_AVAILABLE[i];
            if (measuredWidth < widthAvailable) {
                measuredPath = IMAGE_PATH_WIDTH_AVAILABLE[i];
                break;
            }
        }
        if (measuredPath == null) {
            measuredPath = IMAGE_PATH_WIDTH_AVAILABLE[IMAGE_PATH_WIDTH_AVAILABLE.length - 1];
        }

        Uri uri = new Uri.Builder()
                .scheme("http")
                .authority(IMG_AUTHORITY)
                .appendPath("t")
                .appendPath("p")
                .appendPath(measuredPath)
                .build();
        return uri.toString();
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
}
