package com.cec.doctorapp.ui.activities

import android.Manifest
import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.print.PrintAttributes
import android.print.PrintManager
import android.view.View
import android.webkit.WebView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.cec.doctorapp.R
import com.cec.doctorapp.databinding.ActivityPdfviewrBinding
import com.cec.doctorapp.helper.Vu
import com.cec.doctorapp.ui.adapters.PdfDocumentAdapter
import com.cec.doctorapp.utility.FileUtils
import com.downloader.Error
import com.downloader.OnDownloadListener
import com.downloader.PRDownloader
import kotlinx.android.synthetic.main.activity_pdf_webview.*
import kotlinx.android.synthetic.main.activity_pdfviewr.*
import kotlinx.android.synthetic.main.activity_pdfviewr.progressBar
import java.io.File



class PDFViewActivity : BaseActivity(), View.OnClickListener {
    private var binding: ActivityPdfviewrBinding? = null
    private val REQUEST_EXTERNAL_STORAGE = 1
    private val PERMISSIONS_STORAGE = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    val hidedownload:Boolean=false

    companion object {
        private const val PDF_SELECTION_CODE = 99
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPdfviewrBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        rootLayout = binding!!.rootLayout
        binding!!.toolbar.docHome.setTextColor(ContextCompat.getColor(this@PDFViewActivity, R.color.black_exact))
        binding!!.toolbar.docHome.setText("COVID19_TestingInformedConsent")
        Vu.setTransparentBG(binding!!.toolbar.commontoolbar)
        Vu.gone(binding!!.toolbar.notify)
        binding!!.toolbar.back.setOnClickListener(this)
        binding!!.printbtn.setOnClickListener(this)
        binding!!.downloadbtn.setOnClickListener(this)
        verifyStoragePermissions(this)
        PRDownloader.initialize(applicationContext)
        checkPdfAction(intent)
    }





    private fun selectPdfFromStorage() {
        Toast.makeText(this, "selectPDF", Toast.LENGTH_LONG).show()
        val browseStorage = Intent(Intent.ACTION_GET_CONTENT)
        browseStorage.type = "application/pdf"
        browseStorage.addCategory(Intent.CATEGORY_OPENABLE)
        startActivityForResult(
                Intent.createChooser(browseStorage, "Select PDF"), PDF_SELECTION_CODE
        )
    }

    private fun showPdfFromUri(uri: Uri?) {
        pdfView.fromUri(uri)
                .defaultPage(0)
                .spacing(10)
                .load()
    }

    private fun showPdfFromFile(file: File) {
        binding?.printbtn?.tag = file
        pdfView.fromFile(file)
                .defaultPage(0)
                .enableSwipe(true)
                .swipeHorizontal(false)
                .enableDoubletap(true)
                .onPageError { page, _ ->
                    Toast.makeText(
                            this@PDFViewActivity,
                            "Error at page: $page", Toast.LENGTH_LONG
                    ).show()
                }
                .load()
    }

    private fun downloadPdfFromInternet(url: String, dirPath: String, fileName: String,download:Boolean=false) {
        PRDownloader.download(
                url,
                dirPath,
                fileName
        ).build()

                .start(object : OnDownloadListener {
                    override fun onDownloadComplete() {
                        if (download) {
//
                            Toast.makeText(this@PDFViewActivity, "downloadComplete", Toast.LENGTH_LONG)
                                    .show()
                        } else {
//                        showSnack("DownloadComplete")
//                        Toast.makeText(this@PDFViewActivity, "downloadComplete", Toast.LENGTH_LONG)
//                                .show()
                            val downloadedFile = File(dirPath, fileName)
                            progressBar.visibility = View.GONE
                            showPdfFromFile(downloadedFile)
                        }
                    }
                    override fun onError(error: Error?) {
//                        showSnack("Error in downloading file")
//                        Toast.makeText(
//                                this@PDFViewActivity,
//                                "Error in downloading file : $error",
//                                Toast.LENGTH_LONG
//                        )
//                                .show()
                    }
                })
    }

    private fun checkPdfAction(intent: Intent) {
        if(intent.getBooleanExtra("DOWNLOAD",false)){
            Vu.show(binding?.downloadbtn)
            Vu.show(binding?.printbtn)

        }

        intent.getStringExtra("CONSENT_PDF_LINK")?.let { link ->
            progressBar.visibility = View.VISIBLE
            val fileName = "myFile.pdf"
            downloadPdfFromInternet(
                    link,
                    FileUtils.getRootDirPath(this),
                    fileName
            )

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PDF_SELECTION_CODE && resultCode == Activity.RESULT_OK && data != null) {
            val selectedPdfFromStorage = data.data
            showPdfFromUri(selectedPdfFromStorage)
        }
    }

    fun verifyStoragePermissions(activity: Activity?) {
        // Check if we have write permission
        val permission = ActivityCompat.checkSelfPermission(activity!!, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            )
        }
    }

    override fun onClick(v: View?) {
        val id = v!!.id
        when (id) {
            R.id.back -> {
                onBackPressed()
            }
            R.id.printbtn -> {
                (binding?.printbtn?.tag as? File)?.let { file ->
                    doPrint(file)
                }
            }
            R.id.downloadbtn -> {
                val fileName = "myFile.pdf"
                intent.getStringExtra("CONSENT_PDF_LINK")?.let { link ->
                   FileUtils.downloadDocumentImage(link, this)
//                    downloadPdfFromInternet(link,
//                            FileUtils.getRootDirPath(this),
//                            fileName,true)
                }

            }
        }


        }



    private fun doPrint(file: File) {
        // Get a PrintManager instance
        val printManager = getSystemService(Context.PRINT_SERVICE) as PrintManager

        // Set job name, which will be displayed in the print queue
        val jobName: String = getString(R.string.app_name) + " Document"

        // Start a print job, passing in a PrintDocumentAdapter implementation
        // to handle the generation of a print document
        printManager.print(jobName, PdfDocumentAdapter(this, file.absolutePath),
                null) //
    }

    @TargetApi(19)
    @SuppressLint("NewApi")
    private fun createWebPrintJob(webView: WebView) {
        val printManager = this.getSystemService(PRINT_SERVICE) as PrintManager
        val printAdapter = webView.createPrintDocumentAdapter()
        val jobName = getString(R.string.app_name) + " Print Test"
        printManager.print(jobName, printAdapter, PrintAttributes.Builder().build())
    }
}

