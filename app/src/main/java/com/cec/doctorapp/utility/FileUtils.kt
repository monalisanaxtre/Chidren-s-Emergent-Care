package com.cec.doctorapp.utility

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Environment

import android.util.Log
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.cec.doctorapp.R
import com.cec.doctorapp.helper.Utility

import com.downloader.Error
import com.downloader.OnDownloadListener
import com.downloader.PRDownloader
import java.io.File

object FileUtils {

    @kotlin.jvm.JvmStatic


    fun getRootDirPath(context: Context): String {
        Log.d("PATH", "" + context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)!!.absolutePath)
        return context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)!!.absolutePath
    }

    fun getFileExtensionForName(link: String, name: String):String {
        return name.plus(".").plus(link.substring(link.indexOf(".")))
    }
    fun downloadDocumentImage(
        fileUrl: String,
        context: Context
    ) {
        val fileNameArray = fileUrl.split("/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val fileName = fileNameArray[fileNameArray.size - 1]
        Log.d("FILE_URL", fileUrl)
        val uid = System.currentTimeMillis().toInt()
        showSneakBar(context, "Downloading $fileName")
        var mBuilder: NotificationCompat.Builder =
            NotificationManager
                .simpleBuilder(context, fileName, "Download Started", R.drawable.ceclogo, NotificationManager.highChannelId)
                .setOngoing(true)
                .setProgress(100, 0, true)
        NotificationManager.notify(context, uid, mBuilder.build())
        mBuilder = NotificationManager.simpleBuilder(context, fileName, "Download Started", R.drawable.ceclogo, NotificationManager.defaultChannelId)
        var time = System.currentTimeMillis()
        val mFileUrl = if (fileUrl.contains(" ")) {
            fileUrl.replace(" ".toRegex(), "%20")
        } else {
            fileUrl
        }
        //val s = mFileUrl.split("/").toTypedArray()
        // The id of the channel.
        mBuilder = NotificationManager.simpleBuilder(context, fileName, "Download Started", R.drawable.ceclogo, NotificationManager.defaultChannelId, priority = NotificationCompat.PRIORITY_HIGH)
        Log.d("filename", fileName)
        PRDownloader.download(mFileUrl, Util.getRootDirPath1(context), fileName)
            .build()
            .setOnStartOrResumeListener {
                Log.d("DOWNLOAD", "START RESUME")
                mBuilder = NotificationManager
                    .simpleBuilder(
                        context,
                        fileName,
                        "Download Started",
                        R.drawable.ceclogo,
                        NotificationManager.highChannelId,
                        priority = NotificationCompat.PRIORITY_HIGH)
                mBuilder.setContentText("Download in progress")
                    .setAutoCancel(true)
                    .setSound(null)
                    .setProgress(100, 0, true)
                    .setOngoing(true)
                NotificationManager.notify(context, uid, mBuilder.build())
                mBuilder = NotificationManager.simpleBuilder(context,
                    fileName,
                    "Download Started",
                    R.drawable.ceclogo,
                    NotificationManager.lowChannelId)
            }
            .setOnPauseListener { Log.d("DOWNLOAD", "Pause") }
            .setOnCancelListener { Log.d("DOWNLOAD", "Cancel") }
            .setOnProgressListener { progress ->
                Log.d("DOWNLOAD", "PROG")
                if (System.currentTimeMillis() - time > 1000) {
                    time = System.currentTimeMillis()
                    mBuilder.setOngoing(true)
                    mBuilder.setProgress(
                        progress.totalBytes.toInt(),
                        progress.currentBytes.toInt(),
                        false)
                        .setPriority(NotificationCompat.PRIORITY_LOW)
                        .setSound(null)
                        .setChannelId(NotificationManager.lowChannelId)
                    NotificationManager.notify(context, uid, mBuilder.build())
                }
            }
            .start(object : OnDownloadListener {
                override fun onDownloadComplete() {
                    Log.d("DOWNLOAD", "COMPLETE")
                    val filePath =
                        Util.getRootDirPath1(context) + "/" + fileName
                    val notificationIntent =
                        Intent(context, NotificationListenerService::class.java)
                            .apply { this.putExtra(NotificationListenerService.PATH, filePath) }
                    val pendingIntent = PendingIntent.getService(
                        context,
                        uid,
                        notificationIntent,
                        PendingIntent.FLAG_ONE_SHOT
                    )
                    mBuilder = NotificationManager.simpleBuilder(
                        context,
                        fileName,
                        "Download completed.",
                        R.drawable.ceclogo,
                        NotificationManager.highChannelId,
                        priority = NotificationCompat.PRIORITY_HIGH,
                        withSound = true)
                        .setProgress(0, 0, false)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)
                    NotificationManager.notify(context, uid, mBuilder.build())
                }

                override fun onError(error: Error) {
                    Log.e("Error", "Error")
                    NotificationManager.cancel(context, uid)
                    if (error.isConnectionError) {
                        showSneakBar(context, context.getString(R.string.err_api_false))
                    } else {
                        showSneakBar(context, "Oops, Something went wrong!!!")
                    }
                }
            })
    }

    fun showSneakBar(context: Context, message: String) {
        try {
            val cxt = context as AppCompatActivity
            val view = cxt.window.decorView.findViewById<View>(android.R.id.content)

            val snackbar = com.google.android.material.snackbar.Snackbar
                .make(view, message, com.google.android.material.snackbar.Snackbar.LENGTH_LONG)

            val sbView = snackbar.view
            sbView.setBackgroundColor(ContextCompat.getColor(cxt, R.color.colorAccent))
            val textView = sbView.findViewById<TextView>(R.id.snackbar_text)
            textView.setPadding(0, 0, 0, 0)
            textView.setTextColor(Color.WHITE)
            snackbar.show()
        } catch (e: Exception) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }
    fun getOpenFileIntent(value: String, context: Context): Intent {

        val file = File(value)
        val uri = FileProvider.getUriForFile(
            context,
            context.getString(R.string.file_provider_authority),
            file
        )
        val mime = MimeTypeMap.getSingleton()
        val ext = file.name.substring(file.name.indexOf(".") + 1)
        val type = mime.getMimeTypeFromExtension(ext)
        val openFile = Intent(Intent.ACTION_VIEW, uri)
        openFile.setDataAndType(uri, type)
        Log.d("MIME", "type::$type  :::file:::${file.name}")
        openFile.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        openFile.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)

        return openFile
    }

}
