package com.example.speedrunpracticeprogram;

public class Trick {
    private String trickName;
    public int curStreak;
    private int timesAttempted;

    public Trick(String trickName, int timesAttempted, int curStreak) {
        this.trickName = trickName;
        this.curStreak = curStreak;
        this.timesAttempted = timesAttempted;
    }

    public Trick(String trickName) {
        this.trickName = trickName;
        this.curStreak = 0;
    }
    public Trick(String trickName, int timesAttempted) {
        this.trickName = trickName;
        this.timesAttempted = timesAttempted;
        this.curStreak = 0;
    }

    public String getTrickName() {
        return trickName;
    }

    public void setTrickName(String trickName) {
        this.trickName = trickName;
    }

    public int getCurStreak() {
        return curStreak;
    }

    public void setCurStreak(int curStreak) {
        this.curStreak = curStreak;
    }

    public int getTimesAttempted() {
        return timesAttempted;
    }

    public void setTimesAttempted(int timesAttempted) {
        this.timesAttempted = timesAttempted;
    }
    public String toString(){
        return "" + trickName + ": " + "Attempted: " + timesAttempted;
    }
}
