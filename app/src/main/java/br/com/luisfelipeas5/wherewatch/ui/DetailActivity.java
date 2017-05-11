package br.com.luisfelipeas5.wherewatch.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import br.com.luisfelipeas5.wherewatch.R;
import br.com.luisfelipeas5.wherewatch.api.WhereWatchApi;
import br.com.luisfelipeas5.wherewatch.api.tasks.GetMovieTask;
import br.com.luisfelipeas5.wherewatch.databinding.ActivityDetailBinding;
import br.com.luisfelipeas5.wherewatch.model.Movie;
import br.com.luisfelipeas5.wherewatch.ui.fragments.DetailFragment;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE =
            "br.com.luisfelipeas5.wherewatch.ui.DetailActivity.EXTRA_MOVIE";
    private ActivityDetailBinding mBinding;
    private GetMovieTask mGetMovieTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_MOVIE)) {
            Movie movie = intent.getParcelableExtra(EXTRA_MOVIE);
            mBinding.setMovie(movie);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        final Movie movie = mBinding.getMovie();
        if (movie != null) {
            mGetMovieTask = WhereWatchApi.getMovie(this, movie, new WhereWatchApi.Callback<Movie>() {
                @Override
                public void onResult(Movie movies) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    DetailFragment detailFragment =
                            (DetailFragment) fragmentManager.findFragmentById(R.id.fragment_detail);
                    detailFragment.setMovie(movie);
                }
            });
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mGetMovieTask != null && !mGetMovieTask.isCancelled()) {
            mGetMovieTask.cancel(true);
        }
    }
}
