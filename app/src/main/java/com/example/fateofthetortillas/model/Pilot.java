package com.example.fateofthetortillas.model;
import java.util.UUID;
public class Pilot extends BaseCrewMember{
    public BaseCrewMember member;
    public Pilot(String name){
        this(3, name, 1, 3, 0, 5, 5, false);
    }
    public Pilot(int maxShield,String name, int skill,  int resilience,  int experience, int energy, int maxEnergy, Boolean trainingSession){
       super(maxShield, name, skill, "Pilot", resilience,  experience, energy, maxEnergy, trainingSession);

    }

    @Override
    public void act(BaseCrewMember member) {
        System.out.println("ahhhhhhhhhhhhhhhhhhhhhhhhhhhhhh");
    }
}
