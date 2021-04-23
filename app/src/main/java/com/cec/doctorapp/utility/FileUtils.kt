package com.cec.doctorapp.utility

import android.content.Context
import android.os.Environment
import android.util.Log
import com.cec.doctorapp.helper.FileUtils

object FileUtils {

    @kotlin.jvm.JvmStatic


    fun getPdfNameFromAssets(): String {
        return "MindOrks_Android_Online_Professional_Course-Syllabus.pdf"
    }

    fun getPdfUrl(): String {
        return "https://mindorks.s3.ap-south-1.amazonaws.com/courses/MindOrks_Android_Online_Professional_Course-Syllabus.pdf"
    }

    fun getRootDirPath(context: Context): String {
        Log.d("PATH", "" + context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)!!.absolutePath)
        return context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)!!.absolutePath
    }

    fun getFileExtensionForName(link: String, name: String):String {
        return name.plus(".").plus(link.substring(link.indexOf(".")))
    }

}
