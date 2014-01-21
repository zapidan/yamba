package com.dano.yamba.activity;

import com.dano.yamba.R;
import com.dano.yamba.fragment.StatusFragment;
import com.dano.yamba.service.RefreshService;
import com.marakana.android.yamba.clientlib.YambaClient;
import com.marakana.android.yamba.clientlib.YambaClientException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class StatusActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (savedInstanceState == null) {
			StatusFragment statusFragment = new StatusFragment();
			getFragmentManager().beginTransaction()
				.add(android.R.id.content, statusFragment, statusFragment.getClass().getSimpleName())
			.commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
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
			default:
				return false;
		}
		
		return true;
	}
	
}
