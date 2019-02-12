package com.runtimepermission

import android.os.Bundle


class MainActivity : RunTimePermission() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestPermission(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION)) { isGranted ->
            if (isGranted) {
                println("Permission granted")
            } else {
                println("Permission denied")
            }
        }
    }
}
