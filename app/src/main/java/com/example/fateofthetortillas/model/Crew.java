package com.example.fateofthetortillas.model;
import java.util.List;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "crew")
public class Crew {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public int victories;
    public int missionsCompleted;
    public List<BaseCrewMember> crew;
    public int faith;
    public Boolean alive;

    public Crew() {}
    @Ignore
    public Crew( int faith, int victories, int missionsCompleted, List<BaseCrewMember> crew) {
        this.faith = faith;
        this.victories = victories;
        this.crew = crew;
        this.missionsCompleted = missionsCompleted;
        this.alive = Boolean.TRUE;
    }
    public List<BaseCrewMember> getCrew() {
        return crew;
    }

    public int getVictories() {
        return victories;
    }
    public int getMissionsCompleted() {
        return missionsCompleted;
    }

    public void addVictories(int victories) {
        this.victories += victories;
    }
    public void addMissionsCompleted(int missionsCompleted) {
        this.missionsCompleted += missionsCompleted;
    }
    public void looseFaith(int faith){
        this.faith -= faith;
    }
    public void gainFaith(int faith){
        this.faith += faith;
    }
    public int getFaith() {
        return faith;
    }


}
