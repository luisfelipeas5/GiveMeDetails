package br.com.luisfelipeas5.wherewatch.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

import br.com.luisfelipeas5.wherewatch.R;
import br.com.luisfelipeas5.wherewatch.adapter.MoviesAdapter;
import br.com.luisfelipeas5.wherewatch.api.WhereWatchApi;
import br.com.luisfelipeas5.wherewatch.api.responsebodies.MoviesResponseBody;
import br.com.luisfelipeas5.wherewatch.databinding.ActivityMainBinding;
import br.com.luisfelipeas5.wherewatch.model.Movie;

public class MainActivity extends AppCompatActivity implements MoviesAdapter.Listener {

    ActivityMainBinding mBinding;

    private MainActivity.MODE mMode;
    private WhereWatchApi.GetMoviesTask mGetPopularMoviesTask;
    private WhereWatchApi.GetMoviesTask mGetTopRatedMoviesTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        mBinding.swipeRefresh.setColorSchemeResources(
                R.color.colorPrimaryDark, R.color.colorAccent, R.color.colorPrimary, R.color.colorAccent);
        mBinding.swipeRefresh.setEnabled(true);
        mBinding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (mMode == MODE.POPULAR) {
                    getPopularMovies();
                } else {
                    getTopRatedMovies();
                }
            }
        });

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        mBinding.recycler.setLayoutManager(layoutManager);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPopularMovies();
    }

    public void getPopularMovies() {
        mMode = MODE.POPULAR;
        mBinding.swipeRefresh.setRefreshing(true);
        mGetPopularMoviesTask = WhereWatchApi.getPopularMovies(this, mGetMoviesCallback);
    }

    private void getTopRatedMovies() {
        mMode = MODE.TOP_RATED;
        mBinding.swipeRefresh.setRefreshing(true);
        mGetTopRatedMoviesTask = WhereWatchApi.getTopRatedMovies(this, mGetMoviesCallback);
    }

    WhereWatchApi.Callback<MoviesResponseBody> mGetMoviesCallback = new WhereWatchApi.Callback<MoviesResponseBody>() {
        @Override
        public void onResult(MoviesResponseBody movies) {
            if (movies != null) {
                setAdapter(movies.getMovies());
            }
            mBinding.recycler.setVisibility(View.VISIBLE);
            mBinding.swipeRefresh.setRefreshing(false);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.action_popular:
                getPopularMovies();
                return true;

            case R.id.action_top_rated:
                getTopRatedMovies();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setAdapter(List<Movie> movies) {
        MoviesAdapter adapter = new MoviesAdapter(movies);
        adapter.setListener(this);
        mBinding.recycler.setAdapter(adapter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mGetPopularMoviesTask != null && !mGetPopularMoviesTask.isCancelled()) {
            mGetPopularMoviesTask.cancel(true);
        }
        if (mGetTopRatedMoviesTask != null && mGetTopRatedMoviesTask.isCancelled()) {
            mGetTopRatedMoviesTask.cancel(true);
        }
    }

    @Override
    public void onMovieClicked(Movie movie) {
        Intent movieDetailsIntent = new Intent(this, DetailActivity.class);
        movieDetailsIntent.putExtra(DetailActivity.EXTRA_MOVIE, movie);
        startActivity(movieDetailsIntent);
    }

    private enum MODE {
        POPULAR, TOP_RATED
    }
}
