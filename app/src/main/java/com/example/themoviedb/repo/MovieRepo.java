package com.example.themoviedb.repo;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.themoviedb.commonItems.RetrofitClient;
import com.example.themoviedb.commonItems.StringConstants;
import com.example.themoviedb.models.ApiResponse;
import com.example.themoviedb.models.PopularMoviesBaseResponse;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;

public class MovieRepo {
    private static final String TAG = "UsersRepo";
    private static MovieRepo repoInstance;
    private final Application application;

    private MovieRepo(Application application) {
        this.application = application;
    }

    public static MovieRepo getInstance(Application application) {
        if (repoInstance == null) {
            repoInstance = new MovieRepo(application);
        }
        return repoInstance;
    }

    public MutableLiveData<ApiResponse> getNowPlayingMovies(String locale, int page) {
        final MutableLiveData<ApiResponse> mutableLiveData = new MutableLiveData<>();
        Call<PopularMoviesBaseResponse> call;
        call = RetrofitClient.getInstance(application).getNowPlayingMovies().getNowPlayingMovies(
                StringConstants.API_KEY,
                locale,
                page);
        call.enqueue(new Callback<PopularMoviesBaseResponse>() {
            @Override
            public void onResponse(@NotNull Call<PopularMoviesBaseResponse> call, @NotNull retrofit2.Response<PopularMoviesBaseResponse> response) {
                if (!response.isSuccessful()) {
                    Log.e(TAG, "onResponse: " + "ErrorCode : " + response.code());
                    mutableLiveData.setValue(new ApiResponse(response.message()));
                    return;
                }
                mutableLiveData.setValue(new ApiResponse(response.body()));
            }

            @Override
            public void onFailure(@NotNull Call<PopularMoviesBaseResponse> call, @NotNull Throwable t) {
                mutableLiveData.setValue(new ApiResponse(t.getLocalizedMessage()));
            }
        });
        return mutableLiveData;
    }
}
