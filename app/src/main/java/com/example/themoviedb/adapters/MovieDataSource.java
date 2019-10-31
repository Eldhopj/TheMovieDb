package com.example.themoviedb.adapters;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.example.themoviedb.models.MovieList;

public class MovieDataSource extends PageKeyedDataSource<Integer, MovieList> {
    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, MovieList> callback) {

    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, MovieList> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, MovieList> callback) {

    }
}
