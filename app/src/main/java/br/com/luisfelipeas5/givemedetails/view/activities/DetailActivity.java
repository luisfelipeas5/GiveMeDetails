package br.com.luisfelipeas5.givemedetails.view.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import br.com.luisfelipeas5.givemedetails.R;
import br.com.luisfelipeas5.givemedetails.databinding.ActivityDetailBinding;
import br.com.luisfelipeas5.givemedetails.view.fragments.details.SummaryFragment;
import br.com.luisfelipeas5.givemedetails.view.fragments.details.PosterFragment;
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

            PosterFragment posterFragment = getDetailPosterFragment();
            posterFragment.setMovieId(movieId);

            SummaryFragment summaryFragment = getSummaryFragment();
            SocialFragment socialFragment = getSocialFragment();
        }
    }

    private SocialFragment getSocialFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        return (SocialFragment) fragmentManager.findFragmentById(R.id.fragment_social);
    }

    private SummaryFragment getSummaryFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        return (SummaryFragment) fragmentManager.findFragmentById(R.id.fragment_detail);
    }

    private PosterFragment getDetailPosterFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        return (PosterFragment) fragmentManager.findFragmentById(R.id.fragment_poster);
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
