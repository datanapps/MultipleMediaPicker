package datanapps.mediapicker.permission;

/*
 *  Yogendra
 *
 * https://github.com/datanapps
 *
 * 10-01-2019 */


// handling permission request

public interface PermissionListener {
    void onPermissionGranted(String permissionName);

    void onPermissionDenied();
}
