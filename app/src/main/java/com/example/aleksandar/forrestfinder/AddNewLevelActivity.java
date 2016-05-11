package com.example.aleksandar.forrestfinder;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Vector;

public class AddNewLevelActivity extends AppCompatActivity {

    LevelData levelData;
    ImageView pozadina;

    private Vector<String> questions;
    private Vector<ArrayList<Point>> answerCoordinates;

    GameData gameData = (GameData) getApplication();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_level);

        levelData = new LevelData();
        pozadina = (ImageView) findViewById(R.id.slikaPozadine);

        questions = new Vector<String>();
        answerCoordinates = new Vector<ArrayList<Point>>();

        String path = "";
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            path = extras.getString("imeSlike");
        }
        loadImageFromStorage(path);
        setLevelName();
    }

    private void loadImageFromStorage(String path)
    {
        try {
            File f=new File(path, "user_gen.jpg");

            /////
            levelData.setLevelBackgroundName("user_gen.jpg");
            /////

            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));

            pozadina.setImageBitmap(b);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

    }

    private void setLevelName()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();
        View promptsView = inflater.inflate(R.layout.new_level_prompt, null);

        TextView textView = (TextView) promptsView.findViewById(R.id.prompt_text);
        final EditText editText = (EditText) promptsView.findViewById(R.id.editTextDialogUserInput);

        textView.setText("Naslov nivoa:");

        builder.setView(promptsView)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String tekst = editText.getText().toString();
                        levelData.setLevelName(tekst);
                        setQuestions();
                    }
                })
                .setCancelable(false);

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    private void setQuestions()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();
        View promptsView = inflater.inflate(R.layout.new_level_prompt, null);

        TextView textView = (TextView) promptsView.findViewById(R.id.prompt_text);
        final EditText editText = (EditText) promptsView.findViewById(R.id.editTextDialogUserInput);

        textView.setText("Pitanje:");

        builder.setView(promptsView)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String tekst = editText.getText().toString();
                        questions.addElement(tekst);
                        getAnswerCoordinates();
                    }
                })
                .setCancelable(false);

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    private void getAnswerCoordinates()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();
        View promptsView = inflater.inflate(R.layout.new_level_prompt, null);

        TextView textView = (TextView) promptsView.findViewById(R.id.prompt_text);
        EditText editText = (EditText) promptsView.findViewById(R.id.editTextDialogUserInput);

        editText.setVisibility(View.GONE);
        textView.setText("Klikni na koordinate zivotinja:");

        builder.setView(promptsView)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //String tekst = editText.getText().toString();
                        //questions.addElement(tekst);
                        waitForCoordinates();
                    }
                })
                .setCancelable(false);

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    private void waitForCoordinates()
    {
        pozadina.setOnTouchListener(new View.OnTouchListener() {

            int touchNum = 0;
            Point point = new Point();
            ArrayList<Point> niz = new ArrayList<Point>();
            int a, b;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (touchNum < 4) {
                    //go to next touch event coord
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        a = Math.round(event.getX() / gameData.getFactorX());
                        b = Math.round(event.getY() / gameData.getFactorY());

                        Toast.makeText(getApplicationContext(), "Koordinate su " + a + " " + b, Toast.LENGTH_SHORT).show();
                        point = new Point(a, b);
                        Log.d("Coord", " Kords = " + a + " " + b);
                        niz.add(point);
                        touchNum++;
                    }

                } else {
                    touchNum = 0;
                    answerCoordinates.add(niz);
                    askIfMoreQuestions();
                }

                return true;
            }
        });

    }

    private void askIfMoreQuestions()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();
        View promptsView = inflater.inflate(R.layout.new_level_prompt, null);

        TextView textView = (TextView) promptsView.findViewById(R.id.prompt_text);
        EditText editText = (EditText) promptsView.findViewById(R.id.editTextDialogUserInput);

        editText.setVisibility(View.GONE);
        textView.setText("Jos pitanja?");

        builder.setView(promptsView)
                .setPositiveButton("Da", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setQuestions();
                    }
                })
                .setNegativeButton("Ne", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            writeToFileAndExit();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                })
                .setCancelable(false);

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    private void writeToFileAndExit() throws IOException {
        levelData.setQuestions(questions);
        levelData.setAnswerCoordinates(answerCoordinates);

        /*File file = new File("addedLevelData.txt");
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");


        sb.append("<level>/n/t<questions>/n");

        randomAccessFile.seek(0);
        randomAccessFile.write(sb.toString().getBytes());*/

        FileOutputStream fos = openFileOutput("addedLevelData.txt", Context.MODE_PRIVATE);
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append("<main>\n\t");
        sb.append("<level>\n\t\t");
        sb.append("<questions>\n");
        for (int i = 0; i < questions.size(); i++)
        {
            //question
            sb.append("\t\t\t<question>");
            sb.append(questions.elementAt(i));
            sb.append("</question>\n\t\t\t\t");

            //coordinates
            //topleft
            sb.append("<topleft>");
            sb.append("" + answerCoordinates.elementAt(i).get(0).x + " " + answerCoordinates.elementAt(i).get(0).y);
            sb.append("</topleft>\n\t\t\t\t");

            //topright
            sb.append("<topright>");
            sb.append("" + answerCoordinates.elementAt(i).get(1).x + " " + answerCoordinates.elementAt(i).get(1).y);
            sb.append("</topright>\n\t\t\t\t");

            //botleft
            sb.append("<botleft>");
            sb.append("" + answerCoordinates.elementAt(i).get(2).x + " " + answerCoordinates.elementAt(i).get(2).y);
            sb.append("</botleft>\n\t\t\t\t");

            //botright
            sb.append("<botright>");
            sb.append("" + answerCoordinates.elementAt(i).get(3).x + " " + answerCoordinates.elementAt(i).get(3).y);
            sb.append("</botright>\n");
        }
        sb.append("\t\t</questions>\n");
        sb.append("\t</level>\n");
        sb.append("</main>");

        String lines = new String(sb);
        fos.write(lines.getBytes());
        fos.close();
        Log.d("Linija je ", lines);
        Log.d("AAA", levelData.getLevelBackgroundName());
        Log.d("AAA", levelData.getLevelName());
        finish();
    }
}
