package com.example.fateofthetortillas.model;

public class Engineer  implements CrewEntity{
    public int id;
    public String name;
    public String specialization;
    public int skill;
    public int resilience;
    public int victories;
    public int experience;
    public int energy;
    public int maxEnergy;
    public int faith;
    public int missionsCompleted;
    public int trainingSession;

    public Engineer(int id, String name, String specializatioString, String skill,
                 int resilience, int victories, int experience, int energy, int maxEnergy, int faith, int missionsCompleted, int trainingSession){
        this.id = id;
        this.name = name;
        this.specialization = specializatioString;
        this.skill = Integer.parseInt(skill);
        this.resilience = resilience;
        this.victories = victories;
        this.experience = experience;
        this.energy = energy;
        this.maxEnergy = maxEnergy;
        this.faith = faith;
        this.missionsCompleted = missionsCompleted;
        this.trainingSession = trainingSession;
    }
    @Override
    public String getEntityType() {
        return "Engineer";
    }
    @Override
    public int act(String actionName){

        return 1;
    }
}
