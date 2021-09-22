package com.cec.doctorapp.utility

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.widget.Toast



class NotificationListenerService : Service() {

    companion object{
        const val PATH="path"
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.getStringExtra(PATH)?.let {
            val mIntent = FileUtils.getOpenFileIntent(it, this)
            try {
                mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_NO_HISTORY)
                val chooser=Intent.createChooser(mIntent,"Open with").apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_NO_HISTORY)
                }
                startActivity(chooser)
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this,"No application found to open this file, Please install a suitable application to open this file.",Toast.LENGTH_LONG).show()
            }
            finally {
                this.stopSelf()
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }


}