package com.example.materialanimationexample.utils

import android.app.Activity
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.documentfile.provider.DocumentFile
const val DELETE_ACTION_CODE = 102
const val RENAME_ACTION_CODE = 103
fun Activity.deleteFile(documentFile: DocumentFile?){
    val itemDocument = documentFile ?: return
    if (itemDocument.canWrite()){
         itemDocument.delete()
    }else{
        val createDeleteRequest = MediaStore.createDeleteRequest(this.contentResolver, listOf(itemDocument.uri))
        this.startIntentSenderForResult(createDeleteRequest.intentSender,DELETE_ACTION_CODE,null,0,0,0)
    }
}

fun Activity.renameFile(name: String,documentFile: DocumentFile?){
    val itemDocument = documentFile ?: return
    if (itemDocument.canWrite()){
        itemDocument.delete()
    }else{
        val createDeleteRequest = MediaStore.createWriteRequest(this.contentResolver, listOf(itemDocument.uri))
        this.startIntentSenderForResult(createDeleteRequest.intentSender,RENAME_ACTION_CODE,null,0,0,0)
    }
}

fun Context.shareFile(documentFile: DocumentFile?){
    val itemDocument = documentFile ?: return
    val itemType = itemDocument.type ?: "*/*"
    val shareIntent = Intent().apply {
        action = Intent.ACTION_SEND
        type = itemType
        flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        putExtra(Intent.EXTRA_STREAM, itemDocument.uri)
    }
    startActivity(Intent.createChooser(shareIntent, "Share item"))
}