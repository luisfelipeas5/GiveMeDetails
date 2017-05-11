package br.com.luisfelipeas5.wherewatch.api.tasks;

import android.net.Uri;

import com.google.gson.Gson;

import br.com.luisfelipeas5.wherewatch.api.WhereWatchApi;
import br.com.luisfelipeas5.wherewatch.model.Movie;

public class GetMovieTask extends Task {

    private WhereWatchApi.Callback<Movie> callback;

    public GetMovieTask(Uri.Builder builder, WhereWatchApi.Callback<Movie> callback) {
        super(builder);
        this.callback = callback;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Movie movie = new Gson().fromJson(s, Movie.class);
        callback.onResult(movie);
    }

}
