package com.example.themoviedb.repo;

import android.app.Application;
import android.util.Log;

import com.example.themoviedb.commonItems.Constants;
import com.example.themoviedb.commonItems.RetrofitClient;
import com.example.themoviedb.models.PopularMoviesBaseResponse;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;

public class MovieNetworkRepo {
    private static final String TAG = "UsersRepo";
    private static MovieNetworkRepo repoInstance;
    private final Application application;
    private final MovieLocalRepo movieLocalRepo;

    private MovieNetworkRepo(Application application) {
        this.application = application;
        movieLocalRepo = MovieLocalRepo.getInstance(application);
    }

    public static MovieNetworkRepo getInstance(Application application) {
        if (repoInstance == null) {
            repoInstance = new MovieNetworkRepo(application);
        }
        return repoInstance;
    }

    public void getNowPlayingMovies(String locale, int page) {
        Call<PopularMoviesBaseResponse> call;
        call = RetrofitClient.getInstance(application).getNowPlayingMovies().getNowPlayingMovies(
                Constants.API_KEY,
                locale,
                page);
        call.enqueue(new Callback<PopularMoviesBaseResponse>() {
            @Override
            public void onResponse(@NotNull Call<PopularMoviesBaseResponse> call, @NotNull retrofit2.Response<PopularMoviesBaseResponse> response) {
                if (!response.isSuccessful()) {
                    Log.e(TAG, "onResponse: " + "ErrorCode : " + response.code());
                    return;
                }
                if (response.body() != null) {
                    movieLocalRepo.insert(response.body().getMovieLists());
                }
            }

            @Override
            public void onFailure(@NotNull Call<PopularMoviesBaseResponse> call, @NotNull Throwable t) {
                Log.d(TAG, "onFailure: " + t.getLocalizedMessage());
            }
        });
    }
}
