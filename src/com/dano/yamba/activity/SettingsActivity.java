package com.dano.yamba.activity;

import com.dano.yamba.fragment.SettingsFragment;

import android.app.Activity;
import android.os.Bundle;

public class SettingsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if (savedInstanceState == null) {
			SettingsFragment settingsFragment = new SettingsFragment();
			
			getFragmentManager().beginTransaction()
				.add(android.R.id.content, settingsFragment, settingsFragment.getClass().getSimpleName())
			.commit();
		}
	}

}
