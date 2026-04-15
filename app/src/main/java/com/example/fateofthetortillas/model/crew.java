package com.example.fateofthetortillas.model;

public class crew {
    public int faith;
    public int victories;
    public int missionsCompleted;
    public crew(int faith, int victories, int missionsCompleted) {
        this.faith = faith;
        this.victories = victories;
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
