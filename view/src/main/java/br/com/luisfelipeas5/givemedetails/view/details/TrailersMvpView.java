package br.com.luisfelipeas5.givemedetails.view.details;

import java.util.List;

import br.com.luisfelipeas5.givemedetails.model.model.trailer.Trailer;
import br.com.luisfelipeas5.givemedetails.view.MvpView;

public interface TrailersMvpView extends MvpView {
    void setMovieId(String movieId, boolean showPreview);

    void onTrailersReady(List<Trailer> trailers);
    void onGetTrailersFailed();

    void showSeeAllTrailersButton();
}
