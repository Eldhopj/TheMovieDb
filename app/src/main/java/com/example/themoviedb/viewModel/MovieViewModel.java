package com.example.themoviedb.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.example.themoviedb.models.MovieList;
import com.example.themoviedb.paging.MovieDataSourceFactory;
import com.example.themoviedb.repo.MovieLocalRepo;
import com.example.themoviedb.repo.MovieNetworkRepo;

public class MovieViewModel extends AndroidViewModel {
    private final MovieNetworkRepo movieNetworkRepo;
    private final MovieLocalRepo movieLocalRepo;
    private final MovieDataSourceFactory dataSourceFactory;
    public LiveData<PagedList<MovieList>> itemPagedList;

    public MovieViewModel(@NonNull Application application) {
        super(application);
        movieNetworkRepo = MovieNetworkRepo.getInstance(application);
        movieLocalRepo = MovieLocalRepo.getInstance(application);
        dataSourceFactory = new MovieDataSourceFactory(application); // Instance of MovieDataSourceFactory

        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(16) //The number of items to load initially.
                .setPageSize(20)
                .setPrefetchDistance(8) // Loads 8 items initially when the screen loads
                .build();
        itemPagedList = new LivePagedListBuilder(dataSourceFactory, config).build();
    }

    public LiveData<PagedList<MovieList>> getMovieResponse(String locale, int page) {
        movieNetworkRepo.getNowPlayingMovies(locale, page);
        return itemPagedList;
    }
}
