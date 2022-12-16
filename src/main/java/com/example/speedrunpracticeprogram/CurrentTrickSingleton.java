package com.example.speedrunpracticeprogram;

public class CurrentTrickSingleton {
    private Trick trick;
    private final static CurrentTrickSingleton INSTANCE = new CurrentTrickSingleton();
    private CurrentTrickSingleton() {}
    public static CurrentTrickSingleton getInstance(){
        return INSTANCE;
    }
    public void setTrickName(String name){
        this.trick.trickName = name;
    }
    public Trick getTrick(){
        return this.trick;
    }
}
