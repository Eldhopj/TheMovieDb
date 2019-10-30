package com.example.themoviedb.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.themoviedb.models.MovieList;
import com.example.themoviedb.repo.MovieLocalRepo;
import com.example.themoviedb.repo.MovieNetworkRepo;

import java.util.List;

public class MovieViewModel extends AndroidViewModel {
    private final MovieNetworkRepo movieNetworkRepo;
    private final MovieLocalRepo movieLocalRepo;

    public MovieViewModel(@NonNull Application application) {
        super(application);
        movieNetworkRepo = MovieNetworkRepo.getInstance(application);
        movieLocalRepo = MovieLocalRepo.getInstance(application);
    }

    public LiveData<List<MovieList>> getMovieResponse(String locale, int page) {
        movieNetworkRepo.getNowPlayingMovies(locale, page);
        return movieLocalRepo.getMovies();
    }
}
