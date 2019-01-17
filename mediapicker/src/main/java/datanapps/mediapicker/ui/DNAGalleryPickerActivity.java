package datanapps.mediapicker.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.FileProvider;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import datanapps.mediapicker.R;
import datanapps.mediapicker.permission.PermissionListener;
import datanapps.mediapicker.permission.RequestPermissionActivity;
import datanapps.mediapicker.utils.AppConstants;
import datanapps.mediapicker.utils.ExceptionHandler;

import java.io.File;
import java.util.ArrayList;
import java.util.List;



/*
 *  Yogendra
 *
 * https://github.com/datanapps
 *
 * 10-01-2019 */

public class DNAGalleryPickerActivity extends RequestPermissionActivity {
    private TabLayout tabLayout;
    private ViewPager vpMediaPicker;


    public static int maxSelection;
    public static int mode;

    public static int selectionTitle;
    private String imageFilePath;


    private View viewRecordingCamera;
    private View viewImageCamera;


    private  Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_gallery_picker);


        setPermissionGrantedListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(String permissionName) {

                // after got permission
                setupBasicsUI();
            }

            @Override
            public void onPermissionDenied() {
                // do your code
                finish();
            }
        });

        String[] permissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};
        if (!hasPermissions(this, permissions)) {
            checkRunTimePermissions(permissions);
        } else {

            setupBasicsUI();

        }

    }


    private void setupBasicsUI() {
        // update tool bar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        setTitle(getString(R.string.select_gallery));
        toolbar.setVisibility(View.INVISIBLE);

        findViewById(R.id.activity_gallery_picker_done).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResultAndFinish();
            }
        });

        viewRecordingCamera = findViewById(R.id.activity_gallery_picker_video);

        viewRecordingCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkCameraPermission(AppConstants.OPEN_CAMERA_FOR_VIDEOS);
            }
        });

        viewImageCamera = findViewById(R.id.activity_gallery_picker_camera);

        viewImageCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkCameraPermission(AppConstants.OPEN_CAMERA_FOR_IMAGE);
            }
        });


        maxSelection = getIntent().getExtras().getInt("maxSelection", 5);

        if (maxSelection == 0) maxSelection = Integer.MAX_VALUE;
        mode = getIntent().getExtras().getInt("mode");

        selectionTitle = 0;


