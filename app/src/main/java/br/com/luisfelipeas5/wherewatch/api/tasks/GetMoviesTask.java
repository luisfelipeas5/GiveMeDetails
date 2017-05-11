package br.com.luisfelipeas5.wherewatch.api.tasks;

import android.net.Uri;

import com.google.gson.Gson;

import br.com.luisfelipeas5.wherewatch.api.WhereWatchApi;
import br.com.luisfelipeas5.wherewatch.api.responsebodies.MoviesResponseBody;

public class GetMoviesTask extends Task {

    private WhereWatchApi.Callback<MoviesResponseBody> callback;

    public GetMoviesTask(Uri.Builder builder, WhereWatchApi.Callback<MoviesResponseBody> callback) {
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
