package com.example.fateofthetortillas;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;

import com.example.fateofthetortillas.database.AppDatabase;
import com.example.fateofthetortillas.crewMembers.Crew;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "tortila-db").allowMainThreadQueries().build();

        // creating a new crew and then saving it to the database then calling it, does this make any senseeeee?????????????????

        Crew myCurrentCrew=new Crew(10,100,0,null);
        db.crewDao().saveCrew(myCurrentCrew);
        Crew savedCrew = db.crewDao().getCrew();
        if (savedCrew != null) {
            System.out.println("Loaded crew with " + savedCrew.crew.size() + " members");
        }
        else {
            System.out.println("No crew found");
        }



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}