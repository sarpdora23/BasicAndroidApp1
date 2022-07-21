package com.sinamekidev.teamfollowapp.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SportsResults {
    @SerializedName("game_spotlight")
    private GameSpotlight resultGames;

    public GameSpotlight getGameSpotlight(){
        return this.resultGames;
    }
}
