package br.com.luisfelipeas5.givemedetails.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import br.com.luisfelipeas5.givemedetails.view.fragments.lists.MoviesFragment;

public class MoviesListsPagerAdapter extends FragmentPagerAdapter {
    private final MoviesFragment[] mMoviesFragments;

    public MoviesListsPagerAdapter(FragmentManager fm, MoviesFragment[] moviesFragments) {
        super(fm);
        mMoviesFragments = moviesFragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mMoviesFragments[position];
    }

    @Override
    public int getCount() {
        return mMoviesFragments.length;
    }
}
