package com.example.fateofthetortillas.statistics;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.fateofthetortillas.R;
import com.example.fateofthetortillas.crewMembers.BaseCrewMember;
import com.example.fateofthetortillas.crewMembers.Crew;
import com.example.fateofthetortillas.database.AppDatabase;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;


public class Statistics  extends AppCompatActivity{
    private TextView textViewSkill;
    private TextView textViewEnergy;
    private AppDatabase db;
    private BarChart barChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics);
        db = AppDatabase.getInstance(this);

        textViewSkill = findViewById(R.id.tv_total_skill);
        textViewEnergy = findViewById(R.id.tv_total_energy);
        barChart = findViewById(R.id.barChart);
        findViewById(R.id.buttonBack).setOnClickListener(v -> finish());
        loadStatistics();
    }
    private void loadStatistics(){
        Executors.newSingleThreadExecutor().execute(()->{
            List<BaseCrewMember> crew = db.baseCrewMemberDao().getAll();
            runOnUiThread(()->{
                if (crew == null || crew.isEmpty()){
                    textViewSkill.setText("No Crew Data Found");
                    textViewEnergy.setText("Start a new game first!");
                    barChart.setVisibility(View.GONE);
                } else{
                    DisplayStats(crew);
                }
            });
        });
    }

    public void DisplayStats(List<BaseCrewMember> crew){
        int totalTeamSkill = 0;
        int totalTeamEnergy = 0;
        ArrayList<Integer> skillData = new ArrayList<>();
        ArrayList<BarEntry> entries = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>();
        for (int i =0; i<crew.size(); i++){
            BaseCrewMember member = crew.get(i);
            totalTeamSkill += member.getSkill();
            totalTeamEnergy += member.getEnergy();
            entries.add(new BarEntry(i, member.getSkill()));
            labels.add(member.getName());
        }
        textViewSkill.setText(String.format("Total Skill: %d", totalTeamSkill));
        textViewEnergy.setText(String.format("Total Energy: %d", totalTeamEnergy));

        BarDataSet barDataSet = new BarDataSet(entries, "Team Members");
        barDataSet.setColor(Color.parseColor("#00D4FF"));
        barDataSet.setValueTextColor(Color.WHITE);
        barDataSet.setValueTextSize(13f);
        BarData barData = new BarData(barDataSet);
        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));
        barChart.getLegend().setTextColor(Color.WHITE);
        barChart.getDescription().setEnabled(false);
        barChart.setData(barData);
        barChart.animateY(2000);
        barChart.invalidate();
    }
}


