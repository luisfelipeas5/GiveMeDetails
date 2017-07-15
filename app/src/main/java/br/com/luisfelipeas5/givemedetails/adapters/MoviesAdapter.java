package br.com.luisfelipeas5.givemedetails.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.List;

import br.com.luisfelipeas5.givemedetails.R;
import br.com.luisfelipeas5.givemedetails.databinding.LayoutMoviesAdapterItemBinding;
import br.com.luisfelipeas5.givemedetails.model.model.Movie;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {
    private final List<Movie> mMovies;

    private Listener mListener;
    private int mPosterWidth;

    public MoviesAdapter(List<Movie> movies, int posterWidth) {
        mMovies = movies;
        mPosterWidth = posterWidth;
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
        Context context = holder.binding.getRoot().getContext();
        Glide.with(context)
                .load(movie.getPoster(mPosterWidth))
                .asBitmap()
                .centerCrop()
                .placeholder(R.color.transparent)
                .into(holder.binding.imgMovie);
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

    public class ViewHolder extends RecyclerView.ViewHolder {
        LayoutMoviesAdapterItemBinding binding;

        ViewHolder(LayoutMoviesAdapterItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

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
        }
    }
}
