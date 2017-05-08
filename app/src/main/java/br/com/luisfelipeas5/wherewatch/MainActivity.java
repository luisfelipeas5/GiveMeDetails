package br.com.luisfelipeas5.wherewatch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import br.com.luisfelipeas5.wherewatch.adapter.MoviesAdapter;
import br.com.luisfelipeas5.wherewatch.api.WhereWatchApi;
import br.com.luisfelipeas5.wherewatch.api.responsebodies.MoviesResponseBody;
import br.com.luisfelipeas5.wherewatch.model.Movie;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(layoutManager);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPopularMovies();
    }

    public void getPopularMovies() {
        mRecyclerView.setVisibility(View.GONE);
        WhereWatchApi.getPopularMovies(this, new WhereWatchApi.Callback<MoviesResponseBody>() {
            @Override
            public void onResult(MoviesResponseBody moviesResponseBody) {
                if (moviesResponseBody != null) {
                    setAdapter(moviesResponseBody.getMovies());
                }
                mRecyclerView.setVisibility(View.VISIBLE);
            }

        });
    }

    private void setAdapter(List<Movie> movies) {
        MoviesAdapter adapter = new MoviesAdapter(movies);
        mRecyclerView.setAdapter(adapter);
    }
}
