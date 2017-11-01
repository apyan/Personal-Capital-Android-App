package com.example.pctesting.personalcapitaltest.AppObjects;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pctesting.personalcapitaltest.AppFunctions.AppConnect;
import com.example.pctesting.personalcapitaltest.AppFunctions.AppGraphics;
import com.example.pctesting.personalcapitaltest.AppFunctions.AsyncTaskImageLoad;
import com.example.pctesting.personalcapitaltest.R;
import com.example.pctesting.personalcapitaltest.ViewingScreen;
import com.example.pctesting.personalcapitaltest.WebRenderScreen;

/**
 * A custom view for articles that are not first
 */

public class ArticleTrailView extends LinearLayout {

    // Variables
    AppGraphics appGraphics;
    AppConnect appConnect;
    ImageView imageHead;
    TextView titleHead;
    View.OnClickListener pressClick;
    LayoutParams params, textParams;
    Intent webRenderer;
    ItemArticleXML itemArticleXML;
    RelativeLayout imageHolder;
    RelativeLayout.LayoutParams imageParams;
    ProgressBar imageProgress;

    public ArticleTrailView(final Context context, final ItemArticleXML xmlItem) {

        // Initialize the LinearLayout for the article
        super(context);
        appGraphics = new AppGraphics(context);
        appConnect = new AppConnect(context);
        setOrientation(LinearLayout.VERTICAL);
        setBackgroundColor(Color.WHITE);
        params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.setMargins(0, (1 * appGraphics.getFullHeight()) / 200, (1 * appGraphics.getFullWidth()) / 255, 0);
        setLayoutParams(params);
        itemArticleXML = xmlItem;

        // Affects the screen pressing by the user
        pressClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Head to the WebRenderer activity
                // But check for connection
                if(appConnect.connectionAvailable()) {
                    webRenderer = new Intent(context, WebRenderScreen.class);
                    webRenderer.putExtra("seekArticle", xmlItem);
                    context.startActivity(webRenderer);
                } else {
                    // Notifies user about connection issue
                    Toast.makeText(context,
                            getResources().getString(R.string.connect_00), Toast.LENGTH_SHORT).show();
                }
            }
        };
        setOnClickListener(pressClick);

        // The Image header
        imageHolder = new RelativeLayout(context);
        imageParams = new RelativeLayout.LayoutParams( RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        imageParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        imageHolder.setLayoutParams(imageParams);
        imageHolder.setGravity(Gravity.CENTER);
        imageHead = new ImageView(context);

        // Checks to see if device is either a tablet or phone
        if(appGraphics.isTablet()) {
            imageHead.setLayoutParams(new LayoutParams(((33 * appGraphics.getFullWidth()) / 100),
                    ((16 * appGraphics.getFullHeight()) / 100)));
        } else {
            imageHead.setLayoutParams(new LayoutParams(((49 * appGraphics.getFullWidth()) / 100),
                    ((106 * appGraphics.getFullHeight()) / 1000)));
        }

        // Sets up the ProgressBar
        imageProgress = new ProgressBar(context);
        imageProgress.setLayoutParams(new LayoutParams(((7 * appGraphics.getFullWidth()) / 100),
                ((7 * appGraphics.getFullWidth()) / 100)));
        imageProgress.setMinimumWidth((7 * appGraphics.getFullWidth()) / 100);
        imageProgress.setMinimumHeight((7 * appGraphics.getFullWidth()) / 100);

        imageHolder.addView(imageHead);
        imageHolder.addView(imageProgress);
        addView(imageHolder);

        // Asynchronously downloads image
        AsyncTaskImageLoad imageLoad = new AsyncTaskImageLoad(context, imageHead, imageProgress);
        imageLoad.execute(xmlItem.imageContentTag);

        // The Title header
        titleHead = new TextView(context);
        // Checks to see if device is either a tablet or phone
        if(appGraphics.isTablet()) {
            textParams = new LayoutParams(((33 * appGraphics.getFullWidth()) / 100),
                    ((16 * appGraphics.getFullWidth()) / 100));
        } else {
            textParams = new LayoutParams(((49 * appGraphics.getFullWidth()) / 100),
                    ((13 * appGraphics.getFullWidth()) / 100));
        }

        textParams.setMargins(0, 0, 0, 0);
        titleHead.setLayoutParams(textParams);
        titleHead.setText(itemArticleXML.titleTag);
        titleHead.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        titleHead.setTextColor(Color.BLACK);
        titleHead.setMaxLines(2);
        titleHead.setEllipsize(TextUtils.TruncateAt.END);
        addView(titleHead);
    }

}
