package com.example.fateofthetortillas.gameManager;

import com.example.fateofthetortillas.crewMembers.BaseCrewMember;
import com.example.fateofthetortillas.alien.BaseEnemyMember;
import com.example.fateofthetortillas.crewMembers.Crew;
import com.example.fateofthetortillas.alien.CrewOfEnemies;

import java.util.Random;

public class BattleManager {
    private Crew crew;
    private CrewOfEnemies crewOfEnemies;
    public boolean isPlayerTurn = true;
    private int turnCounter = 0;
    private Random random = new Random();
    public boolean isPlayerTurn() {
        return isPlayerTurn;    }

    public BattleManager(Crew crew, CrewOfEnemies crewOfEnemies) {
        this.crew =  crew;
        this.crewOfEnemies = crewOfEnemies;
    }
    public String nextTurn(int actionType, BaseCrewMember memberTarget, BaseEnemyMember enemyTarget){
        if (crew.getFaith()<=0){
            return "GAME OVER: your crew has dieeddddddddd, this is the end of the game";
        }
        if (isPlayerTurn){
            BaseCrewMember activeMember = crew.crew.get(turnCounter);

            String result;
            if (actionType==1){
                result = activeMember.act(memberTarget);
            } else {
                result = activeMember.actOther(enemyTarget);
            }
            turnCounter++;
            if (turnCounter >= crew.crew.size()) {
                turnCounter = 0;
                isPlayerTurn = false;
            }
            for (BaseCrewMember memeber : crew.crew){
                if (!memeber.alive){
                    memeber.addShield(1);
                }
            }
            return result;
        }

        //this is of vanessa, she will write the logic of the aliens\\
        else {
            BaseEnemyMember activeAlien = crewOfEnemies.enemyMembers.get(turnCounter);
            String result;
            if (actionType == 1){
                result = activeAlien.act(enemyTarget);
            } else {
                result = activeAlien.actOther(memberTarget);
            }
            turnCounter++;
            if (turnCounter >= crewOfEnemies.enemyMembers.size()){
                turnCounter = 0;
                isPlayerTurn = true;
            }
            for (BaseEnemyMember enemy : crewOfEnemies.enemyMembers){
                if (!enemy.alive){
                    // This is a placeholder for Vanessa
                    System.out.println("DEBUG: " + enemy.name + " is dead. No tacos for them.");
                }
            }
            return result;
        }
    }


}
