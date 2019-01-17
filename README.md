# MultipleMediaPicker
A complete AndroidMultipleMediaPicker with Marshmallow Permission handling, record a video from camera, capture an image from camera, select multiple images and multiple videos from gallery in one module.

For camera :

![alt text](https://github.com/datanapps/MultipleMediaPicker/blob/master/screens/camera_1.gif)

For Multiple media picker All (Camera, camera video, images, videos):

![alt text](https://github.com/datanapps/MultipleMediaPicker/blob/master/screens/camera_2.gif)


![alt text](https://github.com/datanapps/MultipleMediaPicker/blob/master/screens/camera_3.gif)


![alt text](https://github.com/datanapps/MultipleMediaPicker/blob/master/screens/camera_4.gif)


# How To Use 

1. Copy mediapicker (module folder) in your application as module.

2. Add module in your application build file.

implementation project(':mediapicker')


3. Call below method at your button tap.



       void openDNAMediaPicker() {
        Intent intent = new Intent(this, DNAGalleryPickerActivity.class);  
        intent.putExtra(AppConstants.MODE, AppConstants.OPEN_FULL_MODE);
        intent.putExtra(AppConstants.MAX_SELECTION, AppConstants.MAX_MEDIA_COUNT); // default 5
        startActivityForResult(intent, AppConstants.OPEN_MEDIA_PICKER);
        }
      
    4. To Received MediaFile override activity result:
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == AppConstants.OPEN_MEDIA_PICKER) {
            // Make sure the request was successful
            if (resultCode == Activity.RESULT_OK && data != null) {
                List<String> path = data.getStringArrayListExtra(AppConstants.RESULT);
                if (path != null) {
                    selectedMedia.clear();
                    selectedMedia.addAll(path);
                    updateImageList();
                }
            }
        }
    }
    
    # Options
    
   1. It can be work for below mode:
   
    public static int OPEN_CAMERA_FOR_IMAGE = 1; // only camera for image
    
    public static int OPEN_CAMERA_FOR_VIDEOS = 2; // only camera for recording
    
    public static int OPEN_GALLERY_IMAGE = 3; // only select image from gallery
    
    public static int OPEN_GALLERY_VIDEOS = 4; // only select videos from gallery
    
    public static int OPEN_GALLERY_IMAGES_VIDEOS = 5; // only image and videos both
    
    public static int OPEN_FULL_MODE = 6; // camera for image, camera for video, gallery_images, gallery videos
   
   
   
   intent.putExtra(AppConstants.MODE, AppConstants.OPEN_FULL_MODE);
   
   2.    Its handle marshmallow runtime permission.
   3.   You can set no of selection. default file selection is 5.
  
    intent.putExtra(AppConstants.MAX_SELECTION, AppConstants.MAX_MEDIA_COUNT); 
   
   
    
    
    https://github.com/datanapps/MultipleMediaPicker/blob/master/LICENSE
    
