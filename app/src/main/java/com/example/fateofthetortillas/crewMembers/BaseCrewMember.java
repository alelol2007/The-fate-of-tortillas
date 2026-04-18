package com.example.fateofthetortillas.crewMembers;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.example.fateofthetortillas.alien.BaseEnemyMember;

@Entity(tableName = "crew_table")
public class BaseCrewMember {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String name;
    public int skill;
    public String specialization;
    public int energy;
    public int maxEnergy;
    public int maxShield;
    public int resilience;
    public int experience;
    public Boolean alive;
    public Boolean trainingSession;

    public BaseCrewMember() {
        this.alive = true;
    }

    public BaseCrewMember(int maxShield, String name, int skill, String specialization, int resilience, int experience, int energy, int maxEnergy, Boolean trainingSession) {
        this.maxShield = maxShield;
        this.name = name;
        this.skill = skill;
        this.specialization = specialization;
        this.resilience = resilience;
        this.experience = experience;
        this.energy = energy;
        this.maxEnergy = maxEnergy;
        this.trainingSession = trainingSession;
        this.alive = true;
    }

    public BaseCrewMember(int id, String name, int skill, String specialization, int energy) {
        this.id = id;
        this.name = name;
        this.skill = skill;
        this.specialization = specialization;
        this.energy = energy;
        this.maxEnergy = energy;
        this.alive = true;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSkill() {
        return skill;
    }

    public void setSkill(int skill) {
        this.skill = skill;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public int getMaxEnergy() {
        return maxEnergy;
    }

    public void setMaxEnergy(int maxEnergy) {
        this.maxEnergy = maxEnergy;
    }

    public int getMaxShield() {
        return maxShield;
    }

    public void setMaxShield(int maxShield) {
        this.maxShield = maxShield;
    }

    public int getResilience() {
        return resilience;
    }

    public void setResilience(int resilience) {
        this.resilience = resilience;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public Boolean getAlive() {
        return alive;
    }

    public void setAlive(Boolean alive) {
        this.alive = alive;
    }

    public Boolean getTrainingSession() {
        return trainingSession;
    }

    public void setTrainingSession(Boolean trainingSession) {
        this.trainingSession = trainingSession;
    }

    public void damage(int damage) {
        int damage2 = damage;
        damage2 = damage2 - this.resilience;
        if (damage2 < 0) {
            damage2 = 0;
        }
        this.resilience -= damage;
        this.energy -= damage2;
        if (this.energy <= 0) {
            this.energy = 0;
            this.alive = false;
        }
    }

    public void heal(int heal) {
        if (this.energy + heal > this.maxEnergy) {
            this.energy = this.maxEnergy;
        } else {
            this.energy += heal;
        }
    }

    public void addShield(int shield) {
        if (this.resilience + shield > this.maxShield) {
            this.resilience = this.maxShield;
        } else {
            this.resilience += shield;
        }
    }

    public void addSkill(int skill) {
        this.skill += skill;
    }

    public void addExperience(int experience) {
        this.experience += experience;
    }

    public void addExperienceSkill(int exp) {
        this.experience += exp;
    }

    public String act(BaseCrewMember member) {
        return "";
    }

    public String actOther(BaseEnemyMember enemy) {
        return "";
    }

    public void consume() {
    }
}
