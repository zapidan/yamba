package com.dano.yamba.broadcast;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.dano.yamba.service.RefreshService;

public class BootReceiver extends BroadcastReceiver {
	
	private static final String TAG = BootReceiver.class.getSimpleName();
	
	private static final long DEFAULT_INTERVAL = AlarmManager.INTERVAL_FIFTEEN_MINUTES;

	@Override
	public void onReceive(Context context, Intent intent) {
		
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		
		long interval = Long.parseLong(prefs.getString("interval", Long.toString(DEFAULT_INTERVAL)));
		
		PendingIntent pendingIntent = PendingIntent.getService(
			context, -1, new Intent(context, RefreshService.class), PendingIntent.FLAG_CANCEL_CURRENT);
		
		AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
		
		if (interval == 0) {
			alarmManager.cancel(pendingIntent);
			Log.d(TAG, "cancelling repeat operation");
		}
		else {
			alarmManager.setInexactRepeating(AlarmManager.RTC, System.currentTimeMillis(), interval, pendingIntent);
			Log.d(TAG, "setting repeat operation for: " + interval);
		}
		Log.d(TAG, "onReceived");
	}

}
