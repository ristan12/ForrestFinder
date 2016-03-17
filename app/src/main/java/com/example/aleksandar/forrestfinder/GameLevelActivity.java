package com.example.aleksandar.forrestfinder;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.*;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.*;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Iterator;

public class GameLevelActivity extends AppCompatActivity {

    static int questionCounter;

    long oldTime;
    long time;
    int wrongAns;
    LevelData levelData;
    boolean isGameOn;
    GameData gameData = (GameData) getApplication();

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_level);


        int indeks = 0;

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            indeks = extras.getInt("id");
        }

        final GameData gameData = (GameData) getApplication();
        levelData = gameData.getDefaultLevelData().elementAt(indeks);

        ImageView pozadina = (ImageView) findViewById(R.id.pozadina);
        pozadina.setBackground(levelData.getBackgroundPic());

        /*
        pozadina.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float a = 0;
                float b = 0;
                if (event.getAction()==MotionEvent.ACTION_DOWN){
                    a = Math.round(event.getX()/gameData.getFactorX());
                    b = Math.round(event.getY() / gameData.getFactorY());

                    String coord = "Kordinate su X = " + a + " a Y = " + b + " factorX = " + gameData.getFactorX() + " factorY = " + gameData.getFactorY();
                    Toast toast = Toast.makeText(getApplicationContext(), coord, Toast.LENGTH_SHORT);

                    toast.show();
                    Log.d("srdja", coord);
                }


                return true;
            }


        });*/


        isGameOn = false;
        //test dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.dialog_layout, null))
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        questionCounter = 0;
                        wrongAns = 0;
                        oldTime = SystemClock.elapsedRealtime();
                        playLevel();//levelData);
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void playLevel(){//final LevelData levelData){
        isGameOn = true;

        if (questionCounter < levelData.getQuestions().size()) {
            dialogCreater(levelData.getQuestions().elementAt(questionCounter));//, levelData);
            //questionCounter++;
        }
        else{
            time = SystemClock.elapsedRealtime() - oldTime;
            dialogLevelInfo();
        }

    }

    private void dialogCreater(String tekst){//, final LevelData levelData){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.dialog_layout, null))
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (isGameOn) waitForAnswer();//levelData);
                        //dialog.dismiss();
                    }
                });


        AlertDialog dialog = builder.create();
        dialog.show();
        TextView question = (TextView) dialog.findViewById(R.id.dialog_question);
        question.setText(tekst);
    }

    private void dialogLevelInfo(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.dialog_layout, null))
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //if (isGameOn) waitForAnswer(leveldata);
                        Intent intent = new Intent(getApplicationContext(), LevelsActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });


        AlertDialog dialog = builder.create();
        dialog.show();
        TextView question = (TextView) dialog.findViewById(R.id.dialog_question);
        String tekst = "Kraj igre!\n" + "Vreme: " + Math.round(time/1000) + " sekundi\n" + "Netacni: "+ wrongAns;
        question.setText(tekst);

    }

    private void waitForAnswer(){//final LevelData levelData){

        ImageView pozadina = (ImageView) findViewById(R.id.pozadina);
        pozadina.setBackground(levelData.getBackgroundPic());


        pozadina.setOnTouchListener(new View.OnTouchListener() {

            int touchNum = 0;
            public boolean onTouch(View v, MotionEvent event) {
                if (touchNum == 0) {
                    touchNum++;
                    int a = 0;
                    int b = 0;
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        a = Math.round(event.getX() / gameData.getFactorX());
                        b = Math.round(event.getY() / gameData.getFactorY());

                        boolean check = checkAnswer(a, b);//, levelData);
                        Toast toast;

                        String coord = "Kordinate su X = " + a + " a Y = " + b;

                        final ImageView answerMark = (ImageView) findViewById(R.id.answerMark);
                        Animation animation = new ScaleAnimation(0, 1, 0, 1);
                        animation.setDuration(1000);
                        animation.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                answerMark.setImageResource(0);
                                playLevel();
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });

                        if (check) {

                            questionCounter++;

                            MediaPlayer player = MediaPlayer.create(getApplicationContext(), R.raw.correct);
                            player.start();

                            answerMark.setImageResource(R.drawable.checkmark);
                            answerMark.startAnimation(animation);


                        } else {

                            wrongAns++;

                            MediaPlayer player = MediaPlayer.create(getApplicationContext(), R.raw.wrong);
                            player.start();

                            answerMark.setImageResource(R.drawable.xmark);
                            answerMark.startAnimation(animation);

                        }
                    }
                }
                return true;
            }
        });


    }

    private boolean checkAnswer(int a, int b){//, LevelData levelData){
        ArrayList<Point> koordinate = levelData.getAnswerCoordinates().elementAt(questionCounter);
        Point upLeft = koordinate.get(0);
        Point upRight = koordinate.get(1);
        Point downLeft = koordinate.get(2);
        Point downRight = koordinate.get(3);

        if ((a >= upLeft.x && a <= upRight.x) && (b >= upLeft.y && b <= downLeft.y))
            return true;

        return false;
    }
}


