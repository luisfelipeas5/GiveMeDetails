package br.com.luisfelipeas5.givemedetails.view.fragments.lists;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import javax.inject.Inject;

import br.com.luisfelipeas5.givemedetails.R;
import br.com.luisfelipeas5.givemedetails.adapters.MoviesAdapter;
import br.com.luisfelipeas5.givemedetails.databinding.FragmentMoviesBinding;
import br.com.luisfelipeas5.givemedetails.model.model.movie.Movie;
import br.com.luisfelipeas5.givemedetails.presenter.list.MoviesMvpPresenter;
import br.com.luisfelipeas5.givemedetails.view.MoviesApp;
import br.com.luisfelipeas5.givemedetails.view.activities.DetailActivity;
import br.com.luisfelipeas5.givemedetails.utils.NetworkUtils;
import br.com.luisfelipeas5.givemedetails.view.di.components.BaseComponent;
import br.com.luisfelipeas5.givemedetails.view.list.MoviesMvpView;

public abstract class MoviesFragment extends Fragment implements View.OnClickListener, MoviesAdapter.Listener, MoviesMvpView, SwipeRefreshLayout.OnRefreshListener {

    private static final String CONNECTIVITY_CHANGE_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";
    protected static final int REQUEST_CODE_DETAILS = 0;
    private FragmentMoviesBinding mBinding;
    protected MoviesMvpPresenter mPresenter;
    private boolean mIsGettingMovies;

    public MoviesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentMoviesBinding.inflate(inflater, container, false);

        MoviesApp moviesApp = (MoviesApp) getContext().getApplicationContext();
        BaseComponent appComponent = moviesApp.getDiComponent();
        appComponent.inject(this);

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        mBinding.recycler.setLayoutManager(layoutManager);

        mBinding.txtNoMovies.setText(getNoMoviesMessage());
        mBinding.txtNoMovies.setOnClickListener(this);
        mBinding.txtNoMovies.setVisibility(View.GONE);

        mBinding.swipeRefreshLayout.setOnRefreshListener(this);

        onRefresh();

        return mBinding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.attach(this);
    }

    protected int getNoMoviesMessage() {
        return R.string.no_movies;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    public void onMovieClicked(Movie movie) {
        Intent movieDetailsIntent = new Intent(getContext(), DetailActivity.class);
        movieDetailsIntent.putExtra(DetailActivity.EXTRA_MOVIE_ID, movie.getId());
        startActivityForResult(movieDetailsIntent, REQUEST_CODE_DETAILS);
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

    @Override
    public void onRefresh() {
        if (!needsNetworkConnection() || NetworkUtils.isConnected(getContext())) {
            onGetMovies();
        } else {
            mBinding.recycler.setVisibility(View.GONE);
            mBinding.txtNoMovies.setText(getNoNetworkMessage());
            mBinding.txtNoMovies.setVisibility(View.VISIBLE);
            onGettingMovies(false);
            registerReceiverForNetwork();
        }
    }

    protected abstract boolean needsNetworkConnection();

    protected int getNoNetworkMessage() {
        return R.string.no_movies_network_error;
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

    @Override
    public void onMoviesReady(List<Movie> movies) {
        int txtNoMoviesVisibility = View.VISIBLE;
        int recyclerVisibility = View.GONE;
        if (movies != null && movies.size() > 0) {
            int posterWidth = getResources().getDimensionPixelSize(R.dimen.movie_poster_size_median);
            MoviesAdapter adapter = new MoviesAdapter(movies, posterWidth);
            adapter.setListener(this);
            mBinding.recycler.setAdapter(adapter);

            txtNoMoviesVisibility = View.GONE;
            recyclerVisibility = View.VISIBLE;
        }
        mBinding.recycler.setVisibility(recyclerVisibility);

        mBinding.txtNoMovies.setText(getNoMoviesMessage());
        mBinding.txtNoMovies.setVisibility(txtNoMoviesVisibility);
    }

    @Override
    public void onGetMoviesFailed() {
        mBinding.recycler.setVisibility(View.GONE);
        if (!NetworkUtils.isConnected(getContext())) {
            mBinding.txtNoMovies.setText(R.string.no_movies_network_error);
        } else {
            mBinding.txtNoMovies.setText(R.string.no_movies);
        }
    }

    @Override
    public void onGettingMovies(final boolean isGetting) {
        mIsGettingMovies = isGetting;
        mBinding.swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                if (isGetting && mIsGettingMovies) {
                    mBinding.swipeRefreshLayout.setRefreshing(true);
                } else {
                    mBinding.swipeRefreshLayout.setRefreshing(false);
                }
            }
        });
    }

    @Inject
    public void setPresenter(MoviesMvpPresenter presenter) {
        mPresenter = presenter;
        mPresenter.attach(this);
    }

    public abstract int getTitleResource();
}
