package com.cec.doctorapp.utility

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Environment
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

        fun dismissDialog() {
            if (progressDialog!!.isShowing) {
                progressDialog!!.dismiss()
            }
        }
    }
}