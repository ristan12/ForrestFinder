package com.example.aleksandar.forrestfinder;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class StatisticsActivity extends AppCompatActivity {

    GameData gameData = (GameData) getApplication();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        gameData = (GameData) getApplication();

        Button backButton = (Button) findViewById(R.id.stats_back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StatisticsActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });


        final ListView listView = (ListView) findViewById(R.id.stats_list);
        final ArrayList<LevelStatistics> list = new ArrayList<LevelStatistics>();
        //popunjavamo list

        for (int i = 0; i < 2 ; i++)
        {
            LevelStatistics statistics = new LevelStatistics("Forrest" + i, (int)(i+ Math.round(Math.random() * 4)), (int)(i * 5 + Math.round(Math.random() * 4)), getResources().getDrawable(R.drawable.savana_level_thumbnail));
            list.add(statistics);
        }

        final StatisticsListViewAdapter adapter = new StatisticsListViewAdapter(this, list);
        listView.setAdapter(adapter);

    }
}
