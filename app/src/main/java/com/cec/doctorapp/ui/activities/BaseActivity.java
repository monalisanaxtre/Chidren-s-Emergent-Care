package com.cec.doctorapp.ui.activities;
import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.cec.doctorapp.R;
import com.cec.doctorapp.app.AppComponents;
import com.cec.doctorapp.app.AppController;
import com.cec.doctorapp.helper.Constants;
import com.cec.doctorapp.helper.NetworkState;
import com.cec.doctorapp.ui.interfaces.FragmentCallback;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
public class BaseActivity extends AppCompatActivity implements FragmentCallback {

    protected String TAG = "MJERROR";
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {Manifest.permission.WRITE_EXTERNAL_STORAGE};

    public String[] PERMISSIONS = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.INTERNET};
    ProgressDialog progressDialog;
    LocalBroadcastManager localBroadcastManager;
    protected AppComponents graph;
    protected View rootLayout;




    private final BroadcastReceiver networkStateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent == null) return;
            if (intent.getAction().equals(Constants.NETWORK_STATE_CHANGE)) {
                NetworkState state = (NetworkState) intent.getSerializableExtra(Constants.NETWORK_STATE_CHANGE);
                if (state.equals(NetworkState.AVAILABLE)) {
                    BaseActivity.this.finishNoInternet();
                    Log.d("SimpleBaseActivity", "onAvailable::receiver");
                } else if (state.equals(NetworkState.LOST)) {
                    BaseActivity.this.openNoInternet();
                    Log.d("SimpleBaseActivity", "onLost::receiver");
                }

            }
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        localBroadcastManager = LocalBroadcastManager.getInstance(this);

        IntentFilter filter = new IntentFilter();
        filter.addAction("SESSION_EXPIRED");
        filter.addAction("NO_INTERNET");
        filter.addAction("REFRESH_HOME");
        localBroadcastManager.registerReceiver(broadcastListener, filter);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(("Please wait"));
        graph = ((AppController) getApplication()).getGraph();


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

    @TargetApi(Build.VERSION_CODES.M)
    public void requestPermissionsSafely(String[] permissions, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode);
        }
    }








    private View progressBar;

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

    View lytNoData;

    protected void hideNoData() {
        lytNoData = findViewById(R.id.lytNoData);
        if (lytNoData != null) {
            lytNoData.setVisibility(View.GONE);
        }

    }

    protected void showNoData(String msg) {
        lytNoData = findViewById(R.id.lytNoData);

        if (lytNoData != null) {
            AppCompatTextView txtNoData = findViewById(R.id.txtNoData);
            lytNoData.setVisibility(View.VISIBLE);
            if (txtNoData != null && msg != null && msg.length() > 0) {
                txtNoData.setText(msg);
            }
        }
    }







    protected boolean showSnack(String msg) {
        if (rootLayout == null) {
            return false;
        }
        showSnack(msg, rootLayout);
        return true;
    }

    protected final boolean showSnack(String msg, boolean longDuration) {
        if (rootLayout == null) {
            return false;
        }
        showSnack(Snackbar.LENGTH_LONG, msg, rootLayout);
        return true;
    }

    protected void showSnack(@BaseTransientBottomBar.Duration int duration, String msg, View rootView) {
        Snackbar snackBar = Snackbar.make(rootView, msg, duration);
        snackBar.getView().setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        snackBar.show();
    }

    protected final void showSnack(String msg, View rootView) {
        Snackbar snackBar = Snackbar.make(rootView, msg, Snackbar.LENGTH_SHORT);
        snackBar.getView().setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        snackBar.show();
    }

    protected void showSnackError(String msg, View rootView) {
        Snackbar snackBar = Snackbar.make(rootView, Html.fromHtml("<font color=\"#ffffff\">" + msg + "</font>"), Snackbar.LENGTH_SHORT);
        snackBar.getView().setBackgroundColor(getResources().getColor(R.color.orage50));

        snackBar.show();
    }

    protected void showSnack(String msg, View rootView, int color) {
        Snackbar snackBar = Snackbar.make(rootView, msg, Snackbar.LENGTH_SHORT);
        snackBar.getView().setBackgroundColor(color);
        snackBar.show();
    }


    protected void showSnackLong(String msg, View rootView) {
        Snackbar snackBar = Snackbar.make(rootView, msg, Snackbar.LENGTH_LONG);
        snackBar.getView().setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        snackBar.show();
    }


    protected void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
    public void showSuccessDialog(String msg, DialogInterface.OnClickListener listener) {
        new AlertDialog.Builder(this)
                .setMessage(msg)
                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, listener)
                .setCancelable(false)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    @SuppressLint("MissingPermission")
    Boolean isInternetOn() {
        return ((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo().isConnected();

    }

    protected void launchActivity(Class clazz, Bundle bundle, Boolean clearStack) {

        Intent i = new Intent(this, clazz);
        if (bundle != null) {
            i.putExtras(bundle);
        }
        if (clearStack) {
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        }
        startActivity(i);
    }

    protected void launchActivity(Class clazz, Bundle bundle) {

        launchActivity(clazz, bundle, false);
    }


    protected void launchActivityWithoutHistory(Class clazz, Bundle bundle, Boolean clearStack) {

        Intent i = new Intent(this, clazz);
        if (bundle != null) {
            i.putExtras(bundle);
        }
        i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        if (clearStack) {
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        }
        startActivity(i);


    }


    protected void launchActivityWithResult(Class clazz, Bundle bundle, int requestCode) {
        Intent i = new Intent(this, clazz);
        if (bundle != null) {
            i.putExtras(bundle);
        }


        startActivityForResult(i, requestCode);

    }


    @SuppressLint("RestrictedApi")
    protected void launchActivityWithOptions(Class clazz, Bundle bundle, int requestCode, View view) {
        Intent i = new Intent(this, clazz);
        if (bundle != null) {
            i.putExtras(bundle);
        }

        ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this, view, ViewCompat.getTransitionName(view));
        if (requestCode == 0) {
            startActivity(i, activityOptionsCompat.toBundle());
        } else {
            startActivityForResult(i, requestCode, activityOptionsCompat.toBundle());
        }

    }


    @Override
    protected void onPause() {
        super.onPause();
    }




    @Override
    protected void onDestroy() {
        localBroadcastManager.unregisterReceiver(broadcastListener);
        super.onDestroy();
    }




    private final BroadcastReceiver broadcastListener = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            rootLayout = findViewById(R.id.rootLayout);
            switch (intent.getAction()) {
                case "SESSION_EXPIRED": {
//                    showSessionExpiredDialog();
                    break;
                }
                case "NO_INTERNET": {
                    if (!(BaseActivity.this instanceof NoInternetActivity)) {
                        BaseActivity.this.openNoInternet();
                    }
                    break;
                }

            }

        }

    };

    protected final void openNoInternet() {
        if (!(this instanceof NoInternetActivity)) {
            BaseActivity.this.startActivity(new Intent(BaseActivity.this, NoInternetActivity.class));
        }
    }

    protected final void finishNoInternet() {
        if (this instanceof NoInternetActivity) {
            this.finish();
        }
    }


    protected void showSessionExpiredDialog(String msg) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setCancelable(false);
        dialog.setMessage(msg);
        dialog.setPositiveButton("Ok", (dialogInterface, i) -> {

        });

        dialog.create().show();
    }

    public void backBtnPressed(View view) {

        finish();
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

    public void showQuestionDialog(String msg,
                                   @StringRes int positive,
                                   @StringRes int negative,
                                   DialogInterface.OnClickListener listener) {
        new AlertDialog.Builder(this)
                .setMessage(msg)
                .setPositiveButton(positive, listener)
                .setNegativeButton(negative, (d, v) -> {
                })
                .setCancelable(false)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }




    @Override
    protected void onResume() {
        super.onResume();
        this.registerUnRegisterNetworkReceiver(true);
    }

    @Override
    public void onStop() {
        this.registerUnRegisterNetworkReceiver(false);
        super.onStop();
    }

    private void registerUnRegisterNetworkReceiver(Boolean register) {
        if (register) {
            this.localBroadcastManager.registerReceiver(networkStateReceiver,new IntentFilter(Constants.NETWORK_STATE_CHANGE));
        } else {
            this.localBroadcastManager.unregisterReceiver(networkStateReceiver);
        }
    }
}
