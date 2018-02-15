package com.runtimepermission;

import android.os.Bundle;

public class MainActivity extends RunTimePermission {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestPermission(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, new PermissionCallback() {
            @Override
            public void onGranted() {

            }

            @Override
            public void onDenied() {

            }
        });
    }
}
