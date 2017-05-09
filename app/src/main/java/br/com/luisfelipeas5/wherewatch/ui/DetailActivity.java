package br.com.luisfelipeas5.wherewatch.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bumptech.glide.Glide;

import br.com.luisfelipeas5.wherewatch.R;
import br.com.luisfelipeas5.wherewatch.databinding.ActivityDetailBinding;
import br.com.luisfelipeas5.wherewatch.model.Movie;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE =
            "br.com.luisfelipeas5.wherewatch.ui.DetailActivity.EXTRA_MOVIE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDetailBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_MOVIE)) {
            Movie movie = intent.getParcelableExtra(EXTRA_MOVIE);
            binding.setMovie(movie);
        }
    }
}
