package com.example.themoviedb.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.themoviedb.commonItems.Constants;
import com.example.themoviedb.commonItems.Utility;
import com.example.themoviedb.databinding.MovieListItemBinding;
import com.example.themoviedb.interfaces.OnMovieItemAdapterListener;
import com.example.themoviedb.models.MovieList;
import com.example.themoviedb.viewHolder.MoviesViewHolder;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends PagedListAdapter<MovieList, RecyclerView.ViewHolder> {


    private final List<MovieList> mListItems = new ArrayList<>();
    private final Context mContext;
    private OnMovieItemAdapterListener mListener;

    private static DiffUtil.ItemCallback<MovieList> DIFF_CALLBACK = new DiffUtil.ItemCallback<MovieList>() {
        @Override
        public boolean areItemsTheSame(@NonNull MovieList oldItem, @NonNull MovieList newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull MovieList oldItem, @NonNull MovieList newItem) {
            return oldItem.getTitle().equals(newItem.getTitle());
        }
    };


    public void setOnItemClickListener(OnMovieItemAdapterListener listener) {
        mListener = listener;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        MovieListItemBinding binding = MovieListItemBinding
                .inflate(inflater, parent, false);
        return new MoviesViewHolder(binding, mListener, mListItems);
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
        if (mListItems.size() > 1) {
            if (position == mListItems.size() - 1) {
                mListener.callPaginationUpcoming();
            }
        }
    }

    private void setUserViewHolder(MoviesViewHolder userViewHolder, int position) {
        MovieList movie = mListItems.get(position);
        if (movie != null) {
            userViewHolder.bind(movie);
            Utility.loadImageUsingGlide(mContext,
                    userViewHolder.getBinding().movieImageIv,
                    Constants.IMAGE_PREFIX + movie.getPosterPath());
        }
    }

    @Override
    public int getItemCount() {
        return mListItems.size();
    }

    public MovieAdapter(Context context) {
        super(DIFF_CALLBACK);
        this.mContext = context;
    }

    //-------------------------------------Manipulating RecyclerView--------------------------------//
    public void clearData() {
        if (!mListItems.isEmpty()) {
            mListItems.clear();
            notifyDataSetChanged();
        }
    }
}
