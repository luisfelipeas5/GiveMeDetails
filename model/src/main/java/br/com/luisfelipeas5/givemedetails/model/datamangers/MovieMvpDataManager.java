package br.com.luisfelipeas5.givemedetails.model.datamangers;

import java.util.List;

import br.com.luisfelipeas5.givemedetails.model.model.movie.Movie;
import br.com.luisfelipeas5.givemedetails.model.model.reviews.Review;
import br.com.luisfelipeas5.givemedetails.model.model.trailer.Trailer;
import io.reactivex.Single;

public interface MovieMvpDataManager {
    Single<List<Movie>> getPopularMovies();

    Single<List<Movie>> getTopRatedMovies();

    Single<Movie> getMovie(String movieId);

    Single<String> getMoviePosterUrl(int width, String movieId);

    Single<String> getMovieTitle(String movieId);

    Single<Movie> getMovieSummary(String movieId);

    Single<Movie> getMovieSocial(String movieId);

    Single<List<Trailer>> getMovieTrailers(String movieId);

    Single<List<Review>> getMovieReviews(String movieId);

    Single<List<Review>> getMovieReviewsByPage(String movieId, int pageIndex);
}
