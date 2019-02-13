# RunTimePermission

1) Extend RunTimePermission in your Activity or Parent Activity(BaseActivity)
2) Pass required permission in string array

    requestPermission(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION)) { isGranted ->
   
        if (isGranted) {
            println("Permission granted")
        }
        else {
            println("Permission denied")
        }
    }
