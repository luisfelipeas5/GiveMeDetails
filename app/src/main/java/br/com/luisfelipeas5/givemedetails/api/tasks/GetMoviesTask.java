package br.com.luisfelipeas5.givemedetails.api.tasks;

import android.net.Uri;

import com.google.gson.Gson;

import br.com.luisfelipeas5.givemedetails.api.GiveMeDetailsApi;
import br.com.luisfelipeas5.givemedetails.model.model.MoviesResponseBody;

public class GetMoviesTask extends Task {

    private GiveMeDetailsApi.Callback<MoviesResponseBody> callback;

    public GetMoviesTask(Uri.Builder builder, GiveMeDetailsApi.Callback<MoviesResponseBody> callback) {
        super(builder);
        this.callback = callback;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (s != null) {
            MoviesResponseBody moviesResponseBody = new Gson().fromJson(s, MoviesResponseBody.class);
            callback.onResult(moviesResponseBody);
        } else {
            callback.onError();
        }
    }

}
