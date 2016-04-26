package com.example.aleksandar.forrestfinder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class StatisticsActivity extends AppCompatActivity {

    GameData gameData = (GameData) getApplication();
    ListView listView;
    ArrayList<LevelStatistics> list = new ArrayList<LevelStatistics>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        listView = (ListView) findViewById(R.id.stats_list);
        gameData = (GameData) getApplication();
        Button clearButton = (Button) findViewById(R.id.clear_button);

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileOutputStream fs;
                StringBuilder sb = new StringBuilder();
                try {
                    fs = openFileOutput("statisticsData.txt", Context.MODE_PRIVATE);
                    sb.append("");
                    String line = new String(sb);
                    fs.write(line.getBytes());
                    fs.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                gameData.getLevelStatistics().clear();
                list.clear();
                fillViewWithData(listView, list);
            }
        });

        //popunjavamo list
        fillViewWithData(listView, list);
    }

    private void fillViewWithData(ListView listView, ArrayList<LevelStatistics> list){
        int statsSize = gameData.getLevelStatistics().size();
        int sizeForLoop = (statsSize > 10 ? 10 : statsSize);

        Log.d("test fill", "statsSize " + statsSize + " sizeForLoop " + sizeForLoop + "\n");
        for (int j = 0; j < sizeForLoop; j++)
        {
            LevelStatistics statistics = gameData.getLevelStatistics().elementAt(--statsSize);
            list.add(statistics);
        }

        final StatisticsListViewAdapter adapter = new StatisticsListViewAdapter(this, list);
        listView.setAdapter(adapter);
    }
}
