package com.example.materialanimationexample.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.activity.result.ActivityResultLauncher

fun Activity.requestPermission(startForResult: ActivityResultLauncher<Intent>) {
    val hasPermission = hasPermission()
    if (hasPermission == null && Build.VERSION.SDK_INT > 21) {
        startForResult.launch(Intent(Intent.ACTION_OPEN_DOCUMENT_TREE))
    }
    if (hasPermission == true) {

    } else {

    }
}

fun Activity.hasPermission(): Boolean? {
    val highApiAndroid = isHighApiAndroid()
    if (highApiAndroid == null) {
        return null
    } else {
        if (highApiAndroid) {
            return true
        } else {
            return true
        }
    }
}

fun isHighApiAndroid(): Boolean? {
    if (Build.VERSION.SDK_INT == Build.VERSION_CODES.Q) return null
    return Build.VERSION.SDK_INT > Build.VERSION_CODES.Q
}