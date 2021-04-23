package com.cec.doctorapp.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cec.doctorapp.R;
import com.cec.doctorapp.databinding.MoreFragBinding;
import com.cec.doctorapp.helper.Vu;
import com.cec.doctorapp.model.response.GetConsentform;
import com.cec.doctorapp.model.response.ResultModel;
import com.cec.doctorapp.network.NetworkListner;
import com.cec.doctorapp.ui.activities.NotificationActivity;
import com.cec.doctorapp.ui.activities.PDFViewActivity;
import com.cec.doctorapp.ui.activities.SignActivity;
import com.cec.doctorapp.ui.activities.WebviewActivity;
import com.cec.doctorapp.utility.FileUtils;
import com.cec.doctorapp.utility.Util;
import com.downloader.Error;
import com.downloader.OnDownloadListener;
import com.downloader.PRDownloader;
import com.shockwave.pdfium.PdfDocument;

import java.io.File;

public class MoreFrag extends BaseFragment implements View.OnClickListener {

    private MoreFragBinding binding;

    boolean isImageFitToScreen;
    File downloadedFile;
    boolean download;
GetConsentform consentmodel;
    String fileName = "Myfile.pdf";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = MoreFragBinding.inflate(inflater);
        binding.morefragtoolbar.docHome.setText("Consent Form");
        Vu.gone(binding.morefragtoolbar.back);
        binding.morefragtoolbar.notify.setOnClickListener(this);
        binding.morefragtoolbar.docHome.setTextColor(ContextCompat.getColor(requireContext(), R.color.black_exact));
        binding.morefragtoolbar.notify.setImageResource(R.drawable.ic_baseline_blue_notifications_24);
        Vu.setTransparentBG(binding.morefragtoolbar.commontoolbar);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.signbtn.setOnClickListener(v -> {
            Intent i = new Intent(requireContext(), SignActivity.class);
            startActivity(i);
        });
        getconsentform();
        binding.viewicon.setOnClickListener(v -> {
            Intent i = new Intent(requireContext(), PDFViewActivity.class);
            i.putExtra("CONSENT_PDF_LINK", consentmodel.getLink());
            startActivity(i);
        });


        binding.imageview.setOnClickListener(v -> {
            Intent i = new Intent(requireContext(), PDFViewActivity.class);
         i.putExtra("CONSENT_PDF_LINK", consentmodel.getLink());
            startActivity(i);
        });

    }

    private void getconsentform() {
        graph.getServiceCaller().callService(graph.getApis().getconsentform(), new NetworkListner() {
            @Override
            public void onSuccess(ResultModel<?> responseBody) {
                binding.getRoot().post(() -> {
                    hideProgress();
                    if (getActivity() != null) {
                        requireActivity().runOnUiThread(() -> {
                            consentmodel = ((GetConsentform) responseBody.data);
                            Glide.with(requireContext())
                                    .load(consentmodel.getThumbnail())
                                    .placeholder(R.drawable.ss)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)

                                    .into(binding.imageview);
                                binding.dwnldbtn.setOnClickListener(v -> {

                                Intent i = new Intent(requireContext(), PDFViewActivity.class);
                                i.putExtra("CONSENT_PDF_LINK", consentmodel.getLink());
                                i.putExtra("DOWNLOAD",true);
//                                i.putExtra("Consent_PDF_NAME",consentmodel.getName());
                                startActivity(i);
//                                downloadPdfFromInternet(consentmodel.getLink(),
//                                        FileUtils.INSTANCE.getRootDirPath(requireContext()),
//                                        FileUtils.INSTANCE.getFileExtensionForName(consentmodel.getLink(),consentmodel.getName())
//                                );
                            });
                        });
                    }
                });

            }


            @Override
            public void onError(String msg) {
                Log.d("TAG", msg + "");
                hideProgress();
            }

            @Override
            public void onComplete() {
                Log.d("TAG", "onComplete");
                hideProgress();
            }

            @Override
            public void onStart() {
                Log.d("TAG", "onstart");
                showProgress();

            }
        });


    }

    private void downloadPdfFromInternet(String url, String dirPath, String fileName) {
        PRDownloader.download(
                url,
                dirPath,
                fileName
        ).build().setOnStartOrResumeListener(() -> {
        })
                .setOnPauseListener(() -> Log.e("Pause", "Pause"))
                .setOnCancelListener(() -> Log.e("Cancel", "Cancel"))
                .start(new OnDownloadListener() {
                    @Override
                    public void onDownloadComplete() {
                        Toast.makeText(requireContext(), "downloadComplete", Toast.LENGTH_LONG)
                                .show();
                        downloadedFile = new File(dirPath, fileName);
//                       showPdfFromFile(downloadedFile);
                        hideProgress();

                    }

                    @Override
                    public void onError(Error error) {
                        Toast.makeText(
                                requireContext(),
                                "Error in downloading file : $error",
                                Toast.LENGTH_LONG
                        )
                                .show();

                    }
                });
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.notify:{
                Intent i = new Intent(requireContext(), NotificationActivity.class);
                startActivity(i);
                break;
            }

        }

    }
}



