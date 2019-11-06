package com.example.themoviedb.ui;

import android.content.Intent;
import android.os.Bundle;

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
import com.example.themoviedb.repo.MovieLocalRepo;
import com.example.themoviedb.viewModel.MovieViewModel;

public class MainActivity extends AppCompatActivity implements OnMovieItemAdapterListener {
    ActivityMainBinding binding;
    private MovieViewModel viewModel;
    private MovieAdapter movieAdapter;
    private int page = 1;

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
        MovieLocalRepo.getInstance(getApplication()).deleteAllNotes();
        fetchData();
    }

    private void fetchData() {
        binding.swipeRefresh.setRefreshing(true);
        fetchMovieApi(page);
    }

    private void fetchMovieApi(final int page) {
        viewModel.getMovieResponse("en-US", page).observe(this, movieLists -> {
            movieAdapter.submitList(movieLists);
            binding.swipeRefresh.setRefreshing(false);
        });
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
            binding.swipeRefresh.setRefreshing(true);
        ++this.page;
            fetchMovieApi(page);
    }
}
