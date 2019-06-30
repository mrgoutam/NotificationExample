package com.stark.notificationexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import androidx.core.app.RemoteInput;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.media.session.MediaSessionCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static com.stark.notificationexample.App.CHANNEL_1_ID;
import static com.stark.notificationexample.App.CHANNEL_2_ID;


/*
Documentation:
NotificationManager:
developer.android.com/reference/android/app/NotificationManager
NotificationChannel:
developer.android.com/reference/android/app/NotificationChannel
NotificationCompat:
developer.android.com/reference/android/support/v4/app/NotificationCompat
NotificationCompat.Builder:
developer.android.com/reference/android/support/v4/app/NotificationCompat.Builder
 */
public class MainActivity extends AppCompatActivity {

    private NotificationManagerCompat notificationManager;
    private EditText editTextTitle;
    private EditText editTextMessage;
    private MediaSessionCompat mediaSession;
    static List<Message> MESSAGES = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notificationManager = NotificationManagerCompat.from(this);

        editTextTitle = findViewById(R.id.edit_text_title);
        editTextMessage = findViewById(R.id.edit_text_message);
        mediaSession = new MediaSessionCompat(this, "tag");

        MESSAGES.add(new Message("Good morning!", "Jim"));
        MESSAGES.add(new Message("Hello", null));
        MESSAGES.add(new Message("Hi!", "Jenny"));
    }

    public void sendOnChannel1(View v) {
        String title = editTextTitle.getText().toString();
        String message = editTextMessage.getText().toString();

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_priority_high)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notificationManager.notify(1, notification);
    }

    public void sendOnChannel2(View v) {
        String title = editTextTitle.getText().toString();
        String message = editTextMessage.getText().toString();

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_2_ID)
                .setSmallIcon(R.drawable.ic_low_priority)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .build();

        notificationManager.notify(2, notification);
    }

    public void NotificationAction(View view) {
        String title = editTextTitle.getText().toString();
        String message = editTextMessage.getText().toString();


        //this pending intent to go to the main activity
        Intent activityIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this,
                0, activityIntent, 0);

        //this pending intetn to trigger the broadcast and show toast
        Intent broadcastIntent = new Intent(this, NotificationReceiver.class);
        broadcastIntent.putExtra("toastMessage", message);

        //FLAG_UPDATE_CURRENT: update its content
        PendingIntent actionIntent = PendingIntent.getBroadcast(this,
                0, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_priority_high)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)

                //change the color
                .setColor(Color.BLUE)

                //onclick on notification it will navigate to the main activity
                .setContentIntent(contentIntent)

                //notification will dismiss on click on notification
                .setAutoCancel(true)

                //notification will appear
                .setOnlyAlertOnce(true)

                //a button will appear and onclick on it, it performs its action
                .addAction(R.mipmap.ic_launcher, "Trigger Toast", actionIntent)
                .build();

        notificationManager.notify(1, notification);
    }

    public void bigTextStyle(View view) {
        String title = editTextTitle.getText().toString();
        String message = editTextMessage.getText().toString();


        //this pending intent to go to the main activity
        Intent activityIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this,
                0, activityIntent, 0);

        //this pending intetn to trigger the broadcast and show toast
        Intent broadcastIntent = new Intent(this, NotificationReceiver.class);
        broadcastIntent.putExtra("toastMessage", message);

        //FLAG_UPDATE_CURRENT: update its content
        PendingIntent actionIntent = PendingIntent.getBroadcast(this,
                0, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.apple_logo);
        String bigStr = "Dive into your own personal newsstand, with full access to hundreds of magazines and leading newspapers. Flip through current and past issues as covers and layouts come alive in beautiful new ways.** Download a magazine or save a recommended article to read on the go. And continue to enjoy all the amazing features of Apple News — all for one great price.";

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_priority_high)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)

                //change the color
                .setColor(Color.BLUE)

                // attaching a large icon
                .setLargeIcon(largeIcon)

                // giving big text style
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(bigStr)
                        .setBigContentTitle("Big Content Title")
                        .setSummaryText("Summary Text"))

                //onclick on notification it will navigate to the main activity
                .setContentIntent(contentIntent)

                //notification will dismiss on click on notification
                .setAutoCancel(true)

                //notification will appear
                .setOnlyAlertOnce(true)

                //a button will appear and onclick on it, it performs its action
                .addAction(R.mipmap.ic_launcher, "Trigger Toast", actionIntent)
                .build();

        notificationManager.notify(1, notification);
    }

    public void InboxStyle(View view) {
        String title = editTextTitle.getText().toString();
        String message = editTextMessage.getText().toString();

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_2_ID)
                .setSmallIcon(R.drawable.ic_low_priority)
                .setContentTitle(title)
                .setContentText(message)

                //iinbox style upto seven line we can add
                .setStyle(new NotificationCompat.InboxStyle()
                        .addLine("This is line 1")
                        .addLine("This is line 2")
                        .addLine("This is line 3")
                        .addLine("This is line 4")
                        .addLine("This is line 5")
                        .addLine("This is line 6")
                        .addLine("This is line 7")
                        .setBigContentTitle("Big Content Title")
                        .setSummaryText("Summary Text"))
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .build();

        notificationManager.notify(2, notification);
    }

    public void bigPictureStyle(View view) {
        String title = editTextTitle.getText().toString();
        String message = editTextMessage.getText().toString();


        //this pending intent to go to the main activity
        Intent activityIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this,
                0, activityIntent, 0);

        Bitmap bigPicture = BitmapFactory.decodeResource(getResources(), R.drawable.tony);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_priority_high)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)

                //change the color
                .setColor(Color.BLUE)

                // attaching a large icon
                .setLargeIcon(bigPicture)

                // giving big Picture style
                .setLargeIcon(bigPicture)
                .setStyle(new NotificationCompat.BigPictureStyle()
                        .bigPicture(bigPicture)
                        .bigLargeIcon(null))  //as already attache above

                //onclick on notification it will navigate to the main activity
                .setContentIntent(contentIntent)

                //notification will dismiss on click on notification
                .setAutoCancel(true)

                //notification will appear
                .setOnlyAlertOnce(true)
                .build();

        notificationManager.notify(1, notification);
    }

    public void mediaStyle(View view) {
        Bitmap artwork = BitmapFactory.decodeResource(getResources(), R.drawable.apple_logo);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_2_ID)
                .setSmallIcon(R.drawable.ic_low_priority)
                .setContentTitle("Content Title")
                .setContentText("Content text")
                .setLargeIcon(artwork)
                .addAction(R.drawable.ic_dislike, "Dislike", null)
                .addAction(R.drawable.ic_previous, "Previous", null)
                .addAction(R.drawable.ic_pause, "Pause", null)
                .addAction(R.drawable.ic_next, "Next", null)
                .addAction(R.drawable.ic_like, "Like", null)
                .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                        .setShowActionsInCompactView(1, 2, 3) //at collapse: previous, pause, next image will show
                        //but in expand mode all will show
                        .setMediaSession(mediaSession.getSessionToken()))
                .setSubText("Sub Text")
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .build();

        notificationManager.notify(2, notification);
    }

    public void reply(View v) {
        sendChannel1Notification(this);
    }

    public static void sendChannel1Notification(Context context) {
        Intent activityIntent = new Intent(context, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context,
                0, activityIntent, 0);

        RemoteInput remoteInput = new RemoteInput.Builder("key_text_reply")
                .setLabel("Your answer...")
                .build();

        Intent replyIntent;
        PendingIntent replyPendingIntent = null;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            replyIntent = new Intent(context, DirectReplyReceiver.class);
            replyPendingIntent = PendingIntent.getBroadcast(context,
                    0, replyIntent, 0);
        } else {
            //start chat activity instead (PendingIntent.getActivity)
            //cancel notification with notificationManagerCompat.cancel(id)
        }

        NotificationCompat.Action replyAction = new NotificationCompat.Action.Builder(
                R.drawable.ic_reply,
                "Reply",
                replyPendingIntent
        ).addRemoteInput(remoteInput).build();

        NotificationCompat.MessagingStyle messagingStyle =
                new NotificationCompat.MessagingStyle("Me");
        messagingStyle.setConversationTitle("Group Chat");

        for (Message chatMessage : MESSAGES) {
            NotificationCompat.MessagingStyle.Message notificationMessage =
                    new NotificationCompat.MessagingStyle.Message(
                            chatMessage.getText(),
                            chatMessage.getTimestamp(),
                            chatMessage.getSender()
                    );
            messagingStyle.addMessage(notificationMessage);
        }

        Notification notification = new NotificationCompat.Builder(context, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_priority_high)
                .setStyle(messagingStyle)
                .addAction(replyAction)
                .setColor(Color.BLUE)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setContentIntent(contentIntent)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true)
                .build();

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(1, notification);
    }

    public void indeterminantProgress(View view) {
        final int progressMax = 100;

        final NotificationCompat.Builder notification = new NotificationCompat.Builder(this, CHANNEL_2_ID)
                .setSmallIcon(R.drawable.ic_low_priority)
                .setContentTitle("Download")
                .setContentText("Download in progress")
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setOngoing(true)//it cannot be dismiss by swiping until it is changed to false;
                .setOnlyAlertOnce(true)
                .setProgress(progressMax, 0, true);

        notificationManager.notify(2, notification.build());

        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(2000);
                for (int progress = 0; progress <= progressMax; progress += 20) {
                    SystemClock.sleep(1000);
                }
                notification.setContentText("Download finished")
                        .setProgress(0, 0, false)
                        .setOngoing(false);
                notificationManager.notify(2, notification.build());
            }
        }).start();
    }

    public void determinantProgress(View view) {
        final int progressMax = 100;

        final NotificationCompat.Builder notification = new NotificationCompat.Builder(this, CHANNEL_2_ID)
                .setSmallIcon(R.drawable.ic_low_priority)
                .setContentTitle("Download")
                .setContentText("Download in progress")
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setOngoing(true) //it cannot be dismiss by swiping until it is changed to false;
                .setOnlyAlertOnce(true)
                .setProgress(progressMax, 0, false);

        notificationManager.notify(2, notification.build());

        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(2000);
                for (int progress = 0; progress <= progressMax; progress += 10) {
                    notification.setProgress(progressMax, progress, false);
                    notificationManager.notify(2, notification.build());
                    SystemClock.sleep(1000);
                }
                notification.setContentText("Download finished")
                        .setProgress(0, 0, false)
                        .setOngoing(false);
                notificationManager.notify(2, notification.build());
            }
        }).start();
    }

    public void notificationGroup(View view) {
        Toast.makeText(this, "CF: Coming soon", Toast.LENGTH_SHORT).show();
    }

    public void nofificationChannelGroup(View view) {
        Toast.makeText(this, "CF: Coming soon", Toast.LENGTH_SHORT).show();
    }

    public void notificationChannelSetting(View view) {
        Toast.makeText(this, "CF: Coming soon", Toast.LENGTH_SHORT).show();
    }

    public void deleteNotificationChannel(View view) {
        Toast.makeText(this, "CF: Coming soon", Toast.LENGTH_SHORT).show();
    }

    public void customNotification(View view) {
        Toast.makeText(this, "CF: Coming soon", Toast.LENGTH_SHORT).show();
    }
}