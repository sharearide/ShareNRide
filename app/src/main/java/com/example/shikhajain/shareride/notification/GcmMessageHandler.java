package com.example.shikhajain.shareride.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;

import com.google.android.gms.gcm.GcmListenerService;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by master on 2015-10-14.
 */
public class GcmMessageHandler extends GcmListenerService {
    public static final int MESSAGE_NOTIFICATION_ID = 435345;

    @Override
    public void onMessageReceived(String from, Bundle data) {
    }

    private void createNotificationChat(Context context, JSONObject jsonObjectChat) {
        /*NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new Notification(icon, notiTitle, notificationTime);

        Intent notificationIntent = new Intent(context, .class);
        notificationIntent.putExtra("member_id", Id);
        notificationIntent.putExtra("member_name", name);

        //notiTitle	=	getResources().getString(R.string.app_name);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        notification.setLatestEventInfo(context, notiTitle, notiMsg, pendingIntent);
        notification.flags |= notification.FLAG_AUTO_CANCEL;
        notification.defaults |= Notification.DEFAULT_SOUND;
        notification.vibrate = new long[]{100L, 100L, 200L, 500L};
        notificationManager.notify(1, notification);
        //  ChatActivity.doChatRefresh();

        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "TAG");
        wl.acquire();
    }*/

    }
}