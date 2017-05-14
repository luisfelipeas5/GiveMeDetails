package br.com.luisfelipeas5.givemedetails.ui.fragments.details;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.luisfelipeas5.givemedetails.databinding.FragmentSocialBinding;
import br.com.luisfelipeas5.givemedetails.model.Movie;

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
        }
    }

    public void setMovie(Movie movie) {
        mMovie = movie;
        inflateBinding();
    }
}
