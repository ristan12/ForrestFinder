package com.example.aleksandar.forrestfinder;

import android.content.Context;
import android.util.Log;
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

        //indeks
        TextView indeks = (TextView) rowView.findViewById(R.id.indeks);

        //level_name
        TextView levelName = (TextView) rowView.findViewById(R.id.level_name);

        //levelThumb
        ImageView levelThumb = (ImageView) rowView.findViewById(R.id.levelThumb);

        //wrong ans
        TextView wrongAns = (TextView) rowView.findViewById(R.id.wrongAns);

        //time
        TextView timeText = (TextView) rowView.findViewById(R.id.timeText);


        LevelStatistics levelStatistics = values.get(position);

        levelName.setText(levelStatistics.getLevelName());
        levelThumb.setImageDrawable(levelStatistics.getLevelThumbnail());

        String tekst = "" + levelStatistics.getWrongAnswers();
        wrongAns.setText(tekst);

        int minutesTime = levelStatistics.getTime() / 60;
        int secondsTime = levelStatistics.getTime() % 60;
        if (secondsTime < 10)
        {
            tekst = "" + minutesTime + ":0" + secondsTime;
        }
            else
        {
            tekst = "" + minutesTime + ":" + secondsTime;
        }
        timeText.setText(tekst);

        indeks.setText("" + (position + 1) + ".");
        return rowView;
    }
}
