package br.com.luisfelipeas5.givemedetails.adapters.recyclers;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import br.com.luisfelipeas5.givemedetails.databinding.ReviewsAdapterItemBinding;
import br.com.luisfelipeas5.givemedetails.model.model.reviews.Review;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ViewHolder> {
    private final List<Review> mReviews;
    private final boolean mShowAllLines;

    public ReviewsAdapter(List<Review> reviews, boolean showAllLines) {
        mReviews = reviews;
        mShowAllLines = showAllLines;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ReviewsAdapterItemBinding binding = ReviewsAdapterItemBinding.inflate(inflater, parent, false);
        int maxLines = 3;
        if (mShowAllLines) {
            maxLines = Integer.MAX_VALUE;
        }
        binding.txtContent.setMaxLines(maxLines);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Review review = mReviews.get(position);
        holder.binding.setReview(review);
    }

    @Override
    public int getItemCount() {
        return mReviews.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final ReviewsAdapterItemBinding binding;

        ViewHolder(ReviewsAdapterItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
