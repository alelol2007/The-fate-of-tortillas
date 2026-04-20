package com.example.fateofthetortillas;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fateofthetortillas.database.AppDatabase;
import com.example.fateofthetortillas.crewMembers.BaseCrewMember;
import com.example.fateofthetortillas.crewMembers.Engineer;
import com.example.fateofthetortillas.crewMembers.Medic;
import com.example.fateofthetortillas.crewMembers.Pilot;
import com.example.fateofthetortillas.crewMembers.Scientist;
import com.example.fateofthetortillas.crewMembers.Soldier;
import com.example.fateofthetortillas.alien.AlienSoldier;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MissionActivity extends AppCompatActivity {

    private BaseCrewMember playerOne;
    private BaseCrewMember playerTwo;
    private AlienSoldier activeAlien;

    private View cardEnemy, cardPlayerOne, cardPlayerTwo;
    private Button btnAttack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission);

        cardEnemy = findViewById(R.id.card_enemy);
        cardPlayerOne = findViewById(R.id.card_player_one);
        cardPlayerTwo = findViewById(R.id.card_player_two);
        btnAttack = findViewById(R.id.btn_attack);

        // Hide the button initially until the database loads
        btnAttack.setEnabled(false);

        // Spawn our Alien
        activeAlien = new AlienSoldier(1, "Zorgon", 15, "Brute", 50, 50);

        // FETCH REAL FIGHTERS FROM DATABASE
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            List<BaseCrewMember> roster = AppDatabase.getInstance(getApplicationContext()).baseCrewMemberDao().getAll();

            new Handler(Looper.getMainLooper()).post(() -> {
                // Check if we have enough crew members to fight!
                if (roster.size() >= 2) {
                    // We pass the raw database data into our rebuilder so they get their powers back!
                    playerOne = rebuildFighter(roster.get(0));
                    playerTwo = rebuildFighter(roster.get(1));
                } else if (roster.size() == 1) {
                    // If they only recruited one person, they fight alone!
                    playerOne = rebuildFighter(roster.get(0));
                    playerTwo = rebuildFighter(roster.get(0));
                } else {
                    Toast.makeText(this, "You have no crew! Go to the Recruitment Desk.", Toast.LENGTH_LONG).show();
                    finish(); // Kick them back to the quarters
                    return;
                }

                // Now that we have our real players, draw them on the screen!
                updateAllCards();
                btnAttack.setEnabled(true); // Turn the attack button back on

                // Setup the combat button
                btnAttack.setOnClickListener(v -> performCombatRound());
            });
        });
    }

    private void performCombatRound() {
        // --- 1. CHECK SPECIAL ABILITIES ---

        // Example: If Player 1 is a Medic, they heal Player 2!
        if (playerOne instanceof Medic) {
            playerTwo.setEnergy(Math.min(100, playerTwo.getEnergy() + 15));
            Toast.makeText(this, playerOne.getName() + " used HEALING AURA!", Toast.LENGTH_SHORT).show();
        }

        // Example: If Player 2 is a Soldier, they get a rage damage boost!
        int soldierBonus = 0;
        if (playerTwo instanceof Soldier) {
            soldierBonus = 10;
            Toast.makeText(this, playerTwo.getName() + " used RAGE STRIKE!", Toast.LENGTH_SHORT).show();
        }

        // --- 2. PLAYERS ATTACK ALIEN ---
        int totalPlayerDamage = playerOne.getSkill() + playerTwo.getSkill() + soldierBonus;
        activeAlien.setEnergy(activeAlien.getEnergy() - totalPlayerDamage);

        // --- 3. ALIEN ATTACKS PLAYERS ---
        if (activeAlien.getEnergy() > 0) {
            int alienDamage = activeAlien.getSkill();

            // Example: If Player 1 is a Pilot, they dodge half the damage!
            if (playerOne instanceof Pilot) {
                playerOne.setEnergy(playerOne.getEnergy() - (alienDamage / 4));
                Toast.makeText(this, playerOne.getName() + " EVADED the attack!", Toast.LENGTH_SHORT).show();
            } else {
                playerOne.setEnergy(playerOne.getEnergy() - (alienDamage / 2));
            }
            playerTwo.setEnergy(playerTwo.getEnergy() - (alienDamage / 2));
        }

        // --- 4. UPDATE THE DATABASE WITH NEW HEALTH ---
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            AppDatabase.getInstance(getApplicationContext()).baseCrewMemberDao().update(playerOne);
            if(playerOne != playerTwo) {
                AppDatabase.getInstance(getApplicationContext()).baseCrewMemberDao().update(playerTwo);
            }
        });

        // --- 5. UPDATE THE SCREEN ---
        updateAllCards();

        // --- 6. CHECK FOR WIN / LOSS ---
        if (activeAlien.getEnergy() <= 0) {
            btnAttack.setEnabled(false);
            btnAttack.setText("VICTORY!");
            Toast.makeText(this, "Alien Defeated!", Toast.LENGTH_LONG).show();
        } else if (playerOne.getEnergy() <= 0 && playerTwo.getEnergy() <= 0) {
            btnAttack.setEnabled(false);
            btnAttack.setText("DEFEAT...");
            Toast.makeText(this, "Your squad was wiped out.", Toast.LENGTH_LONG).show();
        }
    }

    private void updateAllCards() {
        // Adding the specialization to their name on the battle board!
        String p1NameAndClass = playerOne.getName() + " (" + playerOne.getSpecialization() + ")";
        String p2NameAndClass = playerTwo.getName() + " (" + playerTwo.getSpecialization() + ")";

        updateSingleCard(cardPlayerOne, p1NameAndClass, "Energy: " + playerOne.getEnergy(), "Skill: " + playerOne.getSkill());
        updateSingleCard(cardPlayerTwo, p2NameAndClass, "Energy: " + playerTwo.getEnergy(), "Skill: " + playerTwo.getSkill());
        updateSingleCard(cardEnemy, activeAlien.getName(), "Health: " + activeAlien.getEnergy(), "Threat: " + activeAlien.getSkill());
    }

    private void updateSingleCard(View cardLayout, String name, String energy, String skill) {
        TextView txtName = cardLayout.findViewById(R.id.txtName);
        TextView txtEnergy = cardLayout.findViewById(R.id.txtEnergy);
        TextView txtSkill = cardLayout.findViewById(R.id.txtSkill);

        txtName.setText(name);
        txtEnergy.setText(energy);
        txtSkill.setText(skill);
    }

    // --- THE RECONSTRUCTOR ---
    // This takes a generic database character and turns them back into their specific class
    private BaseCrewMember rebuildFighter(BaseCrewMember rawMember) {
        String name = rawMember.getName();
        String spec = rawMember.getSpecialization();
        int currentEnergy = rawMember.getEnergy();
        int currentSkill = rawMember.getSkill();
        int resilience = rawMember.getResilience();
        int experience = rawMember.getExperience();
        int maxEnergy = rawMember.getMaxEnergy();
        boolean trainingSession = rawMember.trainingSession != null ? rawMember.trainingSession : false;

        switch (spec) {
            case "Pilot":
                return new Pilot(0, name, currentSkill, resilience, experience, currentEnergy, maxEnergy, trainingSession);
            case "Engineer":
                return new Engineer(0, name, currentSkill, resilience, experience, currentEnergy, maxEnergy, trainingSession);
            case "Soldier":
                return new Soldier(0, name, currentSkill, resilience, experience, currentEnergy, maxEnergy, trainingSession);
            case "Medic":
                return new Medic(0, name, currentSkill, resilience, experience, currentEnergy, maxEnergy, trainingSession);
            case "Scientist":
                return new Scientist(0, name, currentSkill, resilience, experience, currentEnergy, maxEnergy, trainingSession);
            default:
                return rawMember;
        }
    }
}
