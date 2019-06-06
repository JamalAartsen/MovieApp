package com.example.movieapp;

import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.movieapp.API.ViewModel;
import com.example.movieapp.Model.Movie;

import java.util.List;

public class MainActivity extends AppCompatActivity implements RecyclerView.OnItemTouchListener {

    private MoviesAdapter moviesAdapter;
    private RecyclerView recyclerView;
    private EditText mYear;
    private List<Movie> moviesList;
    private ViewModel viewModel;
    ProgressDialog progressDialog;
    public static final String MOVIE_DETAIL_PAGE = "detail";
    public static final int REQUESTCODE = 1;
    private GestureDetector mGestureDetector;
    Button getAllMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle(getResources().getText(R.string.movielist));

        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Loading.....");

        getAllMovies = findViewById(R.id.submitButton);
        recyclerView = findViewById(R.id.recyclerView);
        mYear = findViewById(R.id.editTextJaar);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        getAllMovies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                int year = Integer.parseInt(mYear.getText().toString());
                viewModel.getPopularMovies(year);
            }
        });

        viewModel = ViewModelProviders.of(this).get(ViewModel.class);

        viewModel.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG)
                        .show();
            }
        });

        viewModel.getAllMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                progressDialog.dismiss();
                moviesList = movies;
                updateUI();
            }
        });

        mGestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });

        recyclerView.addOnItemTouchListener(this);
    }

    private void updateUI() {
        if (moviesAdapter == null) {
            moviesAdapter = new MoviesAdapter(moviesList);
            recyclerView.setAdapter(moviesAdapter);
        } else {
            moviesAdapter.swapList(moviesList);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
        View child = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
        int mAdapterPosition = recyclerView.getChildAdapterPosition(child);
        if (child != null && mGestureDetector.onTouchEvent(motionEvent)) {
            System.out.println("Jamal Error: " + moviesList.size());
            Intent intent = new Intent(MainActivity.this, MovieDetailActivity.class);
            intent.putExtra(MainActivity.MOVIE_DETAIL_PAGE, moviesList.get(mAdapterPosition));
            startActivityForResult(intent, REQUESTCODE);
        }
        return false;
    }

    @Override
    public void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean b) {

    }
}
