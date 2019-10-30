package com.example.themoviedb.interfaces;

import com.example.themoviedb.models.PopularMoviesBaseResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Movies {
    @GET("3/movie/now_playing")
    Call<PopularMoviesBaseResponse> getNowPlayingMovies(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") int pageNum);
}
