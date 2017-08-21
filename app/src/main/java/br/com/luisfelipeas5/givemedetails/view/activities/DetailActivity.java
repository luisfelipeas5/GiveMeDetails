package br.com.luisfelipeas5.givemedetails.view.activities;

import android.animation.Animator;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ScrollView;

import javax.inject.Inject;

import br.com.luisfelipeas5.givemedetails.R;
import br.com.luisfelipeas5.givemedetails.databinding.ActivityDetailBinding;
import br.com.luisfelipeas5.givemedetails.model.model.movie.Movie;
import br.com.luisfelipeas5.givemedetails.presenter.details.MovieDetailMvpPresenter;
import br.com.luisfelipeas5.givemedetails.view.MoviesApp;
import br.com.luisfelipeas5.givemedetails.view.details.MovieMvpView;
import br.com.luisfelipeas5.givemedetails.view.di.components.BaseComponent;
import br.com.luisfelipeas5.givemedetails.view.fragments.details.LoveFragment;
import br.com.luisfelipeas5.givemedetails.view.fragments.details.ReviewsFragment;
import br.com.luisfelipeas5.givemedetails.view.fragments.details.SummaryFragment;
import br.com.luisfelipeas5.givemedetails.view.fragments.details.PosterFragment;
import br.com.luisfelipeas5.givemedetails.view.fragments.details.SocialFragment;
import br.com.luisfelipeas5.givemedetails.view.fragments.details.TrailersFragment;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener, MovieMvpView {

    public static final String EXTRA_MOVIE_ID = "br.com.luisfelipeas5.givemedetails.view.activities.DetailActivity.EXTRA_MOVIE_ID";
    private static final long FADE_DURATION = 250;
    private MovieDetailMvpPresenter mPresenter;
    private ActivityDetailBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        mBinding.imgBackArrow.setOnClickListener(this);

        MoviesApp moviesApp = (MoviesApp) getApplicationContext();
        BaseComponent appComponent = moviesApp.getDiComponent();
        appComponent.inject(this);

        Intent intent = getIntent();
        if (intent != null) {
            String movieId = intent.getStringExtra(EXTRA_MOVIE_ID);
            mPresenter.getMovie(movieId);
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

    @Override
    public void onMovieReady(Movie movie) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        String movieId = movie.getId();

        PosterFragment posterFragment = (PosterFragment) fragmentManager.findFragmentById(R.id.fragment_poster_background);
        posterFragment.setMovieId(movieId);

        SummaryFragment summaryFragment = (SummaryFragment) fragmentManager.findFragmentById(R.id.fragment_detail);
        summaryFragment.setMovieId(movieId);

        SocialFragment socialFragment = (SocialFragment) fragmentManager.findFragmentById(R.id.fragment_social);
        socialFragment.setMovieId(movieId);

        TrailersFragment trailersPreviewFragment = (TrailersFragment) fragmentManager.findFragmentById(R.id.fragment_trailers_preview);
        trailersPreviewFragment.setMovieId(movieId, true);
        trailersPreviewFragment.setListener(new TrailersFragment.Listener() {

            @Override
            public void onSeeAllTrailersClicked() {
                showAllTrailers();
            }

        });

        TrailersFragment trailersFragment = (TrailersFragment) fragmentManager.findFragmentById(R.id.fragment_trailers);
        trailersFragment.setMovieId(movieId, false);

        ReviewsFragment reviewsPreviewFragment = (ReviewsFragment) fragmentManager.findFragmentById(R.id.fragment_reviews_preview);
        reviewsPreviewFragment.setMovieId(movieId, true);
        reviewsPreviewFragment.setListener(new ReviewsFragment.Listener() {
            @Override
            public void onSeeAllReviewsClicked() {
                showAllReviews();
            }
        });

        ReviewsFragment reviewsFragment = (ReviewsFragment) fragmentManager.findFragmentById(R.id.fragment_reviews);
        reviewsFragment.setMovieId(movieId, false);

        LoveFragment loveFragment = (LoveFragment) fragmentManager.findFragmentById(R.id.fragment_love);
        loveFragment.setMovieId(movieId);
    }

    @Override
    public void onGetMovieFailed() {

    }

    @Inject
    public void setPresenter(MovieDetailMvpPresenter presenter) {
        mPresenter = presenter;
        presenter.attach(this);
    }

    @Override
    public void onBackPressed() {
        if (mBinding.scrollView.getVisibility() == View.VISIBLE) {
            super.onBackPressed();
        } else {
            switchScrolls(mBinding.scrollView, mBinding.subScrollView, 0, null);
        }
    }

    private void switchScrolls(final ScrollView scrollViewShow, final ScrollView scrollViewHide, int fragmentIdsToShow, int[] fragmentIdsToHide) {
        if (fragmentIdsToHide != null) {
            for (int fragmentToHide : fragmentIdsToHide) {
                hideFragment(fragmentToHide);
            }
        }

        if (fragmentIdsToShow > 0) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .show(fragmentManager.findFragmentById(fragmentIdsToShow))
                    .commit();
        }

        scrollViewHide.setAlpha(1);
        scrollViewHide.animate()
                .alpha(0)
                .setDuration(FADE_DURATION)
                .setInterpolator(new AccelerateInterpolator())
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {
                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        scrollViewHide.setAlpha(0);
                        scrollViewHide.setVisibility(View.GONE);

                        scrollViewShow.setAlpha(0);
                        scrollViewShow.setVisibility(View.VISIBLE);
                        scrollViewShow.animate()
                                .alpha(1)
                                .setDuration(FADE_DURATION)
                                .setInterpolator(new DecelerateInterpolator())
                                .setListener(new Animator.AnimatorListener() {
                                    @Override
                                    public void onAnimationStart(Animator animator) {
                                    }

                                    @Override
                                    public void onAnimationEnd(Animator animator) {
                                        scrollViewShow.setAlpha(1);
                                        scrollViewShow.setVisibility(View.VISIBLE);
                                    }

                                    @Override
                                    public void onAnimationCancel(Animator animator) {

                                    }

                                    @Override
                                    public void onAnimationRepeat(Animator animator) {

                                    }
                                });
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });
    }

    private void showAllReviews() {
        final int[] fragmentsToHide = {R.id.fragment_trailers};
        final int fragmentsToShow = R.id.fragment_reviews;
        switchScrolls(mBinding.subScrollView, mBinding.scrollView, fragmentsToShow, fragmentsToHide);
    }

    private void showAllTrailers() {
        final int[] fragmentsToHide = {R.id.fragment_reviews};
        final int fragmentsToShow = R.id.fragment_trailers;
        switchScrolls(mBinding.subScrollView, mBinding.scrollView, fragmentsToShow, fragmentsToHide);
    }

    private void hideFragment(int fragmentId) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .hide(fragmentManager.findFragmentById(fragmentId))
                .commit();
    }
}
