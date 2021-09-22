package com.cec.doctorapp.utility

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Build
import android.os.Environment
import android.util.Log
import com.cec.doctorapp.R
import com.cec.doctorapp.helper.Constants.IMAGE_DIRECTORY_NAME
import java.io.File

open class Util {
    companion object {
        private var progressDialog: Dialog? = null


        fun showProgressDialog(c: Activity) {
            progressDialog = Dialog(c, R.style.theme_Dialog)
            progressDialog!!.setContentView(R.layout.layout_progress)
            progressDialog!!.setCancelable(false)

            if (!c.isFinishing) {
                progressDialog!!.show()
            }
        }

        fun getRootDirPath(context: Context): String {


            val folder = File(
                    Environment.getExternalStoragePublicDirectory(
                            Environment.DIRECTORY_DOWNLOADS
                    ).toString() + "/" + IMAGE_DIRECTORY_NAME
            )
            if (!folder.exists()) {
                folder.mkdirs()
            }
            return folder.absolutePath
        }
        fun getRootDirPath1(context: Context): String? {
            return getRootDirPath(context, false)
        }
        fun getRootDirPath(context: Context, cache: Boolean): String {
            if (cache) {
                return context.externalCacheDir!!.absolutePath
            }
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                Log.d(
                    "PATH", "" + context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)!!
                        .absolutePath
                )
                context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)!!.absolutePath
            } else {
                val file = File(
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                    context.getString(R.string.app_name)
                )
                if (file.exists()) {
                    file.mkdir()
                }
                Log.d("PATH", "" + file.absolutePath)
                file.absolutePath
            }
        }


        fun dismissDialog() {
            if (progressDialog!!.isShowing) {
                progressDialog!!.dismiss()
            }
        }

    }
}