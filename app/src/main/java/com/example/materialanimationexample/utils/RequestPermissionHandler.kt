package com.example.materialanimationexample.utils

import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.NonNull
import androidx.core.app.ActivityCompat

class RequestPermissionHandler {
    private var mActivity: Activity? = null
    private var mRequestPermissionListener: RequestPermissionListener? = null
    private var mRequestCode = 0
    fun requestPermission(activity: Activity?, @NonNull permissions: Array<String>, requestCode: Int, listener: RequestPermissionListener?) {
        mActivity = activity
        mRequestCode = requestCode
        mRequestPermissionListener = listener
        if (!needRequestRuntimePermissions()) {
            mRequestPermissionListener!!.onSuccess()
            return
        }
        requestUnGrantedPermissions(permissions, requestCode)
    }

    private fun needRequestRuntimePermissions(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
    }

    private fun requestUnGrantedPermissions(permissions: Array<String>, requestCode: Int) {
        val activity = mActivity ?: return
        val unGrantedPermissions = findUnGrantedPermissions(permissions)
        if (unGrantedPermissions.isEmpty()) {
            mRequestPermissionListener!!.onSuccess()
            return
        }
        ActivityCompat.requestPermissions(activity, unGrantedPermissions.toTypedArray(), requestCode)
    }

    private fun isPermissionGranted(permission: String): Boolean {
        val activity = mActivity ?: return false
        return (ActivityCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED)
    }

    private fun findUnGrantedPermissions(permissions: Array<String>): List<String> {
        val unGrantedPermissionList: MutableList<String> = ArrayList()
        for (permission in permissions) {
            if (!isPermissionGranted(permission)) {
                unGrantedPermissionList.add(permission)
            }
        }
        return unGrantedPermissionList
    }

    fun onRequestPermissionsResult(
        requestCode: Int, @NonNull permissions: Array<String?>?,
        @NonNull grantResults: IntArray
    ) {
        if (requestCode == mRequestCode) {
            if (grantResults.isNotEmpty()) {
                for (grantResult in grantResults) {
                    if (grantResult != PackageManager.PERMISSION_GRANTED) {
                        mRequestPermissionListener!!.onFailed()
                        return
                    }
                }
                mRequestPermissionListener!!.onSuccess()
            } else {
                mRequestPermissionListener!!.onFailed()
            }
        }
    }

    interface RequestPermissionListener {
        fun onSuccess()
        fun onFailed()
    }
}