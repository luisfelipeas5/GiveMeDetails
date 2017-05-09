package br.com.luisfelipeas5.wherewatch.api;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

import br.com.luisfelipeas5.wherewatch.R;
import br.com.luisfelipeas5.wherewatch.api.responsebodies.MoviesResponseBody;

public class WhereWatchApi {
    public static final String IMG_BASE_URL_THUMBNAIL = "http://image.tmdb.org/t/p/w185";
    public static final String IMG_BASE_URL_ORIGINAL = "http://image.tmdb.org/t/p/original";
    private static final String AUTHORITY = "api.themoviedb.org";

    private static final String MOVIES_POPULAR = "3/movie/popular";
    private static final String MOVIES_TOP_RATED = "3/movie/top_rated";

    private static final String KEY_QUERY_API_KEY = "api_key";

    public interface Callback<T> {
        void onResult(T movies);
    }

    public static GetMoviesTask getPopularMovies(Context context, final Callback<MoviesResponseBody> callback) {
        Uri.Builder builder = getDefaultBuilder(context)
                .path(MOVIES_POPULAR);

        GetMoviesTask task = new GetMoviesTask(builder, callback);
        task.execute();
        return task;
    }

    public static GetMoviesTask getTopRatedMovies(Context context, final Callback<MoviesResponseBody> callback) {
        Uri.Builder builder = getDefaultBuilder(context)
                .path(MOVIES_TOP_RATED);

        GetMoviesTask task = new GetMoviesTask(builder, callback);
        task.execute();
        return task;
    }

    private static Uri.Builder getDefaultBuilder(Context context) {
        return new Uri.Builder()
                .scheme("https")
                .appendQueryParameter(KEY_QUERY_API_KEY, context.getString(R.string.the_movie_db_api_key))
                .authority(AUTHORITY);
    }

    private static String getResponseFromHttpUrl(URL url) throws IOException {
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

    public static class GetMoviesTask extends Task {

        private Callback<MoviesResponseBody> callback;

        private GetMoviesTask(Uri.Builder builder, Callback<MoviesResponseBody> callback) {
            super(builder);
            this.callback = callback;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            MoviesResponseBody moviesResponseBody = new Gson().fromJson(s, MoviesResponseBody.class);
            callback.onResult(moviesResponseBody);
        }
    }

    static class Task extends AsyncTask<Void, Void, String>{
        private Uri.Builder builder;

        private Task(Uri.Builder builder) {
            this.builder = builder;
        }

        @Override
        protected String doInBackground(Void... params) {
            URL url;
            try {
                url = new URL(builder.build().toString());
                return getResponseFromHttpUrl(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
