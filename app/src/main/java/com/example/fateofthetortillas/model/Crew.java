package com.example.fateofthetortillas.model;
import java.util.List;
import androidx.room.Entity;
@Entity(tableName = "crew")
public class Crew {
    public int faith;
    public int victories;
    public int missionsCompleted;
    public List<BaseCrewMember> crew;

    public Crew(int faith, int victories, int missionsCompleted, List<BaseCrewMember> crew) {
        this.faith = faith;
        this.victories = victories;
        this.crew = crew;
        this.missionsCompleted = missionsCompleted;
    }
    public List<BaseCrewMember> getCrew() {
        return crew;
    }
    public int getFaith() {
        return faith;
    }
    public int getVictories() {
        return victories;
    }
    public int getMissionsCompleted() {
        return missionsCompleted;
    }
    public void addFaith(int faith) {
        this.faith += faith;
    }
    public void addVictories(int victories) {
        this.victories += victories;
    }
    public void addMissionsCompleted(int missionsCompleted) {
        this.missionsCompleted += missionsCompleted;
    }
    public void lessFaith(int faith) {
        this.faith -= faith;
    }

}
