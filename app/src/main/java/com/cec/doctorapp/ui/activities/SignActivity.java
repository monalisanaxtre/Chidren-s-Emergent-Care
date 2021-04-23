package com.cec.doctorapp.ui.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.cec.doctorapp.R;
import com.cec.doctorapp.databinding.ActivityOtpBookingBinding;
import com.cec.doctorapp.databinding.ActivitySignBinding;
import com.cec.doctorapp.helper.Utility;
import com.cec.doctorapp.helper.Vu;
import com.cec.doctorapp.model.request.Savepdfmodel;
import com.cec.doctorapp.model.response.GetConsentform;
import com.cec.doctorapp.model.response.PatientRegisterModel;
import com.cec.doctorapp.model.response.ResultModel;
import com.cec.doctorapp.network.NetworkListner;
import com.cec.doctorapp.ui.fragments.FeedFrag;
import com.github.gcacace.signaturepad.views.SignaturePad;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class SignActivity  extends BaseActivity implements View.OnClickListener {
  private ActivitySignBinding binding;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private File currentFile;



    private static String[] PERMISSIONS_STORAGE = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
    Savepdfmodel savepdfmodel;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        rootLayout = binding.rootLayout;
        binding.toolBar.docHome.setText("Sign");
        binding.toolBar.back.setOnClickListener(this);

        binding.toolBar.docHome.setTextColor(ContextCompat.getColor(this, R.color.black_exact));
//        binding.toolBar.notify.setImageResource(R.drawable.ic_baseline_blue_notifications_24);
        Vu.setTransparentBG(binding.toolBar.commontoolbar);
//        binding.toolBar.notify.setOnClickListener(v -> {
//            Intent intent=new Intent(SignActivity.this,NotificationActivity.class);
//            startActivity(intent);
//        });


        binding.clerbtn.setOnClickListener(view -> binding.signaturePad.clear());
       binding.submitbtn.setOnClickListener(view -> savepdfdata());
        //onCreate
//        binding.signaturePad.setOnSignedListener(new SignaturePad.OnSignedListener() {
//
//            @Override
//            public void onStartSigning() {
//
//            }
//
//            @Override
//            public void onSigned() {
//                showSnack("Please give your Signature");
//
//
//            }
//
//            @Override
//            public void onClear() {
//
//            }
//        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length <= 0
                        || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(SignActivity.this, "Cannot write images to external storage", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public File getAlbumStorageDir(String albumName) {
        // Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), albumName);
        if (!file.mkdirs()) {
            Log.e("SignaturePad", "Directory not created");
        }
        return file;
    }

    public void saveBitmapToJPG(Bitmap bitmap, File photo) throws IOException {
        Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newBitmap);
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(bitmap, 0, 0, null);
        OutputStream stream = new FileOutputStream(photo);
        newBitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);
        stream.close();
    }

    public boolean addJpgSignatureToGallery(Bitmap signature) {
        boolean result = false;
        try {
            currentFile = new File(getAlbumStorageDir("SignaturePad"), String.format("Signature_%d.jpg", System.currentTimeMillis()));
            saveBitmapToJPG(signature, currentFile);
            scanMediaFile(currentFile);
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private void scanMediaFile(File photo) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri contentUri = Uri.fromFile(photo);
        mediaScanIntent.setData(contentUri);
       SignActivity.this.sendBroadcast(mediaScanIntent);
    }

    public boolean addSvgSignatureToGallery(String signatureSvg) {
        boolean result = false;
        try {
            currentFile = new File(getAlbumStorageDir("SignaturePad"), String.format("Signature_%d.svg", System.currentTimeMillis()));
            OutputStream stream = new FileOutputStream(currentFile);
            OutputStreamWriter writer = new OutputStreamWriter(stream);
            writer.write(signatureSvg);
            writer.close();
            stream.flush();
            stream.close();
            scanMediaFile(currentFile);
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Checks if the app has permission to write to device storage
     * <p/>
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     * @param activity the activity from which permissions are checked
     */
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }


    private Boolean validateInput() {
        if (binding.etName.getText().toString().trim().isEmpty()) {
            showSnack("Please enter your Name");
            return false;

        } else if (binding.etMail.getText().toString().trim().isEmpty()) {
            showSnack("Please enter Email");
            return false;
        }   else if (!isValidEmail(binding.etMail.getText().toString())) {
            showSnack("Please enter valid email.");
            return false;
        }
        else if(binding.signaturePad.isEmpty()){
           showSnack("Please add Signature");
           return false;
        }


        return true;
    }
    private void savepdfdata(){
                if (validateInput()) {
                   Savepdfmodel savepdfmodel=new Savepdfmodel();
                   savepdfmodel.name = binding.etName.getText().toString();
                   savepdfmodel.email=binding.etMail.getText().toString();
                    Bitmap signatureBitmap = binding.signaturePad.getSignatureBitmap();
                    if (addJpgSignatureToGallery(signatureBitmap)) {
//                        Toast.makeText(SignActivity.this, "Signature saved into the Gallery", Toast.LENGTH_SHORT).show();
                    } else {
//                        Toast.makeText(SignActivity.this, "Unable to store the signature", Toast.LENGTH_SHORT).show();
                    }
//                    if (addSvgSignatureToGallery(binding.signaturePad.getSignatureSvg())) {
//                        Toast.makeText(SignActivity.this, "SVG Signature saved into the Gallery", Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(SignActivity.this, "Unable to store the SVG signature", Toast.LENGTH_SHORT).show();
//                    }
                    MultipartBody.Builder builder = new MultipartBody.Builder();
                    builder.setType(MultipartBody.FORM);
                    if (currentFile == null) {
                        builder.addFormDataPart("sign", "");
                    } else {
                        builder.addFormDataPart("sign",
                                currentFile.getName(), RequestBody.create(Utility.fileBytes(currentFile),
                                        MediaType.parse("image/png")));
                    }
                    builder.addFormDataPart("name", savepdfmodel.name);
                    builder.addFormDataPart("email", savepdfmodel.email);
                    MultipartBody body = builder.build();
                    //noinspection unchecked
                    graph.getServiceCaller().callService(
                            graph.getApis().savepdfdata(body), new NetworkListner() {

                                @Override
                                public void onSuccess(ResultModel<?> responseBody) {
                                    hideProgress();
                                    if (responseBody.status== 0) {
                                        showSnack("file not status ");
                                    } else {
                                        showSuccessDialog(responseBody.msg, (v, w) -> {
                                            Intent i=new Intent(SignActivity.this, HomeActivity.class);
                                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(i);
                                            finish();
                                        });
                                    }
                                }

                                @Override
                                public void onError(String msg) {
                                    hideProgress();
                                }

                                @Override
                                public void onComplete() {

                                    hideProgress();
                                }

                                @Override
                                public void onStart() {
                                    showProgress();
                                }
                            });
                }
            }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {


            case R.id.back:
            {
                onBackPressed();
                break;
            }
            case R.id.notify:{
                Intent i = new Intent(this, NotificationActivity.class);
                startActivity(i);
                break;
            }
        }

    }

}








