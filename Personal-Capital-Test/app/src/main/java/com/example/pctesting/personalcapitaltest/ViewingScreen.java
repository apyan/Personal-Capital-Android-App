package com.example.pctesting.personalcapitaltest;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.pctesting.personalcapitaltest.AppFunctions.AppConnect;
import com.example.pctesting.personalcapitaltest.AppFunctions.AsyncTaskLoadingXML;

public class ViewingScreen extends Activity {

    // Variables of Activity
    public String xmlSourceLink = "https://blog.personalcapital.com/feed/?cat=3,891,890,68,284";

    LinearLayout linearLayoutParent, navigationBarCapsule, articleContentHolder;
    RelativeLayout navigationBarLayout;
    LinearLayout.LayoutParams parentLayoutParams;
    RelativeLayout.LayoutParams focusParams, navigationBarParams;
    ScrollView scrollView_00;
    Button exitButton, refreshButton;
    View.OnClickListener exitClick, refreshClick;
    AppConnect appConnect;
    AsyncTaskLoadingXML seeker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appConnect = new AppConnect(this);

        // Set up the LinearLayout Parent root
        linearLayoutParent = new LinearLayout(this);
        linearLayoutParent.setOrientation(LinearLayout.VERTICAL);
        linearLayoutParent.setGravity(Gravity.TOP);
        parentLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);

        // Set up the Navigation Bar at the top
        navigationBarLayout = new RelativeLayout(this);
        navigationBarParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        navigationBarLayout.setBackgroundColor(Color.GRAY);
        navigationBarLayout.setLayoutParams(navigationBarParams);

        // Setting up the Exit Button
        focusParams = new RelativeLayout.LayoutParams(
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 120, getResources().getDisplayMetrics()),
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, getResources().getDisplayMetrics()));
        focusParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        exitButton = new Button(this);
        exitButton.setText(R.string.view_button_00);
        exitButton.setLayoutParams(focusParams);
        exitClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        };
        exitButton.setOnClickListener(exitClick);

        // Setting up the Refresh Button
        focusParams = new RelativeLayout.LayoutParams(
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 120, getResources().getDisplayMetrics()),
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, getResources().getDisplayMetrics()));
        focusParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        refreshButton = new Button(this);
        refreshButton.setText(R.string.view_button_01);
        refreshButton.setLayoutParams(focusParams);
        refreshClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Rest and reloads articles
                // Perform the Asynchronous Task to retrieve the XML data

                // Checks for connection first
                if(appConnect.connectionAvailable()) {

                    // Reset the Article Layout
                    articleContentHolder.removeAllViews();

                    seeker = new AsyncTaskLoadingXML(getBaseContext(), true, articleContentHolder);
                    seeker.execute(xmlSourceLink);
                } else {
                    // Notifies user about connection issue
                    Toast.makeText(getApplicationContext(),
                            getResources().getString(R.string.connect_00), Toast.LENGTH_SHORT).show();
                }
            }
        };
        refreshButton.setOnClickListener(refreshClick);

        // Set up the Navigation Bar Linear Capsule
        navigationBarCapsule = new LinearLayout(this);
        navigationBarCapsule.setOrientation(LinearLayout.VERTICAL);
        navigationBarCapsule.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));

        // Add the two buttons into the navigation bar at the top
        navigationBarLayout.addView(exitButton);
        navigationBarLayout.addView(refreshButton);
        navigationBarCapsule.addView(navigationBarLayout);
        linearLayoutParent.addView(navigationBarCapsule);

        // Setting up the ScrollView to place contents
        scrollView_00 = new ScrollView(this);
        scrollView_00.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                        0, getResources().getDisplayMetrics()), 1.0f));
        scrollView_00.setScrollbarFadingEnabled(false);

        // Sets up the LinearLayout that would hold the article contents
        // (This is where the articles will show up)
        articleContentHolder = new LinearLayout(this);
        articleContentHolder.setOrientation(LinearLayout.VERTICAL);
        articleContentHolder.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        scrollView_00.addView(articleContentHolder);

        // Add the ScrollView display
        linearLayoutParent.addView(scrollView_00);

        // And set the LinearLayout and LayoutParams as the root
        setContentView(linearLayoutParent, parentLayoutParams);

        // Perform the Asynchronous Task to retrieve the XML data
        // Checks for connection first
        if(appConnect.connectionAvailable()) {
            seeker = new AsyncTaskLoadingXML(this, false, articleContentHolder);
            seeker.execute(xmlSourceLink);
        } else {
            // Notifies user about connection issue
            Toast.makeText(this, getResources().getString(R.string.connect_00), Toast.LENGTH_SHORT).show();
        }
    }
}
