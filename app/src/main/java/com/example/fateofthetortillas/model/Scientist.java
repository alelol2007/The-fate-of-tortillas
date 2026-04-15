package com.example.fateofthetortillas.model;

public class Scientist extends BaseCrewMember{
    public BaseCrewMember member;
    public Scientist(String name){
        this(3, name, 1, 3, 0, 2, 2, false);
    }
    public Scientist(int maxShield,String name, int skill,  int resilience,  int experience, int energy, int maxEnergy, Boolean trainingSession){
        super(maxShield, name, skill, "Scientist", resilience,  experience, energy, maxEnergy, trainingSession);

    }

    @Override
    public String act(BaseCrewMember member) {
        member.addSkill(1);
        addExperienceSkill(1);
        return"I'm proportioning the power of science and life, so now evange us allllll, go for it my child";
    }
    public String actOther(BaseEnemyMember enemy){

        if (enemy.skill >=1) return "dammit I cannot reserch such a dumb creature";
        addExperienceSkill(1);
        enemy.skill -=1;
        return  "research succesful, I have found that its intelligence can be derivated";
    }

}
