package com.example.pctesting.personalcapitaltest.AppFunctions;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.Toast;

import com.example.pctesting.personalcapitaltest.AppObjects.ArticleHeaderView;
import com.example.pctesting.personalcapitaltest.AppObjects.ArticleTrailView;
import com.example.pctesting.personalcapitaltest.AppObjects.ItemArticleXML;
import com.example.pctesting.personalcapitaltest.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Asynchronous Task for loading the main XML file
 */

public class AsyncTaskLoadingXML extends AsyncTask<String, String, String> {

    // Variables of the Asynchronous Task
    private String error = "";
    private String xmlSourceLink = "https://blog.personalcapital.com/feed/?cat=3,891,890,68,284";

    Context eContext;
    ProgressDialog progressDialog;
    LinearLayout articleCapsuleFiles;
    boolean refreshType;
    ArrayList<ItemArticleXML> xmlArticles;
    AppGraphics appGraphics;
    int counter = 0;

    // Constructor
    public AsyncTaskLoadingXML(Context context, boolean refreshLoad, LinearLayout articleCapsule) {
        eContext = context;
        refreshType = refreshLoad;
        articleCapsuleFiles = articleCapsule;
        appGraphics = new AppGraphics(context);
    }

    @Override
    protected void onPreExecute() {
        // Checks to see if the user wants to refresh
        if(refreshType) {
            // Outputs a refresh dialog
            /*progressDialog = ProgressDialog.show(eContext,
                    eContext.getResources().getString(R.string.refresh_search_00),
                    eContext.getResources().getString(R.string.refresh_search_01));*/
            Toast.makeText(eContext,
                    eContext.getResources().getString(R.string.refresh_00), Toast.LENGTH_SHORT).show();
        } else {
            // Outputs a search dialog
            progressDialog = ProgressDialog.show(eContext,
                    eContext.getResources().getString(R.string.progress_search_00),
                    eContext.getResources().getString(R.string.progress_search_01));
        }
    }

    @Override
    protected String doInBackground(String... params) {

        // Initiate to collect articles
        xmlArticles = new ArrayList<>();

        // Download needed contents
        try {
            URL url = new URL(params[0]);

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(false);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(getInputStream(url), "UTF_8");

            boolean insideItem = false;
            ItemArticleXML newArticle = new ItemArticleXML();

            // Returns the type of current event
            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    // Get 'Item' list
                    if(xpp.getName().equalsIgnoreCase(eContext.getResources().getString(R.string.xml_tag_00))) {
                        insideItem = true;
                        // Get 'Title' list
                    } else if(xpp.getName().equalsIgnoreCase(eContext.getResources().getString(R.string.xml_tag_01))) {
                        if(insideItem) newArticle.titleTag = xpp.nextText();
                        // Get 'Link' list
                    } else if(xpp.getName().equalsIgnoreCase(eContext.getResources().getString(R.string.xml_tag_02))) {
                        if(insideItem) newArticle.linkTag = xpp.nextText();
                        // Get 'pubDate' list
                    } else if(xpp.getName().equalsIgnoreCase(eContext.getResources().getString(R.string.xml_tag_03))) {
                        if(insideItem) newArticle.pubDateTag = xpp.nextText();
                        // Get 'Description' list
                    } else if(xpp.getName().equalsIgnoreCase(eContext.getResources().getString(R.string.xml_tag_04))) {
                        if(insideItem) newArticle.descriptionTag = xpp.nextText();
                        // Get 'Content:Encoded' list
                    } else if(xpp.getName().equalsIgnoreCase(eContext.getResources().getString(R.string.xml_tag_05))) {
                        if(insideItem) newArticle.contentEncoded = xpp.nextText();
                        // Get 'Media:Content' list
                    } else if(xpp.getName().equalsIgnoreCase(eContext.getResources().getString(R.string.xml_tag_06))) {
                        // Get the image url
                        if(insideItem) newArticle.imageContentTag = xpp.getAttributeValue(0);
                    }
                } else if(eventType == XmlPullParser.END_TAG &&
                        xpp.getName().equalsIgnoreCase(eContext.getResources().getString(R.string.xml_tag_00))) {
                    insideItem = false;

                    // Adds a new article item once all needed info are recorded
                    xmlArticles.add(newArticle);
                    newArticle = new ItemArticleXML();
                }
                eventType = xpp.next();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            error = e.getMessage();
        }
        return error;
    }

    // Get the URL input stream
    public InputStream getInputStream(URL url) {
        try {
            return url.openConnection().getInputStream();
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    protected void onPostExecute(String args) {

        // Dismiss Progress Dialog
        if(!refreshType) progressDialog.dismiss();

        // For row of articles (2-3)
        LinearLayout deviceRowOrientation = new LinearLayout(eContext);
        deviceRowOrientation.setOrientation(LinearLayout.HORIZONTAL);
        deviceRowOrientation.setGravity(Gravity.CENTER);
        deviceRowOrientation.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));

        // Rendered all found articles onto the layout
        for(int index = 0; index < xmlArticles.size(); index++) {
            // For the header article
            if(index == 0) {
                articleCapsuleFiles.addView(new ArticleHeaderView(eContext, xmlArticles.get(index)));
            } else {
                // For trailing articles
                // Checks to see if device is a tablet or phone
                if(appGraphics.isTablet()) {
                    counter++;
                    deviceRowOrientation.addView(new ArticleTrailView(eContext, xmlArticles.get(index)));

                    // For a full row
                    if(counter == 3 || (index == (xmlArticles.size() - 1))) {
                        counter = 0;
                        articleCapsuleFiles.addView(deviceRowOrientation);

                        // New row is formed
                        deviceRowOrientation = new LinearLayout(eContext);
                        deviceRowOrientation.setOrientation(LinearLayout.HORIZONTAL);
                        deviceRowOrientation.setGravity(Gravity.CENTER);
                        deviceRowOrientation.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT));
                    }
                } else {
                    counter++;
                    deviceRowOrientation.addView(new ArticleTrailView(eContext, xmlArticles.get(index)));

                    // For a full row
                    if(counter == 2 || (index == (xmlArticles.size() - 1))) {
                        counter = 0;
                        articleCapsuleFiles.addView(deviceRowOrientation);

                        // New row is formed
                        deviceRowOrientation = new LinearLayout(eContext);
                        deviceRowOrientation.setOrientation(LinearLayout.HORIZONTAL);
                        deviceRowOrientation.setGravity(Gravity.CENTER);
                        deviceRowOrientation.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT));
                    }
                }
            }
        }
    }

}
