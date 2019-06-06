package com.example.movieapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.movieapp.Model.Movie;

public class MovieDetailActivity extends AppCompatActivity {

    ImageView backDropImage;
    ImageView posterImage;
    TextView title;
    TextView rating;
    TextView overview;
    TextView releaseDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        backDropImage = findViewById(R.id.backDropImage);
        posterImage = findViewById(R.id.posterImage);
        title = findViewById(R.id.titleMovie);
        rating = findViewById(R.id.rating);
        overview = findViewById(R.id.overview);
        releaseDate = findViewById(R.id.releaseDate);

        Movie movie = getIntent().getParcelableExtra(MainActivity.MOVIE_DETAIL_PAGE);

        new DownloadImageTask(backDropImage).execute(movie.getBackdropPath());
        new DownloadImageTask(posterImage).execute(movie.getPosterPath());

        title.setText(movie.getTitle());
        rating.setText(movie.getVoteAverage().toString());
        overview.setText(movie.getOverview());
        releaseDate.setText("Release date: " + movie.getReleaseDate());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) ;
        finish();
        return super.onOptionsItemSelected(item);
    }
}
