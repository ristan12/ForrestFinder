package com.example.aleksandar.forrestfinder;

import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

/**
 * Created by Aleksandar on 02/03/2016.
 */



public class XMLParser {

    public class Entry{
        public final String question;
        public final Point topleft;
        public final Point topright;
        public final Point botleft;
        public final Point botright;

        public Entry(String question, Point topleft, Point topright, Point botleft, Point botright){
            this.question = question;
            this.topleft = topleft;
            this.topright = topright;
            this.botleft = botleft;
            this.botright = botright;
        }

    }

    private XmlPullParser parser;


    public XMLParser(InputStream is){
        this.parser = Xml.newPullParser();
        try {
            this.parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(is, null);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        Log.d("srdja", "Isao i konstruktor XML PArsera");
    }

    public XmlPullParser getParser() {
        return parser;
    }

    public void parseXML(Vector<LevelData> levelDataVector) throws XmlPullParserException, IOException{
        //variable for levelData
        LevelData levelData = new LevelData();
        //variable for 2 strings in levelData
        StringBuilder backgroundName = new StringBuilder("");
        StringBuilder levelName = new StringBuilder("");
        //variabales for questions and answers
        Vector<String> questions;
        Vector<ArrayList<Point>> answerCoordinates;


        //niz koji sadrzi Entry-je sa pitanjem i odgovorom
        List<Entry> entries = new ArrayList<Entry>();


        //parsiranje
        int event = parser.getEventType();

        while(event != XmlPullParser.END_DOCUMENT){
            if (event == XmlPullParser.START_DOCUMENT)
                Log.d("srdja", "Start document " + parser.getName());
            else if (event == XmlPullParser.START_TAG) {
                if (parser.getName().equals("level")) {
                    entries = new ArrayList<Entry>();

                    //procita sva pitanja okruzena <questions> </questions>
                    readQuestions(parser, entries);

                    //tekst between </questions> and <data> is before this next parser.next()
                    event = parser.next();

                    //readData(parser, data);
                    readData(parser, backgroundName, levelName);

                    Log.d("srdja", "Start tag inside if after parser.next is 100 " + parser.getName() + " a tekst je " + parser.getText());

                }
                Log.d("srdja", "Start tag " + parser.getName());
            }
            else if (event == XmlPullParser.END_TAG) {



                if (parser.getName().equals("level")){
                    Log.d("srdja2", "************Info za ceo level je ******* ");
                    Log.d("srdja2", "Ime levela = " + levelName);
                    Log.d("srdja2", "Ime backgrounda = " + backgroundName);
                    Log.d("srdja", "************Questions**************");
                    for(int i = 0; i < entries.size(); i++){
                        Log.d("srdja2", "Question " + (i+1));
                        Log.d("srdja2", "Text pitanja = " + entries.get(i).question);
                        Log.d("srdja2", "Koordinata topleft = " + entries.get(i).topleft.x + ", " + entries.get(i).topleft.x);
                        Log.d("srdja2", "Koordinata topright = " + entries.get(i).topright.x + ", " + entries.get(i).topright.x);
                        Log.d("srdja2", "Koordinata botleft = " + entries.get(i).botleft.x + ", " + entries.get(i).botleft.x);
                        Log.d("srdja2", "Koordinata botright = " + entries.get(i).botright.x + ", " + entries.get(i).botright.x);
                        Log.d("srdja2", "***************************************");
                    }
                    //filling levelData with Data
                    levelData = new LevelData();
                    fillLevelData(levelData, backgroundName.toString(), levelName.toString(), entries);
                    levelDataVector.addElement(levelData);

                    Log.d("srdja", "End tag " + parser.getName());
                    backgroundName.delete(0, backgroundName.capacity());
                    levelName.delete(0, levelName.capacity());
                }


            }
            else if (event == XmlPullParser.TEXT)
                Log.d("srdja", "Text " + parser.getText());

            event = parser.next();

        }
        Log.d("srdja", "Doc End ");


    }

    private void readQuestions(XmlPullParser parser, List<Entry> entries) throws IOException, XmlPullParserException{
        String question = "";
        Point topleft = new Point(0,0);
        Point topright = new Point(0,0);
        Point botleft = new Point(0,0);
        Point botright = new Point(0,0);

        //<level> tekst
        parser.next();
        //<questions> tag
        parser.next();
        //<questions> tekst
        parser.next();

        int event = parser.getEventType();

        Log.d("srdja", "Read Level ---------- " + parser.getName());
        Log.d("srdja", "first event = " + event + " first name = " + parser.getName());

        while (!(event == XmlPullParser.END_TAG && parser.getName().equals("questions")))
        {
            if (event == XmlPullParser.START_TAG && parser.getName().equals("question")){
                parser.next();
                Log.d("srdja", "11111111111111111111 ");
                question = parser.getText();
            }
            else if(event == XmlPullParser.START_TAG && parser.getName().equals("topleft")) {
                parser.next();
                Log.d("srdja", "00000000000000000 ");
                topleft = toPoint(parser.getText());
            }
            else if(event == XmlPullParser.START_TAG && parser.getName().equals("topright")) {
                parser.next();
                Log.d("srdja", "22222222222222222 ");
                topright = toPoint(parser.getText());
            }
            else if (event == XmlPullParser.START_TAG && parser.getName().equals("botleft")) {
                parser.next();
                Log.d("srdja", "333333333333333333 ");
                botleft = toPoint(parser.getText());
            }
            else if(event == XmlPullParser.START_TAG && parser.getName().equals("botright")) {
                parser.next();
                Log.d("srdja", "44444444444444444444 ");
                botright = toPoint(parser.getText());
                //procitao je poslednji podatak za question i dodajemo ga u listu
                entries.add(new Entry(question, topleft, topright, botleft, botright));
                //sta smo dodali?
                Log.d("srdja", " Svi podaci : " + question + " " + topleft.x + " " + topleft.y + " " +
                        topright.x + " " + topright.y + " " +
                        botleft.x + " " + botleft.y + " " +
                        botright.x + " " + botright.y + " ");

            }
            event = parser.next();
            Log.d("srdja", "novi event = " + event + " novo name = " + parser.getName());
        }
    }

    private void readData(XmlPullParser parser, StringBuilder backgroundName, StringBuilder levelName)throws IOException, XmlPullParserException{
        parser.next();  //skipping the text between </questions> and <data>
        parser.next();  //skipping the <data> tag
        parser.next();  //skipping the text between <data> and <backgroundName>

        int event = parser.getEventType();

        //we are now on <backgroundName> tag

        if (event == XmlPullParser.START_TAG && parser.getName().equals("backgroundName")){
            parser.next();  //skipping the <backgroundName> tag
            //data.backgroundName = parser.getText();
            backgroundName.append(parser.getText());
            //Log.d("ime", "backgroundName is = " + data.backgroundName);
            Log.d("srdja", "Start tag inside if after parser.next is 32 " + parser.getName() + " a tekst je " + parser.getText());
            parser.next();  //skipping the <backgroundName> text
            parser.next();  //skipping the </backgroundName> tag
        }
        Log.d("srdja", "Start tag inside if after parser.next is 33 " + parser.getName() + " a tekst je " + parser.getText());

        event = parser.next();  //skippping the text between </backgroundName> and <levelName>

        //we are now on <levelData> tag

        Log.d("srdja", "Start tag inside if after parser.next is 34 " + parser.getName() + " a tekst je " + parser.getText());


        if (event == XmlPullParser.START_TAG && parser.getName().equals("levelName")){
            parser.next();  //skipping the <levelName> tag
            //data.levelName = parser.getText();
            levelName.append(parser.getText());
            //Log.d("ime", "LevelName is = " + data.levelName);
            Log.d("srdja", "Start tag inside if after parser.next is 35 " + parser.getName() + " a tekst je " + parser.getText());
            parser.next();  //skipping the <levelName> text
            parser.next();  //skipping the </levelName> tag
        }
        parser.next();  //skipping the text between </levelName> and </data>
        parser.next();  //skipping the </data> tag

        //we are now on text between </data> and </level>
    }

    private Point toPoint(String line){
        Scanner sc = new Scanner(line);
        int first = sc.nextInt();
        int second = sc.nextInt();
        return new Point(first, second);
    }

    //popunjava podatke za level u levelData
    private void fillLevelData(LevelData levelData, String backgroundPicName, String levelName, List<Entry> entries) {
        Vector<String> questions = new Vector<String>();
        Vector<ArrayList<Point>> answerCoordinates = new Vector<ArrayList<Point>>();

        //setting questions and answers
        for (int i = 0; i < entries.size(); i++){
            questions.addElement(entries.get(i).question);

            ArrayList<Point> coords = new ArrayList<Point>();
            coords.add(entries.get(i).topleft);
            coords.add(entries.get(i).topright);
            coords.add(entries.get(i).botleft);
            coords.add(entries.get(i).botright);
            answerCoordinates.addElement(coords);

        }

        levelData.setLevelBackgroundName(backgroundPicName);
        levelData.setLevelName(levelName);
        levelData.setAnswerCoordinates(answerCoordinates);
        levelData.setQuestions(questions);

        //drawables are set outside, since we do not have access to resource here
    }
}
