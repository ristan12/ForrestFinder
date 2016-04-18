package com.example.aleksandar.forrestfinder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Aleksandar on 18/04/2016.
 */
public class StatisticsListViewAdapter extends ArrayAdapter<LevelStatistics> {

    private final Context context;
    private final ArrayList<LevelStatistics> values;

    public StatisticsListViewAdapter(Context context, ArrayList<LevelStatistics> values) {
        super(context, R.layout.statistics_list_view_item, values);
        this.context = context;
        this.values = values;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.statistics_list_view_item, parent, false);

        //level_name
        TextView levelName = (TextView) rowView.findViewById(R.id.level_name);

        //levelThumb
        ImageView levelThumb = (ImageView) rowView.findViewById(R.id.levelThumb);

        //stopwach image
        ImageView stopwatchImage = (ImageView) rowView.findViewById(R.id.stopwatch_image);

        //checkmark image
        ImageView checkmarkImage = (ImageView) rowView.findViewById(R.id.checkmark_image);

        //xmark image
        ImageView xmarkImage = (ImageView) rowView.findViewById(R.id.xmark_image);

        //correct ans
        TextView correctAns = (TextView) rowView.findViewById(R.id.correctAns);

        //wrong ans
        TextView wrongAns = (TextView) rowView.findViewById(R.id.wrongAns);

        //time
        TextView timeText = (TextView) rowView.findViewById(R.id.timeText);


        LevelStatistics levelStatistics = values.get(position);

        levelName.setText(levelStatistics.getLevelName());
        //levelThumb.setImageDrawable(levelStatistics.getLevelThumbnail());
        levelThumb.setImageDrawable(levelStatistics.getLevelThumbnail());

        stopwatchImage.setImageResource(R.drawable.stopwatch);
        checkmarkImage.setImageResource(R.drawable.checkmark);
        xmarkImage.setImageResource(R.drawable.xmark);


        correctAns.setText("15");

        String tekst = "" + levelStatistics.getWrongAnswers();
        wrongAns.setText(tekst);
        tekst = "" + levelStatistics.getTime();
        timeText.setText(tekst);

        return rowView;
    }
}
