package com.example.themoviedb.commonItems;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.themoviedb.interfaces.MovieDao;
import com.example.themoviedb.models.MovieList;


@Database(entities = {MovieList.class}, version = 1, exportSchema = false)
public abstract class RoomClient extends RoomDatabase {

    private static volatile RoomClient INSTANCE;

    public static RoomClient getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (RoomClient.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            RoomClient.class, "movie_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract MovieDao movieDao();
}
