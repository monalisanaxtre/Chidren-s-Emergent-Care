package com.cec.doctorapp.ui.fragments;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.cec.doctorapp.R;
import com.cec.doctorapp.app.AppComponents;
import com.cec.doctorapp.app.AppController;
import com.cec.doctorapp.ui.activities.NoInternetActivity;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Objects;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class BaseFragment extends Fragment {

    ProgressDialog progressDialog;
    protected AppComponents graph;
    protected View rootLayout;
    private View progressBar;
    View lytNoData;
    public int PERMISSION_ALL = 1;
    public String[] PERMISSIONS = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.INTERNET};
    protected String userId;
    protected String key;
    protected final String myFormat = "dd-MM-yyyy";
    protected final String sendFormat = "yyyy-MM-dd";
    protected String guId = "";
    LocalBroadcastManager localBroadcastManager;

    protected final String getViewDate(String date) {
        try {
            return new SimpleDateFormat(myFormat, Locale.ROOT)
                    .format(Objects.requireNonNull(new SimpleDateFormat(sendFormat, Locale.ROOT).parse(date)));
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public boolean isAttached() {
        return attached;
    }


    private boolean attached;


    public int getIntUserId() {
        try {
            return Integer.parseInt(userId);
        } catch (Exception e) {
            return 1;
        }
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        localBroadcastManager = LocalBroadcastManager.getInstance(requireContext());
        IntentFilter fitler = new IntentFilter();
        fitler.addAction("SESSION_EXPIRED");
        fitler.addAction("NO_INTERNET");
        fitler.addAction("REFRESH_HOME");
        fitler.addAction("REFRESH_NOTIFICATION");
        localBroadcastManager.registerReceiver(broadcastListener, fitler);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(("Please wait"));
        graph = ((AppController) getActivity().getApplication()).getGraph();
//            if (graph.spUtils().getUserData() != null) {
//                userId = graph.spUtils().getUserData().getWebUserId();
//                key = graph.spUtils().getUserData().getKey();
//            }
    }

    @TargetApi(Build.VERSION_CODES.M)
    public boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

//        final protected void showConfirmAlertDialog(View.OnClickListener deleteListener, String msg) {
//            final View view = LayoutInflater.from(requireContext()).inflate(R.layout.delete_alert_preveducation, null, false);
//            final androidx.appcompat.app.AlertDialog dialog = new androidx.appcompat.app.AlertDialog.Builder(requireContext()).create();
//            Button delete = view.findViewById(R.id.deletebtn);
//            Button cancel = view.findViewById(R.id.Cancel);
//            if (msg != null) ((TextView) view.findViewById(R.id.msgText)).setText(msg);
//            cancel.setOnClickListener(v -> dialog.dismiss());
//            delete.setOnClickListener(v -> {
//                deleteListener.onClick(v);
//                dialog.dismiss();
//            });
//            if (dialog.getWindow() != null) {
//                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//            }
//            dialog.setView(view);
//            dialog.show();
//        }

    public void showQuestionDialog(String msg, DialogInterface.OnClickListener listener) {
        new AlertDialog.Builder(requireContext())
                .setMessage(msg)
                .setPositiveButton(android.R.string.yes, listener)
                .setNegativeButton(android.R.string.no, (d, v) -> {
                })
                .setCancelable(false)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }


    public void showSingleButtonNonCancelableDialog(String msg, DialogInterface.OnClickListener listener) {
        if (isAttached()) {
            new AlertDialog.Builder(requireContext())
                    .setMessage(msg)
                    // Specifying a listener allows you to take an action before dismissing the dialog.
                    // The dialog is automatically dismissed when a dialog button is clicked.
                    .setPositiveButton(android.R.string.yes, listener)
                    .setCancelable(false)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        localBroadcastManager.unregisterReceiver(broadcastListener);
        super.onStop();
    }

    private BroadcastReceiver broadcastListener = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            rootLayout = getView().findViewById(R.id.rootLayout);
            switch (intent.getAction()) {
                case "SESSION_EXPIRED": {
//                    showSessionExpiredDialog(String.msg);
                    break;
                }
                case "NO_INTERNET": {
                    if (!(requireActivity() instanceof NoInternetActivity)) {
                        BaseFragment.this.openNoInternet();
                    }

//                    if (rootLayout != null) {
//                        showSnackLong(getString(R.string.no_internet_msg), rootLayout);
//                    }
                    break;
                }


            }

        }

    };

    protected final void openNoInternet() {
        if (!(requireActivity() instanceof NoInternetActivity)) {
            BaseFragment.this.startActivity(new Intent(requireActivity(), NoInternetActivity.class));
        }
    }

    protected final void finishNoInternet() {
        if (requireActivity() instanceof NoInternetActivity) {
            requireActivity().finish();
        }
    }

    public void showSuccessDialog(String msg, DialogInterface.OnClickListener listener) {
        if (isAttached()) {
            new AlertDialog.Builder(requireContext())
                    .setMessage(msg)
                    // Specifying a listener allows you to take an action before dismissing the dialog.
                    // The dialog is automatically dismissed when a dialog button is clicked.
                    .setPositiveButton(android.R.string.yes, listener)
                    .setCancelable(false)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }

    public void showAlertDialog(Context context, String msg) {
        if (isAttached()) {
            new AlertDialog.Builder(context)
                    .setMessage(msg)

                    // Specifying a listener allows you to take an action before dismissing the dialog.
                    // The dialog is automatically dismissed when a dialog button is clicked.
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Continue with delete operation
                        }
                    })

                    // A null listener allows the button to dismiss the dialog and take no further action.
                    .setNegativeButton(android.R.string.no, null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }


    protected String getNonNullString(Editable text) {
        if (text == null) {
            return "";
        } else {
            return text.toString().trim();
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void requestPermissionsSafely(String[] permissions, int requestCode, Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(activity, permissions, requestCode);
        }
    }

    public void showProgress() {
        if (rootLayout == null) {
            return;
        }
        progressBar = rootLayout.findViewById(R.id.lyt_progress);
        if (progressBar != null) {
            progressBar.post(() -> progressBar.setVisibility(View.VISIBLE));
        }
    }

    public void hideProgress() {
        if (rootLayout == null) {
            return;
        }
        progressBar = rootLayout.findViewById(R.id.lyt_progress);
        if (progressBar != null) {
            progressBar.post(() -> progressBar.setVisibility(View.GONE));
        }
    }

    protected void hideKeyboard() {
        try {
            View view = requireActivity().getCurrentFocus();

            InputMethodManager imm = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    protected void hideNoData() {
        lytNoData = getView().findViewById(R.id.lytNoData);
        if (lytNoData != null) {
            lytNoData.setVisibility(View.GONE);
        }

    }

    //deprecation fix
    public RequestBody createRequestBody(String value) {
        return RequestBody.create(value, MultipartBody.FORM);
    }

    protected void showNoData(String msg) {
        lytNoData = getView().findViewById(R.id.lytNoData);

        if (lytNoData != null) {
            AppCompatTextView txtNoData = getView().findViewById(R.id.txtNoData);
            lytNoData.setVisibility(View.VISIBLE);
            if (txtNoData != null && msg != null && msg.length() > 0) {
                txtNoData.setText(msg);
            }
        }
    }

    protected boolean showSnack(String msg, boolean longDuration) {
        if (rootLayout == null) {
            return false;
        }
        showSnack(msg, rootLayout, Snackbar.LENGTH_LONG);
        return true;
    }

    protected boolean showSnack(@StringRes int msgId) {
        if (rootLayout == null) {
            return false;
        }
        String msg = getString(msgId);
        showSnack(msg);
        return true;
    }

    protected final boolean showSnack(String msg) {
        if (rootLayout == null) {
            return false;
        }
        showSnack(msg, rootLayout);
        return true;
    }

    protected void showSnack(String msg, View rootView) {
        showSnack(msg, rootView, Snackbar.LENGTH_SHORT);
    }

    protected void showSnack(String msg, View rootView, @BaseTransientBottomBar.Duration int duration) {
        Snackbar snackBar = Snackbar.make(rootView, msg, duration);
        snackBar.getView().setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        snackBar.show();
    }

    public boolean isValidMobile(String phone) {
        if (phone.length() != 10) {
            return false;
        }
        return android.util.Patterns.PHONE.matcher(phone).matches();
    }

    public boolean isValidEmail(String emailId) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(emailId).matches();
    }

    public boolean dispatchBackPress(){
        return false;
    }
}




