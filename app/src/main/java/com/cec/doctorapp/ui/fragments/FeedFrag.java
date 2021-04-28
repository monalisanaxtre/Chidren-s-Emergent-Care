package com.cec.doctorapp.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cec.doctorapp.R;
import com.cec.doctorapp.databinding.FeedFragBinding;
import com.cec.doctorapp.helper.Constants;
import com.cec.doctorapp.helper.Vu;
import com.cec.doctorapp.model.request.GetToken;
import com.cec.doctorapp.model.response.FbFeedWrapper;
import com.cec.doctorapp.ui.activities.NotificationActivity;
import com.cec.doctorapp.ui.adapters.HomeAdapter;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.HttpMethod;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import static com.facebook.FacebookSdk.getApplicationContext;


public class FeedFrag extends BaseFragment {
    FeedFragBinding binding;
    private HomeAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FeedFragBinding.inflate(inflater);
        rootLayout = binding.rootLayout;
        return binding.getRoot();


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.feedToolbar.docHome.setText(R.string.title_feed);
        Vu.gone(binding.feedToolbar.back);

        binding.feedToolbar.notify.setOnClickListener(v -> {
            Intent i = new Intent(requireContext(), NotificationActivity.class);
            startActivity(i);
        });
        binding.feedToolbar.back.setOnClickListener(v -> {
            Intent i = new Intent(requireContext(), NotificationActivity.class);
            startActivity(i);
        });
//        getFeed();
//        binding.pullToRefresh.setOnRefreshListener(() -> {
//            refreshData();
//            binding.pullToRefresh.setRefreshing(false);
//        });
        WebSettings webViewSettings = binding.webview.getSettings();
        webViewSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webViewSettings.setJavaScriptEnabled(true);
        webViewSettings.setPluginState(WebSettings.PluginState.ON);
        binding.webview.setWebViewClient(new WebViewClient());
              binding. webview.loadUrl("https://cec-nodejs.herokuapp.com/fb_feed");
    }
//    private void refreshData(){
//
//        new Handler().postDelayed(() -> binding.pullToRefresh.setRefreshing(false), 2000);
////      getFeed();
////        webview();
//    }
    public class WebViewClient  extends android.webkit.WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
//            binding.progressBar.setVisibility(View.VISIBLE);
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
//           hideProgress();
//           binding.progressBar.setVisibility(View.GONE);
        }

        @Override
        public void onPageCommitVisible(WebView view, String url) {
            super.onPageCommitVisible(view, url);
            binding.progressBar.setVisibility(View.GONE);
        }

    }
//    {

//        WebSettings webViewSettings = binding.webview.getSettings();
//        webViewSettings.setJavaScriptCanOpenWindowsAutomatically(true);
//        webViewSettings.setJavaScriptEnabled(true);
//        webViewSettings.setPluginState(WebSettings.PluginState.ON);
//        binding.webview.setWebViewClient(new WebViewClient());
//        binding.webview.loadUrl("https://www.google.com");
//        webView.loadUrl("http://182.77.57.62:8096/cec/fb.html");
//       webView.loadDataWithBaseURL(null, <iframe src="https://www.facebook.com/plugins/post.php?href=https%3A%2F%2Fwww.facebook.com%2Fnaxtre.mohali%2Fposts%2F1261620024173829&width=500&show_text=true&appId=686626434822083&height=375" width="500" height="375" style="border:none;overflow:hidden" scrolling="no" frameborder="0" allowfullscreen="true" allow="autoplay; clipboard-write; encrypted-media; picture-in-picture; web-share"></iframe>, encoding="text/html", "UTF-8", "null");
//        String webContent="";
//        if(webContent.contains("iframe")){
//            Matcher matcher = Pattern.compile("src=\"([^\"]+)\"").matcher(webContent);
//            matcher.find();
//            String src = matcher.group(1);
//            webContent=src;
//
//            try {
//                URL myURL = new URL(src);
//                binding.webview.loadUrl(src);
//
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            }
//        }else {
//
//          binding.webview.loadDataWithBaseURL(null, "<iframe src=\"https://www.facebook.com/plugins/page.php?href=https%3A%2F%2Fwww.facebook.com%2Fnaxtre1&tabs=timeline&width=340&height=1000&small_header=true&adapt_container_width=true&hide_cover=false&show_facepile=true&appId=686626434822083\" width=\"340\" height=\"1000\" style=\"border:none;overflow:hidden\" scrolling=\"no\" frameborder=\"0\" allowfullscreen=\"true\" allow=\"autoplay; clipboard-write; encrypted-media; picture-in-picture; web-share\"></iframe>" + webContent, "text/html", "UTF-8", null);}
//
//    }



