package com.example.fateofthetortillas.statistics;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.fateofthetortillas.R;
import com.example.fateofthetortillas.crewMembers.BaseCrewMember;
import com.example.fateofthetortillas.crewMembers.Crew;
import com.example.fateofthetortillas.database.AppDatabase;


public class Statistics  extends AppCompatActivity{
    private TextView textViewSkill;
    private TextView textViewEnergy;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics);
        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "tortila-db")
                .allowMainThreadQueries()
                .build();
        textViewSkill = findViewById(R.id.tv_total_skill);
        textViewEnergy = findViewById(R.id.tv_total_energy);
        findViewById(R.id.buttonBack).setOnClickListener(v -> finish());
        DisplayStats();
    }
    public void DisplayStats(){
        Crew savedCrew = db.crewDao().getCrew();
        if (savedCrew != null && savedCrew.getCrew() != null){
            int totalTeamSkill = 0;
            int totalTeamEnergy=0;
            for (BaseCrewMember member : savedCrew.getCrew()){
                totalTeamSkill += member.getSkill();
                totalTeamEnergy += member.getEnergy();
            }
            textViewSkill.setText("Total Skill: " + totalTeamSkill);
            textViewEnergy.setText("Total Energy: " + totalTeamEnergy);
        } else {
            textViewSkill.setText("No Crew Data Found");
            textViewEnergy.setText("Start a new game first!");
        }
}
}


