package br.com.luisfelipeas5.givemedetails.api.tasks;

import android.net.Uri;

import com.google.gson.Gson;

import br.com.luisfelipeas5.givemedetails.api.GiveMeDetailsApi;
import br.com.luisfelipeas5.givemedetails.model.model.Movie;

public class GetMovieTask extends Task {

    private GiveMeDetailsApi.Callback<Movie> callback;

    public GetMovieTask(Uri.Builder builder, GiveMeDetailsApi.Callback<Movie> callback) {
        super(builder);
        this.callback = callback;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (s != null) {
            Movie movie = new Gson().fromJson(s, Movie.class);
            callback.onResult(movie);
        } else {
            callback.onError();
        }
    }

}
