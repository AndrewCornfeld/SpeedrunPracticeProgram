package com.example.speedrunpracticeprogram;

public class AttemptEntry {
    public int allEntriesID;
    public boolean success;
    public int sessionID;
    public int streak;

    public AttemptEntry(int allEntriesID, boolean success, int sessionID, int streak) {
        this.allEntriesID = allEntriesID;
        this.success = success;
        this.sessionID = sessionID;
        this.streak = streak;
    }

    public int getAllEntriesID() {
        return allEntriesID;
    }

    public void setAllEntriesID(int allEntriesID) {
        this.allEntriesID = allEntriesID;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getSessionID() {
        return sessionID;
    }

    public void setSessionID(int sessionID) {
        this.sessionID = sessionID;
    }

    public int getStreak() {
        return streak;
    }

    public void setStreak(int streak) {
        this.streak = streak;
    }
}
