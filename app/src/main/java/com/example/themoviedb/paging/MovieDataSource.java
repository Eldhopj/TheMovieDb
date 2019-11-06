package com.example.themoviedb.paging;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.example.themoviedb.models.MovieList;
import com.example.themoviedb.repo.MovieLocalRepo;

import java.util.List;

public class MovieDataSource extends PageKeyedDataSource<Integer, MovieList> {
    private final MovieLocalRepo movieLocalRepo;

    public MovieDataSource(Application application) {
        this.movieLocalRepo = MovieLocalRepo.getInstance(application);
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, MovieList> callback) {
        List<MovieList> movies = movieLocalRepo.getMovies();
        if (movies.size() != 0) {
            callback.onResult(movies, 0, 1);
        }
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, MovieList> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, MovieList> callback) {

    }
}
