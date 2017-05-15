package br.com.luisfelipeas5.givemedetails.ui.fragments.movielists;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import br.com.luisfelipeas5.givemedetails.R;
import br.com.luisfelipeas5.givemedetails.adapter.MoviesAdapter;
import br.com.luisfelipeas5.givemedetails.api.GiveMeDetailsApi;
import br.com.luisfelipeas5.givemedetails.api.responsebodies.MoviesResponseBody;
import br.com.luisfelipeas5.givemedetails.api.tasks.GetMoviesTask;
import br.com.luisfelipeas5.givemedetails.databinding.FragmentMoviesBinding;
import br.com.luisfelipeas5.givemedetails.model.Movie;
import br.com.luisfelipeas5.givemedetails.ui.DetailActivity;
import br.com.luisfelipeas5.givemedetails.utils.NetworkUtils;

public abstract class MoviesFragment extends Fragment implements View.OnClickListener, MoviesAdapter.Listener {

    private static final String CONNECTIVITY_CHANGE_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";
    private FragmentMoviesBinding mBinding;
    private GetMoviesTask mGetMoviesTask;

    public MoviesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentMoviesBinding.inflate(inflater, container, false);

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        mBinding.recycler.setLayoutManager(layoutManager);
        mBinding.txtNoMovies.setOnClickListener(this);

        onRefresh();

        return mBinding.getRoot();
    }

    public abstract GetMoviesTask getMovies(GiveMeDetailsApi.Callback<MoviesResponseBody> callback);

    GiveMeDetailsApi.Callback<MoviesResponseBody> mGetMoviesCallback = new GiveMeDetailsApi.Callback<MoviesResponseBody>() {
        @Override
        public void onResult(MoviesResponseBody movies) {
            if (movies != null) {
                setAdapter(movies.getMovies());
            }
            mBinding.recycler.setVisibility(View.VISIBLE);
        }

        @Override
        public void onError() {
            mBinding.recycler.setVisibility(View.GONE);
            if (!NetworkUtils.isConnected(getContext())) {
                mBinding.txtNoMovies.setText(R.string.no_movies_network_error);
            } else {
                mBinding.txtNoMovies.setText(R.string.no_movies);
            }
        }
    };

    private void setAdapter(List<Movie> movies) {
        MoviesAdapter adapter = new MoviesAdapter(movies);
        adapter.setListener(this);
        mBinding.recycler.setAdapter(adapter);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mGetMoviesTask != null && !mGetMoviesTask.isCancelled()) {
            mGetMoviesTask.cancel(true);
        }
    }

    @Override
    public void onMovieClicked(Movie movie) {
        Intent movieDetailsIntent = new Intent(getContext(), DetailActivity.class);
        movieDetailsIntent.putExtra(DetailActivity.EXTRA_MOVIE, movie);
        startActivity(movieDetailsIntent);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.txt_no_movies:
                onRefresh();
                break;
        }
    }

    public void onRefresh() {
        if (NetworkUtils.isConnected(getContext())) {
            mGetMoviesTask = getMovies(mGetMoviesCallback);
        } else {
            mBinding.recycler.setVisibility(View.GONE);
            mBinding.txtNoMovies.setVisibility(View.VISIBLE);
            mBinding.txtNoMovies.setText(R.string.no_movies_network_error);
            registerReceiverForNetwork();
        }
    }

    private void registerReceiverForNetwork() {
        IntentFilter filters = new IntentFilter();
        filters.addAction(CONNECTIVITY_CHANGE_ACTION);
        getContext().registerReceiver(connectivityChangeReceiver, filters);
    }

    private BroadcastReceiver connectivityChangeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(CONNECTIVITY_CHANGE_ACTION)) {
                if (NetworkUtils.isConnected(getContext())) {
                    getContext().unregisterReceiver(this);
                    onRefresh();
                }
            }
        }
    };

    public abstract int getTitleResource();
}
