package com.dano.yamba.broadcast;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.dano.yamba.activity.MainActivity;

public class NotificationReceiver extends BroadcastReceiver {
	
	public static final int NOTIFICATION_ID = 42;
	
	private static final String TAG = NotificationReceiver.class.getSimpleName();

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	@Override
	public void onReceive(Context context, Intent intent) {
		NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
		
		int count = intent.getIntExtra("count", 0);
		
		PendingIntent pendingIntent = PendingIntent.getActivity(
			context, -1, new Intent(context, MainActivity.class), PendingIntent.FLAG_ONE_SHOT);
		
		Notification notification = new Notification.Builder(context)
			.setContentTitle("New Tweets")
			.setContentText("You've got " + count + " tweets")
			.setSmallIcon(android.R.drawable.sym_action_email)
			.setContentIntent(pendingIntent)
			.setAutoCancel(true)
			.build();
		
		notificationManager.notify(NOTIFICATION_ID, notification);
		
		Log.d(TAG, "onReceive");
	}

}
