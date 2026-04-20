package com.example.fateofthetortillas.alien;
import com.example.fateofthetortillas.crewMembers.BaseCrewMember;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;
public abstract class BaseEnemyMember {
    public String id;
    public String name;
    public int type;
    public int skill;
    public int energy;
    public int maxEnergy;
    public Boolean alive;
    public int maxShield;

    public int resilience;
    public Float possibility;
    private static final List<String> funnyNames = new ArrayList<>(Arrays.asList(
            "Marsha Mellow",
            "Al O'Vera",
            "Kerry Oki",
            "Willie Makeit",
            "Justin Time",
            "Sal Monella",
            "Artie Choke",
            "Walter Melon",
            "Biff Wellington",
            "Chris P. Bacon",
            "Russell Sprout",
            "Patty O'Furniture",
            "Anita Resume",
            "Dan Druff",
            "Ella Funt",
            "Barb Dwyer",
            "Rick O'Shea",
            "Misty Meanor",
            "Amanda Lynn",
            "Shirley U. Jest"
    ));

    public BaseEnemyMember(int type, int skill, int energy, int maxEnergy, Boolean alive, int maxShield, int resilience, Float possibility){
        this(null, type, skill, energy, maxEnergy, alive, maxShield, resilience, possibility);
    }

    public BaseEnemyMember(String name, int type, int skill, int energy, int maxEnergy, Boolean alive, int maxShield, int resilience, Float possibility){
        this.id = UUID.randomUUID().toString();
        if (name == null || name.isEmpty()) {
            int randomIdx = new Random().nextInt(funnyNames.size());
            this.name = funnyNames.get(randomIdx);
        } else {
            this.name = name;
        }
        this.type = type;
        this.skill = skill;
        this.energy = energy;
        this.maxEnergy = maxEnergy;
        this.alive = alive;
        this.maxShield = maxShield;
        this.resilience = resilience;
        this.possibility = possibility;
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
    public void setEnergy(int energy){
        this.energy = energy;
    }
    public int getMaxEnergy(){
        return this.maxEnergy;
    }
    public int getResilience(){
        return this.resilience;
    }
    public int getSkill(){
        return this.skill;
    }
    public Boolean getAlive(){
        return this.alive;
    }
    public Float getPossibility(){
        return this.possibility;
    }
    public int getType(){
        return this.type;
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
    public void addShield(int shield){
        if (this.resilience + shield > this.maxShield){
            this.resilience = this.maxShield;
        }
        this.maxShield += shield;
    }
    public void addSkill(int skill){
        this.skill += skill;
    }
    public void heal(int heal) {
        if (this.energy +heal > this.maxEnergy){
            this.energy = this.maxEnergy;
        }
        else{
            this.energy += heal;
        }
    }
    public abstract String act(BaseEnemyMember enemy);
    public abstract String actOther(BaseCrewMember crew);

}
