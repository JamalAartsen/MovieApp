package com.example.movieapp.API;

import com.example.movieapp.Model.GetAllMovies;

import retrofit2.Call;

public class MoviesRepository {

    private MoviesApiService allMoviesApiService = MoviesApi.create();

    Call<GetAllMovies> getPopularMovies2018(int year) {
        return allMoviesApiService.getPopularMovies(year);
    }
}
