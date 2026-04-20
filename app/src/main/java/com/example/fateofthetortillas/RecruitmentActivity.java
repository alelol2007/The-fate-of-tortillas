package com.example.fateofthetortillas;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fateofthetortillas.database.AppDatabase;
import com.example.fateofthetortillas.crewMembers.BaseCrewMember;
import com.example.fateofthetortillas.crewMembers.Engineer;
import com.example.fateofthetortillas.crewMembers.Pilot;
import com.example.fateofthetortillas.crewMembers.Soldier;
import com.example.fateofthetortillas.crewMembers.Medic;
import com.example.fateofthetortillas.crewMembers.Scientist;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RecruitmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruitment);

        EditText edtName = findViewById(R.id.edt_crew_name);
        Spinner spinnerSpec = findViewById(R.id.spinner_specialization);
        Button btnRecruit = findViewById(R.id.btn_confirm_recruit);

        String[] classes = {"Pilot", "Engineer", "Soldier", "Medic", "Scientist"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, classes);
        spinnerSpec.setAdapter(adapter);

        btnRecruit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtName.getText().toString();
                String spec = spinnerSpec.getSelectedItem().toString();

                if (name.isEmpty()) {
                    Toast.makeText(RecruitmentActivity.this, "Please enter a name!", Toast.LENGTH_SHORT).show();
                    return;
                }

                BaseCrewMember newMember = null;
                switch (spec) {
                    case "Pilot": newMember = new Pilot(0, name, 8, 5, 0, 100, 100, false); break;
                    case "Engineer": newMember = new Engineer(0, name, 6, 8, 0, 100, 100, false); break;
                    case "Soldier": newMember = new Soldier(0, name, 12, 4, 0, 120, 120, false); break;
                    case "Medic": newMember = new Medic(0, name, 5, 10, 0, 90, 90, false); break;
                    case "Scientist": newMember = new Scientist(0, name, 9, 6, 0, 80, 80, false); break;
                }

                BaseCrewMember finalMember = newMember;
                ExecutorService executor = Executors.newSingleThreadExecutor();
                executor.execute(() -> {
                    if (finalMember != null) {
                        AppDatabase.getInstance(getApplicationContext()).baseCrewMemberDao().insert(finalMember);

                        runOnUiThread(() -> {
                            Toast.makeText(RecruitmentActivity.this, name + " recruited!", Toast.LENGTH_SHORT).show();
                            finish();
                        });
                    }
                });
            }
        });
    }
}
