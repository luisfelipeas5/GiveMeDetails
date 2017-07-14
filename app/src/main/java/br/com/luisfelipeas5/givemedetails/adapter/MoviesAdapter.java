package br.com.luisfelipeas5.givemedetails.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.signature.StringSignature;

import java.util.List;

import javax.inject.Inject;

import br.com.luisfelipeas5.givemedetails.R;
import br.com.luisfelipeas5.givemedetails.databinding.LayoutMoviesAdapterItemBinding;
import br.com.luisfelipeas5.givemedetails.model.model.Movie;
import br.com.luisfelipeas5.givemedetails.presenter.details.MoviePosterMvpPresenter;
import br.com.luisfelipeas5.givemedetails.view.details.MoviePosterMvpView;
import br.com.luisfelipeas5.givemedetails.view.di.AppComponent;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {
    private final List<Movie> mMovies;

    private final AppComponent mAppComponent;
    private Listener mListener;

    public MoviesAdapter(List<Movie> movies, AppComponent appComponent) {
        mMovies = movies;
        mAppComponent = appComponent;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        LayoutMoviesAdapterItemBinding binding =
                LayoutMoviesAdapterItemBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Movie movie = mMovies.get(position);
        holder.setMovieId(movie.getId());
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    public void setListener(Listener listener) {
        mListener = listener;
    }

    public interface Listener {
        void onMovieClicked(Movie movie);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements MoviePosterMvpView {
        LayoutMoviesAdapterItemBinding binding;
        private MoviePosterMvpPresenter mPresenter;
        private String mMovieId;
        private int mImgMovieWidth;

        ViewHolder(LayoutMoviesAdapterItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            mAppComponent.inject(this);

            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (mListener != null && position != RecyclerView.NO_POSITION) {
                        Movie movie = mMovies.get(position);
                        mListener.onMovieClicked(movie);
                    }
                }
            });
            mPresenter.attach(this);
        }

        @Override
        public void onMoviePosterUrlReady(final String posterUrl) {
            Context context = binding.getRoot().getContext();
            final String movieId = mMovieId;
            final int positionCurrent = getAdapterPosition();
            if (positionCurrent != RecyclerView.NO_POSITION) {
                Glide.with(context)
                        .load(posterUrl)
                        .asBitmap()
                        .centerCrop()
                        .signature(new StringSignature(movieId))
                        .placeholder(R.color.transparent)
                        .listener(new RequestListener<String, Bitmap>() {
                            @Override
                            public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Bitmap resource, String model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                return !movieId.equals(mMovieId);
                            }
                        })
                        .into(binding.imgMovie);
            }
        }

        @Override
        public void onGetMoviePosterUrlFailed() {

        }

        @Inject
        public void setPresenter(MoviePosterMvpPresenter presenter) {
            mPresenter = presenter;
        }

        @Override
        public void getPosterWidth() {
            mImgMovieWidth = binding.imgMovie.getWidth();
            if (mImgMovieWidth <= 0) {
                binding.imgMovie.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        binding.imgMovie.getViewTreeObserver().removeOnPreDrawListener(this);
                        getPosterWidth();
                        return true;
                    }
                });
            } else {
                mPresenter.getMoviePosterUrl(mMovieId, mImgMovieWidth);
            }
        }

        @Override
        public void onGetMovieTitleReady(String movieTitle) {
            Context context = binding.getRoot().getContext();
            binding.imgMovie.setContentDescription(context.getString(R.string.movie_poster_description, movieTitle));
        }

        @Override
        public void onGetMovieTitleFailed() {

        }

        void setMovieId(String movieId) {
            clearPreviewData();

            mMovieId = movieId;
            mPresenter.getMovieTitle(movieId);
            getPosterWidth();
        }

        private void clearPreviewData() {
            Context context = binding.getRoot().getContext();
            Glide.clear(binding.imgMovie);
            binding.imgMovie.setContentDescription(context.getString(R.string.movie_poster));
        }
    }
}
