package br.com.luisfelipeas5.givemedetails.view.details;

import java.util.Date;

import br.com.luisfelipeas5.givemedetails.view.MvpView;

public interface SummaryMvpView extends MvpView {
    void onTitleReady(String title);

    void onSummaryFailed();

    void onOriginalTitleReady(String originalTitle);

    void onOverviewReady(String overview);

    void onReleaseDateReady(Date releaseDate);
}
