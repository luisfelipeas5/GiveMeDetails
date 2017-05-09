package br.com.luisfelipeas5.wherewatch;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

import br.com.luisfelipeas5.wherewatch.adapter.MoviesAdapter;
import br.com.luisfelipeas5.wherewatch.api.WhereWatchApi;
import br.com.luisfelipeas5.wherewatch.api.responsebodies.MoviesResponseBody;
import br.com.luisfelipeas5.wherewatch.model.Movie;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private WhereWatchApi.GetMoviesTask mGetPopularMoviesTask;
    private WhereWatchApi.GetMoviesTask mGetTopRatedMoviesTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);
        mRecyclerView.setLayoutManager(layoutManager);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPopularMovies();
    }

    public void getPopularMovies() {
        mSwipeRefreshLayout.setRefreshing(true);
        mGetPopularMoviesTask = WhereWatchApi.getPopularMovies(this, mGetMoviesCallback);
    }

    private void getTopRatedMovies() {
        mSwipeRefreshLayout.setRefreshing(true);
        mGetTopRatedMoviesTask = WhereWatchApi.getTopRatedMovies(this, mGetMoviesCallback);
    }

    WhereWatchApi.Callback<MoviesResponseBody> mGetMoviesCallback = new WhereWatchApi.Callback<MoviesResponseBody>() {
        @Override
        public void onResult(MoviesResponseBody movies) {
            if (movies != null) {
                setAdapter(movies.getMovies());
            }
            mRecyclerView.setVisibility(View.VISIBLE);
            mSwipeRefreshLayout.setRefreshing(false);
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
        mRecyclerView.setAdapter(adapter);
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
}
