package com.example.aleksandar.forrestfinder;

import android.graphics.drawable.Drawable;

/**
 * Created by Aleksandar on 18/02/2016.
 */
public class LevelStatistics {
    private String levelName;
    private int wrongAnswers;
    private int time;
    private Drawable levelThumbnail;

    public LevelStatistics(String levelName, int wrongAnswers, int time, Drawable levelThumbnail){
        this.levelName = levelName;
        this.wrongAnswers = wrongAnswers;
        this.time = time;
        this.levelThumbnail = levelThumbnail;
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

    public Drawable getLevelThumbnail() {
        return levelThumbnail;
    }

    public void setLevelThumbnail(Drawable levelThumbnail) {
        this.levelThumbnail = levelThumbnail;
    }
}
