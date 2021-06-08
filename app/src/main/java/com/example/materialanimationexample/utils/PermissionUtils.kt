package com.example.materialanimationexample.utils

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.documentfile.provider.DocumentFile
import com.bumptech.glide.Glide
import com.example.materialanimationexample.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.HashSet

fun ImageView.loadUri(uri: Uri?) {
    if (uri != null) {
        Glide.with(context)
            .load(uri)
            .placeholder(R.color.white)
            .into(this)
    }
}

fun Context.actionViewFile(uri: Uri, mineType: String) {
    val intent = Intent(Intent.ACTION_VIEW)
    intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
    intent.setDataAndType(uri, mineType)
    startActivity(Intent.createChooser(intent, "view action"))
}

fun Context.requestPermission(startForResult: ActivityResultLauncher<Intent>) {
    if (hasPermission()) {
        startForResult.launch(Intent(Intent.ACTION_OPEN_DOCUMENT_TREE))
    } else {

    }
}

fun Context.getUriFromAction(startForResult: ActivityResultLauncher<Intent>) {
    startForResult.launch(Intent(Intent.ACTION_OPEN_DOCUMENT_TREE))
}

suspend fun Context.queryFileImage() : List<DocumentFile> {
   return withContext(Dispatchers.Default){
        val volumeName: String = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val externalVolumeNames = MediaStore.getExternalVolumeNames(this@queryFileImage)

            externalVolumeNames.toList()[0]
        } else {
            "external"
        }
        val uriExternal = MediaStore.Images.Media.getContentUri(volumeName)
        val projection: Array<String> = arrayOf(
            MediaStore.Images.ImageColumns._ID,
            MediaStore.Images.ImageColumns.DISPLAY_NAME
        )
        val listImage = ArrayList<DocumentFile>()

        contentResolver.query(
            uriExternal,
            projection,
            null,
            null,
            null
        )?.use { cursor ->
            val columnID = cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns._ID)
            val columTitle = cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.DISPLAY_NAME)
            if (cursor.moveToFirst()) {
                do {
                    val displayname = cursor.getString(columTitle)
                    val itemId = cursor.getInt(columnID)
                    Log.d("abc", "$displayname")
                    val uri = uriExternal.buildUpon().appendPath(itemId.toString()).build()
                    val fromSingleUri = DocumentFile.fromSingleUri(this@queryFileImage, uri) ?: continue
                    listImage.add(fromSingleUri)
                } while (cursor.moveToNext())
            }
        }
        return@withContext listImage
    }
}

fun Context.queryFileInStore() {
    val volumeName: String = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        val externalVolumeNames = MediaStore.getExternalVolumeNames(this)

        externalVolumeNames.toList()[0]
    } else {
        "external"
    }
    val uriExternal = MediaStore.Files.getContentUri("external")
    val projection: Array<String> = arrayOf(
        MediaStore.Files.FileColumns._ID,
        MediaStore.Files.FileColumns.DISPLAY_NAME,
        MediaStore.Files.FileColumns.MIME_TYPE
    )

    contentResolver.query(
        uriExternal,
        projection,
        null,
        null,
        null
    )?.use { cursor ->
        val columnID = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns._ID)
        val columTitle = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DISPLAY_NAME)
        val columMine = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.MIME_TYPE)
        if (cursor.moveToFirst()) {
            do {
                val titleValue = cursor.getString(columTitle)
                val audioId = cursor.getInt(columnID)
                val mineId = cursor.getString(columMine)
                Log.d("abc", "$mineId")

            } while (cursor.moveToNext())
        }
    }
}

fun Context.hasPermission(): Boolean {
    val highApiAndroid = isHighApiAndroid()
    if (highApiAndroid) {
        return true
    } else {
        return true
    }
}

fun isHighApiAndroid(): Boolean {
    return Build.VERSION.SDK_INT > Build.VERSION_CODES.P
}

object PreferencesHelper {

    private const val SHARED_PREFERENCES_NAME = "file_manager"

    private lateinit var sharedPreferences: SharedPreferences

    fun start(appContext: Context) {
        sharedPreferences =
            appContext.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    fun putInt(key: String, value: Int) {
        sharedPreferences.edit().putInt(key, value).apply()
    }

    fun putLong(key: String, value: Long) {
        sharedPreferences.edit().putLong(key, value).apply()
    }

    fun putFloat(key: String, value: Float) {
        sharedPreferences.edit().putFloat(key, value).apply()
    }

    fun putString(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun putBoolean(key: String, value: Boolean) {
        sharedPreferences.edit().putBoolean(key, value).apply()
    }

    fun getInt(key: String, defaultValue: Int): Int {
        return sharedPreferences.getInt(key, defaultValue)
    }

    fun getLong(key: String, defaultValue: Long): Long {
        return sharedPreferences.getLong(key, defaultValue)
    }

    fun getFloat(key: String, defaultValue: Float): Float {
        return sharedPreferences.getFloat(key, defaultValue)
    }

    fun getString(key: String, defaultValue: String): String {
        return sharedPreferences.getString(key, defaultValue).toString()
    }

    fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return sharedPreferences.getBoolean(key, defaultValue)
    }

    fun getStringSet(key: String): Set<String> {
        return sharedPreferences.getStringSet(key, HashSet())!!
    }

    fun putStringSet(key: String, value: Set<String>) {
        sharedPreferences.edit().putStringSet(key, value).apply()
    }
}