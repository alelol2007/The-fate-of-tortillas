package com.example.fateofthetortillas.alien;

import java.util.List;

public class CrewOfEnemies {
    public List<BaseEnemyMember> enemyMembers;
    public boolean alive;

    public CrewOfEnemies(List<BaseEnemyMember> enemyMembers, boolean alive){
        this.enemyMembers =enemyMembers;
        this.alive = alive;
    }
}
