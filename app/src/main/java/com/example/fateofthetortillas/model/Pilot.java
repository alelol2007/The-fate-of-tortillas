package com.example.fateofthetortillas.model;

public class Pilot implements CrewEntity{
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
    public Boolean trainingSession;
    public Boolean alive;


    public Pilot(int id, String name, String specializatioString, int skill,
                 int resilience, int victories, int experience, int energy, int maxEnergy, int faith, int missionsCompleted, Boolean trainingSession){
        this.id = id;
        this.name = name;
        this.specialization = specializatioString;
        this.skill = skill;
        this.resilience = resilience;
        this.victories = victories;
        this.experience = experience;
        this.energy = energy;
        this.maxEnergy = maxEnergy;
        this.faith = faith;
        this.missionsCompleted = missionsCompleted;
        this.trainingSession = trainingSession;
        this.alive = Boolean.TRUE;
    }
    @Override
    public String getEntityType() {
        return "Pilot";
    }
    public int getId(){
        return this.id;
    }
    public int getEnergy(){
        return this.energy;
    }
    public int getMaxEnergy(){
        return this.maxEnergy;
    }
    public int getResilience(){
        return this.resilience;
    }
    public int getVictories(){
        return this.victories;
    }
    public int getExperience(){
        return this.experience;
    }

    public void damage(int damage){
        int damage2 = damage;
        this.resilience -= damage;
        this.energy -= damage2;
        if (this.energy <= 0){
            this.alive = Boolean.FALSE;
        }
    }

    public void heal(int heal) {
        if (this.energy +heal > this.maxEnergy){
            this.energy = this.maxEnergy;
        }
        else{
            this.energy += heal;
        }
    }

    public void addExperience(int experience){
        this.experience += experience;
    }
    public void addVictories(int victories){
        this.victories += victories;
    }
    public void addMissionsCompleted(int missionsCompleted){
        this.missionsCompleted += missionsCompleted;
    }
    public void switchTrainingSession(int trainingSession){
        if (trainingSession == 1){
            this.trainingSession = Boolean.TRUE;
        }
        else{
            this.trainingSession = Boolean.FALSE;
        }
    }
    public void looseFaith(int faith){
        this.faith -= faith;
    }
    public void gainSkill(int skill){
        this.skill += skill;
    }



    @Override
    public int act(String actionName){
        if (actionName.equals("damaged")){

        }
    }
    @Override
    public int actUpon(String actionName){
        return 1;
    }
    @Override
    public void defend(int damage){
    }

}
