package br.com.luisfelipeas5.givemedetails.view.activities;

import android.databinding.DataBindingUtil;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import br.com.luisfelipeas5.givemedetails.R;
import br.com.luisfelipeas5.givemedetails.adapters.MoviesListsPagerAdapter;
import br.com.luisfelipeas5.givemedetails.databinding.ActivityMainBinding;
import br.com.luisfelipeas5.givemedetails.pagetransformers.ZoomOutPageTransformer;
import br.com.luisfelipeas5.givemedetails.view.fragments.lists.LovedMoviesFragment;
import br.com.luisfelipeas5.givemedetails.view.fragments.lists.PopularMoviesFragment;
import br.com.luisfelipeas5.givemedetails.view.fragments.lists.MoviesFragment;
import br.com.luisfelipeas5.givemedetails.view.fragments.lists.TopRatedMoviesFragment;

public class HomeActivity extends AppCompatActivity {

    private MoviesListsPagerAdapter mMoviesListsAdapter;
    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        MoviesFragment[] moviesFragments = {new PopularMoviesFragment(), new TopRatedMoviesFragment(),
                new LovedMoviesFragment()};
        mBinding.txtTitle.setText(moviesFragments[0].getTitleResource());

        mMoviesListsAdapter = new MoviesListsPagerAdapter(getSupportFragmentManager(),
                moviesFragments);
        mBinding.viewPager.setAdapter(mMoviesListsAdapter);
        mBinding.viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        mBinding.viewPager.setOffscreenPageLimit(2);

        mBinding.pageIndicator.setViewPager(mBinding.viewPager);
        mBinding.pageIndicator.setOnPageChangeListener(mOnAdapterChangeListener);
    }

    private ViewPager.OnPageChangeListener mOnAdapterChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            MoviesFragment moviesListFragment = (MoviesFragment) mMoviesListsAdapter.getItem(position);
            mBinding.txtTitle.setText(moviesListFragment.getTitleResource());
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}
