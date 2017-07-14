package br.com.luisfelipeas5.givemedetails.view.fragments.details;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.luisfelipeas5.givemedetails.databinding.FragmentDetailBinding;
import br.com.luisfelipeas5.givemedetails.model.model.Movie;

public class SummaryFragment extends Fragment {

    private Movie mMovie;
    private FragmentDetailBinding mBinding;

    public SummaryFragment() {
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
        }
    }

    public void setMovie(Movie movie) {
        mMovie = movie;
        inflateBinding();
    }
}
