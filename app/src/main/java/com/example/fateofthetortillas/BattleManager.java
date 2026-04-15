package com.example.fateofthetortillas;

import com.example.fateofthetortillas.model.BaseCrewMember;
import com.example.fateofthetortillas.model.BaseEnemyMember;
import com.example.fateofthetortillas.model.Crew;
import com.example.fateofthetortillas.model.CrewOfEnemies;

import java.util.List;
import java.util.Random;
public


public class BattleManager {
    private Crew crew;
    private CrewOfEnemies crewOfEnemies;
    private boolean isPlayerTurn = true;
    private int turnCounter = 0;
    private Random random = new Random();

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
                    // Logic for dead enemies
                }
            }
            return result;
        }
    }


}
