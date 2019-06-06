package com.example.movieapp.API;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.example.movieapp.Model.GetAllMovies;
import com.example.movieapp.Model.Movie;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewModel extends AndroidViewModel {

    private MoviesRepository moviesRepository = new MoviesRepository();

    private MutableLiveData<List<Movie>> movie = new MutableLiveData<>();
    private MutableLiveData<String> error = new MutableLiveData<>();

    public ViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<String> getError() {
        return error;
    }

    public MutableLiveData<List<Movie>> getAllMovies() {
        return movie;
    }

    public void getPopularMovies(int year) {
        moviesRepository.getPopularMovies2018(year)
                .enqueue(new Callback<GetAllMovies>() {
                    @Override
                    public void onResponse(Call<GetAllMovies> call, Response<GetAllMovies> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            movie.setValue(response.body().getResults());
                        } else {
                            error.setValue("Api Error: " + response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<GetAllMovies> call, Throwable t) {
                        error.setValue("Api Error: " + t.getMessage());
                    }
                });
    }
}
