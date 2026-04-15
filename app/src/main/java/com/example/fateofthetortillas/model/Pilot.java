package com.example.fateofthetortillas.model;
import java.util.UUID;
public class Pilot extends BaseCrewMember{
    public BaseCrewMember member;
    public BaseEnemyMember enemy;
    public Pilot(String name){
        this(3, name, 1, 3, 0, 5, 5, false);
    }
    public Pilot(int maxShield,String name, int skill,  int resilience,  int experience, int energy, int maxEnergy, Boolean trainingSession){
       super(maxShield, name, skill, "Pilot", resilience,  experience, energy, maxEnergy, trainingSession);

    }

    @Override
    public String act(BaseCrewMember member) {
        this.addExperience(1);
        if (this.experience >= 2) {
            this.addSkill(1);
            this.experience = 0;}
        member.addShield((int)(0.5*this.skill));
        return "we have evaded the enemy, but we are not on the clear";

    }
    @Override
    public String actOther(BaseEnemyMember enemy){
        enemy.damage((int)(0.5*this.skill));
        return "An attack from the tall skies";
    }
}
