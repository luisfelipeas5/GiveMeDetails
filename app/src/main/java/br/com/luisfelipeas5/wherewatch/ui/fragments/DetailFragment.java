package br.com.luisfelipeas5.wherewatch.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.luisfelipeas5.wherewatch.databinding.FragmentDetailBinding;
import br.com.luisfelipeas5.wherewatch.model.Movie;

public class DetailFragment extends Fragment {

    private Movie mMovie;
    private FragmentDetailBinding mBinding;

    public DetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentDetailBinding.inflate(inflater, container, false);
        inflateBinding();
        return mBinding.getRoot();
    }

    private void inflateBinding() {
        if (mMovie != null && mBinding != null) {
            mBinding.setMovie(mMovie);
            mBinding.cardLoading.setVisibility(View.GONE);
            mBinding.layoutContent.setVisibility(View.VISIBLE);
        } else if (mBinding != null){
            mBinding.cardLoading.setVisibility(View.VISIBLE);
            mBinding.layoutContent.setVisibility(View.GONE);
        }
    }

    public void setMovie(Movie movie) {
        mMovie = movie;
        inflateBinding();
    }
}
