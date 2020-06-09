package com.example.top10downloader;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;

public class ParseApp {
    private static final String TAG = "ParseApp";
    public ArrayList<Feed> arrayList;

    public ParseApp() {
        this.arrayList = new ArrayList<>();
    }

    public ArrayList<Feed> getItems(){

        return arrayList;
    }
    public boolean parse(String xml){
        boolean status = true;
        Feed currentFeed = null;
        boolean inFactory = false;
        String textVal = "";

        try{
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser parser = factory.newPullParser() ;

            parser.setInput(new StringReader(xml));
            int e = parser.getEventType();
            System.out.println("event type"+e);
            System.out.println("exbkdsafb"+XmlPullParser.TEXT);

            while (e != XmlPullParser.END_DOCUMENT){
                String tagName = parser.getName();
                System.out.println(e != XmlPullParser.END_DOCUMENT);
                switch (e){
                    case XmlPullParser.START_TAG:
                        System.out.println("Reached start");
                        if ("entry".equalsIgnoreCase(tagName)){
                            inFactory = true;
                            currentFeed = new Feed();

                        }
                        break;
                        case XmlPullParser.TEXT:

                            textVal = parser.getText();
                            break;
                    case XmlPullParser.END_TAG:
                        if (inFactory){
                            System.out.println(tagName);
                            if ("entry".equalsIgnoreCase(tagName)){
                                System.out.println("entry".equalsIgnoreCase(tagName));
                                arrayList.add(currentFeed);
                                inFactory = false;
                            }
                            else if ("name".equalsIgnoreCase(tagName)){

                                currentFeed.setName(textVal);
                            }
                            else if ("artist".equalsIgnoreCase(tagName)){
                                currentFeed.setArtist(textVal);
                            }
                            else if ("releaseDate".equalsIgnoreCase(tagName)){
                                currentFeed.setReleaseDate(textVal);
                            }
                            else if ("summary".equalsIgnoreCase(tagName)){
                                currentFeed.setSummary(textVal);
                            }
                            else if ("image".equalsIgnoreCase(tagName)){
                                currentFeed.setImageURL(textVal);
                            }

                        }
                            break;

                    default:
                        Log.i(TAG, "parse: Default");
                }
               e = parser.next();
            }
            for (Feed ee : arrayList){
                Log.d(TAG, "parse:fsgfd "+ ee.getImageURL());
            }
        }
        catch (Exception e){
            status = false;
            Log.e(TAG, "parse: "+e.getMessage());
        }
        return status;
    }

}
