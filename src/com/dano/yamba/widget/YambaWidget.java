package com.dano.yamba.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.text.format.DateUtils;
import android.util.Log;
import android.widget.RemoteViews;

import com.dano.yamba.R;
import com.dano.yamba.activity.MainActivity;
import com.dano.yamba.database.StatusContract;

public class YambaWidget extends AppWidgetProvider {
	
	private static final String TAG = YambaWidget.class.getSimpleName();

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d(TAG, "onReceive");
		super.onReceive(context, intent);
		
		AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
		
		this.onUpdate(
				context, appWidgetManager, 
				appWidgetManager.getAppWidgetIds(
					new ComponentName(context, YambaWidget.class)));
	}

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
		int[] appWidgetIds) {
		Log.d(TAG, "onUpdate");
		
		Cursor cursor = context.getContentResolver().query(
				StatusContract.CONTENT_URI, null, null, null, StatusContract.DEFAULT_SORT);
				
		if (!cursor.moveToFirst()) {
			return;
		}
			
		String user = cursor.getString(cursor.getColumnIndex(StatusContract.Column.USER));
		String message = cursor.getString(cursor.getColumnIndex(StatusContract.Column.MESSAGE));
		long createdAt = cursor.getLong(cursor.getColumnIndex(StatusContract.Column.CREATED_AT));
		
		PendingIntent pendingIntent = PendingIntent.getActivity(
			context, -1, new Intent(context, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
		
		for (int appWidgetId : appWidgetIds) {
			RemoteViews remoteView = new RemoteViews(context.getPackageName(), R.layout.widget);
			
			remoteView.setTextViewText(R.id.list_item_text_user, user);
			remoteView.setTextViewText(R.id.list_item_text_message, message);
			remoteView.setTextViewText(R.id.list_item_text_created_at, DateUtils.getRelativeTimeSpanString(createdAt));
			
			remoteView.setOnClickPendingIntent(R.id.list_item_text_user, pendingIntent);
			remoteView.setOnClickPendingIntent(R.id.list_item_text_message, pendingIntent);
			
			appWidgetManager.updateAppWidget(appWidgetId, remoteView);
		}		
	}
	
}
