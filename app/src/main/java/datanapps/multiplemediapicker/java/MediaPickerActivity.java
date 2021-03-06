package datanapps.multiplemediapicker.java;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import datanapps.mediapicker.utils.AppConstants;
import datanapps.mediapicker.ui.DNAGalleryPickerActivity;

import java.util.ArrayList;
import java.util.List;


import datanapps.multiplemediapicker.R;


/*
* This is main screen which is need to select media from gallery
*
* */
public class MediaPickerActivity extends AppCompatActivity {


    /*
    * List of selected media path list
    * */
    private List<String> selectedMedia;

    /*
    *
    * Recycle view which will display images and video after selected
    * */
    private RecyclerView recyclerView;
    private SelectedMediaListAdapter selectedMediaListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_picker);
        selectedMedia = new ArrayList<>();


        /*
        * Click button to open gallery
        * */
        findViewById(R.id.activity_media_picker_select_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMediaFile();
            }
        });


        setRecycleViewImageList();
    }

    private void setRecycleViewImageList() {
        recyclerView = findViewById(R.id.activity_media_picker_media_list);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        selectedMediaListAdapter = new SelectedMediaListAdapter(this);
        recyclerView.setAdapter(selectedMediaListAdapter);
    }


    /*
    * Open media gallery
    *
    * */

    void openMediaFile() {
        Intent intent = new Intent(this, DNAGalleryPickerActivity.class);

        //intent.putExtra(AppConstants.MODE, AppConstants.OPEN_FULL_MODE);
        //intent.putExtra(AppConstants.MAX_SELECTION, AppConstants.MAX_MEDIA_COUNT); // default 5
        startActivityForResult(intent, AppConstants.OPEN_MEDIA_PICKER);
    }


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


    /*
    *
    * this update selected media path in adapter to refresh selected media list
    * */
    private void updateImageList() {
        selectedMediaListAdapter.setMediaList(selectedMedia);
    }
}
