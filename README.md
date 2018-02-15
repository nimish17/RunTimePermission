# RunTimePermission

1) Extend RunTimePermission in your Activity or Parent Activity(BaseActivity)
2) Pass required permission in string array

        requestPermission(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, new PermissionCallback() {

            @Override
            public void onGranted() {

            }

            @Override
            public void onDenied() {

            }
        });
