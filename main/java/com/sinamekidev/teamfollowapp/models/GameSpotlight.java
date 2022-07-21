package com.sinamekidev.teamfollowapp.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GameSpotlight {
    @SerializedName("teams")
    private List<ResultTeam> resultTeams;

    public List<ResultTeam> getResultTeams() {
        return this.resultTeams;
    }
}
