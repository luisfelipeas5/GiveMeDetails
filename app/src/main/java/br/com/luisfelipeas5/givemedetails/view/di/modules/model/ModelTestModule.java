package br.com.luisfelipeas5.givemedetails.view.di.modules.model;

import com.google.gson.Gson;

import java.util.List;

import br.com.luisfelipeas5.givemedetails.model.datamangers.MovieDataManager;
import br.com.luisfelipeas5.givemedetails.model.datamangers.MovieMvpDataManager;
import br.com.luisfelipeas5.givemedetails.model.helpers.MovieApiMvpHelper;
import br.com.luisfelipeas5.givemedetails.model.helpers.MovieCacheMvpHelper;
import br.com.luisfelipeas5.givemedetails.model.model.movie.Movie;
import br.com.luisfelipeas5.givemedetails.model.model.movie.MovieTMDb;
import br.com.luisfelipeas5.givemedetails.model.model.trailer.Trailer;
import dagger.Module;
import dagger.Provides;
import io.reactivex.Observable;
import io.reactivex.Single;

@Module
public class ModelTestModule {

    public ModelTestModule() {
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
                return Observable.just(getMovieMocked());
            }

            @Override
            public Observable<Movie> getMovieSummary(String movieId) {
                return Observable.just(getMovieMocked());
            }

            @Override
            public Observable<Movie> getMovieSocial(String movieId) {
                return Observable.just(getMovieMocked());
            }

            @Override
            public Observable<List<Trailer>> getTrailers(String movieId) {
                return null;
            }
        };
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
    MovieMvpDataManager provideMovieMvpDataManager(MovieApiMvpHelper movieApiMvpHelper,
                                                   MovieCacheMvpHelper movieCacheMvpHelper) {
        return new MovieDataManager(movieApiMvpHelper, movieCacheMvpHelper);
    }

}
