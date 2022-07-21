package com.sinamekidev.teamfollowapp.models;

import com.google.gson.annotations.SerializedName;

public class QueryModel {
    @SerializedName("knowledge_graph")
    private KnowdlegeGraph knowdlegeGraph;

    @SerializedName("sports_results")
    private SportsResults sportsResults;
    public SportsResults getSportsResults(){
        return this.sportsResults;
    }
    public KnowdlegeGraph getKnowdlegeGraph() {
        return this.knowdlegeGraph;
    }
}
