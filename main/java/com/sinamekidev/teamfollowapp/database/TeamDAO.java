package com.sinamekidev.teamfollowapp.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.sinamekidev.teamfollowapp.models.Team;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface TeamDAO{
    @Query("SELECT * FROM Team")
    Single<List<Team>> getAll();
    @Insert
    Completable addTeam(Team team);
    @Delete
    Completable deleteTeam(Team team);

}
