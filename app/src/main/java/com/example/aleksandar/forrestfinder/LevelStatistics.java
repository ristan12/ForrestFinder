package com.example.aleksandar.forrestfinder;

/**
 * Created by Aleksandar on 18/02/2016.
 */
public class LevelStatistics {
    private String levelName;
    private int wrongAnswers;
    private int time;

    public LevelStatistics(String levelName, int wrongAnswers, int time){
        this.levelName = levelName;
        this.wrongAnswers = wrongAnswers;
        this.time = time;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public int getWrongAnswers() {
        return wrongAnswers;
    }

    public void setWrongAnswers(int wrongAnswers) {
        this.wrongAnswers = wrongAnswers;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
