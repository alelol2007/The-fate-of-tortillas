package com.example.fateofthetortillas;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import com.example.fateofthetortillas.crewMembers.BaseCrewMember;
import com.example.fateofthetortillas.crewMembers.Crew;
import com.example.fateofthetortillas.crewMembers.Medic;
import com.example.fateofthetortillas.crewMembers.Soldier;
import com.example.fateofthetortillas.alien.BaseEnemyMember;
import com.example.fateofthetortillas.alien.CrewOfEnemies;
import com.example.fateofthetortillas.alien.AlienSoldier;
import com.example.fateofthetortillas.alien.AlienBomb;
import com.example.fateofthetortillas.alien.AlienFaithKiller;
import com.example.fateofthetortillas.gameManager.BattleManager;

import java.util.ArrayList;
import java.util.List;

public class test {

    private BattleManager battleManager;
    private Crew playerCrew;
    private CrewOfEnemies enemyCrew;

    @Before
    public void setUp() {
        System.out.println("\n[SETUP] Initializing Battlefield...");
        List<BaseCrewMember> members = new ArrayList<>();
        members.add(new Medic("Doc"));
        members.add(new Soldier("Viking"));

        playerCrew = new Crew(10, 0, 0, members);

        List<BaseEnemyMember> aliens = new ArrayList<>();
        aliens.add(new AlienSoldier(5, 0.5f));
        aliens.add(new AlienBomb(8, 0.1f));
        aliens.add(new AlienFaithKiller(10, 0.05f));

        enemyCrew = new CrewOfEnemies(aliens, true);
        battleManager = new BattleManager(playerCrew, enemyCrew);

        System.out.println("[SETUP] Player Crew: " + members.size() + " members.");
        System.out.println("[SETUP] Enemy Crew: " + aliens.size() + " aliens.");
        System.out.println("------------------------------------------");
    }

    @Test
    public void testFullTurnRotation() {
        System.out.println("\n>>> STARTING TURN ROTATION TEST <<<");

        // Turn 1: Medic acts
        System.out.println("[TURN 1] Medic is attacking...");
        String p1Result = battleManager.nextTurn(2, null, enemyCrew.enemyMembers.get(0));
        System.out.println("Result: " + p1Result);
        System.out.println("Is Player Turn? " + battleManager.isPlayerTurn());

        // Turn 2: Soldier acts
        System.out.println("\n[TURN 2] Soldier is attacking...");
        String p2Result = battleManager.nextTurn(2, null, enemyCrew.enemyMembers.get(0));
        System.out.println("Result: " + p2Result);
        System.out.println("Is Player Turn? " + battleManager.isPlayerTurn() + " (Should be FALSE now)");

        assertFalse("Should flip to Alien turn after crew acts", battleManager.isPlayerTurn());

        // Alien Phase
        System.out.println("\n>>> ENEMY PHASE STARTING <<<");
        for (int i = 0; i < enemyCrew.enemyMembers.size(); i++) {
            System.out.println("[ALIEN TURN " + (i + 1) + "] Alien acts...");
            String aResult = battleManager.nextTurn(2, playerCrew.crew.get(0), null);
            System.out.println("Result: " + aResult);
        }

        System.out.println("\n[PHASE CHECK] All aliens moved. Is Player Turn? " + battleManager.isPlayerTurn());
        assertTrue("Should flip back to Player turn", battleManager.isPlayerTurn());
    }

    @Test
    public void testFaithLossGameOver() {
        System.out.println("\n>>> TESTING GAME OVER CONDITION <<<");
        System.out.println("Current Faith: " + playerCrew.getFaith());

        System.out.println("Reducing faith to 0...");
        playerCrew.looseFaith(playerCrew.getFaith());

        String result = battleManager.nextTurn(2, null, null);
        System.out.println("BattleManager says: " + result);

        assertEquals("GAME OVER: your crew has dieeddddddddd, this is the end of the game", result);
    }

    @Test
    public void testAlienAbilities() {
        System.out.println("\n>>> TESTING ALIEN ABILITIES (BOMB) <<<");
        AlienBomb bomb = (AlienBomb) enemyCrew.enemyMembers.get(1);

        System.out.println("Triggering Alien Bomb Explosion...");
        String boom = bomb.explosion(playerCrew);
        System.out.println("Explosion Log: " + boom);
        System.out.println("Is Bomb still alive? " + bomb.alive);

        assertTrue("Log should contain BOOM", boom.contains("BOOM!"));
        assertFalse("Bomb should be dead after exploding", bomb.alive);
    }
}