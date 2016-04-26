package com.example.aleksandar.forrestfinder;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.*;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.*;

import org.w3c.dom.Text;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Vector;


public class GameLevelActivity extends AppCompatActivity {

    final static int numberOfQuestions = 10;
    static int questionCounter;
    static int randomQuestion;

    int last;

    long oldTime;
    long time;
    int wrongAns;
    LevelData levelData;
    boolean isGameOn;
    GameData gameData = (GameData) getApplication();

    ImageView pozadina;
    ImageButton questionMark;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL, WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);

        setContentView(R.layout.activity_game_level);

        int indeks = 0;

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            indeks = extras.getInt("id");
        }

        levelData = gameData.getDefaultLevelData().elementAt(indeks);

        questionMark = (ImageButton) findViewById(R.id.questionmark);
        //questionMark.setVisibility(View.INVISIBLE);
        pozadina = (ImageView) findViewById(R.id.pozadina);
        pozadina.setBackground(levelData.getBackgroundPic());
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setMax(10);
        //progressBar.getIndeterminateDrawable().setColorFilter(Color.GREEN, android.graphics.PorterDuff.Mode.SRC_IN);
        //progressBar.getProgressDrawable().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
        //progressBar.setProgressBackgroundTintList();

        isGameOn = false;
        //test dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.dialog_layout, null))
                .setPositiveButton("Da!!!", new DialogInterface.OnClickListener() {
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

    private void playLevel(){
        isGameOn = true;

        if (questionCounter < numberOfQuestions){
            //random question
            Random rand = new Random();
            randomQuestion = rand.nextInt(levelData.getQuestions().size());

            while (randomQuestion == last)
            {
                rand = new Random();
                randomQuestion = rand.nextInt(levelData.getQuestions().size());
            }
            last = randomQuestion;
            dialogCreater(levelData.getQuestions().elementAt(randomQuestion));
            //questionCounter++;
        }
        else{
            time = SystemClock.elapsedRealtime() - oldTime;
            dialogLevelInfo();
        }
    }

    private void dialogCreater(final String tekst){//, final LevelData levelData){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyCustomTheme);

        LayoutInflater inflater = getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.dialog_layout, null))
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (isGameOn) {

                            questionMark.setVisibility(View.VISIBLE);
                            questionMark.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialogCreater(tekst);
                                }
                            });
                            waitForAnswer();
                        }
                        //dialog.dismiss();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        questionMark.setOnClickListener(null);
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
                        //Intent intent = new Intent(getApplicationContext(), LevelsActivity.class);
                        //startActivity(intent);
                        finish();
                    }
                });


        AlertDialog dialog = builder.create();
        dialog.show();
        TextView question = (TextView) dialog.findViewById(R.id.dialog_question);
        String tekst = "Kraj igre!\n" + "Vreme: " + Math.round(time/1000) + " sekundi\n" + "Netacni: "+ wrongAns;
        question.setText(tekst);

        //popunjavanje statistike
        FileOutputStream fs;
        StringBuilder sb = new StringBuilder();
        try {
            fs = openFileOutput("statisticsData.txt", Context.MODE_APPEND);

            sb.append(levelData.getLevelName());
            //fs.write(levelData.getLevelName().getBytes());
            sb.append(" " + wrongAns);
            //fs.write(("" + wrongAns).getBytes());
            sb.append(" " + Math.round(time/1000));
            //fs.write(("" + Math.round(time/100)).getBytes());
            sb.append(" " + levelData.getLevelBackgroundName() + "\n");
            //fs.write(("" + levelData.getThumbnail()).getBytes());
            String line = new String(sb);
            fs.write(line.getBytes());
            fs.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

                        final boolean check = checkAnswer(a, b);
                        Toast toast;

                        String coord = "Kordinate su X = " + a + " a Y = " + b;

                        final ImageView answerMark = (ImageView) findViewById(R.id.answerMark);
                        Animation animation = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                        animation.setDuration(1000);
                        animation.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                if (check) {
                                    progressBar.incrementProgressBy(1);
                                }
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

    private boolean checkAnswer(int a, int b){
        ArrayList<Point> koordinate = levelData.getAnswerCoordinates().elementAt(randomQuestion);
        Point upLeft = koordinate.get(0);
        Point upRight = koordinate.get(1);
        Point downLeft = koordinate.get(2);
        Point downRight = koordinate.get(3);

        if ((a >= upLeft.x && a <= upRight.x) && (b >= upLeft.y && b <= downLeft.y))
            return true;

        return false;
    }
}


