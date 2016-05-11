package com.example.aleksandar.forrestfinder;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Vector;

@TargetApi(21)
public class MainActivity extends AppCompatActivity {

    GameData gameData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameData = new GameData();
        fillGameData(gameData);
        gameData.setFactor(getWindowManager().getDefaultDisplay().getWidth(), getWindowManager().getDefaultDisplay().getHeight());

        try {
            FileOutputStream fos = openFileOutput("addedLevelData.txt", Context.MODE_APPEND);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Button levels = (Button) findViewById(R.id.levels_btn);
        levels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LevelsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);// | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        Button exit = (Button) findViewById(R.id.exit_btn);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                //System.exit(0);
            }
        });

        Button statistics = (Button) findViewById(R.id.stats_btn);
        statistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, StatisticsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);// | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        Button options = (Button) findViewById(R.id.options_btn);
        options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, OptionsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);// | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

    }

    private void fillGameData(GameData gameData) {
        gameData.setAddedLevelNumber(0);

        fillGameStatistics();
        fillAddedLevels();
        Vector<LevelData> def = new Vector<LevelData>();

        InputStream is = this.getResources().openRawResource(R.raw.leveldata);
        XMLParser parser = new XMLParser(is);
        try {
            parser.parseXML(def);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //getting drawables
        completeLevelData(def);
        //setting default Levels
        gameData.setDefaultLevelData(def);


        def = new Vector<LevelData>();
        FileInputStream fis = null;
        try {
            fis = openFileInput("addedLevelData.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        parser = new XMLParser(fis);
        try {
            parser.parseXML(def);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //getting drawables
        completeAddedLevelData(def);
        //setting added levels
        gameData.setAddedLevelData(def);
    }

    private void fillLevelData(LevelData levelData, String backgroundPicName, String levelName, Drawable backgroundPic, Drawable thumbnail) {
        levelData.setLevelBackgroundName(backgroundPicName);
        levelData.setLevelName(levelName);
        levelData.setBackgroundPic(backgroundPic);
        levelData.setThumbnail(thumbnail);
    }

    private void completeLevelData(Vector<LevelData> def){
        for (int i = 0; i < def.size(); i++){
            String backgroundName = def.get(i).getLevelBackgroundName();
            String thumbnailName = backgroundName + "_thumbnail";

            int identifier = getApplication().getResources().getIdentifier(backgroundName, "drawable", getPackageName());
            Drawable background = getResources().getDrawable(identifier);

            identifier = getApplication().getResources().getIdentifier(thumbnailName, "drawable", getPackageName());
            Drawable thumbnail = getResources().getDrawable(identifier);

            def.get(i).setBackgroundPic(background);
            def.get(i).setThumbnail(thumbnail);
        }
    }

    private void completeAddedLevelData(Vector<LevelData> def)
    {

    }

    protected void onResume(){
        super.onResume();
        fillGameStatistics();
    }

    private void fillGameStatistics(){

        Vector<LevelStatistics> stats = new Vector<LevelStatistics>();
        LevelStatistics levelStatistics;

        FileInputStream fi;
        try {
            fi = openFileInput("statisticsData.txt");

            InputStreamReader isr = new InputStreamReader(fi);
            BufferedReader bufferedReader = new BufferedReader(isr);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                StringBuilder sb = new StringBuilder();
                sb.append(line);
                line = new String(sb);
                String[] split = line.split(" ");
                String levelName = split[0];
                int wrongAnswers = Integer.parseInt(split[1]);
                int time = Integer.parseInt(split[2]);

                String thumbnailName = split[3] + "_thumbnail";
                int identifier = getApplication().getResources().getIdentifier(thumbnailName, "drawable", getPackageName());
                Drawable levelThumbnail = getResources().getDrawable(identifier);

                levelStatistics = new LevelStatistics(levelName, wrongAnswers, time, levelThumbnail);
                stats.add(levelStatistics);
                Log.d("mrdja", line);
            }
            fi.close();
            gameData.setLevelStatistics(stats);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void fillAddedLevels()
    {
        FileInputStream fi;
        try {
            fi = openFileInput("addedLevelData.txt");

            InputStreamReader isr = new InputStreamReader(fi);
            BufferedReader bufferedReader = new BufferedReader(isr);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                StringBuilder sb = new StringBuilder();
                sb.append(line);
                line = new String(sb);
                /*
                String[] split = line.split(" ");
                String levelName = split[0];
                int wrongAnswers = Integer.parseInt(split[1]);
                int time = Integer.parseInt(split[2]);

                String thumbnailName = split[3] + "_thumbnail";
                int identifier = getApplication().getResources().getIdentifier(thumbnailName, "drawable", getPackageName());
                Drawable levelThumbnail = getResources().getDrawable(identifier);
                */
                Log.d("novalin", line);
            }
            fi.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}