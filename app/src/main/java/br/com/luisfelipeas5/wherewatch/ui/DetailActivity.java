package br.com.luisfelipeas5.wherewatch.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import br.com.luisfelipeas5.wherewatch.R;
import br.com.luisfelipeas5.wherewatch.api.WhereWatchApi;
import br.com.luisfelipeas5.wherewatch.api.tasks.GetMovieTask;
import br.com.luisfelipeas5.wherewatch.databinding.ActivityDetailBinding;
import br.com.luisfelipeas5.wherewatch.model.Movie;
import br.com.luisfelipeas5.wherewatch.ui.fragments.DetailFragment;
import br.com.luisfelipeas5.wherewatch.ui.fragments.SocialFragment;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String EXTRA_MOVIE =
            "br.com.luisfelipeas5.wherewatch.ui.DetailActivity.EXTRA_MOVIE";

    private ActivityDetailBinding mBinding;
    private GetMovieTask mGetMovieTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        mBinding.imgBackArrow.setOnClickListener(this);

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_MOVIE)) {
            Movie movie = intent.getParcelableExtra(EXTRA_MOVIE);
            mBinding.setMovie(movie);
        }

        getMovie();
    }

    private void getMovie() {
        mBinding.cardLoading.setVisibility(View.VISIBLE);
        final Movie movie = mBinding.getMovie();
        if (movie != null) {
            mGetMovieTask = WhereWatchApi.getMovie(this, movie, new WhereWatchApi.Callback<Movie>() {
                @Override
                public void onResult(Movie movie) {
                    onMovieReady(movie);
                }
            });
        }
    }

    private void onMovieReady(Movie movie) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        DetailFragment detailFragment =
                (DetailFragment) fragmentManager.findFragmentById(R.id.fragment_detail);
        detailFragment.setMovie(movie);

        SocialFragment socialFragment =
                (SocialFragment) fragmentManager.findFragmentById(R.id.fragment_social);
        socialFragment.setMovie(movie);

        mBinding.cardLoading.setVisibility(View.GONE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mGetMovieTask != null && !mGetMovieTask.isCancelled()) {
            mGetMovieTask.cancel(true);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.img_back_arrow:
                onBackPressed();
                break;
        }
    }
}
