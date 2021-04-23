package com.cec.doctorapp.ui.activities;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.core.content.ContextCompat;

import com.cec.doctorapp.R;
import com.cec.doctorapp.databinding.ActivityPdfWebviewBinding;
import com.cec.doctorapp.databinding.ActivityTimeBinding;
import com.cec.doctorapp.helper.Vu;
import com.cec.doctorapp.ui.fragments.FeedFrag;

public class WebviewActivity extends BaseActivity implements View.OnClickListener {
    private ActivityPdfWebviewBinding binding;
    WebView webView;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPdfWebviewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        rootLayout = binding.rootLayout;

        binding.toolbar.back.setOnClickListener(this);
        binding.toolbar.docHome.setTextColor(ContextCompat.getColor(WebviewActivity.this, R.color.black_exact));
        binding.toolbar.docHome.setText("COVID19_TestingInformedConsent");
        Vu.setTransparentBG(binding.toolbar.commontoolbar);
        WebSettings webViewSettings = binding.webview.getSettings();
        webViewSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webViewSettings.setJavaScriptEnabled(true);
        webViewSettings.setPluginState(WebSettings.PluginState.ON);
        binding.webview.setWebViewClient(new WebViewClient());
        String myPdfUrl = "http://182.77.57.62:4202/uploads/ConsentFormAdmin/COVID19_Testing_Informed_Consent_v1.0.pdf";
        String url = "https://docs.google.com/gview?embedded=true&url=" + myPdfUrl;

        binding.webview.loadUrl(url);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {

            case R.id.back: {
                onBackPressed();
                break;
            }
        }
    }

    public class WebViewClient extends android.webkit.WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

            binding.progressBar.setVisibility(View.GONE);
//            new Handler().postDelayed(() -> createWebPrintJob(binding.webview), 1000);
        }
    }

    @TargetApi(19)
    @SuppressLint("NewApi")
    private void createWebPrintJob(WebView webView) {

        PrintManager printManager = (PrintManager) this.getSystemService(Context.PRINT_SERVICE);

        PrintDocumentAdapter printAdapter = webView.createPrintDocumentAdapter();

        String jobName = getString(R.string.app_name) + " Print Test";

        printManager.print(jobName, printAdapter, new PrintAttributes.Builder().build());
    }
}
//        Vu.gone(binding.toolBar.back);



