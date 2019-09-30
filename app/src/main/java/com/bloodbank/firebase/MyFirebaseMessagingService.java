package com.bloodbank.firebase;

/**
 * Created by Manish on 11/22/2016.
 */

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.bloodbank.R;
import com.bloodbank.data.sqlite.Config;
import com.bloodbank.data.sqlite.KeyValueDb;
import com.bloodbank.ui.MainActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

import net.simplifiedcoding.mvvmsampleapp.data.preferences.PreferenceProvider;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONException;
import org.json.JSONObject;
import org.kodein.di.Kodein;
import org.kodein.di.KodeinAware;
import org.kodein.di.KodeinContext;
import org.kodein.di.KodeinTrigger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


public class MyFirebaseMessagingService extends FirebaseMessagingService implements KodeinAware {
    private static final String TAG = "MyFirebaseMsgService";
    private int notification_id = 0, message_id = 0;
    private String CHANNEL_ID = "borrow";
    String TYPE;

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.d("Refreshed Token", s);
        KeyValueDb.set(getApplicationContext(), Config.TOKEN, s, 1);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.d("onMessageReceived","Check");
        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());
            try {

                Log.d("Try","Just got into try");
                JSONObject json = new JSONObject(remoteMessage.getData().toString());


                parseNotificationData(json);
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
        }

    }


    //this method will display the notification
    //We are passing the JSONObject that is received from
    //firebase cloud messaging
    private void parseNotificationData(JSONObject json) {
        //optionally we can display the json into log
        Log.e(TAG, "Notification JSON " + json.toString());
        try {
            //getting the json data
            JSONObject data = json.getJSONObject("data");
            JSONObject payload = data.getJSONObject("payload");

            String message = payload.getString("message");
            String type = payload.getString("type");

        }
        catch (Exception e) {
            Log.e("Exception",e.getMessage());
        }
    }

    private void showNotification(String title, String message) throws JSONException {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        Intent intent = new Intent(this, MainActivity.class);

        Gson gson =new Gson();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(title)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(message))
                .setShowWhen(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);


        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(notification_id, mBuilder.build());
    }

//    private void saveNotification(String title, String message, String userid, User from) {
//        DatabaseReference notRefs = FirebaseDatabase.getInstance().getReference(Config.FIREBASE_NOTIFICATIONS);
//        String id = notRefs.push().getKey();
//        Notification notification = new Notification(id, title, message, getTime(), userid, from);
//        notRefs.child(id).setValue(notification);
//    }

    public String getTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm ", Locale.US);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        return sdf.format(new Date());
    }


    @NotNull
    @Override
    public Kodein getKodein() {
        return null;
    }

    @NotNull
    @Override
    public KodeinContext<?> getKodeinContext() {
        return null;
    }

    @Nullable
    @Override
    public KodeinTrigger getKodeinTrigger() {
        return null;
    }
}

