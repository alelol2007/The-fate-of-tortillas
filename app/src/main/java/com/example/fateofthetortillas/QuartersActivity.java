package com.example.fateofthetortillas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fateofthetortillas.database.AppDatabase;
import com.example.fateofthetortillas.crewMembers.BaseCrewMember;
import com.example.fateofthetortillas.statistics.Statistics;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class QuartersActivity extends AppCompatActivity {

     private RecyclerView recyclerView;
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quarters);
        recyclerView = findViewById(R.id.recycler_crew_cards);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        findViewById(R.id.btn_view_stats).setOnClickListener(v ->
                 startActivity(new Intent(this, Statistics.class)));
        findViewById(R.id.btn_go_to_recruitment).setOnClickListener(v -> startActivity(new Intent(this, RecruitmentActivity.class)));
        findViewById(R.id.btn_launch_mission).setOnClickListener(v -> startActivity(new Intent(this, MissionActivity.class)));
     }
     @Override
    protected void onResume() {
         super.onResume();
         loadCrewData();
     }

     private void loadCrewData() {
         Executors.newSingleThreadExecutor().execute(() -> {
             List<BaseCrewMember> myRealHand = AppDatabase.getInstance(this).baseCrewMemberDao().getAll();
            runOnUiThread(() -> {
                CrewCardAdapter adapter = new CrewCardAdapter(myRealHand);
                recyclerView.setAdapter(adapter);
            });
         });
     }

}
