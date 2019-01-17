package datanapps.mediapicker.permission;


import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PermissionInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import datanapps.mediapicker.R;
import datanapps.mediapicker.utils.ExceptionHandler;




/*
 *
 *  Yogendra
 *
 * https://github.com/datanapps
 *
 * 10-01-2019
 * This class is base class of activities for project. All other activities extend this class
 * **/

public class RequestPermissionActivity extends AppCompatActivity {
    protected PermissionListener permissionListener;
    public static final int PERMISSIONS_REQUEST = 11;
    /**
     * Create all class objects.
     */
    private AlertDialog permissionAlert;
    private String[] permissions;

    /**
     * This method is used to check that this app has permission or not.
     */
    public boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    public void checkRunTimePermissions(String[] permissions) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M || permissions == null) {
            return;
        }
        this.permissions = permissions;
        if (!hasPermissions(this, permissions)) {
            ActivityCompat.requestPermissions(this, permissions, PERMISSIONS_REQUEST);
        }
    }

    public void setPermissionGrantedListener(PermissionListener listener) {
        this.permissionListener = listener;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_REQUEST && permissions.length > 0) {
            boolean isGranted = true;
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    isGranted = (i == 0) || isGranted;
                } else {
                    isGranted = false;
                }
            }
            if (isGranted) {
                permissionListener.onPermissionGranted("");
            } else {
                denyPermission(permissions.length - 1);
            }
        }
    }

    /**
     * This method is user show popup if permission denied by user.
     */
    @TargetApi(Build.VERSION_CODES.M)
    private void denyPermission(int i) {
        if (permissions != null && permissions.length > 0) {
            boolean showRationale = shouldShowRequestPermissionRationale(permissions[i]);
            if (showRationale)
                showAlertForPermission();
            else
                showAlertForPermissionAndShowSettings(getPermissionListName());
        }
    }

    /**
     * This method is user to show popup to and move to set app permission
     */
    private void showAlertForPermission() {
        if (permissionAlert == null) {
            permissionAlert = showAlertWithListener(this, getString(R
                    .string.msg_permission), getString(R.string.set_permission), getString(android.R.string.no), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    checkRunTimePermissions(permissions);
                }
            }, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    permissionListener.onPermissionDenied();
                }
            });
            permissionAlert.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    permissionAlert = null;
                }
            });
            permissionAlert.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    permissionAlert = null;
                }
            });
            permissionAlert.show();
        }
    }

    /**
     * This method is user to show popup to and move to app settings
     */
    private void showAlertForPermissionAndShowSettings(String permissionName) {
        if (permissionAlert == null) {
            permissionAlert = showAlertWithListener(this, permissionName + "\n" + getString(R.string.msg_permission_with_settings), getString(R.string.set_permission), getString(android.R.string.no), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    openAppSettingPage();
                }
            }, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    permissionListener.onPermissionDenied();
                }
            });
            permissionAlert.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    permissionAlert = null;
                }
            });
            permissionAlert.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    permissionAlert = null;
                }
            });
            permissionAlert.show();
        }
    }

    /**
     * This method is used to get user readable name of given permission
     */
    private String getPermissionLabel(String permission) {
        try {
            PackageManager packageManager = getPackageManager();
            PermissionInfo permissionInfo = packageManager.getPermissionInfo(permission, 0);
            return permissionInfo.loadLabel(packageManager).toString();
        } catch (PackageManager.NameNotFoundException e) {
            ExceptionHandler.handleException(e);
        }
        return "";
    }

    /**
     * This method is used to get user all readable permission name
     */
    private String getPermissionListName() {
        StringBuilder permissionName = new StringBuilder();
        for (String permission : permissions) {
            permissionName.append(getPermissionLabel(permission) + ",");
        }
        return permissionName.toString();
    }

    /**
     * This method is used to move app setting page to set app permission
     */
    private void openAppSettingPage() {
        Intent i = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        i.setData(Uri.parse("package:" + getPackageName()));
        startActivityForResult(i, PERMISSIONS_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PERMISSIONS_REQUEST) {
            checkRunTimePermissions(permissions);
        }


    }


    private AlertDialog showAlertWithListener(Context context, String message, String positiveBtnText, String negativeBtnText, DialogInterface.OnClickListener positiveListener, DialogInterface.OnClickListener negativeListener) {

        AlertDialog alertDialog;
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setIcon(R.drawable.ic_launcher)
                .setTitle(context.getString(R.string.app_name))
                .setMessage(message);
        if (positiveBtnText != null && positiveListener != null) {
            alert.setPositiveButton(positiveBtnText, positiveListener);
        }
        if (negativeBtnText != null && negativeListener != null) {
            alert.setNegativeButton(negativeBtnText, negativeListener);
        }
        if (positiveListener == null && negativeListener == null) {
            alert.setNegativeButton(android.R.string.ok, null);
        }
        alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);
        return alertDialog;
    }


}
