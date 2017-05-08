package br.com.luisfelipeas5.wherewatch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;

import java.util.List;

import br.com.luisfelipeas5.wherewatch.adapter.MoviesAdapter;
import br.com.luisfelipeas5.wherewatch.api.WhereWatchApi;
import br.com.luisfelipeas5.wherewatch.model.Movie;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);
        ImageButton btnSearch = (ImageButton) findViewById(R.id.btn_search);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        btnSearch.setOnClickListener(this);
    }

    public void onSearch() {
        WhereWatchApi.getMovies(this, new WhereWatchApi.Callback<List<Movie>>() {

            @Override
            public void onResult(List<Movie> movies) {
                if (movies != null) {
                    setAdapter(movies);
                }
            }

        });
    }

    private void setAdapter(List<Movie> movies) {
        MoviesAdapter adapter = new MoviesAdapter(movies);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_search:
                onSearch();
                break;
        }
    }
}
