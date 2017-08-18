package br.com.luisfelipeas5.givemedetails.view.di.modules.model;

import com.google.gson.Gson;

import java.util.LinkedList;
import java.util.List;

import br.com.luisfelipeas5.givemedetails.model.datamangers.MovieDataManager;
import br.com.luisfelipeas5.givemedetails.model.datamangers.MovieMvpDataManager;
import br.com.luisfelipeas5.givemedetails.model.helpers.DatabaseMvpHelper;
import br.com.luisfelipeas5.givemedetails.model.helpers.MovieApiMvpHelper;
import br.com.luisfelipeas5.givemedetails.model.helpers.MovieCacheMvpHelper;
import br.com.luisfelipeas5.givemedetails.model.model.movie.Movie;
import br.com.luisfelipeas5.givemedetails.model.model.movie.MovieLove;
import br.com.luisfelipeas5.givemedetails.model.model.movie.MovieTMDb;
import br.com.luisfelipeas5.givemedetails.model.model.responsebodies.ReviewsResponseBody;
import br.com.luisfelipeas5.givemedetails.model.model.responsebodies.TrailersResponseBody;
import br.com.luisfelipeas5.givemedetails.model.model.reviews.Review;
import br.com.luisfelipeas5.givemedetails.model.model.trailer.Trailer;
import dagger.Module;
import dagger.Provides;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

@Module
public class ModelTestModule {

    private List<Trailer> trailers;
    private Movie movie;
    private List<Review> reviews;
    private MovieLove movieLove;

    public ModelTestModule() {
        setMovie(getMovieMocked());
        setTrailers(getTrailersMocked());
        setReviews(getReviewsMocked());
        setMovieLove(getMovieLoveMocked());
    }

    @Provides
    MovieApiMvpHelper provideMovieApiMvpHelper() {
        return new MovieApiMvpHelper() {
            @Override
            public Observable<List<Movie>> getPopular() {
                return null;
            }

            @Override
            public Observable<List<Movie>> getTopRated() {
                return null;
            }

            @Override
            public Observable<Movie> getMovie(String movieId) {
                if (movie == null) {
                    return Observable.error(new Exception("Mocked error loading movie"));
                }
                return Observable.just(movie);
            }

            @Override
            public Observable<Movie> getMovieSummary(String movieId) {
                if (movie == null) {
                    return Observable.error(new Exception("Mocked error loading movie's social summary"));
                }
                return Observable.just(movie);
            }

            @Override
            public Observable<Movie> getMovieSocial(String movieId) {
                if (movie == null) {
                    return Observable.error(new Exception("Mocked error loading movie's social info"));
                }
                return Observable.just(movie);
            }

            @Override
            public Observable<List<Trailer>> getTrailers(String movieId) {
                if (trailers == null) {
                    return Observable.error(new Exception("Mocked error loading trailers"));
                }
                return Observable.just(trailers);
            }

            @Override
            public Observable<List<Review>> getReviews(String movieId, int pageIndex) {
                if (reviews == null) {
                    return Observable.error(new Exception("Mocked error loading reviewss"));
                }
                return Observable.just(reviews);
            }
        };
    }

    @Provides
    MovieCacheMvpHelper provideMovieCacheMvpHelper() {
        return new MovieCacheMvpHelper() {
            @Override
            public Single<Movie> getMovie(String movieId) {
                return Single.just(getMovieMocked());
            }

            @Override
            public Single<Boolean> hasMoviePosterOnCache(String movieId) {
                return Single.just(true);
            }

            @Override
            public Single<Boolean> hasMovieTitleOnCache(String movieId) {
                return Single.just(true);
            }

            @Override
            public Single<Boolean> hasMovieSummaryOnCache(String movieId) {
                return Single.just(true);
            }

            @Override
            public Single<Boolean> saveMovie(Movie movie) {
                return Single.just(true);
            }

            @Override
            public Single<Boolean> hasMovieSocialOnCache(String movieId) {
                return Single.just(true);
            }

        };
    }

