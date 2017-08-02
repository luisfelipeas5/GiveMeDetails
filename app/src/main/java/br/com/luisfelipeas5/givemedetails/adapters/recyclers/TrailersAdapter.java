package br.com.luisfelipeas5.givemedetails.adapters.recyclers;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import br.com.luisfelipeas5.givemedetails.model.model.trailer.Trailer;

public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.ViewHolder> {
    private final List<Trailer> mTrailers;

    public TrailersAdapter(List<Trailer> mTrailers) {
        this.mTrailers = mTrailers;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mTrailers.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
