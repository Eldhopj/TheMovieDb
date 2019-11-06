package com.example.themoviedb.paging;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import com.example.themoviedb.models.MovieList;

/**
 * A DataSource.Factory is responsible for creating a DataSource.
 */
public class MovieDataSourceFactory extends DataSource.Factory {


    private MutableLiveData<PageKeyedDataSource<Integer, MovieList>> liveDataSource = new MutableLiveData<>();

    private Application application;

    public MovieDataSourceFactory(Application application) {
        this.application = application;
    }


    @Override
    public DataSource create() { // This method will return our data source
        MovieDataSource dataSource = new MovieDataSource(application); // Instance of DataSource
        liveDataSource.postValue(dataSource); // Putting value from DataSource into liveDataSource
        return dataSource;
    }
}
