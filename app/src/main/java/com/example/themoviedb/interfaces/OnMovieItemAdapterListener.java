package com.example.themoviedb.interfaces;

import com.example.themoviedb.models.MovieList;

public interface OnMovieItemAdapterListener {
    void onItemClick(MovieList movieList);

    void callPaginationUpcoming();
}
