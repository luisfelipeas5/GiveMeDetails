package br.com.luisfelipeas5.wherewatch.api.tasks;

import android.net.Uri;
import android.os.AsyncTask;

import java.io.IOException;
import java.net.URL;

import br.com.luisfelipeas5.wherewatch.api.WhereWatchApi;

class Task extends AsyncTask<Void, Void, String> {
    private Uri.Builder builder;

    Task(Uri.Builder builder) {
        this.builder = builder;
    }

    @Override
    protected String doInBackground(Void... params) {
        URL url;
        try {
            url = new URL(builder.build().toString());
            return WhereWatchApi.getResponseFromHttpUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