//when token expire manually refresh call the same api so that user
    private void gettoken() {
        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "me/accounts/",
                null,
                HttpMethod.GET,
                response -> {
                    if (getContext() != null) {
                        /* handle the result */
                        GetToken wrapper = new Gson().fromJson(response.getRawResponse(), GetToken.class);
                    graph.spUtils().put(Constants.FACEBOOK_FEED_TOKEN,wrapper.accessToken);
                    Log.d("GRAPH",wrapper.accessToken+ " ");

//                        Log.d("name",wrapper.name+"");
//                    binding.rootLayout.post(this::getFeed);
                    }
                }).executeAsync();
    }
    public void showError() {
        showSnack(" Please Check your Internet Connection ");
//        Toast.makeText(getApplicationContext(), "Something went wrong with your internet connection", Toast.LENGTH_LONG).show();
    }

    public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo wifiNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiNetwork != null && wifiNetwork.isConnected()) {
            return true;
        }
        NetworkInfo mobileNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (mobileNetwork != null && mobileNetwork.isConnected()) {
            return true;
        }
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
        //  return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    private void getFeed() {
        if (!isNetworkConnected()) {
            showError();
        } else {

// permanetn acess token:EAAptd3YS6jgBAKZCrLzcKATLt5RxnUHVZAFL0BxCqxjshTFSH3v6s64PJLNXABZBfDw56svzvJkQnffxDOYa80
            Bundle params = new Bundle();
            params.putString("access_token", "EAAptd3YS6jgBAAyQnOgY9FZCMpjrNCorCTcxTuc2Ec1C8ugoJ6AIuufPi27yoTYLZAgppHkdJjP6MDxEdLMeRAUK4QeGrly91GZA7Ro1j9x3KneNP5frTLKcV8GcFrbXrCNll3xfjC1pPZCASnkNFEYgpG2DmOzH8llmrlvTywZDZD");
            showProgress();
            new GraphRequest(
                    AccessToken.getCurrentAccessToken(),
                    "414915155337210/feed?fields=id,created_time,message,full_picture,permalink_url",
                    params,
                    HttpMethod.GET,
                    response -> {
                        hideProgress();
                        if (getContext() != null) {
//                            showNoData("No Feed Found");
                            /* handle the result */
                            String rawResponse=response.getRawResponse();
                            GetToken m=new Gson().fromJson(rawResponse,GetToken.class);
                            FbFeedWrapper wrapper = new Gson().fromJson(rawResponse, FbFeedWrapper.class);
                            if (rawResponse.trim().isEmpty()||wrapper==null){
                                gettoken();
                            }else{
                                adapter = new HomeAdapter(FeedFrag.this.requireContext(), wrapper.data);
                                binding.homeRv.post(() -> binding.homeRv.setAdapter(adapter));
                                Log.d("GRAPH", response.getRawResponse() + "");
                            }
                        }
                    }

            ).executeAsync();
        }
    }


    @Override
    public boolean dispatchBackPress() {
        super.dispatchBackPress();
        if (binding.webview.canGoBack()){
            binding.webview.goBack();
            return true;
        }else{
            return false;
        }
    }
}












