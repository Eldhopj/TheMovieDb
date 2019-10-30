package com.example.themoviedb.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.example.themoviedb.R;
import com.example.themoviedb.adapters.MovieAdapter;
import com.example.themoviedb.commonItems.IntentConstants;
import com.example.themoviedb.commonItems.Utility;
import com.example.themoviedb.databinding.ActivityMainBinding;
import com.example.themoviedb.interfaces.OnMovieItemAdapterListener;
import com.example.themoviedb.models.MovieList;
import com.example.themoviedb.models.PopularMoviesBaseResponse;
import com.example.themoviedb.viewModel.MovieViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMovieItemAdapterListener {
    ActivityMainBinding binding;
    private MovieViewModel viewModel;
    private MovieAdapter movieAdapter;
    private int page = 1;
    private int totalPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        viewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        initRecyclerView();
        fetchData();
        swipeToRefresh();
    }

    private void swipeToRefresh() {
        binding.swipeRefresh.setOnRefreshListener(this::refreshData);
    }


    private void initRecyclerView() {
        movieAdapter = new MovieAdapter(getApplicationContext());
        movieAdapter.setOnItemClickListener(this);
        Utility.setVerticalRecyclerView(binding.recyclerView, movieAdapter, getApplicationContext(), false);
    }

    private void refreshData() {
        page = 1;
        movieAdapter.clearData();
        fetchData();
    }

    private void fetchData() {
        binding.swipeRefresh.setRefreshing(true);
        fetchMovieApi(page);
    }

    private void fetchMovieApi(final int page) {
        viewModel.getUsersResponse("en-US", page).observe(this, apiResponse -> {
            binding.swipeRefresh.setRefreshing(false);
            if (apiResponse.getResponse() != null) {
                ++this.page;
                processUsersResponse((PopularMoviesBaseResponse) apiResponse.getResponse());
            } else if (apiResponse.getError() != null) {
                Toast.makeText(this, apiResponse.getError(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void processUsersResponse(@NonNull PopularMoviesBaseResponse response) {
        List<MovieList> movies = new ArrayList<>(response.getMovieLists());
        movieAdapter.addItemRange(movies);
        totalPage = response.getTotalPages();
    }

    private void navigateIntoMovieDetailPage(MovieList movieList) {
        Intent detailedActivityIntent = new Intent(getApplicationContext(), DetailedActivity.class);
        detailedActivityIntent.putExtra(IntentConstants.MOVIE_DETAIL,
                movieList); // passing object as parcelable
        startActivity(detailedActivityIntent);
    }

    @Override
    public void onItemClick(MovieList movieList) {
        navigateIntoMovieDetailPage(movieList);
    }

    @Override
    public void callPaginationUpcoming() {
        if (page < totalPage) {
            binding.swipeRefresh.setRefreshing(true);
            fetchMovieApi(page);
        }
    }
}
