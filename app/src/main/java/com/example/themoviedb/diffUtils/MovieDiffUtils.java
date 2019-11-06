package com.example.themoviedb.diffUtils;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import com.example.themoviedb.models.MovieList;

import java.util.List;

public class MovieDiffUtils extends DiffUtil.Callback {

    private List<MovieList> oldMovieList;
    private List<MovieList> newMovieList;

    public MovieDiffUtils(List<MovieList> oldMovieList, List<MovieList> newMovieList) {
        this.oldMovieList = oldMovieList;
        this.newMovieList = newMovieList;
    }

    @Override
    public int getOldListSize() {
        return oldMovieList.size();
    }

    @Override
    public int getNewListSize() {
        return newMovieList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldMovieList.get(oldItemPosition).getId().equals(newMovieList.get(newItemPosition).getId());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldMovieList.get(oldItemPosition).getOriginalTitle().equals(newMovieList.get(newItemPosition).getOriginalTitle());
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
