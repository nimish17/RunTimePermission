package com.runtimepermission;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;


public class RunTimePermission extends AppCompatActivity {

    private PermissionCallback callback;

    public void requestPermission(String[] permissions, PermissionCallback callback) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            boolean granted = true;
            for (String permission : permissions) {
                if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                    granted = false;
                    break;
                }
            }
            if (granted) {
                callback.onGranted();
            } else {
                this.callback = callback;
                requestPermissions(permissions, Integer.MAX_VALUE);
            }
        } else
            callback.onGranted();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (null != callback && requestCode == Integer.MAX_VALUE) {
            boolean granted = true;
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    granted = false;
                    break;
                }
            }
            if (granted)
                callback.onGranted();
            else onDenied();
        }
    }

    private void onDenied() {
        callback.onDenied();
        setAlertMessage();
    }

    private void setAlertMessage() {
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        adb.setTitle(getString(R.string.app_name));
        String msg = "<p>Dear User, </p>" +
                "<p>Seems like you have <b>\"Denied\"</b> the minimum requirement permission to access more features of application.</p>" +
                "<p>You must have to <b>\"Allow\"</b> all permission. We will not share your data with anyone else.</p>" +
                "<p>Do you want to enable all requirement permission ?</p>" +
                "<p>Go To : Settings >> App > " + getString(R.string.app_name) + " Permission : Allow ALL</p>";

        adb.setMessage(Html.fromHtml(msg));
        adb.setPositiveButton("Allow All", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.dismiss();
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            }
        });

        adb.setNegativeButton("Remind Me Later", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.dismiss();
            }
        });
        if (!isFinishing())
            adb.show();
    }

    public interface PermissionCallback {

        void onGranted();

        void onDenied();
    }
}

