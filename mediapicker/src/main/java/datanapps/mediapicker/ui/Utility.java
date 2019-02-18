package datanapps.mediapicker.ui;

import android.content.Context;

import datanapps.mediapicker.R;

public class Utility {

    public static int calculateNoOfColumns(Context context) { // For example columnWidthdp=180
        int noOfColumns =context.getResources().getBoolean(R.bool.isTablet) ? 5:3;
        return noOfColumns;
    }
}
