package br.com.luisfelipeas5.givemedetails.view.di.modules.model;

import com.google.gson.Gson;

import java.util.LinkedList;
import java.util.List;

import br.com.luisfelipeas5.givemedetails.model.datamangers.MovieDataManager;
import br.com.luisfelipeas5.givemedetails.model.datamangers.MovieMvpDataManager;
import br.com.luisfelipeas5.givemedetails.model.helpers.MovieApiMvpHelper;
import br.com.luisfelipeas5.givemedetails.model.helpers.MovieCacheMvpHelper;
import br.com.luisfelipeas5.givemedetails.model.model.movie.Movie;
import br.com.luisfelipeas5.givemedetails.model.model.movie.MovieTMDb;
import br.com.luisfelipeas5.givemedetails.model.model.responsebodies.TrailersResponseBody;
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
                return Observable.just(getTrailersMocked());
            }
        };
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
