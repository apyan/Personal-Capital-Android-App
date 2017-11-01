package com.example.pctesting.personalcapitaltest.AppFunctions;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * This class is used for connection purposes.
 */

public class AppConnect {

    // Variables for the class
    Context eContext;

    // Constructor
    public AppConnect(Context context) {
        eContext = context;
    }

    // Checking for Internet Connectivity
    public boolean connectionAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                eContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return ((activeNetworkInfo != null) && (activeNetworkInfo.isConnected()));
    }
}
