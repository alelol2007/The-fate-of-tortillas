package com.example.fateofthetortillas.crewMembers;
import java.util.UUID;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.fateofthetortillas.alien.BaseEnemyMember;

@Entity(tableName = "crew_members")
public abstract class BaseCrewMember {
    @PrimaryKey
    public String id;

    public String name;
    public String specialization;
    public int skill;
    public int resilience;
    public int experience;
    public int energy;
    public int maxEnergy;
    public Boolean trainingSession;
    public Boolean alive;
    public int maxShield;
    public BaseCrewMember(int maxShield, String name, int skill, String specialization, int resilience,  int experience, int energy, int maxEnergy, Boolean trainingSession){
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.specialization = specialization;
        this.skill = skill;
        this.resilience = resilience;
        this.experience = experience;
        this.energy = energy;
        this.maxEnergy = maxEnergy;
        this.trainingSession = trainingSession;
        this.alive = Boolean.TRUE;
        this.maxShield = maxShield;
    }

    public String getName(){
        return this.name;
    }
    public String getId(){
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

    public int getExperience(){
        return this.experience;
    }
    public Boolean getAlive(){
        return this.alive;
    }
    public String getSpecialization(){
        return this.specialization;
    }

    public void damage(int damage){
        int damage2 = damage;
        damage2 = damage2 - this.resilience;
        if (damage2 < 0){
            damage2 = 0;
        }
        this.resilience -= damage;
        this.energy -= damage2;
        if (this.energy <= 0){
            this.alive = Boolean.FALSE;
        }
    }


    public void addExperience(int experience){
        this.experience += experience;
    }

    public void addEnergy(int energy){
        this.energy += energy;
        if (this.energy > this.maxEnergy){
            this.energy = this.maxEnergy;
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
    public void addShield(int shield){
        if (this.resilience + shield > this.maxShield){
            this.resilience = this.maxShield;
        }
        this.maxShield += shield;
    }
    public void addSkill(int skill){
        this.skill += skill;
    }
    public void addExperienceSkill(int experience) {
        if (this.experience >= 2) {
            this.addSkill(1);
            this.experience = 0;
        }
        this.addExperience(experience);
    }

// the action works by having the member who commits it get points, and then the target gets affected, this can range from affecting the aliens to many other things
    public abstract String act(BaseCrewMember target);
    public abstract String actOther(BaseEnemyMember enemy);


    public int getSkill() {
        return this.skill;
    }
}
