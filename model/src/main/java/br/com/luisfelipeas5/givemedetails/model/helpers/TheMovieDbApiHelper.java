package br.com.luisfelipeas5.givemedetails.model.helpers;

import android.content.Context;

import java.util.LinkedList;
import java.util.List;

import br.com.luisfelipeas5.givemedetails.model.R;
import br.com.luisfelipeas5.givemedetails.model.model.movie.Movie;
import br.com.luisfelipeas5.givemedetails.model.model.movie.MovieTMDb;
import br.com.luisfelipeas5.givemedetails.model.model.responsebodies.MoviesResponseBody;
import br.com.luisfelipeas5.givemedetails.model.model.responsebodies.TrailersResponseBody;
import br.com.luisfelipeas5.givemedetails.model.model.trailer.Trailer;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class TheMovieDbApiHelper implements MovieApiMvpHelper {

    private static final String BASE_URL = "https://api.themoviedb.org/";

    private static final String GET_MOVIES_POPULAR_PATH = "3/movie/popular";
    private static final String GET_MOVIES_TOP_RATED_PATH = "3/movie/top_rated";

    private static final String MOVIE_ID_PATH = "movieId";
    private static final String GET_MOVIE_PATH = "3/movie/{" + MOVIE_ID_PATH + "}";
    private static final String GET_TRAILERS_PATH = GET_MOVIE_PATH + "/videos";

    private static final String KEY_QUERY_API_KEY = "api_key";

    private final TheMovieDbApi mTheMovieDbApi;
    private String mApiKey;

    public TheMovieDbApiHelper(Context context) {
        Retrofit retrofit = getRetrofit();
        mTheMovieDbApi = retrofit.create(TheMovieDbApi.class);

        if (context != null) {
            mApiKey = context.getString(R.string.the_movie_db_api_key);
        }
    }

    @android.support.annotation.NonNull
    private Retrofit getRetrofit() {
        return new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
    }

    @Override
    public Observable<List<Movie>> getPopular() {
        return mTheMovieDbApi.getPopular(mApiKey)
                .flatMap(getMovieResponseMapper());
    }

    @Override
    public Observable<List<Movie>> getTopRated() {
        return mTheMovieDbApi.getTopRated(mApiKey)
                .flatMap(getMovieResponseMapper());
    }

    @Override
    public Observable<Movie> getMovie(String movieId) {
        return mTheMovieDbApi
                .getMovie(movieId, mApiKey)
                .cast(Movie.class);
    }

    @Override
    public Observable<Movie> getMovieSummary(String movieId) {
        return getMovie(movieId);
    }

    @Override
    public Observable<Movie> getMovieSocial(String movieId) {
        return getMovie(movieId);
    }

    @Override
    public Observable<List<Trailer>> getTrailers(String movieId) {
        return mTheMovieDbApi.getTrailers(movieId, mApiKey)
                .flatMap(getTrailerResponseMapper());
    }

    private Function<TrailersResponseBody, Observable<List<Trailer>>> getTrailerResponseMapper() {
        return new Function<TrailersResponseBody, Observable<List<Trailer>>>() {
            @Override
            public Observable<List<Trailer>> apply(@NonNull TrailersResponseBody trailersResponseBody) throws Exception {
                List<Trailer> trailers = new LinkedList<Trailer>(trailersResponseBody.getTrailers());
                return Observable.just(trailers);
            }
        };
    }


    @android.support.annotation.NonNull
    private Function<MoviesResponseBody, Observable<List<Movie>>> getMovieResponseMapper() {
        return new Function<MoviesResponseBody, Observable<List<Movie>>>() {
            @Override
            public Observable<List<Movie>> apply(@NonNull MoviesResponseBody moviesResponseBody) throws Exception {
                List<Movie> movies = new LinkedList<>();
                movies.addAll(moviesResponseBody.getMovies());
                return Observable.just(movies);
            }
        };
    }

    interface TheMovieDbApi {

        @GET(GET_MOVIES_POPULAR_PATH)
        Observable<MoviesResponseBody> getPopular(@Query(KEY_QUERY_API_KEY) String apiKey);

        @GET(GET_MOVIES_TOP_RATED_PATH)
        Observable<MoviesResponseBody> getTopRated(@Query(KEY_QUERY_API_KEY) String apiKey);

        @GET(GET_MOVIE_PATH)
        Observable<MovieTMDb> getMovie(@Path(MOVIE_ID_PATH) String movieId,
                                       @Query(KEY_QUERY_API_KEY) String apiKey);

        @GET(GET_TRAILERS_PATH)
        Observable<TrailersResponseBody> getTrailers(@Path(MOVIE_ID_PATH) String movieId,
                                                     @Query(KEY_QUERY_API_KEY) String apiKey);
    }

}
