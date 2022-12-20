package com.example.speedrunpracticeprogram;

public class Trick {
    public String trickName;
    public int curStreak;
    public int timesAttempted;

    public Trick(String trickName) {
        this.trickName = trickName;
        this.curStreak = 0;
    }
    public Trick(String trickName, int timesAttempted) {
        this.trickName = trickName;
        this.timesAttempted = timesAttempted;
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
}
