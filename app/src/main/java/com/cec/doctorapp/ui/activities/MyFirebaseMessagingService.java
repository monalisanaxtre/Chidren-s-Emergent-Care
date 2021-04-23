package com.cec.doctorapp.ui.activities;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.cec.doctorapp.R;
import com.cec.doctorapp.helper.Constants;
import com.cec.doctorapp.helper.SPUtils;
import com.cec.doctorapp.helper.Utility;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.jetbrains.annotations.NotNull;

import java.util.Map;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private SPUtils sharedPrefsHelper;
    private String title = "";
    private String message = "";
    private String image = "";

    @Override
    public void onNewToken(@NotNull String token) {
        super.onNewToken(token);
        sharedPrefsHelper = SPUtils.getFCMPref(this);
        sharedPrefsHelper.put(Constants.DEVICE_TOKEN, token);
        Log.d("TAG", "token genrate");
    }

    /**
     * data: {
     * "title": req.body.subject,
     * "body": req.body.msg ,
     * "image": img_url
     * },
     */
    @Override
    public void onMessageReceived(@NotNull RemoteMessage message) {
        super.onMessageReceived(message);
        sharedPrefsHelper = SPUtils.getFCMPref(getApplicationContext());
        Map<String, String> map = message.getData();
        if (map != null && !map.isEmpty()) {
            this.title = map.get("title") != null ? map.get("title") : "";
            this.message = map.get("body") != null ? map.get("message") : "";
            this.image = map.get("image") != null ? map.get("image") : "";
            Log.d("TAG", "" + map);
            sendNotification();
        } else if (message.getNotification() != null) {
//                this.message =  body;
            this.title = message.getNotification().getTitle() != null ? message.getNotification().getTitle() : "";
            this.message = message.getNotification().getBody() != null ? message.getNotification().getBody() : "";
            this.image = message.getNotification().getImageUrl() != null ? String.valueOf(message.getNotification().getImageUrl()) : "";
            Log.d("TAG", "" + "title" + title + "\nbody" + this.message+""+image);
            sendNotification();
        }
    }


    private void sendNotification() {
        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        Intent intent = new Intent(this, NotificationActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        final NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "channel_id")
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentIntent(pendingIntent)
                .setLargeIcon(icon)
                .setColor(Color.RED)
                .setLights(Color.RED, 1000, 300)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setSmallIcon(R.mipmap.cec);


        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        // Notification Channel is required for Android O and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    "channel_id", "channel_name", NotificationManager.IMPORTANCE_DEFAULT
            );
            channel.setDescription("channel description");
            channel.setShowBadge(true);
            channel.canShowBadge();
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500});
            notificationManager.createNotificationChannel(channel);
        }


        if (Utility.isNotEmpty(image)) {
            Glide.with(this)
                    .asBitmap()
                    .load(Uri.parse(image))
                    .placeholder(R.mipmap.ic_launcher)
                    .into(new CustomTarget<Bitmap>(200, 200) {
                        @Override
                        public void onResourceReady(@NotNull Bitmap resource, Transition<? super Bitmap> transition) {
                            new Handler(Looper.getMainLooper()).post(()->{
                                notificationBuilder.setLargeIcon(resource);
                                launchNotification(notificationBuilder,notificationManager);
                            });
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {

                        }
                    });
        }else{
            launchNotification(notificationBuilder,notificationManager);
        }


    }


    private void launchNotification(NotificationCompat.Builder notificationBuilder, NotificationManagerCompat notificationManager) {
        notificationManager.notify(0, notificationBuilder.build());
    }

}


//
//public class MyFirebaseMessagingService  extends FirebaseMessagingService {
//
//    private SPUtils sharedPrefsHelper;
//    @Override
//    public void onNewToken(String token) {
//        super.onNewToken(token);
//        sharedPrefsHelper = SPUtils.getFCMPref(this);
//        sharedPrefsHelper.put(Constants.DEVICE_TOKEN, token);
//        Log.d("TAG", "token genrate");
//    }
//
//    @Override
//    public void onMessageReceived(RemoteMessage remoteMessage) {
//        super.onMessageReceived(remoteMessage);
//        // TODO(developer): Handle FCM messages here.
//        RemoteMessage.Notification notification = remoteMessage.getNotification();
//        Map<String, String> data = remoteMessage.getData();
//        sendNotification(notification, data);
//
//    }
//    /**
//     * Create and show a custom notification containing the received FCM message.
//     *
//     * @param notification FCM notification payload received.
//     * @param data FCM data payload received.
//     */
//    private void sendNotification(RemoteMessage.Notification notification, Map<String, String> data) {
//        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
//
//        Intent intent = new Intent(this, MainActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
//
//        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "channel_id")
//                .setContentTitle(notification.getTitle())
//                .setContentText(notification.getBody())
//                .setAutoCancel(true)
//                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
//                .setContentIntent(pendingIntent)
//                .setContentInfo(notification.getTitle())
//                .setLargeIcon(icon)
//                .setColor(Color.RED)
//                .setLights(Color.RED, 1000, 300)
//                .setDefaults(Notification.DEFAULT_VIBRATE)
//                .setSmallIcon(R.mipmap.ic_launcher);
//
//        try {
//            String picture_url = data.get("picture_url");
//            if (picture_url != null && !"".equals(picture_url)) {
//                URL url = new URL(picture_url);
//                Bitmap bigPicture = BitmapFactory.decodeStream(url.openConnection().getInputStream());
//                notificationBuilder.setStyle(
//                        new NotificationCompat.BigPictureStyle().bigPicture(bigPicture).setSummaryText(notification.getBody())
//                );
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//        // Notification Channel is required for Android O and above
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            NotificationChannel channel = new NotificationChannel(
//                    "channel_id", "channel_name", NotificationManager.IMPORTANCE_DEFAULT
//            );
//            channel.setDescription("channel description");
//            channel.setShowBadge(true);
//            channel.canShowBadge();
//            channel.enableLights(true);
//            channel.setLightColor(Color.RED);
//            channel.enableVibration(true);
//            channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500});
//            notificationManager.createNotificationChannel(channel);
//        }
//
//        notificationManager.notify(0, notificationBuilder.build());
//    }