    @Provides
    DatabaseMvpHelper provideDatabaseMvpHelper() {
        return new DatabaseMvpHelper() {
            @Override
            public Single<Boolean> isLoved(String movieId) {
                return Single.just(movieLove.isLoved());
            }

            @Override
            public Completable setIsLoved(Movie movie, boolean isLoved) {
                return Completable.complete();
            }
        };
    }

    @Provides
    MovieMvpDataManager provideMovieMvpDataManager(MovieApiMvpHelper movieApiMvpHelper,
                                                   MovieCacheMvpHelper movieCacheMvpHelper,
                                                   DatabaseMvpHelper databaseMvpHelper) {
        return new MovieDataManager(movieApiMvpHelper, movieCacheMvpHelper, databaseMvpHelper);
    }

    public void setTrailers(List<Trailer> trailers) {
        this.trailers = trailers;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    private void setMovieLove(MovieLove movieLove) {
        this.movieLove = movieLove;
    }

    public static List<Trailer> getTrailersMocked() {
        String trailersJsonString = "{\n" +
                "    \"id\": 315635,\n" +
                "    \"results\": [\n" +
                "        {\n" +
                "            \"id\": \"58f142879251415536004a66\",\n" +
                "            \"iso_639_1\": \"en\",\n" +
                "            \"iso_3166_1\": \"US\",\n" +
                "            \"key\": \"rk-dF1lIbIg\",\n" +
                "            \"name\": \"Official Trailer\",\n" +
                "            \"site\": \"YouTube\",\n" +
                "            \"size\": 1080,\n" +
                "            \"type\": \"Trailer\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"58f142cd9251415556004851\",\n" +
                "            \"iso_639_1\": \"en\",\n" +
                "            \"iso_3166_1\": \"US\",\n" +
                "            \"key\": \"DiTECkLZ8HM\",\n" +
                "            \"name\": \"Official Trailer #2\",\n" +
                "            \"site\": \"YouTube\",\n" +
                "            \"size\": 1080,\n" +
                "            \"type\": \"Trailer\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"58f1430ac3a3681a52004703\",\n" +
                "            \"iso_639_1\": \"en\",\n" +
                "            \"iso_3166_1\": \"US\",\n" +
                "            \"key\": \"xpu9yRO_rvU\",\n" +
                "            \"name\": \"Official International Trailer #2\",\n" +
                "            \"site\": \"YouTube\",\n" +
                "            \"size\": 1080,\n" +
                "            \"type\": \"Trailer\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"58f14331c3a36819db004235\",\n" +
                "            \"iso_639_1\": \"en\",\n" +
                "            \"iso_3166_1\": \"US\",\n" +
                "            \"key\": \"lCkVr1n1eCA\",\n" +
                "            \"name\": \"Official International Trailer\",\n" +
                "            \"site\": \"YouTube\",\n" +
                "            \"size\": 1080,\n" +
                "            \"type\": \"Trailer\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"592540c4c3a36877bc00dacd\",\n" +
                "            \"iso_639_1\": \"en\",\n" +
                "            \"iso_3166_1\": \"US\",\n" +
                "            \"key\": \"xEvV3OsE2WM\",\n" +
                "            \"name\": \"Official Trailer #3\",\n" +
                "            \"site\": \"YouTube\",\n" +
                "            \"size\": 1080,\n" +
                "            \"type\": \"Trailer\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"5925cde9c3a36877bc014de4\",\n" +
                "            \"iso_639_1\": \"en\",\n" +
                "            \"iso_3166_1\": \"US\",\n" +
                "            \"key\": \"xbQdPBiF3Co\",\n" +
                "            \"name\": \"International Trailer #3\",\n" +
                "            \"site\": \"YouTube\",\n" +
                "            \"size\": 1080,\n" +
                "            \"type\": \"Trailer\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        TrailersResponseBody trailersResponseBody = new Gson()
                .fromJson(trailersJsonString, TrailersResponseBody.class);
        return new LinkedList<Trailer>(trailersResponseBody.getTrailers());
    }

    public static Movie getMovieMocked() {
        String movieJsonString = "{\n" +
                "            \"vote_count\": 1613,\n" +
                "            \"id\": 315635,\n" +
                "            \"video\": false,\n" +
                "            \"vote_average\": 7.4,\n" +
                "            \"title\": \"Homem-Aranha: De Volta ao Lar\",\n" +
                "            \"popularity\": 75.463116,\n" +
                "            \"poster_path\": \"/9C8NPGOpDUDPRVe5gSrAayQhPkL.jpg\",\n" +
                "            \"original_language\": \"en\",\n" +
                "            \"original_title\": \"Spider-Man: Homecoming\",\n" +
                "            \"genre_ids\": [\n" +
                "                28,\n" +
                "                12,\n" +
                "                878\n" +
                "            ],\n" +
                "            \"backdrop_path\": \"/fn4n6uOYcB6Uh89nbNPoU2w80RV.jpg\",\n" +
                "            \"adult\": false,\n" +
                "            \"overview\": \"Depois de atuar ao lado dos Vingadores, chegou a hora do pequeno Peter Parker (Tom Holland) voltar para casa e para a sua vida, já não mais tão normal. Lutando diariamente contra pequenos crimes nas redondezas, ele pensa ter encontrado a missão de sua vida quando o terrível vilão Abutre (Michael Keaton) surge amedrontando a cidade. O problema é que a tarefa não será tão fácil como ele imaginava.\",\n" +
                "            \"release_date\": \"2017-07-05\"\n" +
                "        }";
        return new Gson().fromJson(movieJsonString, MovieTMDb.class);
    }

    public static List<Review> getReviewsMocked() {
        String reviewsResponseBodyJson = "{\n" +
                "    \"id\": 211672,\n" +
                "    \"page\": 1,\n" +
                "    \"results\": [\n" +
                "        {\n" +
                "            \"id\": \"55a58e46c3a3682bb2000065\",\n" +
                "            \"author\": \"Daniel Doglas\",\n" +
                "            \"content\": \"Daniel Doglas The minions are a nice idea and the animation and London recreation is really good, but that's about it.\\r\\n\\r\\nThe script is boring and the jokes not really funny.\",\n" +
                "            \"url\": \"https://www.themoviedb.org/review/55a58e46c3a3682bb2000065\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"55e108c89251416c0b0006dd\",\n" +
                "            \"author\": \"Luís Felipe\",\n" +
                "            \"content\": \"Luís Felipe a nice idea and the animation.the new thing in animation field.a movie that every one should like an kid or old man.\",\n" +
                "            \"url\": \"https://www.themoviedb.org/review/55e108c89251416c0b0006dd\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"55a58e46c3a3682bb2000065\",\n" +
                "            \"author\": \"Galdino Galdino\",\n" +
                "            \"content\": \"Galdino Galdino The minions are a nice idea and the animation and London recreation is really good, but that's about it.\\r\\n\\r\\nThe script is boring and the jokes not really funny.\",\n" +
                "            \"url\": \"https://www.themoviedb.org/review/55a58e46c3a3682bb2000065\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"55a58e46c3a3682bb2000065\",\n" +
                "            \"author\": \"Andres Gomez\",\n" +
                "            \"content\": \"The minions are a nice idea and the animation and London recreation is really good, but that's about it.\\r\\n\\r\\nThe script is boring and the jokes not really funny.\",\n" +
                "            \"url\": \"https://www.themoviedb.org/review/55a58e46c3a3682bb2000065\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"55e108c89251416c0b0006dd\",\n" +
                "            \"author\": \"movizonline.com\",\n" +
                "            \"content\": \"a nice idea and the animation.the new thing in animation field.a movie that every one should like an kid or old man.\",\n" +
                "            \"url\": \"https://www.themoviedb.org/review/55e108c89251416c0b0006dd\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"total_pages\": 1,\n" +
                "    \"total_results\": 2\n" +
                "}";
        ReviewsResponseBody reviewsResponseBody = new Gson().fromJson(reviewsResponseBodyJson, ReviewsResponseBody.class);
        return new LinkedList<Review>(reviewsResponseBody.getReviews());
    }

    private MovieLove getMovieLoveMocked() {
        MovieLove movieLove = new MovieLove();
        movieLove.setMovieId(movie.getId());
        movieLove.setLoved(true);
        return movieLove;
    }
}
