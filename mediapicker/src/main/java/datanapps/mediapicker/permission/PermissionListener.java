package datanapps.mediapicker.permission;


public interface PermissionListener {
    void onPermissionGranted(String permissionName);

    void onPermissionDenied();
}
