package br.com.luisfelipeas5.wherewatch.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import br.com.luisfelipeas5.wherewatch.R;
import br.com.luisfelipeas5.wherewatch.api.WhereWatchApi;
import br.com.luisfelipeas5.wherewatch.model.Movie;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {
    private final List<Movie> mMovies;

    public MoviesAdapter(List<Movie> movies) {
        mMovies = movies;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.layout_movies_adapter_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Movie movie = mMovies.get(position);
        holder.setMovie(movie);
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView mImgMovie;

        ViewHolder(View itemView) {
            super(itemView);
            mImgMovie = (ImageView) itemView.findViewById(R.id.img_movie);
        }

        void setMovie(Movie movie) {
            Context context = mImgMovie.getContext();

            String posterUrl = WhereWatchApi.IMG_BASE_URL + movie.getPoster();
            Glide.with(context)
                    .load(posterUrl)
                    .dontAnimate()
                    .into(mImgMovie);
            mImgMovie.setContentDescription(movie.getTitle());
        }
    }
}
