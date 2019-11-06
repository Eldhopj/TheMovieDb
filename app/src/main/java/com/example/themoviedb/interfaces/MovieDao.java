package com.example.themoviedb.interfaces;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.themoviedb.models.MovieList;

import java.util.List;

@Dao
public interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
        // to replace in-case of any conflicts
    void insert(List<MovieList> data);

    @Query("DELETE FROM movie_table")
    void deleteAllData();


    @Query("SELECT * FROM movie_table")
    List<MovieList> getAllData();// Observe the object , so if there is any changes in the table this value will be auto updated and the activity will be notified
}
