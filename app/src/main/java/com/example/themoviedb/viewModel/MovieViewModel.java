package com.example.themoviedb.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.themoviedb.models.ApiResponse;
import com.example.themoviedb.repo.MovieRepo;

public class MovieViewModel extends AndroidViewModel {
    private final MovieRepo movieRepo;

    public MovieViewModel(@NonNull Application application) {
        super(application);
        movieRepo = MovieRepo.getInstance(application);
    }

    public LiveData<ApiResponse> getUsersResponse(String locale, int page) {
        return movieRepo.getNowPlayingMovies(locale, page);
    }
}
