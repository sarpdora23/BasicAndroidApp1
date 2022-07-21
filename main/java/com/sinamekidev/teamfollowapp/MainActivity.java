package com.sinamekidev.teamfollowapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.sinamekidev.teamfollowapp.database.AppDatabase;
import com.sinamekidev.teamfollowapp.database.TeamDAO;
import com.sinamekidev.teamfollowapp.databinding.ActivityMainBinding;
import com.sinamekidev.teamfollowapp.models.Team;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private AppDatabase appDatabase;
    private TeamDAO teamDAO;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        appDatabase = Room.databaseBuilder(this,AppDatabase.class,"Team").build();
        teamDAO = appDatabase.teamDAO();
        compositeDisposable.add(teamDAO.getAll().observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(this::postHandler));
    }
    private void postHandler(List<Team> teamList){

        TeamsRWAdapter adapter = new TeamsRWAdapter(teamList);
        binding.teamsRW.setLayoutManager(new LinearLayoutManager(this));
        binding.teamsRW.setAdapter(adapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.add_club){
            Intent intent = new Intent(MainActivity.this,AddClubActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}