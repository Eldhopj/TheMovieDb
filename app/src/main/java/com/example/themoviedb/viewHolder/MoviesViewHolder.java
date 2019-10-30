package com.example.themoviedb.viewHolder;

import androidx.recyclerview.widget.RecyclerView;

import com.example.themoviedb.databinding.MovieListItemBinding;
import com.example.themoviedb.interfaces.OnMovieItemAdapterListener;
import com.example.themoviedb.models.MovieList;

import java.util.List;

public class MoviesViewHolder extends RecyclerView.ViewHolder {
    private MovieListItemBinding mBinding;
    private OnMovieItemAdapterListener mListener;

    public MoviesViewHolder(MovieListItemBinding binding, OnMovieItemAdapterListener listener, List<MovieList> mListItems) {
        super(binding.getRoot());
        this.mBinding = binding;
        this.mListener = listener;
        movieItemClick(mListItems);
    }

    private void movieItemClick(List<MovieList> mListItems) {
        mBinding.getRoot().setOnClickListener(view -> {
            if (mListener != null) {
                int position = getAdapterPosition(); // Get the index of the view holder
                if (position != RecyclerView.NO_POSITION) { // Makes sure this position is still valid
                    mListener.onItemClick(mListItems.get(position)); // we catch the click on the item view then pass it over the interface and then to our activity
                }
            }
        });
    }


    public MovieListItemBinding getBinding() {
        return mBinding;
    }

    public void bind(MovieList movies) {
        mBinding.setMovieList(movies);
        mBinding.executePendingBindings();//is important in order to execute the data mBinding immediately. Otherwise it can populate incorrect view.
    }
}
