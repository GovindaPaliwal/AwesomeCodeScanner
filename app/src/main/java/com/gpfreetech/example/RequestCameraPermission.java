package com.gpfreetech.example;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

public class RequestCameraPermission {

    private Activity activity;
    // Storage Permissions
    private static final int REQUEST_CAMERA = 1;
    private static String[] PERMISSIONS = {
            Manifest.permission.CAMERA
    };

    public RequestCameraPermission(Activity activity) {
        this.activity = activity;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public boolean verifyCameraPermission() {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.CAMERA);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS,
                    REQUEST_CAMERA
            );
            return false;
        } else {
            return true;
        }
    }
}
