package com.sinamekidev.teamfollowapp.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Team {
    @PrimaryKey (autoGenerate = true)
    public int uid;
    @ColumnInfo (name = "team_name")
    public String team_name;
    @ColumnInfo (name = "image_url")
    public String image_url;
    public Team(String team_name,String image_url){
        this.team_name = team_name;
        this.image_url = image_url;
    }
}
