package com.example.fateofthetortillas.model;

public class Soldier extends BaseCrewMember{
    public BaseCrewMember member;
    public Soldier(String name){
        this(3, name, 1, 3, 0, 5, 5, false);
    }
    public Soldier(int maxShield,String name, int skill,  int resilience,  int experience, int energy, int maxEnergy, Boolean trainingSession){
        super(maxShield, name, skill, "Soldier", resilience,  experience, energy, maxEnergy, trainingSession);

    }

    @Override
    public String act(BaseCrewMember member) {
        member.heal((int)(0.2*skill));
        return"I'm very very bad at this, hopefully next time I dont almost kill you";
    }
    public String actOther(BaseEnemyMember enemy){
        enemy.damage((int)(skill*2));
        if (enemy.getAlive()) return "MUAHAHAHAAHAH ME the great viking of the north oh I am pleased to kill my enemies"; return "next time I'll get you, I have marked you";
    }

}
