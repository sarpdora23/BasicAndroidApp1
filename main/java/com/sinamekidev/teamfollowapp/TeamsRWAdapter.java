package com.sinamekidev.teamfollowapp;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sinamekidev.teamfollowapp.apiinterface.FootballApi;
import com.sinamekidev.teamfollowapp.databinding.TeamRowBinding;
import com.sinamekidev.teamfollowapp.models.GameSpotlight;
import com.sinamekidev.teamfollowapp.models.QueryModel;
import com.sinamekidev.teamfollowapp.models.ResultTeam;
import com.sinamekidev.teamfollowapp.models.SportsResults;
import com.sinamekidev.teamfollowapp.models.Team;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TeamsRWAdapter extends RecyclerView.Adapter<TeamsRWAdapter.MyViewHolder> {
    private List<Team> teams_list;
    private String[] colors = {"#3F5FFE","#F12B2B","#AC2BF1","#15E300","#E3DC00","#5E5E5E","#6F3E15","#47156F"};
    private Retrofit retrofit;
    private FootballApi footballApi;
    private final String API_KEY = "30bcb7e2d495b6bae20de36ba97330758c433b04d318b38aa358efc5f366b5ce";
    public TeamsRWAdapter(List<Team> teams_list){
        this.teams_list = teams_list;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MyViewHolder myViewHolder = new MyViewHolder(TeamRowBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));
        System.out.println("My View Holder: " + myViewHolder);
        retrofit = new Retrofit.Builder().baseUrl("https://serpapi.com/").addConverterFactory(GsonConverterFactory.create()).build();
        footballApi = retrofit.create(FootballApi.class);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.binding.getRoot().setBackgroundColor(Color.parseColor(colors[position % 8]));
        Team team = teams_list.get(position);
        System.out.println(team.team_name);
        holder.binding.teamNameText.setText(team.team_name);
        Picasso.get().load(team.image_url).into(holder.binding.teamLogo);
        Call<QueryModel> queryModelCall = footballApi.getQueryModel(team.team_name +"last match",API_KEY);
        queryModelCall.enqueue(new Callback<QueryModel>() {
            @Override
            public void onResponse(Call<QueryModel> call, Response<QueryModel> response) {
                if(response.isSuccessful()){
                    QueryModel queryModel = response.body();
                    SportsResults sportsResults = queryModel.getSportsResults();
                    GameSpotlight lastgame = sportsResults.getGameSpotlight();
                    List<ResultTeam> result_teams = lastgame.getResultTeams();
                    String text = result_teams.get(0).getName()+" "+result_teams.get(0).getScore() + " - "+result_teams.get(1).getName() +" " +result_teams.get(1).getScore();
                    holder.binding.lastMatchText.setText(text);
                }
                else{
                    Toast.makeText(holder.binding.getRoot().getContext(),"CANT CONNECT API",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<QueryModel> call, Throwable t) {
                Toast.makeText(holder.binding.getRoot().getContext(),"FAILED TO CONNECT API",Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return teams_list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TeamRowBinding binding;
        public MyViewHolder(@NonNull TeamRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
