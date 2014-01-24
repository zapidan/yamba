package com.dano.yamba.activity;

import com.dano.yamba.R;
import com.dano.yamba.database.StatusContract;
import com.dano.yamba.service.RefreshService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.action_settings:
				startActivity(new Intent(this, SettingsActivity.class));
				break;
			case R.id.action_tweet:
				startActivity(new Intent(this, StatusActivity.class));
				break;
			case R.id.action_referesh:
				startService(new Intent(this, RefreshService.class));
				break;
			case R.id.action_purge:
				int rows = getContentResolver().delete(StatusContract.CONTENT_URI, null, null);
				Toast.makeText(this, "Deleted: " + rows + " rows" , Toast.LENGTH_LONG).show();
				break;
			default:
				return false;
		}
		
		return true;
	}

}
