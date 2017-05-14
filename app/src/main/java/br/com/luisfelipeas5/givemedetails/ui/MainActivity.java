package br.com.luisfelipeas5.givemedetails.ui;

import android.databinding.DataBindingUtil;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import br.com.luisfelipeas5.givemedetails.R;
import br.com.luisfelipeas5.givemedetails.adapter.MoviesListsPagerAdapter;
import br.com.luisfelipeas5.givemedetails.databinding.ActivityMainBinding;
import br.com.luisfelipeas5.givemedetails.pagetransformers.ZoomOutPageTransformer;
import br.com.luisfelipeas5.givemedetails.ui.fragments.movielists.PopularMoviesFragment;
import br.com.luisfelipeas5.givemedetails.ui.fragments.movielists.MoviesFragment;
import br.com.luisfelipeas5.givemedetails.ui.fragments.movielists.TopRatedMoviesFragment;

public class MainActivity extends AppCompatActivity {

    private MoviesListsPagerAdapter mMoviesListsAdapter;
    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        MoviesFragment[] moviesFragments = {new PopularMoviesFragment(), new TopRatedMoviesFragment()};
        mBinding.txtTitle.setText(moviesFragments[0].getTitleResource());

        mMoviesListsAdapter = new MoviesListsPagerAdapter(getSupportFragmentManager(),
                moviesFragments);
        mBinding.viewPager.setAdapter(mMoviesListsAdapter);
        mBinding.viewPager.setPageTransformer(true, new ZoomOutPageTransformer());

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
