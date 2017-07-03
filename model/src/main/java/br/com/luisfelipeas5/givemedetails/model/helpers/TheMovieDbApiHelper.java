package br.com.luisfelipeas5.givemedetails.model.helpers;

import android.content.Context;
import android.net.Uri;

import br.com.luisfelipeas5.givemedetails.model.R;
import br.com.luisfelipeas5.givemedetails.model.model.Movie;
import br.com.luisfelipeas5.givemedetails.model.model.MoviesResponseBody;
import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class TheMovieDbApiHelper implements MovieApiMvpHelper {

    private static final String AUTHORITY = "api.themoviedb.org";

    private static final String GET_MOVIES_POPULAR_PATH = "3/movie/popular";
    private static final String GET_MOVIES_TOP_RATED_PATH = "3/movie/top_rated";

    private static final String MOVIE_ID_PATH = "{movieId}";
    private static final String GET_MOVIE_PATH = "3/movie" + MOVIE_ID_PATH;

    private static final String KEY_QUERY_API_KEY = "api_key";

    private final TheMovieDbApi mTheMovieDbApi;
    private String mApiKey;

    public TheMovieDbApiHelper(Context context) {
        Uri.Builder builder = new Uri.Builder()
                .scheme("https")
                .authority(AUTHORITY);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(builder.toString())
                .build();
        mTheMovieDbApi = retrofit.create(TheMovieDbApi.class);

        if (context != null) {
            mApiKey = context.getString(R.string.the_movie_db_api_key);
        }
    }

    @Override
    public Observable<MoviesResponseBody> getPopular() {
        return mTheMovieDbApi.getPopular(mApiKey);
    }

    @Override
    public Observable<MoviesResponseBody> getTopRated() {
        return mTheMovieDbApi.getTopRated(mApiKey);
    }

    @Override
    public Observable<Movie> getMovie(String movieId) {
        return mTheMovieDbApi.getMovie(movieId, mApiKey);
    }

    interface TheMovieDbApi {

        @GET(GET_MOVIES_POPULAR_PATH)
        Observable<MoviesResponseBody> getPopular(@Query(KEY_QUERY_API_KEY) String apiKey);

        @GET(GET_MOVIES_TOP_RATED_PATH)
        Observable<MoviesResponseBody> getTopRated(@Query(KEY_QUERY_API_KEY) String apiKey);

        @GET(GET_MOVIE_PATH)
        Observable<Movie> getMovie(@Path(MOVIE_ID_PATH) String movieId,
                                   @Query(KEY_QUERY_API_KEY) String apiKey);
    }

}
