package com.example.themoviedb.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.themoviedb.R;
import com.example.themoviedb.commonItems.IntentConstants;
import com.example.themoviedb.commonItems.StringConstants;
import com.example.themoviedb.commonItems.Utility;
import com.example.themoviedb.databinding.ActivityDetailedBinding;
import com.example.themoviedb.models.MovieList;

public class DetailedActivity extends AppCompatActivity {
    ActivityDetailedBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detailed);
        getExtras();
    }

    private void getExtras() {
        Intent intent = getIntent();
        MovieList movieList = intent.getParcelableExtra(IntentConstants.MOVIE_DETAIL); //The values inside the clicked item object is in the clicked item
        setData(movieList);
    }

    private void setData(MovieList movieList) {
        binding.setMovieList(movieList);

        Utility.loadImageUsingGlide(getApplicationContext(),
                binding.movieImage,
                StringConstants.IMAGE_PREFIX + movieList.getPosterPath());
    }
}
