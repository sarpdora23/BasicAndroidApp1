package com.sinamekidev.teamfollowapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Database;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.sinamekidev.teamfollowapp.apiinterface.FootballApi;
import com.sinamekidev.teamfollowapp.database.AppDatabase;
import com.sinamekidev.teamfollowapp.database.TeamDAO;
import com.sinamekidev.teamfollowapp.databinding.ActivityAddClubBinding;
import com.sinamekidev.teamfollowapp.models.ApiTeams;
import com.sinamekidev.teamfollowapp.models.KnowdlegeGraph;
import com.sinamekidev.teamfollowapp.models.QueryModel;
import com.sinamekidev.teamfollowapp.models.Team;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddClubActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private ActivityAddClubBinding binding;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private AppDatabase appDatabase;
    private TeamDAO teamDAO;
    private Retrofit retrofit;
    private FootballApi footballApi;
    private List<ApiTeams> apiTeamsList;
    private final String API_KEY = "30bcb7e2d495b6bae20de36ba97330758c433b04d318b38aa358efc5f366b5ce";
    private String image_url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddClubBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.leagueSpinner.setOnItemSelectedListener(this);
        appDatabase = Room.databaseBuilder(this,AppDatabase.class,"Team").build();
        teamDAO = appDatabase.teamDAO();
        retrofit = new Retrofit.Builder().baseUrl("https://serpapi.com/").addConverterFactory(GsonConverterFactory.create()).build();
        footballApi = retrofit.create(FootballApi.class);
        binding.teamSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                imageSelect(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    public void onItemSelected(AdapterView<?> parent, View view, int pos,long id){
        if(id != 0){
            OnLeagueSelect();
        }
    }
    private void imageSelect(int i){
        Picasso.get().load(apiTeamsList.get(i).getImage()).into(binding.imageView);
        image_url = apiTeamsList.get(i).getImage();
    }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {}

    private void OnLeagueSelect(){
        //30bcb7e2d495b6bae20de36ba97330758c433b04d318b38aa358efc5f366b5ce
        ArrayList<String> team_list = new ArrayList<>();
        String league_name = binding.leagueSpinner.getSelectedItem().toString();
        Call<QueryModel> queryModelCall = footballApi.getQueryModel(league_name+ "+takımlar",API_KEY);
        queryModelCall.enqueue(new Callback<QueryModel>() {
            @Override
            public void onResponse(Call<QueryModel> call, Response<QueryModel> response) {
                if(response.isSuccessful()){
                    KnowdlegeGraph kg = response.body().getKnowdlegeGraph();
                    apiTeamsList = kg.getTeams();
                   // team_list.add("");
                    for(ApiTeams apiTeam: apiTeamsList){
                        String name = apiTeam.getName();
                        team_list.add(name);
                    }
                    ArrayAdapter team_spinner_adapter = new ArrayAdapter(AddClubActivity.this, android.R.layout.simple_spinner_item,team_list);
                    team_spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    binding.teamSpinner.setAdapter(team_spinner_adapter);
                    binding.teamSpinner.setEnabled(true);
                }
                else{
                    Toast.makeText(AddClubActivity.this,"CAN'T CONNECT API",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<QueryModel> call, Throwable t) {
                Toast.makeText(AddClubActivity.this,"FAILED TO CONNECT API",Toast.LENGTH_LONG).show();
            }
        });
    }
    public void onClickSaveButton(View view){
        if(binding.teamSpinner.getSelectedItem() == null || binding.leagueSpinner.getSelectedItem() == null){
            Toast.makeText(this,"League and Team section can't be empty!",Toast.LENGTH_LONG).show();
        }
        else{
            Intent intent = new Intent(AddClubActivity.this,MainActivity.class);
            compositeDisposable.add(teamDAO.addTeam(new Team(binding.teamSpinner.getSelectedItem().toString(),image_url))
                    .subscribeOn(Schedulers.io()).subscribe());
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }
}