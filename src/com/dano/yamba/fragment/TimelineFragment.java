package com.dano.yamba.fragment;

import android.app.ListFragment;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.SimpleCursorAdapter.ViewBinder;
import android.widget.TextView;

import com.dano.yamba.R;
import com.dano.yamba.activity.DetailsActivity;
import com.dano.yamba.database.StatusContract;

public class TimelineFragment extends ListFragment implements LoaderCallbacks<Cursor> {
	
	private static final String TAG = TimelineFragment.class.getSimpleName();
	
	private static final String[] FROM = 
		{ StatusContract.Column.USER, StatusContract.Column.MESSAGE, 
		  StatusContract.Column.CREATED_AT, StatusContract.Column.CREATED_AT };
	
	private static final int[] TO = 
		{ R.id.list_item_text_user, R.id.list_item_text_message, 
		  R.id.list_item_text_created_at, R.id.list_item_text_created_at };
	
	private static final int LOADER_ID = 42;
	
	private SimpleCursorAdapter simpleCursorAdapter;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		setEmptyText("Loading data...");
		
		simpleCursorAdapter = new SimpleCursorAdapter(
			getActivity(), R.layout.list_item, null, FROM, TO, 0);
		simpleCursorAdapter.setViewBinder(new TimelineViewBinder());
		
		setListAdapter(simpleCursorAdapter);
		
		getLoaderManager().initLoader(LOADER_ID, null, this);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		if (id != LOADER_ID)
			return null;
		Log.d(TAG, "onCreateLoader");
		
		return new CursorLoader(getActivity(), StatusContract.CONTENT_URI, null, null, null, StatusContract.DEFAULT_SORT);
	}
	

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		DetailsFragment detailsFragment = (DetailsFragment)getFragmentManager().findFragmentById(R.id.fragment_details);
		if (detailsFragment != null && detailsFragment.isVisible()) {
			detailsFragment.updateView(id);
		}
		else {
			startActivity(new Intent(getActivity(), DetailsActivity.class).putExtra(StatusContract.Column.ID, id));
		}
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		Log.d(TAG, "onLoadFinished with cursor: " + cursor.getCount());
		
		simpleCursorAdapter.swapCursor(cursor);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		simpleCursorAdapter.swapCursor(null);
	}
	
	private class TimelineViewBinder implements ViewBinder {

		@Override
		public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
			if (view.getId() != R.id.list_item_text_created_at)
			return false;
			
			long timestamp = cursor.getLong(columnIndex);
			CharSequence relativeTime = DateUtils.getRelativeTimeSpanString(timestamp);
			((TextView)view).setText(relativeTime);
			
			return true;
		}
		
	}

}
