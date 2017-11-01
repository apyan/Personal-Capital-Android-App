package com.example.pctesting.personalcapitaltest.AppFunctions;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.example.pctesting.personalcapitaltest.R;

/**
 * Asynchronous Task for loading the image from a source.
 */

public class AsyncTaskImageLoad extends AsyncTask<String, String, String> {

    // Variables of the Asynchronous Task
    private String errorMessage = "";
    Context eContext;
    ProgressDialog progressDialog;
    ImageView image_00;
    ProgressBar progressBar;
    AppGraphics appGraphics;
    Bitmap imageDownload;

    // Constructor
    public AsyncTaskImageLoad(Context context, ImageView imageView, ProgressBar imageProgress) {
        eContext = context;
        image_00 = imageView;
        appGraphics = new AppGraphics(context);
        progressBar = imageProgress;
    }

    @Override
    protected void onPreExecute() {
        /*progressDialog = ProgressDialog.show(eContext,
                eContext.getResources().getString(R.string.progress_search_00),
                eContext.getResources().getString(R.string.progress_search_01));*/
    }

    @Override
    protected String doInBackground(String... params) {

        // Download needed contents
        imageDownload = appGraphics.getBitmapFromURL(params[0]);
        imageDownload = appGraphics.getResizedBitmap(imageDownload, 780, 300);

        return errorMessage;
    }

    @Override
    protected void onPostExecute(String args) {

        // Uploads image onto article
        progressBar.setVisibility(View.INVISIBLE);
        image_00.setImageBitmap(imageDownload);

        // Dismiss Progress Dialog
        //progressDialog.dismiss();
    }

}
