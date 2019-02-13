package com.runtimepermission

import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.text.Html

open class RunTimePermission : AppCompatActivity() {

    private var callback: ((Boolean) -> Unit)? = null

    fun requestPermission(permissions: Array<String>, callback: (isGranted: Boolean) -> Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            var granted = true
            for (permission in permissions) {
                if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                    granted = false
                    break
                }
            }
            if (granted) {
                callback(true)
            } else {
                this.callback = callback
                requestPermissions(permissions, Integer.MAX_VALUE)
            }
        } else callback(true)

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Integer.MAX_VALUE) {
            var granted = true
            for (i in 0 until grantResults.size) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    granted = false
                    break
                }
            }
            if (granted)
                callback?.invoke(true)
            else onDenied()
        }
    }

    private fun onDenied() {
        callback?.invoke(false)
        setAlertMessage()
    }

    private fun setAlertMessage() {
        val adb = AlertDialog.Builder(this)
        adb.setTitle(getString(R.string.app_name))
        val msg = "<p>Dear User, </p>" +
                "<p>Seems like you have <b>\"Denied\"</b> the minimum requirement permission to access more features of application.</p>" +
                "<p>You must have to <b>\"Allow\"</b> all permission. We will not share your data with anyone else.</p>" +
                "<p>Do you want to enable all requirement permission ?</p>" +
                "<p>Go To : Settings >> App > " + getString(R.string.app_name) + " Permission : Allow ALL</p>"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            adb.setMessage(Html.fromHtml(msg, Html.FROM_HTML_MODE_LEGACY))
        else
            adb.setMessage(Html.fromHtml(msg))
        adb.setPositiveButton("Allow All") { dialog, _ ->
            dialog.dismiss()
            val intent = Intent()
            intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            val uri = Uri.fromParts("package", packageName, null)
            intent.data = uri
            startActivity(intent)
        }

        adb.setNegativeButton("Remind Me Later") { dialog, _ -> dialog.dismiss() }
        if (!isFinishing)
            adb.show()
    }
}