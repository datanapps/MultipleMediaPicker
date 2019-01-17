package datanapps.mediapicker.utils;


import android.util.Log;

/*

 /*
 *  Yogendra
 *
 * https://github.com/datanapps
 *
 * 10-01-2019
 * This class is responsible to manage exception logs and error of application
 * */
public class ExceptionHandler {

    ExceptionHandler() {
        // nothing to do here
    }

    /**
     * Handle Exception
     *
     * @Param e exception of app
     */
    public static void handleException(Exception e) {
        if (e != null) {
            Log.e("DNA", e.getMessage());
        }
    }

    /**
     * Handle Exception
     *
     * @Param e exception of app
     */
    public static void handleException(Error e) {
        if (e != null) {
            Log.e("DNA", e.getMessage());
        }
    }

    /**
     * Handle Exception
     *
     * @Param e String of app
     */
    public static void handleException(String errorValue) {
        if (errorValue != null) {
            Log.e("DNA", errorValue);
        }
    }
}
