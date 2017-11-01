package com.example.pctesting.personalcapitaltest;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TitleScreen extends Activity {

    // Variables of Activity
    LinearLayout linearLayoutParent;
    LinearLayout.LayoutParams parentLayoutParams, childLayoutParams;
    TextView text_00, text_01, text_02;
    View.OnClickListener pressClick;
    Intent nextActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set up the LinearLayout Parent root
        linearLayoutParent = new LinearLayout(this);
        linearLayoutParent.setOrientation(LinearLayout.VERTICAL);
        linearLayoutParent.setGravity(Gravity.CENTER|Gravity.TOP);
        parentLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);

        // Affects the screen pressing by the user
        pressClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Head to the ViewingScreen activity
                nextActivity = new Intent(getApplicationContext(), ViewingScreen.class);
                startActivity(nextActivity);
                finish();
            }
        };
        linearLayoutParent.setOnClickListener(pressClick);

        // LayoutParams used by child element
        childLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        childLayoutParams.setMargins(0,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                        125, getResources().getDisplayMetrics()),
                0, 0);

        // Set up the TextView children
        text_00 = new TextView(this);
        text_00.setText(getString(R.string.app_title_00));
        text_00.setLayoutParams(childLayoutParams);
        text_00.setTextSize(TypedValue.COMPLEX_UNIT_SP, 32);
        linearLayoutParent.addView(text_00);

        text_01 = new TextView(this);
        text_01.setText(getString(R.string.app_title_01));
        text_01.setLayoutParams(childLayoutParams);
        text_01.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        linearLayoutParent.addView(text_01);

        text_02 = new TextView(this);
        text_02.setText(getString(R.string.app_proceed));
        text_02.setLayoutParams(childLayoutParams);
        text_02.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        linearLayoutParent.addView(text_02);

        // And set the LinearLayout and LayoutParams as the root
        setContentView(linearLayoutParent, parentLayoutParams);
    }
}
