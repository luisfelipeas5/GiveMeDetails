package br.com.luisfelipeas5.givemedetails.view.details;

import br.com.luisfelipeas5.givemedetails.model.model.Movie;
import br.com.luisfelipeas5.givemedetails.view.MvpView;

public interface SummaryMvpView extends MvpView {
    void onSummaryFailed();

    void onMovieSummaryReady(Movie mMovie);
}
