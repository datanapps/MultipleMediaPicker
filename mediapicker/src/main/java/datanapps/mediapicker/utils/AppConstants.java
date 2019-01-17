package datanapps.mediapicker.utils;

import android.provider.MediaStore;

public class AppConstants {


    public static String RESULT = "result";

    public static String TITLE = "title";

    public static String TITLE_VALUE = "Select Gallery";

    public static String MODE = "mode";

    public static String MAX_SELECTION = "maxSelection";

    public static int MAX_MEDIA_COUNT = 10;


    public static  int OPEN_MEDIA_PICKER = 101;

    public static int OPEN_CAMERA_PICKER_IMAGE = 102;
    public static int OPEN_CAMERA_PICKER_VIDEO = 103;



    public static int OPEN_CAMERA = 1;
    public static int OPEN_VIDEO = 2;



    public static final String[] projectionBucket = new String[]{ MediaStore.Images.Media.BUCKET_DISPLAY_NAME, MediaStore.Images.Media.DATA };
    public static final String[] projectionImageName = new String[]{MediaStore.Images.Media.DISPLAY_NAME,MediaStore.Images.Media.DATA };


}
