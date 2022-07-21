package com.sinamekidev.teamfollowapp.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.sinamekidev.teamfollowapp.models.Team;

@Database(entities = {Team.class},version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TeamDAO teamDAO();
}
