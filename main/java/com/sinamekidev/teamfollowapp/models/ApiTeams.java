package com.sinamekidev.teamfollowapp.models;

import com.google.gson.annotations.SerializedName;

public class ApiTeams {
    @SerializedName("name")
    private String name;
    @SerializedName("link")
    private String link;
    @SerializedName("serpapi_link")
    private String serpapi_link;
    @SerializedName("image")
    private String image;

    public String getName() {
        return name;
    }

    public String getLink() {
        return link;
    }

    public String getSerpapi_link() {
        return serpapi_link;
    }

    public String getImage() {
        return image;
    }
}
