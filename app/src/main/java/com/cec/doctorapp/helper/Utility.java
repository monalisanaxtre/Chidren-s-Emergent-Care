package com.cec.doctorapp.helper;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.TypedValue;
import android.view.View;
import android.widget.Toast;


import com.cec.doctorapp.network.DoctorConfig;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Utility {
    Context context;
    DoctorConfig config;

    public Utility() {

    }

    public Utility(Context context, DoctorConfig config) {
        this.context = context;
        this.config = config;
    }

    public static String SERVER_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";//2020-02-25 10:49:09
    public static String SERVER_DATE_FORMAT = "yyyy-MM-dd";//2020-02-25 10:49:09
    public static String LOCAL_DATE_FORMAT = "MMM dd, yyyy hh:mm a";//2020-02-25 10:49:09

    public static Boolean isEmpty(String s) {
        if(s==null) return true;
        String s1 = s.trim();
        return s1.isEmpty();

    }


    public static boolean isNotEmpty(String s) {
        return !isEmpty(s);
    }

    public static String getTempImageLocation(Context context) {
        return context.getCacheDir().getAbsolutePath();
    }

    public static void scaleYToOne(View view) {
        view.post(() -> {
            view.setScaleY(0f);
            view.setPivotX(1f);
            view.setPivotY(0f);
            view.animate()
                    .setDuration(200)
                    .scaleY(1.0f);
        });
    }

    public static void scaleYToZero(View view, Runnable runnable) {
        view.post(() -> {
            view.setPivotX(1f);
            view.setPivotY(0f);
            view.animate()
                    .setDuration(200)
                    .withEndAction(runnable)
                    .scaleY(0f);
        });
    }


    public static void rotate(View view, float v,Runnable runnable) {
        view.post(()->{
            view.animate()
                    .withEndAction(runnable)
                    .rotationBy(v);
        });
    }

    public static final long ONE_DAY_MILLIS = 86400000;

    public static boolean isValidISBN(String isbn) {
        if (isbn.length() == 13) {
            int sum = (isbn.charAt(0) - '0')
                    + (isbn.charAt(1) - '0') * 3
                    + (isbn.charAt(2) - '0')
                    + (isbn.charAt(3) - '0') * 3
                    + (isbn.charAt(4) - '0')
                    + (isbn.charAt(5) - '0') * 3
                    + (isbn.charAt(6) - '0')
                    + (isbn.charAt(7) - '0') * 3
                    + (isbn.charAt(8) - '0')
                    + (isbn.charAt(9) - '0') * 3
                    + (isbn.charAt(10) - '0')
                    + (isbn.charAt(11) - '0') * 3;
            int check = 10 - (sum % 10);
            return Integer.parseInt(isbn.charAt(12) + "") == check;
        } else {
            return isValidISBN10Digits(isbn);
        }
    }

    private static boolean isValidISBN10Digits(String isbn) {
        // length must be 10
        if (isbn.length() != 10)
            return false;

        // Computing weighted sum
        // of first 9 digits
        int sum = 0;
        for (int i = 0; i < 9; i++) {
            int digit = isbn.charAt(i) - '0';
            if (0 > digit || 9 < digit)
                return false;
            sum += (digit * (10 - i));
        }

        // Checking last digit.
        char last = isbn.charAt(9);
        if (last != 'X' && (last < '0' ||
                last > '9'))
            return false;

        // If last digit is 'X', add 10
        // to sum, else add its value
        sum += ((last == 'X') ? 10 : (last - '0'));

        // Return true if weighted sum
        // of digits is divisible by 11.
        return (sum % 11 == 0);
    }

    public static void launchGmail(String receiver,Context context) {
        final Intent intent =new Intent(Intent.ACTION_SEND);
        final String[] recipients = new String[]{receiver};
        intent.putExtra(Intent.EXTRA_EMAIL, recipients);
        intent.putExtra(Intent.EXTRA_TEXT, "Body of the content here...");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Subject text here...");
        intent.setType("message/rfc822");
        intent.setPackage("com.google.android.gm");
        try {
            context.startActivity(intent);
        } catch (Exception exception) {
            Toast.makeText(context,"Gmail is not installed in your phone",Toast.LENGTH_LONG).show();
        }
    }

    public static byte[] fileBytes(File file) {
        int size = (int) file.length();
        byte[] bytes = new byte[size];
        try {
            BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
            //noinspection ResultOfMethodCallIgnored
            buf.read(bytes, 0, bytes.length);
            buf.close();
            return bytes;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

//    public static void showImageDialog(Context mContext, String url) {
//        final View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_view_image, null, false);
//        final androidx.appcompat.app.AlertDialog dialog = new androidx.appcompat.app.AlertDialog.Builder(mContext).create();
//        final AppCompatImageButton cancel = view.findViewById(R.id.btnCancelChatImg);
//        final AppCompatImageView chatImg=view.findViewById(R.id.chatImg);
//        cancel.setOnClickListener(v -> dialog.dismiss());
//        Glide.with(chatImg)
//                .load(url)
//                .into(chatImg);
//        if (dialog.getWindow() != null) {
//            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//        }
//        dialog.setView(view);
//        dialog.show();
//    }

    @SuppressLint("SimpleDateFormat")
    public String convertDate(String oldDate) {
        SimpleDateFormat serverSDF = new SimpleDateFormat(SERVER_DATETIME_FORMAT);
        SimpleDateFormat localSDF = new SimpleDateFormat(LOCAL_DATE_FORMAT);
        try {

            Date date = serverSDF.parse(oldDate);
            return localSDF.format(date);
        } catch (Exception e) {
//            e.printStackTrace();
        }
        return oldDate;
    }

    public static float dpToPixel(Context context, float dip) {

        Resources r = context.getResources();
        return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dip,
                r.getDisplayMetrics()
        );
    }

    @SuppressLint("MissingPermission")
    public static Boolean isInternetOn(Context context) {
        try {

            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            return networkInfo != null && networkInfo.isConnected();

        } catch (Exception e) {
            return false;
        }
    }


    @SuppressLint("SimpleDateFormat")
    public String convertDateToServerFormat(Date toDate) {
        try {
            return new SimpleDateFormat(SERVER_DATE_FORMAT).format(toDate);
        } catch (Exception e) {
            return "";

        }
    }

    public void copyToClipBoard(String text) {
        if (text == null || text.isEmpty()) {
            return;
        }
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("", text);
        if (clipboard != null) {
            clipboard.setPrimaryClip(clip);
        }
    }

    private static final String SERVER_DOB_FMT = "dd-MM-yyyy";

    @SuppressLint("SimpleDateFormat")
    public Date parseDOB(String signup_dob) {
        try {
            return new SimpleDateFormat(SERVER_DATE_FORMAT).parse(signup_dob);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Date();
    }

    @SuppressLint("SimpleDateFormat")
    public String formatDOB(Date dob) {
        try {
            return new SimpleDateFormat(SERVER_DOB_FMT).format(dob);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    @SuppressLint("SimpleDateFormat")
    public String formatDOBStringTOString(String signup_dob) {
        try {
            Date date = new SimpleDateFormat(SERVER_DOB_FMT).parse(signup_dob);
            return new SimpleDateFormat(SERVER_DATE_FORMAT).format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return signup_dob;
    }

    public String generateOTP() {
        return generateOTP(4);
    }

    public String generateOTP(int maxLen) {
        StringBuffer otp = new StringBuffer();
//        if (config.environment == UlyanaConfig.Environment.DEV) {
//            return "1234";
//        }
        int i = 0;
        Random r = new Random();
        do {
            otp.append(r.nextInt(9));
            i++;
        } while (i < maxLen);

        return otp.toString();
    }


    public static boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public void showErrorDialog(String msg, Context context) {
        new AlertDialog.Builder(context)
                .setMessage(msg)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }
}