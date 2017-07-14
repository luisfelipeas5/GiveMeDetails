package br.com.luisfelipeas5.givemedetails.view.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import br.com.luisfelipeas5.givemedetails.R;
import br.com.luisfelipeas5.givemedetails.databinding.ActivityDetailBinding;
import br.com.luisfelipeas5.givemedetails.model.model.Movie;
import br.com.luisfelipeas5.givemedetails.view.fragments.details.DetailFragment;
import br.com.luisfelipeas5.givemedetails.view.fragments.details.DetailPosterFragment;
import br.com.luisfelipeas5.givemedetails.view.fragments.details.SocialFragment;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String EXTRA_MOVIE_ID =
            "br.com.luisfelipeas5.givemedetails.view.activities.DetailActivity.EXTRA_MOVIE_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDetailBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        binding.imgBackArrow.setOnClickListener(this);

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_MOVIE_ID)) {
            String movieId = intent.getStringExtra(EXTRA_MOVIE_ID);

            DetailPosterFragment detailPosterFragment = getDetailPosterFragment();
            detailPosterFragment.setMovieId(movieId);
        }
    }

    private DetailPosterFragment getDetailPosterFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        return (DetailPosterFragment) fragmentManager.findFragmentById(R.id.fragment_poster);
    }

    private void onMovieReady(Movie movie) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        DetailFragment detailFragment =
                (DetailFragment) fragmentManager.findFragmentById(R.id.fragment_detail);
        detailFragment.setMovie(movie);

        SocialFragment socialFragment =
                (SocialFragment) fragmentManager.findFragmentById(R.id.fragment_social);
        socialFragment.setMovie(movie);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.img_back_arrow:
                onBackPressed();
                break;
        }
    }
}
