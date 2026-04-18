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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quarters);

        Button btnStats = findViewById(R.id.btn_view_stats);
        btnStats.setOnClickListener(v -> {
            Intent intent = new Intent(QuartersActivity.this, Statistics.class);
            startActivity(intent);
        });


        RecyclerView recyclerView = findViewById(R.id.recycler_crew_cards);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        Button btnLaunch = findViewById(R.id.btn_launch_mission);
        btnLaunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuartersActivity.this, MissionActivity.class);
                startActivity(intent);
            }
        });

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {

            List<BaseCrewMember> myRealHand = AppDatabase.getInstance(getApplicationContext()).baseCrewMemberDao().getAll();

            runOnUiThread(() -> {
                CrewCardAdapter adapter = new CrewCardAdapter(myRealHand);
                recyclerView.setAdapter(adapter);
            });
        });
    }
}
