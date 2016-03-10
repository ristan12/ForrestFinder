package com.example.aleksandar.forrestfinder;

import android.app.Application;

import java.util.Vector;

/**
 * Created by Aleksandar on 18/02/2016.
 */
public class GameData extends Application{
    private static final int gameX = 100;
    private static final int gameY = 56;

    private static float factorX;
    private static float factorY;

    private static final int defaultLevelNumber = 3;
    private static int addedLevelNumber;
    private static Vector<LevelData> defaultLevelData;
    private static Vector<LevelData> addedLevelData;
    private static Vector<LevelStatistics> levelStatistics;

    public GameData(){
    }

    public static float getFactorX() {
        return factorX;
    }

    public static float getFactorY() {
        return factorY;
    }

    public static void setFactor(float factorX, float factorY) {
        float resRatio = factorX/factorY;
        GameData.factorX = factorX/gameX;
        GameData.factorY = factorY/gameY;
    }

    public static int getDefaultLevelNumber() {
        return defaultLevelNumber;
    }

    public static int getAddedLevelNumber() {
        return addedLevelNumber;
    }

    public static void setAddedLevelNumber(int addedLevelNumber) {
        GameData.addedLevelNumber = addedLevelNumber;
    }

    public static Vector<LevelData> getDefaultLevelData() {
        return defaultLevelData;
    }

    public static void setDefaultLevelData(Vector<LevelData> levelDatas) {
        GameData.defaultLevelData = levelDatas;
    }

    public static Vector<LevelStatistics> getLevelStatistics() {
        return levelStatistics;
    }

    public static void setLevelStatistics(Vector<LevelStatistics> levelStatistics) {
        GameData.levelStatistics = levelStatistics;
    }

    public static Vector<LevelData> getAddedLevelData() {
        return addedLevelData;
    }

    public static void setAddedLevelData(Vector<LevelData> addedLevelDatas) {
        GameData.addedLevelData = addedLevelDatas;
    }
}
