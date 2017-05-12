package br.com.luisfelipeas5.wherewatch.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.luisfelipeas5.wherewatch.databinding.FragmentSocialBinding;
import br.com.luisfelipeas5.wherewatch.model.Movie;

public class SocialFragment extends Fragment {

    private FragmentSocialBinding mBinding;
    private Movie mMovie;

    public SocialFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mBinding = FragmentSocialBinding.inflate(inflater, container, false);
        inflateBinding();
        return mBinding.getRoot();
    }

    private void inflateBinding() {
        if (mMovie != null && mBinding != null) {
            mBinding.setMovie(mMovie);
            mBinding.layoutContent.setVisibility(View.VISIBLE);
        } else if (mBinding != null){
            mBinding.layoutContent.setVisibility(View.GONE);
        }
    }

    public void setMovie(Movie movie) {
        mMovie = movie;
        inflateBinding();
    }
}
