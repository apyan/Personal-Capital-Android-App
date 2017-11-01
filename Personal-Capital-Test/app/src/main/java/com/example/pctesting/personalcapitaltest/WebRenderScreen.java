package com.example.pctesting.personalcapitaltest;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.example.pctesting.personalcapitaltest.AppObjects.ItemArticleXML;

public class WebRenderScreen extends Activity {

    // Variables of Activity
    LinearLayout linearLayoutParent;
    LinearLayout.LayoutParams parentLayoutParams;
    String htmlHeader;
    WebView articleView;
    WebSettings articleSettings;
    ItemArticleXML articleTrack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Gather the article information
        articleTrack = (ItemArticleXML) getIntent().getParcelableExtra("seekArticle");

        // Set up the LinearLayout Parent root
        linearLayoutParent = new LinearLayout(this);
        linearLayoutParent.setOrientation(LinearLayout.VERTICAL);
        linearLayoutParent.setGravity(Gravity.TOP);
        linearLayoutParent.setBackgroundColor(Color.WHITE);
        parentLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);

        // Form the Title and Publish Date in HTML format
        htmlHeader = "<h1>" + articleTrack.titleTag + "</h1><h3>" + articleTrack.pubDateTag + "</h3>";

        // Set up WebView
        articleView = new WebView(this);
        articleView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        articleSettings = articleView.getSettings();
        articleSettings.setDefaultTextEncodingName("utf-8");
        articleView.loadData(htmlHeader + articleTrack.contentEncoded, "text/html; charset=utf-8",null);
        linearLayoutParent.addView(articleView);

        // And set the LinearLayout and LayoutParams as the root
        setContentView(linearLayoutParent, parentLayoutParams);
    }
}
