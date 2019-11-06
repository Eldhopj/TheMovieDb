package com.example.themoviedb.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.themoviedb.commonItems.Constants;
import com.example.themoviedb.commonItems.Utility;
import com.example.themoviedb.databinding.MovieListItemBinding;
import com.example.themoviedb.diffUtils.MovieDiffUtils;
import com.example.themoviedb.interfaces.OnMovieItemAdapterListener;
import com.example.themoviedb.models.MovieList;
import com.example.themoviedb.viewHolder.MoviesViewHolder;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private final List<MovieList> mMoviesList = new ArrayList<>();
    private final Context mContext;
    private OnMovieItemAdapterListener mListener;


    //constructor
    public MovieAdapter(Context context) {
        this.mContext = context;
    }

    public void setOnItemClickListener(OnMovieItemAdapterListener listener) {
        mListener = listener;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        MovieListItemBinding binding = MovieListItemBinding
                .inflate(inflater, parent, false);
        return new MoviesViewHolder(binding, mListener, mMoviesList);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MoviesViewHolder userViewHolder;

        if (holder instanceof MoviesViewHolder) {
            userViewHolder = (MoviesViewHolder) holder;
            setUserViewHolder(userViewHolder, position);
        }
        setUpPaginationCall(position);
    }

    private void setUpPaginationCall(int position) {
        if (mMoviesList.size() > 1) {
            if (position == mMoviesList.size() - 1) {
                mListener.callPaginationUpcoming();
            }
        }
    }

    private void setUserViewHolder(MoviesViewHolder userViewHolder, int position) {
        MovieList movie = mMoviesList.get(position);
        if (movie != null) {
            userViewHolder.bind(movie);
            Utility.loadImageUsingGlide(mContext,
                    userViewHolder.getBinding().movieImageIv,
                    Constants.IMAGE_PREFIX + movie.getPosterPath());
        }
    }

    @Override
    public int getItemCount() {
        return mMoviesList.size();
    }

    //-------------------------------------Manipulating RecyclerView--------------------------------//
    public void clearData() {
        if (!mMoviesList.isEmpty()) {
            mMoviesList.clear();
            notifyDataSetChanged();
        }
    }

    public void addItemRange(List<MovieList> newMoviesList) {
        if (newMoviesList != null) {
            int position = mMoviesList.size();
            mMoviesList.addAll(position, newMoviesList);
            final DiffUtil.DiffResult result = DiffUtil.calculateDiff(new MovieDiffUtils(mMoviesList, newMoviesList), false);
            result.dispatchUpdatesTo(MovieAdapter.this);

//            notifyItemRangeInserted(position, newMoviesList.size());
        }
    }
}
