package datanapps.mediapicker.utils;

import android.content.Context;

import datanapps.mediapicker.R;

public class Utility {

    /*
     *
     * This is utils to manage grid coloumn in media display
     *
     * */
 public static int calculateNoOfColumns(Context context) {
        if(context==null){
            return 3;
        }
        return context.getResources().getBoolean(R.bool.isTablet) ? 5:3;
    }
}
