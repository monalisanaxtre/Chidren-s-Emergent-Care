package com.cec.doctorapp.ui.activities



import android.os.Bundle
import android.view.View
import com.cec.doctorapp.R
import kotlinx.android.synthetic.main.activity_no_internet.*


import java.net.URL
import java.util.concurrent.Executors

class NoInternetActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_no_internet)
        rootLayout = findViewById(R.id.rootLayout)
        this.btnRetry.setOnClickListener {
            checkInternetAndFinish()
        }
    }

    private fun checkInternetAndFinish() {
        try {
            Executors.newSingleThreadExecutor().execute {
                this.showProgress()
                try {
                    val conn=URL("https://www.google.com/").openConnection()
                    conn.connectTimeout=1000
                    conn.connect()
                    rootLayout.post { this.finishNoInternet() }
                }catch (e:Exception){
                    Thread.sleep(1000)
                    this.hideProgress()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun backBtnPressed(view: View?) {

    }

    override fun onBackPressed() {

    }
}