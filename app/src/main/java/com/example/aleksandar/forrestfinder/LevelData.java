package com.example.aleksandar.forrestfinder;

import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.media.Image;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by Aleksandar on 18/02/2016.
 */

public class LevelData {
    public class animalCoordinates{
        Point tl, tr, bl, br;

        public Point getTl() {
            return tl;
        }

        public void setTl(Point tl) {
            this.tl = tl;
        }

        public Point getTr() {
            return tr;
        }

        public void setTr(Point tr) {
            this.tr = tr;
        }

        public Point getBl() {
            return bl;
        }

        public void setBl(Point bl) {
            this.bl = bl;
        }

        public Point getBr() {
            return br;
        }

        public void setBr(Point br) {
            this.br = br;
        }
    }

    private Vector<String> questions;
    private Vector<ArrayList<Point>> answerCoordinates;
    private String levelBackgroundName;
    private Drawable backgroundPic;
    private Drawable thumbnail;
    private int drawableId;
    private String levelName;

    public Drawable getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Drawable thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public Drawable getBackgroundPic() {
        return backgroundPic;
    }

    public void setBackgroundPic(Drawable backgroundPic) {
        this.backgroundPic = backgroundPic;
    }

    public LevelData(Vector<String> questions, Vector<ArrayList<Point>> answerCoordinates){
        this.questions = questions;
        this.answerCoordinates = answerCoordinates;
    }

    public LevelData(){
        this.questions = new Vector<String>();
        this.answerCoordinates = new Vector<ArrayList<Point>>();
    }

    public Vector<String> getQuestions() {
        return questions;
    }

    public void setQuestions(Vector<String> questions) {
        this.questions = questions;
    }

    public Vector<ArrayList<Point>> getAnswerCoordinates() {
        return answerCoordinates;
    }

    public void setAnswerCoordinates(Vector<ArrayList<Point>> answerCoordinates) {
        this.answerCoordinates = answerCoordinates;
    }

    public String getLevelBackgroundName() {
        return levelBackgroundName;
    }

    public void setLevelBackgroundName(String levelBackgroundName) {
        this.levelBackgroundName = levelBackgroundName;
    }

    public int getDrawableId() {
        return drawableId;
    }

    public void setDrawableId(int drawableId) {
        this.drawableId = drawableId;
    }
}
