package com.sinamekidev.teamfollowapp.models;

import com.google.gson.annotations.SerializedName;

public class ResultTeam {
    @SerializedName("name")
    private String name;
    @SerializedName("thumbnail")
    private String thumbnail;
    @SerializedName("score")
    private String score;

    public String getName() {
        return this.name;
    }
    public String getThumbnail(){
        return  this.thumbnail;
    }
    public String getScore(){
        return this.score;
    }
}
