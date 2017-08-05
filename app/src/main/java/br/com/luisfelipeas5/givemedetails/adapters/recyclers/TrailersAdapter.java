package br.com.luisfelipeas5.givemedetails.adapters.recyclers;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import br.com.luisfelipeas5.givemedetails.R;
import br.com.luisfelipeas5.givemedetails.databinding.TrailerAdapterItemBinding;
import br.com.luisfelipeas5.givemedetails.model.model.trailer.Trailer;

public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.ViewHolder> {
    private final List<Trailer> mTrailers;

    public TrailersAdapter(List<Trailer> mTrailers) {
        this.mTrailers = mTrailers;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        TrailerAdapterItemBinding binding = TrailerAdapterItemBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Trailer trailer = mTrailers.get(position);
        holder.binding.setTrailer(trailer);

        Context context = holder.binding.getRoot().getContext();
        String trailerThumbUrl = trailer.getThumbUrl();
        Glide.with(context)
                .load(trailerThumbUrl)
                .apply(RequestOptions.placeholderOf(R.color.black))
                .apply(RequestOptions.circleCropTransform())
                .apply(RequestOptions.centerCropTransform())
                .into(holder.binding.imgTrailerThumb);
    }

    @Override
    public int getItemCount() {
        return mTrailers.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TrailerAdapterItemBinding binding;
        ViewHolder(TrailerAdapterItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
