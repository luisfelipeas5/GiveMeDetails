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
import br.com.luisfelipeas5.givemedetails.model.model.responsebodies.MoviesResponseBody;
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
    private List<Movie> mList;

    public ModelTestModule() {
        setMovie(getMovieMocked());
        setTrailers(getTrailersMocked());
        setReviews(getReviewsMocked());
        setMovieLove(getMovieLoveMocked(movie.getId()));
        setMovieLists(getMoviesMocked());
    }

    @Provides
    MovieApiMvpHelper provideMovieApiMvpHelper() {
        return new MovieApiMvpHelper() {
            @Override
            public Observable<List<Movie>> getPopular() {
                if (mList != null) {
                    return Observable.just(mList);
                }
                return Observable.error(new Exception("Mocked error getting Popular"));
            }

            @Override
            public Observable<List<Movie>> getTopRated() {
                if (mList != null) {
                    return Observable.just(mList);
                }
                return Observable.error(new Exception("Mocked error getting Top Rated"));
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

            @Override
            public Single<Boolean> clearCache() {
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

            @Override
            public Observable<List<Movie>> getLovedMovies() {
                return Observable.just(mList);
            }

            @Override
            public Single<Movie> getMovie(String movieId) {
                return Single.just(movie);
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

    public void setMovieLove(MovieLove movieLove) {
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

    private static MovieLove getMovieLoveMocked(String movieId) {
        MovieLove movieLove = new MovieLove();
        movieLove.setMovieId(movieId);
        movieLove.setLoved(false);
        return movieLove;
    }

    private void setMovieLists(List<Movie> movieLists) {
        this.mList = movieLists;
    }

    private static List<Movie> getMoviesMocked() {
        String moviesJson = "{\n" +
                "    \"page\": 1,\n" +
                "    \"total_results\": 19620,\n" +
                "    \"total_pages\": 981,\n" +
                "    \"results\": [\n" +
                "        {\n" +
                "            \"vote_count\": 4102,\n" +
                "            \"id\": 211672,\n" +
                "            \"video\": false,\n" +
                "            \"vote_average\": 6.4,\n" +
                "            \"title\": \"Minions\",\n" +
                "            \"popularity\": 239.482372,\n" +
                "            \"poster_path\": \"/q0R4crx2SehcEEQEkYObktdeFy.jpg\",\n" +
                "            \"original_language\": \"en\",\n" +
                "            \"original_title\": \"Minions\",\n" +
                "            \"genre_ids\": [\n" +
                "                10751,\n" +
                "                16,\n" +
                "                12,\n" +
                "                35\n" +
                "            ],\n" +
                "            \"backdrop_path\": \"/uX7LXnsC7bZJZjn048UCOwkPXWJ.jpg\",\n" +
                "            \"adult\": false,\n" +
                "            \"overview\": \"Minions Stuart, Kevin and Bob are recruited by Scarlet Overkill, a super-villain who, alongside her inventor husband Herb, hatches a plot to take over the world.\",\n" +
                "            \"release_date\": \"2015-06-17\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"vote_count\": 4670,\n" +
                "            \"id\": 321612,\n" +
                "            \"video\": false,\n" +
                "            \"vote_average\": 6.8,\n" +
                "            \"title\": \"Beauty and the Beast\",\n" +
                "            \"popularity\": 114.381442,\n" +
                "            \"poster_path\": \"/tWqifoYuwLETmmasnGHO7xBjEtt.jpg\",\n" +
                "            \"original_language\": \"en\",\n" +
                "            \"original_title\": \"Beauty and the Beast\",\n" +
                "            \"genre_ids\": [\n" +
                "                10751,\n" +
                "                14,\n" +
                "                10749\n" +
                "            ],\n" +
                "            \"backdrop_path\": \"/6aUWe0GSl69wMTSWWexsorMIvwU.jpg\",\n" +
                "            \"adult\": false,\n" +
                "            \"overview\": \"A live-action adaptation of Disney's version of the classic 'Beauty and the Beast' tale of a cursed prince and a beautiful young woman who helps him break the spell.\",\n" +
                "            \"release_date\": \"2017-03-16\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"vote_count\": 2448,\n" +
                "            \"id\": 315635,\n" +
                "            \"video\": false,\n" +
                "            \"vote_average\": 7.4,\n" +
                "            \"title\": \"Spider-Man: Homecoming\",\n" +
                "            \"popularity\": 100.221314,\n" +
                "            \"poster_path\": \"/c24sv2weTHPsmDa7jEMN0m2P3RT.jpg\",\n" +
                "            \"original_language\": \"en\",\n" +
                "            \"original_title\": \"Spider-Man: Homecoming\",\n" +
                "            \"genre_ids\": [\n" +
                "                28,\n" +
                "                12,\n" +
                "                878\n" +
                "            ],\n" +
                "            \"backdrop_path\": \"/vc8bCGjdVp0UbMNLzHnHSLRbBWQ.jpg\",\n" +
                "            \"adult\": false,\n" +
                "            \"overview\": \"Following the events of Captain America: Civil War, Peter Parker, with the help of his mentor Tony Stark, tries to balance his life as an ordinary high school student in Queens, New York City, with fighting crime as his superhero alter ego Spider-Man as a new threat, the Vulture, emerges.\",\n" +
                "            \"release_date\": \"2017-07-05\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"vote_count\": 1297,\n" +
                "            \"id\": 324852,\n" +
                "            \"video\": false,\n" +
                "            \"vote_average\": 6.2,\n" +
                "            \"title\": \"Despicable Me 3\",\n" +
                "            \"popularity\": 59.912177,\n" +
                "            \"poster_path\": \"/5qcUGqWoWhEsoQwNUrtf3y3fcWn.jpg\",\n" +
                "            \"original_language\": \"en\",\n" +
                "            \"original_title\": \"Despicable Me 3\",\n" +
                "            \"genre_ids\": [\n" +
                "                878,\n" +
                "                12,\n" +
                "                16,\n" +
                "                35,\n" +
                "                10751\n" +
                "            ],\n" +
                "            \"backdrop_path\": \"/puV2PFq42VQPItaygizgag8jrXa.jpg\",\n" +
                "            \"adult\": false,\n" +
                "            \"overview\": \"Gru and his wife Lucy must stop former '80s child star Balthazar Bratt from achieving world domination.\",\n" +
                "            \"release_date\": \"2017-06-15\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"vote_count\": 1068,\n" +
                "            \"id\": 281338,\n" +
                "            \"video\": false,\n" +
                "            \"vote_average\": 6.7,\n" +
                "            \"title\": \"War for the Planet of the Apes\",\n" +
                "            \"popularity\": 55.427425,\n" +
                "            \"poster_path\": \"/y52mjaCLoJJzxfcDDlksKDngiDx.jpg\",\n" +
                "            \"original_language\": \"en\",\n" +
                "            \"original_title\": \"War for the Planet of the Apes\",\n" +
                "            \"genre_ids\": [\n" +
                "                18,\n" +
                "                878,\n" +
                "                10752\n" +
                "            ],\n" +
                "            \"backdrop_path\": \"/ulMscezy9YX0bhknvJbZoUgQxO5.jpg\",\n" +
                "            \"adult\": false,\n" +
                "            \"overview\": \"Caesar and his apes are forced into a deadly conflict with an army of humans led by a ruthless Colonel. After the apes suffer unimaginable losses, Caesar wrestles with his darker instincts and begins his own mythic quest to avenge his kind. As the journey finally brings them face to face, Caesar and the Colonel are pitted against each other in an epic battle that will determine the fate of both their species and the future of the planet.\",\n" +
                "            \"release_date\": \"2017-07-11\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"vote_count\": 3597,\n" +
                "            \"id\": 283995,\n" +
                "            \"video\": false,\n" +
                "            \"vote_average\": 7.6,\n" +
                "            \"title\": \"Guardians of the Galaxy Vol. 2\",\n" +
                "            \"popularity\": 55.32575,\n" +
                "            \"poster_path\": \"/y4MBh0EjBlMuOzv9axM4qJlmhzz.jpg\",\n" +
                "            \"original_language\": \"en\",\n" +
                "            \"original_title\": \"Guardians of the Galaxy Vol. 2\",\n" +
                "            \"genre_ids\": [\n" +
                "                28,\n" +
                "                12,\n" +
                "                35,\n" +
                "                878\n" +
                "            ],\n" +
                "            \"backdrop_path\": \"/aJn9XeesqsrSLKcHfHP4u5985hn.jpg\",\n" +
                "            \"adult\": false,\n" +
                "            \"overview\": \"The Guardians must fight to keep their newfound family together as they unravel the mysteries of Peter Quill's true parentage.\",\n" +
                "            \"release_date\": \"2017-04-19\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"vote_count\": 227,\n" +
                "            \"id\": 396422,\n" +
                "            \"video\": false,\n" +
                "            \"vote_average\": 6.3,\n" +
                "            \"title\": \"Annabelle: Creation\",\n" +
                "            \"popularity\": 53.327718,\n" +
                "            \"poster_path\": \"/tb86j8jVCVsdZnzf8I6cIi65IeM.jpg\",\n" +
                "            \"original_language\": \"en\",\n" +
                "            \"original_title\": \"Annabelle: Creation\",\n" +
                "            \"genre_ids\": [\n" +
                "                53,\n" +
                "                27\n" +
                "            ],\n" +
                "            \"backdrop_path\": \"/o8u0NyEigCEaZHBdCYTRfXR8U4i.jpg\",\n" +
                "            \"adult\": false,\n" +
                "            \"overview\": \"Several years after the tragic death of their little girl, a dollmaker and his wife welcome a nun and several girls from a shuttered orphanage into their home, soon becoming the target of the dollmaker's possessed creation, Annabelle.\",\n" +
                "            \"release_date\": \"2017-08-09\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"vote_count\": 1352,\n" +
                "            \"id\": 374720,\n" +
                "            \"video\": false,\n" +
                "            \"vote_average\": 7.4,\n" +
                "            \"title\": \"Dunkirk\",\n" +
                "            \"popularity\": 35.442866,\n" +
                "            \"poster_path\": \"/bOXBV303Fgkzn2K4FeKDc0O31q4.jpg\",\n" +
                "            \"original_language\": \"en\",\n" +
                "            \"original_title\": \"Dunkirk\",\n" +
                "            \"genre_ids\": [\n" +
                "                28,\n" +
                "                18,\n" +
                "                36,\n" +
                "                53,\n" +
                "                10752\n" +
                "            ],\n" +
                "            \"backdrop_path\": \"/fudEG1VUWuOqleXv6NwCExK0VLy.jpg\",\n" +
                "            \"adult\": false,\n" +
                "            \"overview\": \"Miraculous evacuation of Allied soldiers from Belgium, Britain, Canada, and France, who were cut off and surrounded by the German army from the beaches and harbor of Dunkirk, France, between May 26 and June 04, 1940, during Battle of France in World War II.\",\n" +
                "            \"release_date\": \"2017-07-19\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"vote_count\": 8160,\n" +
                "            \"id\": 135397,\n" +
                "            \"video\": false,\n" +
                "            \"vote_average\": 6.5,\n" +
                "            \"title\": \"Jurassic World\",\n" +
                "            \"popularity\": 35.393394,\n" +
                "            \"poster_path\": \"/jjBgi2r5cRt36xF6iNUEhzscEcb.jpg\",\n" +
                "            \"original_language\": \"en\",\n" +
                "            \"original_title\": \"Jurassic World\",\n" +
                "            \"genre_ids\": [\n" +
                "                28,\n" +
                "                12,\n" +
                "                878,\n" +
                "                53\n" +
                "            ],\n" +
                "            \"backdrop_path\": \"/dkMD5qlogeRMiEixC4YNPUvax2T.jpg\",\n" +
                "            \"adult\": false,\n" +
                "            \"overview\": \"Twenty-two years after the events of Jurassic Park, Isla Nublar now features a fully functioning dinosaur theme park, Jurassic World, as originally envisioned by John Hammond.\",\n" +
                "            \"release_date\": \"2015-06-09\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"vote_count\": 10061,\n" +
                "            \"id\": 157336,\n" +
                "            \"video\": false,\n" +
                "            \"vote_average\": 8.1,\n" +
                "            \"title\": \"Interstellar\",\n" +
                "            \"popularity\": 33.339208,\n" +
                "            \"poster_path\": \"/nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg\",\n" +
                "            \"original_language\": \"en\",\n" +
                "            \"original_title\": \"Interstellar\",\n" +
                "            \"genre_ids\": [\n" +
                "                12,\n" +
                "                18,\n" +
                "                878\n" +
                "            ],\n" +
                "            \"backdrop_path\": \"/xu9zaAevzQ5nnrsXN6JcahLnG4i.jpg\",\n" +
                "            \"adult\": false,\n" +
                "            \"overview\": \"Interstellar chronicles the adventures of a group of explorers who make use of a newly discovered wormhole to surpass the limitations on human space travel and conquer the vast distances involved in an interstellar voyage.\",\n" +
                "            \"release_date\": \"2014-11-05\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"vote_count\": 5430,\n" +
                "            \"id\": 263115,\n" +
                "            \"video\": false,\n" +
                "            \"vote_average\": 7.5,\n" +
                "            \"title\": \"Logan\",\n" +
                "            \"popularity\": 32.998108,\n" +
                "            \"poster_path\": \"/gGBu0hKw9BGddG8RkRAMX7B6NDB.jpg\",\n" +
                "            \"original_language\": \"en\",\n" +
                "            \"original_title\": \"Logan\",\n" +
                "            \"genre_ids\": [\n" +
                "                28,\n" +
                "                18,\n" +
                "                878\n" +
                "            ],\n" +
                "            \"backdrop_path\": \"/5pAGnkFYSsFJ99ZxDIYnhQbQFXs.jpg\",\n" +
                "            \"adult\": false,\n" +
                "            \"overview\": \"In the near future, a weary Logan cares for an ailing Professor X in a hideout on the Mexican border. But Logan's attempts to hide from the world and his legacy are upended when a young mutant arrives, pursued by dark forces.\",\n" +
                "            \"release_date\": \"2017-02-28\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"vote_count\": 8968,\n" +
                "            \"id\": 118340,\n" +
                "            \"video\": false,\n" +
                "            \"vote_average\": 7.9,\n" +
                "            \"title\": \"Guardians of the Galaxy\",\n" +
                "            \"popularity\": 31.709847,\n" +
                "            \"poster_path\": \"/y31QB9kn3XSudA15tV7UWQ9XLuW.jpg\",\n" +
                "            \"original_language\": \"en\",\n" +
                "            \"original_title\": \"Guardians of the Galaxy\",\n" +
                "            \"genre_ids\": [\n" +
                "                28,\n" +
                "                878,\n" +
                "                12\n" +
                "            ],\n" +
                "            \"backdrop_path\": \"/bHarw8xrmQeqf3t8HpuMY7zoK4x.jpg\",\n" +
                "            \"adult\": false,\n" +
                "            \"overview\": \"Light years from Earth, 26 years after being abducted, Peter Quill finds himself the prime target of a manhunt after discovering an orb wanted by Ronan the Accuser.\",\n" +
                "            \"release_date\": \"2014-07-30\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"vote_count\": 2006,\n" +
                "            \"id\": 126889,\n" +
                "            \"video\": false,\n" +
                "            \"vote_average\": 5.7,\n" +
                "            \"title\": \"Alien: Covenant\",\n" +
                "            \"popularity\": 30.213402,\n" +
                "            \"poster_path\": \"/zecMELPbU5YMQpC81Z8ImaaXuf9.jpg\",\n" +
                "            \"original_language\": \"en\",\n" +
                "            \"original_title\": \"Alien: Covenant\",\n" +
                "            \"genre_ids\": [\n" +
                "                27,\n" +
                "                878,\n" +
                "                53\n" +
                "            ],\n" +
                "            \"backdrop_path\": \"/kMU8trT43p5LFoJ4plIySMOsZ1T.jpg\",\n" +
                "            \"adult\": false,\n" +
                "            \"overview\": \"Bound for a remote planet on the far side of the galaxy, the crew of the colony ship 'Covenant' discovers what is thought to be an uncharted paradise, but is actually a dark, dangerous world – which has its sole inhabitant the 'synthetic', David, survivor of the doomed Prometheus expedition.\",\n" +
                "            \"release_date\": \"2017-05-09\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"vote_count\": 10119,\n" +
                "            \"id\": 293660,\n" +
                "            \"video\": false,\n" +
                "            \"vote_average\": 7.4,\n" +
                "            \"title\": \"Deadpool\",\n" +
                "            \"popularity\": 29.902786,\n" +
                "            \"poster_path\": \"/inVq3FRqcYIRl2la8iZikYYxFNR.jpg\",\n" +
                "            \"original_language\": \"en\",\n" +
                "            \"original_title\": \"Deadpool\",\n" +
                "            \"genre_ids\": [\n" +
                "                28,\n" +
                "                12,\n" +
                "                35\n" +
                "            ],\n" +
                "            \"backdrop_path\": \"/n1y094tVDFATSzkTnFxoGZ1qNsG.jpg\",\n" +
                "            \"adult\": false,\n" +
                "            \"overview\": \"Deadpool tells the origin story of former Special Forces operative turned mercenary Wade Wilson, who after being subjected to a rogue experiment that leaves him with accelerated healing powers, adopts the alter ego Deadpool. Armed with his new abilities and a dark, twisted sense of humor, Deadpool hunts down the man who nearly destroyed his life.\",\n" +
                "            \"release_date\": \"2016-02-09\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"vote_count\": 28,\n" +
                "            \"id\": 346364,\n" +
                "            \"video\": false,\n" +
                "            \"vote_average\": 5.9,\n" +
                "            \"title\": \"It\",\n" +
                "            \"popularity\": 29.814934,\n" +
                "            \"poster_path\": \"/tp9SBVBvouRipujcx0Q793R3ik2.jpg\",\n" +
                "            \"original_language\": \"en\",\n" +
                "            \"original_title\": \"It\",\n" +
                "            \"genre_ids\": [\n" +
                "                27\n" +
                "            ],\n" +
                "            \"backdrop_path\": \"/g8wnyyR6vlZjfdePD2v1lKGLUix.jpg\",\n" +
                "            \"adult\": false,\n" +
                "            \"overview\": \"In a small town in Maine, seven children known as The Losers Club come face to face with life problems, bullies and a monster that takes the shape of a clown called Pennywise.\",\n" +
                "            \"release_date\": \"2017-08-17\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"vote_count\": 8884,\n" +
                "            \"id\": 76341,\n" +
                "            \"video\": false,\n" +
                "            \"vote_average\": 7.2,\n" +
                "            \"title\": \"Mad Max: Fury Road\",\n" +
                "            \"popularity\": 29.751139,\n" +
                "            \"poster_path\": \"/kqjL17yufvn9OVLyXYpvtyrFfak.jpg\",\n" +
                "            \"original_language\": \"en\",\n" +
                "            \"original_title\": \"Mad Max: Fury Road\",\n" +
                "            \"genre_ids\": [\n" +
                "                28,\n" +
                "                12,\n" +
                "                878,\n" +
                "                53\n" +
                "            ],\n" +
                "            \"backdrop_path\": \"/phszHPFVhPHhMZgo0fWTKBDQsJA.jpg\",\n" +
                "            \"adult\": false,\n" +
                "            \"overview\": \"An apocalyptic story set in the furthest reaches of our planet, in a stark desert landscape where humanity is broken, and most everyone is crazed fighting for the necessities of life. Within this world exist two rebels on the run who just might be able to restore order. There's Max, a man of action and a man of few words, who seeks peace of mind following the loss of his wife and child in the aftermath of the chaos. And Furiosa, a woman of action and a woman who believes her path to survival may be achieved if she can make it across the desert back to her childhood homeland.\",\n" +
                "            \"release_date\": \"2015-05-13\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"vote_count\": 4070,\n" +
                "            \"id\": 61791,\n" +
                "            \"video\": false,\n" +
                "            \"vote_average\": 7,\n" +
                "            \"title\": \"Rise of the Planet of the Apes\",\n" +
                "            \"popularity\": 29.174077,\n" +
                "            \"poster_path\": \"/esqXMJv6PiK7GJVRwd2FA3SZUoW.jpg\",\n" +
                "            \"original_language\": \"en\",\n" +
                "            \"original_title\": \"Rise of the Planet of the Apes\",\n" +
                "            \"genre_ids\": [\n" +
                "                53,\n" +
                "                28,\n" +
                "                18,\n" +
                "                878\n" +
                "            ],\n" +
                "            \"backdrop_path\": \"/caIpcr9xhvRcWfOMRDq3F5iIcLB.jpg\",\n" +
                "            \"adult\": false,\n" +
                "            \"overview\": \"Scientist Will Rodman is determined to find a cure for Alzheimer's, the disease which has slowly consumed his father. Will feels certain he is close to a breakthrough and tests his latest serum on apes, noticing dramatic increases in intelligence and brain activity in the primate subjects – especially Caesar, his pet chimpanzee.\",\n" +
                "            \"release_date\": \"2011-08-03\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"vote_count\": 2047,\n" +
                "            \"id\": 315837,\n" +
                "            \"video\": false,\n" +
                "            \"vote_average\": 5.9,\n" +
                "            \"title\": \"Ghost in the Shell\",\n" +
                "            \"popularity\": 28.821079,\n" +
                "            \"poster_path\": \"/myRzRzCxdfUWjkJWgpHHZ1oGkJd.jpg\",\n" +
                "            \"original_language\": \"en\",\n" +
                "            \"original_title\": \"Ghost in the Shell\",\n" +
                "            \"genre_ids\": [\n" +
                "                28,\n" +
                "                80,\n" +
                "                18,\n" +
                "                9648,\n" +
                "                878,\n" +
                "                53\n" +
                "            ],\n" +
                "            \"backdrop_path\": \"/dDVqfmCzSy3TKSmiS2pJ9QB5E3P.jpg\",\n" +
                "            \"adult\": false,\n" +
                "            \"overview\": \"In the near future, Major is the first of her kind: a human saved from a terrible crash, then cyber-enhanced to be a perfect soldier devoted to stopping the world's most dangerous criminals.\",\n" +
                "            \"release_date\": \"2017-03-29\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"vote_count\": 221,\n" +
                "            \"id\": 353491,\n" +
                "            \"video\": false,\n" +
                "            \"vote_average\": 5.8,\n" +
                "            \"title\": \"The Dark Tower\",\n" +
                "            \"popularity\": 28.38409,\n" +
                "            \"poster_path\": \"/i9GUSgddIqrroubiLsvvMRYyRy0.jpg\",\n" +
                "            \"original_language\": \"en\",\n" +
                "            \"original_title\": \"The Dark Tower\",\n" +
                "            \"genre_ids\": [\n" +
                "                28,\n" +
                "                37,\n" +
                "                878,\n" +
                "                14,\n" +
                "                27\n" +
                "            ],\n" +
                "            \"backdrop_path\": \"/2n7Zn6WxJ76ccOwnuQHuhSFMbqt.jpg\",\n" +
                "            \"adult\": false,\n" +
                "            \"overview\": \"The last Gunslinger, Roland Deschain, has been locked in an eternal battle with Walter O’Dim, also known as the Man in Black, determined to prevent him from toppling the Dark Tower, which holds the universe together. With the fate of the worlds at stake, good and evil will collide in the ultimate battle as only Roland can defend the Tower from the Man in Black.\",\n" +
                "            \"release_date\": \"2017-08-03\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"vote_count\": 4151,\n" +
                "            \"id\": 119450,\n" +
                "            \"video\": false,\n" +
                "            \"vote_average\": 7.3,\n" +
                "            \"title\": \"Dawn of the Planet of the Apes\",\n" +
                "            \"popularity\": 28.186698,\n" +
                "            \"poster_path\": \"/2EUAUIu5lHFlkj5FRryohlH6CRO.jpg\",\n" +
                "            \"original_language\": \"en\",\n" +
                "            \"original_title\": \"Dawn of the Planet of the Apes\",\n" +
                "            \"genre_ids\": [\n" +
                "                878,\n" +
                "                28,\n" +
                "                18,\n" +
                "                53\n" +
                "            ],\n" +
                "            \"backdrop_path\": \"/rjUl3pd1LHVOVfG4IGcyA1cId5l.jpg\",\n" +
                "            \"adult\": false,\n" +
                "            \"overview\": \"A group of scientists in San Francisco struggle to stay alive in the aftermath of a plague that is wiping out humanity, while Caesar tries to maintain dominance over his community of intelligent apes.\",\n" +
                "            \"release_date\": \"2014-06-26\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        MoviesResponseBody moviesResponseBody = new Gson().fromJson(moviesJson, MoviesResponseBody.class);
        return new LinkedList<Movie>(moviesResponseBody.getMovies());
    }
}