// remove selected media list
        SubMediaGalleryActivity.selected.clear();
        SubMediaGalleryActivity.imagesSelected.clear();
        // prepare viewPage
        setupViewPager();
    }

    private void checkCameraPermission(final int type) {

        setPermissionGrantedListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(String permissionName) {

                if (type == AppConstants.OPEN_CAMERA_FOR_IMAGE) {
                    OpenCameraForImage();
                } else {
                    OpenCameraForVideo();
                }
            }

            @Override
            public void onPermissionDenied() {
                // do your code
                finish();
            }
        });

        String[] permissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (!hasPermissions(this, permissions)) {
            checkRunTimePermissions(permissions);
        } else {

            if (type == AppConstants.OPEN_CAMERA_FOR_IMAGE) {
                OpenCameraForImage();
            } else {
                OpenCameraForVideo();
            }

        }
    }

    private void OpenCameraForImage() {
        // 1 for camera image
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

        File photoFile = createImageFile(getExternalFilesDir(Environment.DIRECTORY_PICTURES), ".jpg");
        if (photoFile != null) {
            imageFilePath = photoFile.getAbsolutePath();
            Uri photoURI = FileProvider.getUriForFile(DNAGalleryPickerActivity.this, getPackageName() + ".provider", photoFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT,
                    photoURI);
            startActivityForResult(intent, AppConstants.OPEN_CAMERA_PICKER_IMAGE);
        }
    }


    private void OpenCameraForVideo() {
        // 1 for camera image
        Intent videoIntent = new Intent(android.provider.MediaStore.ACTION_VIDEO_CAPTURE);

        File photoFile = createImageFile(getExternalFilesDir(Environment.DIRECTORY_PICTURES), ".mp4");
        if (photoFile != null) {
            imageFilePath = photoFile.getAbsolutePath();
            Uri photoURI = FileProvider.getUriForFile(DNAGalleryPickerActivity.this, getPackageName() + ".provider", photoFile);
            videoIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                    photoURI);
            startActivityForResult(videoIntent, AppConstants.OPEN_CAMERA_PICKER_VIDEO);
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (selectionTitle > 0) {
            setTitle(String.valueOf(selectionTitle + "/" + maxSelection));
        }
    }

    //This method set up the tab view for images and videos
    private void setupViewPager() {

        vpMediaPicker = findViewById(R.id.activity_gallery_picker_vp_pager);

        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(vpMediaPicker);
        MediaPickerPagerAdapter adapter = new MediaPickerPagerAdapter(getSupportFragmentManager());

// for full mode
        toolbar.setVisibility(View.VISIBLE);
        if (mode == AppConstants.OPEN_FULL_MODE) {
            adapter.addFragment(new ImageFragment(), getString(R.string.images));
            adapter.addFragment(new VideoFragment(), getString(R.string.videos));

            viewImageCamera.setVisibility(View.VISIBLE);
            viewRecordingCamera.setVisibility(View.VISIBLE);


        } else if (mode == AppConstants.OPEN_CAMERA_FOR_IMAGE) {
            // camera only
            checkCameraPermission(AppConstants.OPEN_CAMERA_FOR_IMAGE);
            toolbar.setVisibility(View.INVISIBLE);
        } else if (mode == AppConstants.OPEN_CAMERA_FOR_VIDEOS) {
            // videos
            checkCameraPermission(AppConstants.OPEN_CAMERA_FOR_VIDEOS);
            toolbar.setVisibility(View.INVISIBLE);
        }
        // for image tab
        else if (mode == AppConstants.OPEN_GALLERY_IMAGE || mode == AppConstants.OPEN_GALLERY_IMAGES_VIDEOS) {
            //
            adapter.addFragment(new ImageFragment(), getString(R.string.images));

            viewImageCamera.setVisibility(View.GONE);
            viewRecordingCamera.setVisibility(View.GONE);
        }
        if (mode == AppConstants.OPEN_GALLERY_VIDEOS || mode == AppConstants.OPEN_GALLERY_IMAGES_VIDEOS) {
            adapter.addFragment(new VideoFragment(), getString(R.string.videos));
            viewImageCamera.setVisibility(View.GONE);
            viewRecordingCamera.setVisibility(View.GONE);
        }


        vpMediaPicker.setAdapter(adapter);
    }

    class MediaPickerPagerAdapter extends FragmentPagerAdapter {
        private final ArrayList<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public MediaPickerPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    private void setResultAndFinish() {
        Intent returnIntent = new Intent();
        returnIntent.putStringArrayListExtra(AppConstants.RESULT, SubMediaGalleryActivity.imagesSelected);
        setResult(RESULT_OK, returnIntent);
        finish();
    }


    public static File createImageFile(File dir, String suffix) {
        try {
            File image = File.createTempFile(
                    "IMG_" + System.currentTimeMillis(),  /* prefix */
                    suffix,         /* suffix */
                    dir     /* directory */
            );
            return image;
        } catch (Exception e) {
            ExceptionHandler.handleException(e);
        }

        return null;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == AppConstants.OPEN_CAMERA_PICKER_IMAGE) {
            // Make sure the request was successful
            if (resultCode == Activity.RESULT_OK) {
                SubMediaGalleryActivity.imagesSelected.add(imageFilePath);
                setResultAndFinish();
            }
            else{
                finish();
            }
        } else if (requestCode == AppConstants.OPEN_CAMERA_PICKER_VIDEO) {
            // Make sure the request was successful
            if (resultCode == Activity.RESULT_OK) {
                SubMediaGalleryActivity.imagesSelected.add(imageFilePath);
                setResultAndFinish();
            }
            else{
                finish();
            }
        }
    }
}