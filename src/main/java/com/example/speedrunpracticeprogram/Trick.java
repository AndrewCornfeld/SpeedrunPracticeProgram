package com.example.speedrunpracticeprogram;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Trick {
    private SimpleStringProperty trickName;
    public SimpleIntegerProperty curStreak;
    private SimpleIntegerProperty timesAttempted;

    public Trick(String trickName, int timesAttempted, int curStreak) {
        this.trickName = new SimpleStringProperty(trickName);
        this.curStreak = new SimpleIntegerProperty(curStreak);
        this.timesAttempted = new SimpleIntegerProperty(timesAttempted);
    }

    public Trick(String trickName) {
        this.trickName = new SimpleStringProperty(trickName);
        this.curStreak = new SimpleIntegerProperty(0);
    }
    public Trick(String trickName, int timesAttempted) {
        this.trickName = new SimpleStringProperty(trickName);
        this.curStreak = new SimpleIntegerProperty(0);
        this.timesAttempted = new SimpleIntegerProperty(timesAttempted);
    }

    public String getTrickName() {
        return trickName.get();
    }

    public void setTrickName(String trickName) {
        this.trickName = new SimpleStringProperty(trickName);
    }

    public int getCurStreak() {
        return curStreak.get();
    }

    public void setCurStreak(int curStreak) {
        this.curStreak = new SimpleIntegerProperty(curStreak);
    }

    public int getTimesAttempted() {
        return timesAttempted.get();
    }

    public void setTimesAttempted(int timesAttempted) {
        this.timesAttempted = new SimpleIntegerProperty(timesAttempted);
    }
    public String toString(){
        return "" + trickName + ": " + "Attempted: " + timesAttempted;
    }
}
