package com.example.aleksandar.forrestfinder;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PictureDrawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Vector;

@TargetApi(21)
public class MainActivity extends AppCompatActivity {

    long time;

    GameData gameData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("dataflow", "In onCreate in MainActivity:");
        try {
            FileOutputStream fos = openFileOutput("addedLevelData.txt", Context.MODE_APPEND);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        gameData = new GameData();
        fillGameData(gameData);

        gameData.setFactor(getWindowManager().getDefaultDisplay().getWidth(), getWindowManager().getDefaultDisplay().getHeight());

        Button levels = (Button) findViewById(R.id.levels_btn);
        levels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LevelsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        Button exit = (Button) findViewById(R.id.exit_btn);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button statistics = (Button) findViewById(R.id.stats_btn);
        statistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, StatisticsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        Button options = (Button) findViewById(R.id.options_btn);
        options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, OptionsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

    }

    private void fillGameData(GameData gameData){

        Log.d("dataflow", "In fillGameData in MainActivity:");
        ////
        //startCount();

        loadDefaultLevels();


        loadGameStatistics();


        loadAddedLevels();



        //fillAddedLevels();
    }

    ////////funkcije za load defaultLevels i addedLevels
    private void loadDefaultLevels(){

        Log.d("dataflow", "In loadDefaultLevels in MainActivity:");
        //
        startCount();

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

        endCount("loadDefaultLevels");
        //getting drawables
        completeLevelData(def);
        //setting default Levels
        gameData.setDefaultLevelData(def);

    }

    private void loadAddedLevels() {

        Log.d("dataflow", "In loadAddedLevels in MainActivity:");
        //
        startCount();

        Vector<LevelData> def = new Vector<LevelData>();
        FileInputStream fis = null;
        try {
            fis = openFileInput("addedLevelData.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        XMLParser parser = new XMLParser(fis);
        try {
            parser.parseXML(def);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        endCount("loadAddedLevels");
        //getting drawables
        try{
            completeAddedLevelData(def);
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
        //setting added levels
        gameData.setAddedLevelData(def);

    }
    /////////////////////////////////////////////////////

    private void completeLevelData(Vector<LevelData> def){
        Log.d("dataflow", "In completeLevelData in MainActivity:");
        //
        startCount();

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
        endCount("completeLevelData");
    }

    private void completeAddedLevelData(Vector<LevelData> def) throws FileNotFoundException {
        Log.d("dataflow", "In completeAddedLevelData in MainActivity:");
        //
        startCount();

        for (int i = 0; i < def.size(); i++){
            //String backgroundName = def.get(i).getLevelBackgroundName();
            //String thumbnailName = backgroundName + "_thumbnail";

            //int identifier = getApplication().getResources().getIdentifier(backgroundName, "drawable", getPackageName());
            //////////dovlacenje sa internal storagea

            String path = getFilesDir().getAbsolutePath();
            //Log.d("picpath", "path " + path);

            String pathCor = path.substring(0, path.length()-5);
            //Log.d("picpath", "pathCor " + pathCor);

            String finalPath = pathCor + "app_imageDir/";
            //Log.d("picpath", "finalPath " + finalPath);

            File f=new File(finalPath, "user_gen.jpg");
            //Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            String finDrawablePath = ""+finalPath+"user_gen.jpg";
            //Log.d("picpath", "final path for drawable " + finDrawablePath);
            Drawable background = Drawable.createFromPath(finDrawablePath);//new BitmapDrawable(b); // //
            //Log.d("picpath", "" + background.toString());
            //////////

            //Drawable background = getResources().getDrawable(identifier);

            //identifier = getApplication().getResources().getIdentifier(thumbnailName, "drawable", getPackageName());
            Drawable thumbnail = getResources().getDrawable(R.drawable.questionmark);

            def.get(i).setBackgroundPic(background);
            def.get(i).setThumbnail(thumbnail);
        }

        endCount("completeAddedLevelData");
    }

    protected void onResume(){
        super.onResume();

        Log.d("dataflow", "In onResume in MainActivity:");
        //
        startCount();

        if (gameData.shouldUpdateStatistics){
            Log.d("dataflow", "In onResume in MainActivity: (first IF)");

            loadGameStatistics();
            gameData.shouldUpdateStatistics = false;
        }
        if (gameData.shouldUpdateAddedLevels){

            Log.d("dataflow", "In onResume in MainActivity: (second IF)");
            loadAddedLevels();
            gameData.shouldUpdateAddedLevels = false;
        }
        endCount("onResume");
    }

    //popunjavanje statistike u levelData
    private void loadGameStatistics(){

        Vector<LevelStatistics> stats = new Vector<LevelStatistics>();
        LevelStatistics levelStatistics;

        Log.d("dataflow", "In loadGameStatistics in MainActivity:");

        //
        startCount();

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
                if (identifier == 0)
                {
                    identifier = getApplication().getResources().getIdentifier("questionmark_thumbnail", "drawable", getPackageName());
                }
                Log.d("error", ""+thumbnailName+" "+identifier);
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

        endCount("loadGameStatistics");
    }

    //funkcija koja samo ispisuje sta se nalazi u addeLevelData.txt
    private void fillAddedLevels()
    {

        Log.d("dataflow", "In fillAddedLevels in MainActivity:");
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
                Log.d("novalin", line);
            }
            fi.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startCount(){
        time = SystemClock.elapsedRealtime();
    }

    private void endCount(String operation){
        Log.d("dataflow", "***Time for |" + operation + "| : " + (SystemClock.elapsedRealtime() - time));
        time = SystemClock.elapsedRealtime();
    }
}