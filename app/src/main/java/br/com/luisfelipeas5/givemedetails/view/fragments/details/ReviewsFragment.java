package br.com.luisfelipeas5.givemedetails.view.fragments.details;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import javax.inject.Inject;

import br.com.luisfelipeas5.givemedetails.R;
import br.com.luisfelipeas5.givemedetails.adapters.recyclers.ReviewsAdapter;
import br.com.luisfelipeas5.givemedetails.databinding.FragmentReviewsBinding;
import br.com.luisfelipeas5.givemedetails.model.model.reviews.Review;
import br.com.luisfelipeas5.givemedetails.presenter.details.ReviewsMvpPresenter;
import br.com.luisfelipeas5.givemedetails.view.MoviesApp;
import br.com.luisfelipeas5.givemedetails.view.details.ReviewsMvpView;
import br.com.luisfelipeas5.givemedetails.view.di.components.BaseComponent;

public class ReviewsFragment extends Fragment implements ReviewsMvpView, View.OnClickListener {

    private ReviewsMvpPresenter presenter;
    private FragmentReviewsBinding binding;
    private Listener listener;
    private boolean mShowPreview;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentReviewsBinding.inflate(inflater, container, false);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.recyclerReviews.setLayoutManager(layoutManager);
        binding.recyclerReviews.setNestedScrollingEnabled(false);

        binding.buttonSeeAllReviews.setOnClickListener(this);

        MoviesApp moviesApp = (MoviesApp) getContext().getApplicationContext();
        BaseComponent baseComponent = moviesApp.getDiComponent();
        baseComponent.inject(this);

        return binding.getRoot();
    }

    @Override
    public void onReviewsReady(List<Review> reviews) {
        int warningVisibility = View.VISIBLE;
        int recyclerVisibility = View.GONE;

        if (reviews.size() > 0) {
            ReviewsAdapter reviewsAdapter = new ReviewsAdapter(reviews, !mShowPreview);
            binding.recyclerReviews.setAdapter(reviewsAdapter);

            warningVisibility = View.GONE;
            recyclerVisibility = View.VISIBLE;
        }

        binding.recyclerReviews.setVisibility(recyclerVisibility);
        binding.txtNoReviews.setVisibility(warningVisibility);
    }

    @Override
    public void onGetReviewsFailed() {
        binding.recyclerReviews.setVisibility(View.GONE);
        binding.txtNoReviews.setVisibility(View.VISIBLE);
    }

    @Override
    public void setMovieId(String movieId, boolean showPreview) {
        mShowPreview = showPreview;
        if (showPreview) {
            presenter.getReviewsPreviews(movieId);
        } else {
            presenter.getNextReviews(movieId);
        }
    }

    @Override
    public void onGettingReviews(boolean isGetting) {
        int progressBarVisibility = View.GONE;
        if (isGetting) {
            progressBarVisibility = View.VISIBLE;
        }
        binding.progressBar.setVisibility(progressBarVisibility);
    }

    @Override
    public void showSeeAllReviewsButton() {
        binding.buttonSeeAllReviews.setVisibility(View.VISIBLE);
    }

    @Inject
    public void setPresenter(ReviewsMvpPresenter presenter) {
        this.presenter = presenter;
        presenter.attach(this);
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.button_see_all_reviews) {
            if (listener != null) {
                listener.onSeeAllReviewsClicked();
            }
        }
    }

    public interface Listener {
        void onSeeAllReviewsClicked();
    }
}
