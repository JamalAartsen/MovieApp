package com.example.movieapp.API;

import com.example.movieapp.Model.GetAllMovies;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MoviesApiService {

    @GET("3/discover/movie?api_key=1bd5fe600b70f65ca9ab3eed2cd89476&language=en-US&sort_by=popularity.desc&include_adult=false&include_video=false&page=1")
    Call<GetAllMovies> getPopularMovies(@Query("year") int year);
}
