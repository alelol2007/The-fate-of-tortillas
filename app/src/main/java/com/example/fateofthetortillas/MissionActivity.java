package com.example.fateofthetortillas;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import com.example.fateofthetortillas.database.AppDatabase;
import com.example.fateofthetortillas.crewMembers.*;
import com.example.fateofthetortillas.alien.*;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;



import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MissionActivity extends AppCompatActivity {

    private BaseCrewMember playerOne, playerTwo, selectedPlayer;
    private BaseEnemyMember activeAlien;

    private View cardEnemy, cardPlayerOne, cardPlayerTwo;
    private Button btnAttack;
    private TextView txtTurnInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission);

        cardEnemy = findViewById(R.id.card_enemy);
        cardPlayerOne = findViewById(R.id.card_player_one);
        cardPlayerTwo = findViewById(R.id.card_player_two);
        btnAttack = findViewById(R.id.btn_attack);
        txtTurnInfo = findViewById(R.id.txt_current_turn_info);

        // 1. RANDOMIZE THE ALIEN
        activeAlien = getRandomAlien();

        // 2. FETCH REAL FIGHTERS
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            List<BaseCrewMember> roster = AppDatabase.getInstance(this).baseCrewMemberDao().getAll();

            new Handler(Looper.getMainLooper()).post(() -> {
                if (roster.size() < 2) {
                    Toast.makeText(this, "Need 2 crew members to start a mission!", Toast.LENGTH_LONG).show();
                    finish();
                    return;
                }

                playerOne = rebuildFighter(roster.get(0));
                playerTwo = rebuildFighter(roster.get(1));
                selectedPlayer = playerOne;
                setupSelectionLogic();
                updateAllCards();

                btnAttack.setOnClickListener(v -> performCombatRound());
            });
        });
    }

    private void setupSelectionLogic() {
        cardPlayerOne.setOnClickListener(v -> {
            selectedPlayer = playerOne;
            txtTurnInfo.setText("Ready to attack: " + playerOne.getName());
            cardPlayerOne.setBackgroundColor(Color.parseColor("#4000D4FF")); // Highlight
            cardPlayerTwo.setBackgroundColor(Color.TRANSPARENT);
        });

        cardPlayerTwo.setOnClickListener(v -> {
            selectedPlayer = playerTwo;
            txtTurnInfo.setText("Ready to attack: " + playerTwo.getName());
            cardPlayerTwo.setBackgroundColor(Color.parseColor("#4000D4FF")); // Highlight
            cardPlayerOne.setBackgroundColor(Color.TRANSPARENT);
        });

        cardPlayerOne.performClick();
    }

    private void performCombatRound() {
        if (selectedPlayer.getEnergy() <= 0) {
            Toast.makeText(this, selectedPlayer.getName() + " is too exhausted to move!", Toast.LENGTH_SHORT).show();
            return;
        }

        String log = selectedPlayer.actOther(activeAlien);
        Toast.makeText(this, log, Toast.LENGTH_SHORT).show();


        if (activeAlien.getEnergy() > 0) {

            String alienLog = activeAlien.actOther(selectedPlayer);
            new Handler(Looper.getMainLooper()).postDelayed(() ->
                    Toast.makeText(this, activeAlien.getName() + ": " + alienLog, Toast.LENGTH_SHORT).show(), 1000);
        }

        Executors.newSingleThreadExecutor().execute(() -> {
            AppDatabase.getInstance(this).baseCrewMemberDao().update(playerOne);
            AppDatabase.getInstance(this).baseCrewMemberDao().update(playerTwo);
        });

        updateAllCards();
        checkGameStatus();
    }

    private void checkGameStatus() {
        if (activeAlien.getEnergy() <= 0) {
            txtTurnInfo.setText("VICTORYYYYYYYYYYYYYYYYYYY");
            btnAttack.setText("RETURN TO QURTERS");
            btnAttack.setOnClickListener(v -> finish());
        } else if (playerOne.getEnergy() <= 0 && playerTwo.getEnergy() <= 0) {
            txtTurnInfo.setText("MISSION FAILED: Your squad is unconscious.");
            btnAttack.setText("RETREAT TO BASE");
            btnAttack.setBackgroundColor(Color.GRAY);

            btnAttack.setOnClickListener(v -> finish());
        }
    }

    private BaseEnemyMember getRandomAlien() {
        Random rand = new Random();
        int type = rand.nextInt(3);
        int skill = 8 + rand.nextInt(7);

        switch (type) {
            case 0: return new AlienBomb(skill, 0.5f);
            case 1: return new AlienFaithKiller(skill, 0.2f);
            default: return new AlienSoldier(skill, 0.6f);
        }
    }
    private void updateAllCards() {
        updateSingleCard(cardPlayerOne, playerOne);
        updateSingleCard(cardPlayerTwo, playerTwo);
        TextView txtName = cardEnemy.findViewById(R.id.txtName);
        TextView txtEnergy = cardEnemy.findViewById(R.id.txtEnergy);
        TextView txtSkill = cardEnemy.findViewById(R.id.txtSkill);
        ImageView imgEnemy = cardEnemy.findViewById(R.id.imgCharacter);
        txtName.setText(activeAlien.getName());
        txtEnergy.setText("HP: " + activeAlien.getEnergy());
        txtSkill.setText("ATK: " + activeAlien.getSkill());

        if (activeAlien instanceof AlienBomb) {
            imgEnemy.setImageResource(R.drawable.alien);
        } else if (activeAlien instanceof AlienSoldier) {
            imgEnemy.setImageResource(R.drawable.alien);
        } else {
            imgEnemy.setImageResource(R.drawable.alien);
        }

        cardEnemy.findViewById(R.id.txtSpecialization).setVisibility(View.GONE);
        cardEnemy.findViewById(R.id.btn_feed_tortilla).setVisibility(View.GONE);
    }

    private void updateSingleCard(View card, BaseCrewMember member) {
        ((TextView) card.findViewById(R.id.txtName)).setText(member.getName());
        ((TextView) card.findViewById(R.id.txtSpecialization)).setText(member.getSpecialization());
        ((TextView) card.findViewById(R.id.txtEnergy)).setText("Energy: " + member.getEnergy());
        ((TextView) card.findViewById(R.id.txtSkill)).setText("Skill: " + member.getSkill());
        card.findViewById(R.id.btn_feed_tortilla).setVisibility(View.GONE); // Hide training button in mission
    }

    private BaseCrewMember rebuildFighter(BaseCrewMember raw) {
        switch (raw.getSpecialization()) {
            case "Pilot": return new Pilot(raw.maxShield, raw.name, raw.skill, raw.resilience, raw.experience, raw.energy, raw.maxEnergy, raw.trainingSession);
            case "Engineer": return new Engineer(raw.maxShield, raw.name, raw.skill, raw.resilience, raw.experience, raw.energy, raw.maxEnergy, raw.trainingSession);
            case "Soldier": return new Soldier(raw.maxShield, raw.name, raw.skill, raw.resilience, raw.experience, raw.energy, raw.maxEnergy, raw.trainingSession);
            case "Medic": return new Medic(raw.maxShield, raw.name, raw.skill, raw.resilience, raw.experience, raw.energy, raw.maxEnergy, raw.trainingSession);
            default: return new Scientist(raw.maxShield, raw.name, raw.skill, raw.resilience, raw.experience, raw.energy, raw.maxEnergy, raw.trainingSession);
        }
    }
}