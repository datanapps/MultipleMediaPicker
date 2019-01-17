# MultipleMediaPicker
A complete AndroidMultipleMediaPicker with Marshmallow Permission handling, record a video from camera, Capture Image from camera, multiple image and multiple video picker from gallery in one module

For camera :

![alt text](https://github.com/datanapps/MultipleMediaPicker/blob/master/screens/camera_1.gif)

For Multiple media picker All (Camera, camera video, images, videos):
![alt text](https://github.com/datanapps/MultipleMediaPicker/blob/master/screens/camera_2.gif)


![alt text](https://github.com/datanapps/MultipleMediaPicker/blob/master/screens/camera_3.gif)


![alt text](https://github.com/datanapps/MultipleMediaPicker/blob/master/screens/camera_4.gif)


# How To Use 

1. Copy mediapicker in your application as module.

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
    
    
