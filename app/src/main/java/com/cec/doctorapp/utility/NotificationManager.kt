package com.cec.doctorapp.utility

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationChannelGroup
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.os.Build
import androidx.annotation.DrawableRes
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.cec.doctorapp.R

@Suppress("unused", "MemberVisibilityCanBePrivate")
class NotificationManager {

    companion object {
        public const val defaultChannelId = "com.cec.doctorapp.default"
        public const val defaultChannelName = "Children's Emurgent Care Normal"

        public const val emergencyChannelId = "com.cec.doctorapp.emergency"
        public const val emergencyChannelName = "Children's Emurgent Care Emergency"

        public const val highChannelId = "com.cec.doctorapp.high"
        public const val highChannelName = "Children's Emurgent Care High"

        public const val mediumChannelId = "com.cec.doctorapp.medium"
        public const val mediumChannelName = "Children's Emurgent Care Medium"


        public const val lowChannelId = "com.cec.doctorapp.low"
        public const val lowChannelName = "Children's Emurgent Care Low"


        public const val defaultGroupId = "com.cec.doctorapp.grp_default"
        public const val defaultGroupName = "Children's Emurgent Care"


        private fun managerCompat(context: Context) = NotificationManagerCompat.from(context)

        fun initializeDefaults(context: Context) {
            this.createDefaultGroup(context)
            this.createDefaultChannel(context)
            this.noCreateNotificationChannel(
                    context,
                    emergencyChannelId,
                    emergencyChannelName,
                    NotificationManagerCompat.IMPORTANCE_MAX,
                    defaultGroupId
            )

            this.noCreateNotificationChannel(
                    context,
                    highChannelId,
                    highChannelName,
                    NotificationManagerCompat.IMPORTANCE_HIGH,
                    defaultGroupId
            )

            this.noCreateNotificationChannel(
                    context,
                    mediumChannelId,
                    mediumChannelName,
                    NotificationManagerCompat.IMPORTANCE_DEFAULT,
                    defaultGroupId
            )

            this.noCreateNotificationChannel(
                    context,
                    lowChannelId,
                    lowChannelName,
                    NotificationManagerCompat.IMPORTANCE_LOW,
                    defaultGroupId
            )

        }

        private fun createDefaultGroup(context: Context) {
            this.createNotificationGroup(context, defaultGroupId, defaultGroupName)
        }

        private fun createDefaultChannel(context: Context) {
            this.noCreateNotificationChannel(
                    context,
                    defaultChannelId,
                    defaultChannelName,
                    NotificationManagerCompat.IMPORTANCE_DEFAULT,
                    defaultGroupId
            )
        }

        fun createNotificationGroup(
                context: Context,
                id: String,
                name: CharSequence
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                this.managerCompat(context).createNotificationChannelGroup(
                        NotificationChannelGroup(
                                id,
                                name
                        )
                )
            }
        }

        fun createNotificationChannel(
                context: Context,
                channelId: String,
                channelName: CharSequence,
                importance: Int,
                groupId: String = defaultGroupId
        ) {
            this.noCreateNotificationChannel(context, channelId, channelName, importance, groupId)
        }


        private fun noCreateNotificationChannel(
                context: Context,
                id: String,
                name: CharSequence,
                importance: Int,
                groupId: String
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                this.managerCompat(context).run {
                    this.getNotificationChannel(id)
                            ?: this.createNotificationChannel(NotificationChannel(id, name, importance).apply {
                                group = groupId
                                if (id == lowChannelId) {
                                    setSound(null, null);
                                } else {
                                    setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION), null)
                                }
                                enableVibration(
                                        when (id) {
                                            emergencyChannelId
                                            -> {
                                                true
                                            }
                                            else -> {
                                                false
                                            }
                                        }
                                )
                                lockscreenVisibility = when (id) {
                                    mediumChannelId,
                                    emergencyChannelId,
                                    highChannelId -> {
                                        NotificationCompat.VISIBILITY_PUBLIC
                                    }
                                    else -> {
                                        NotificationCompat.VISIBILITY_SECRET
                                    }
                                }
                            })
                }
            }
        }


        fun getBuilder(context: Context, channelId: String = defaultChannelId): NotificationCompat.Builder {
            return NotificationCompat.Builder(context, channelId)
        }

        fun simpleBuilder(
                context: Context,
                title: String,
                message: String,
                @DrawableRes smallIcon: Int,
                channelId: String = defaultChannelId,
                groupId: String = defaultGroupId,
                priority: Int = NotificationCompat.PRIORITY_DEFAULT,
                withSound: Boolean = false,
                withLargeIcon: Boolean = false
        ): NotificationCompat.Builder {
            val builder = NotificationCompat.Builder(context, channelId)
                    .setSmallIcon(smallIcon)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setPriority(priority)
                    .setGroup(groupId)
            if (withSound) {
                builder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
            } else {
                builder.setSound(null)
            }
            if (withLargeIcon) {
                builder.setLargeIcon(BitmapFactory.decodeResource(context.resources, R.drawable.ceclogo))
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder.color = ContextCompat.getColor(context, R.color.gradient_orange_start)
            }
            return builder
        }

        fun simpleBigTextBuilder(
                context: Context,
                title: String,
                message: String,
                @DrawableRes smallIcon: Int,
                channelId: String = defaultChannelId,
                priority: Int = NotificationCompat.PRIORITY_DEFAULT,
                withSound: Boolean = false): NotificationCompat.Builder {
            return simpleBuilder(
                    context,
                    title,
                    message,
                    smallIcon,
                    channelId,
                    defaultGroupId,
                    priority,
                    withSound,
                    withLargeIcon = true
            )
                    .setStyle(NotificationCompat.BigTextStyle().bigText(message))
        }


        fun addServiceIntent(
                context: Context,
                notificationBuilder: NotificationCompat.Builder,
                intent: Intent,
                requestCode: Int,
                flags: Int
        ): NotificationCompat.Builder {
            notificationBuilder.setContentIntent(
                    PendingIntent.getService(
                            context,
                            requestCode,
                            intent,
                            flags
                    )
            )
            return notificationBuilder
        }

        fun addActivityIntent(
                context: Context,
                notificationBuilder: NotificationCompat.Builder,
                intent: Intent,
                requestCode: Int,
                flags: Int
        ): NotificationCompat.Builder {
            notificationBuilder.setContentIntent(
                    PendingIntent.getActivity(
                            context,
                            requestCode,
                            intent,
                            flags
                    )
            )
            return notificationBuilder
        }


        fun notify(context: Context, uid: Int, notificationBuilder: NotificationCompat.Builder) {
            this.managerCompat(context).notify(uid, notificationBuilder.build())
        }

        fun notify(context: Context, uid: Int, notification: Notification) {
            this.managerCompat(context).notify(uid, notification)
        }

        fun cancel(context: Context, uid: Int) {
            this.managerCompat(context).cancel(uid)
        }
    }

}