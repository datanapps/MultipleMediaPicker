package datanapps.mediapicker.utils;

import android.provider.MediaStore;

public class AppConstants {


    public static String RESULT = "result";

    public static String MODE = "mode";

    public static String MAX_SELECTION = "maxSelection";

    public static int MAX_MEDIA_COUNT = 10;


    public static  int OPEN_MEDIA_PICKER = 101;

    public static int OPEN_CAMERA_PICKER_IMAGE = 102;
    public static int OPEN_CAMERA_PICKER_VIDEO = 103;




    public static int OPEN_CAMERA_FOR_IMAGE = 1; // only camera for image
    public static int OPEN_CAMERA_FOR_VIDEOS = 2; // only camera for recording
    public static int OPEN_GALLERY_IMAGE = 3; // only select image from gallery
    public static int OPEN_GALLERY_VIDEOS = 4; // only select videos from gallery
    public static int OPEN_GALLERY_IMAGES_VIDEOS = 5; // only image and videos both
    public static int OPEN_FULL_MODE = 6; // camera for image, camera for video, gallery_images, gallery videos



    public static final String[] projectionBucket = new String[]{ MediaStore.Images.Media.BUCKET_DISPLAY_NAME, MediaStore.Images.Media.DATA };
    public static final String[] projectionImageName = new String[]{MediaStore.Images.Media.DISPLAY_NAME,MediaStore.Images.Media.DATA };


}
