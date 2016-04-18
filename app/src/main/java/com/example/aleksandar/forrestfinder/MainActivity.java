package com.example.aleksandar.forrestfinder;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;

@TargetApi(21)
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GameData gameData = new GameData();
        fillGameData(gameData);
        gameData.setFactor(getWindowManager().getDefaultDisplay().getWidth(), getWindowManager().getDefaultDisplay().getHeight());

        Button levels = (Button) findViewById(R.id.levels_btn);
        levels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LevelsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
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
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }

    private void fillGameData(GameData gameData) {
        gameData.setAddedLevelNumber(0);

        //jebeno stvaramo neku statistiku jer se ocigledno sve sjebe
        Vector<LevelStatistics> stats = new Vector<LevelStatistics>();
        Drawable slika = getResources().getDrawable(R.drawable.savana_level_thumbnail);
        LevelStatistics statistics = new LevelStatistics("Forrest!", 10, 120, slika);


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
        gameData.setLevelStatistics(stats);
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
}