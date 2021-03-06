package com.example.aleksandar.forrestfinder;

import android.content.Intent;
import android.graphics.Picture;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.logging.Level;

public class LevelsActivity extends AppCompatActivity {

    GameData gameData = (GameData) getApplication();
    GridView gridView;
    ArrayList<LevelData> list = new ArrayList<LevelData>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levels);

        Log.d("dataflow", "In onCreate in LevelsActivity:");
        gridView = (GridView) findViewById(R.id.levels_grid);

        fillTheTable(gameData);
    }

    private void fillTheTable(GameData gameData){

        int levelNumber = gameData.getDefaultLevelData().size();
        int addedLevelNumber = gameData.getAddedLevelData().size();


        Log.d("dataflow", "In fillTheTable in LevelsActivity:");
        Log.d("velicine levela", "defLevNum " + levelNumber + " adddedLevNum " + addedLevelNumber +"\n");

        for (int j = 0; j < levelNumber; j++)
        {
            LevelData levelData = gameData.getDefaultLevelData().elementAt(j);
            list.add(levelData);
        }

        for (int j = 0; j < addedLevelNumber; j++)
        {
            LevelData levelData = gameData.getAddedLevelData().elementAt(j);
            list.add(levelData);
        }

        final LevelsGridViewAdapter adapter = new LevelsGridViewAdapter(this, list);
        gridView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d("dataflow", "In onResume in LevelsActivity:");
    }
}
