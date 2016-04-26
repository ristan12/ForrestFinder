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

public class LevelsActivity extends AppCompatActivity {

    GameData gameData = (GameData) getApplication();
    GridView gridView;
    ArrayList<LevelData> list = new ArrayList<LevelData>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levels);

        gridView = (GridView) findViewById(R.id.levels_grid);

        fillTheTable(gameData);
    }

    private void fillTheTable(GameData gameData){

        //////
        int levelNumber = gameData.getDefaultLevelData().size();

        Log.d("test level", "statsSize " + levelNumber + " sizeForLoop " + "\n");
        for (int j = 0; j < levelNumber; j++)
        {
            LevelData levelData = gameData.getDefaultLevelData().elementAt(j);
            list.add(levelData);
        }

        final LevelsGridViewAdapter adapter = new LevelsGridViewAdapter(this, list);
        gridView.setAdapter(adapter);




        /////////





        /*
        Dugme button;
        TextView levelName;
        String buttonLevelName, textLevelName;
        int idImageButton, idTextView;

        for (int i = 0; i<gameData.getDefaultLevelNumber(); i++){

            buttonLevelName = "level" + i;                                                          //string za dohvatanje ImageButton-a
            textLevelName = "nameLevel" + i;                                                        //string za dohvatanje TextView-a

            idImageButton = getResources().getIdentifier(buttonLevelName, "id", getPackageName());  //dohvatanje id ImageButton-a
            idTextView = getResources().getIdentifier(textLevelName, "id", getPackageName());       //dohvatanje id TextView-a

            button = (Dugme) findViewById(idImageButton);                                                //dohvatanje ImageButton-a
            if (button != null)
                button.setBackground(gameData.getDefaultLevelData().elementAt(i).getThumbnail());   //postavljanje slicice na pozadinu

            levelName = (TextView) findViewById(idTextView);                                        //dohvatanje TextView
            if (levelName != null)
                levelName.setText(gameData.getDefaultLevelData().elementAt(i).getLevelName());    //postavljanje imena levela

            if (button != null) {
                button.setVisibility(View.VISIBLE);
                button.setIndeksVektora(i);
                final int id = button.getIndeksVektora();

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), GameLevelActivity.class);
                        intent.putExtra("id", id);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);// | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                });
            }
        }*/

    }
}
