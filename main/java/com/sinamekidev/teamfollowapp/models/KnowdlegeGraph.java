package com.sinamekidev.teamfollowapp.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class KnowdlegeGraph {
    @SerializedName("teams")
    private List<ApiTeams> teams;

    public List<ApiTeams> getTeams() {
        return this.teams;
    }
}
