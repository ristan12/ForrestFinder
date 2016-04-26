package com.example.aleksandar.forrestfinder;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Aleksandar on 24/04/2016.
 */
public class LevelsGridViewAdapter extends ArrayAdapter<LevelData> {

    private final Context context;
    private final ArrayList<LevelData> values;

    public LevelsGridViewAdapter(Context context, ArrayList<LevelData> values) {
        super(context, R.layout.levels_grid_view_item, values);
        this.context = context;
        this.values = values;
    }

    public View getView(final int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View gridView = inflater.inflate(R.layout.levels_grid_view_item, parent, false);

        //level name
        TextView levelName = (TextView) gridView.findViewById(R.id.level_thumb_name);

        //level thumbnail
        Dugme levelThumb = (Dugme) gridView.findViewById(R.id.level_thumb);

        LevelData levelData = values.get(position);

        levelName.setText(levelData.getLevelName());
        levelThumb.setImageDrawable(levelData.getThumbnail());
        levelThumb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context.getApplicationContext(), GameLevelActivity.class);
                intent.putExtra("id", position);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);// | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                context.startActivity(intent);
            }
        });

        return gridView;
    }
}
