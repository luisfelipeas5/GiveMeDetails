package br.com.luisfelipeas5.givemedetails.view.di.components;

import br.com.luisfelipeas5.givemedetails.view.activities.DetailActivity;
import br.com.luisfelipeas5.givemedetails.view.fragments.details.PosterFragment;
import br.com.luisfelipeas5.givemedetails.view.fragments.details.ReviewsFragment;
import br.com.luisfelipeas5.givemedetails.view.fragments.details.SocialFragment;
import br.com.luisfelipeas5.givemedetails.view.fragments.details.SummaryFragment;
import br.com.luisfelipeas5.givemedetails.view.fragments.details.TrailersFragment;
import br.com.luisfelipeas5.givemedetails.view.fragments.lists.MoviesFragment;

public interface BaseComponent {

    void inject(MoviesFragment moviesFragment);

    void inject(PosterFragment posterFragment);

    void inject(DetailActivity detailActivity);

    void inject(SummaryFragment summaryFragment);

    void inject(SocialFragment socialFragment);

    void inject(TrailersFragment trailersFragment);

    void inject(ReviewsFragment reviewsFragment);
}
