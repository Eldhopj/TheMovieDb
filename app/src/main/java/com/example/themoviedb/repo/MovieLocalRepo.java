package com.example.themoviedb.repo;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.themoviedb.commonItems.RoomClient;
import com.example.themoviedb.interfaces.MovieDao;
import com.example.themoviedb.models.MovieList;

import java.util.List;

public class MovieLocalRepo {

    private static MovieLocalRepo repoInstance;
    private final MovieDao movieDao;


    private MovieLocalRepo(Application application) {
        RoomClient roomClient = RoomClient.getDatabase(application);
        movieDao = roomClient.movieDao();
    }

    public static MovieLocalRepo getInstance(Application application) {
        if (repoInstance == null) {
            repoInstance = new MovieLocalRepo(application);
        }
        return repoInstance;
    }

    public LiveData<List<MovieList>> getMovies() {
        return movieDao.getAllData();
    }

    //--------------- manupulating --------------------- //

    public void insert(List<MovieList> data) {
        new InsertAsyncTask(movieDao).execute(data);
    }

    public void deleteAllNotes() {
        new DeleteAllMoviesAsyncTask(movieDao).execute();
    }

    private static class InsertAsyncTask extends AsyncTask<List<MovieList>, Void, Void> {

        private final MovieDao mAsyncTaskDao;

        InsertAsyncTask(MovieDao dao) {
            mAsyncTaskDao = dao;
        }

        @SafeVarargs
        @Override
        protected final Void doInBackground(final List<MovieList>... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class DeleteAllMoviesAsyncTask extends AsyncTask<Void, Void, Void> { //NOTE : we don't wanna pass our Note entity
        private MovieDao movieDao;

        private DeleteAllMoviesAsyncTask(MovieDao movieDao) {
            this.movieDao = movieDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            movieDao.deleteAllData();
            return null;
        }
    }
}
