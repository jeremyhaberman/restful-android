package com.jeremyhaberman.restfulandroid.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.jeremyhaberman.restfulandroid.R;
import com.jeremyhaberman.restfulandroid.activity.base.RESTfulListActivity;
import com.jeremyhaberman.restfulandroid.provider.ProfileConstants;
import com.jeremyhaberman.restfulandroid.security.AuthorizationManager;
import com.jeremyhaberman.restfulandroid.service.TwitterServiceHelper;
import com.jeremyhaberman.restfulandroid.util.Logger;

public class TimelineActivity extends RESTfulListActivity {

	private static final String TAG = TimelineActivity.class.getSimpleName();

	private Long requestId;
	private BroadcastReceiver requestReceiver;

	private TwitterServiceHelper mTwitterServiceHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		setContentResId(R.layout.home);
		setRefreshable(true);

		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onResume() {
		super.onResume();

		String name = getNameFromContentProvider();
		if (name != null) {
			showNameToast(name);
		}

		/*
		 * 1. Register for broadcast from TwitterServiceHelper
		 * 
		 * 2. See if we've already made a request. a. If so, check the status.
		 * b. If not, make the request (already coded below).
		 */

		IntentFilter filter = new IntentFilter(TwitterServiceHelper.ACTION_REQUEST_RESULT);
		requestReceiver = new BroadcastReceiver() {

			@Override
			public void onReceive(Context context, Intent intent) {

				long resultRequestId = intent
						.getLongExtra(TwitterServiceHelper.EXTRA_REQUEST_ID, 0);

				Logger.debug(TAG, "Received intent " + intent.getAction() + ", request ID "
						+ resultRequestId);

				if (resultRequestId == requestId) {

					Logger.debug(TAG, "Result is for our request ID");
					
					setRefreshing(false);

					int resultCode = intent.getIntExtra(TwitterServiceHelper.EXTRA_RESULT_CODE, 0);

					Logger.debug(TAG, "Result code = " + resultCode);

					if (resultCode == 200) {

						Logger.debug(TAG, "Updating UI with new data");

						String name = getNameFromContentProvider();
						showNameToast(name);

					} else {
						showToast(getString(R.string.error_occurred));
					}
				} else {
					Logger.debug(TAG, "Result is NOT for our request ID");
				}

			}
		};

		mTwitterServiceHelper = TwitterServiceHelper.getInstance(this);
		this.registerReceiver(requestReceiver, filter);

		if (requestId == null) {
			setRefreshing(true);
			requestId = mTwitterServiceHelper.getProfile();
		} else if (mTwitterServiceHelper.isRequestPending(requestId)) {
			setRefreshing(true);
		} else {
			setRefreshing(false);
			name = getNameFromContentProvider();
			showNameToast(name);
		}

	}

	private String getNameFromContentProvider() {

		String name = null;

		Cursor cursor = getContentResolver().query(ProfileConstants.CONTENT_URI, null, null, null, null);

		if (cursor.moveToFirst()) {
			int index = cursor.getColumnIndexOrThrow(ProfileConstants.NAME);
			name = cursor.getString(index);
		}

		cursor.close();

		return name;
	}

	@Override
	protected void onPause() {
		super.onPause();

		// Unregister for broadcast
		if (requestReceiver != null) {
			try {
				this.unregisterReceiver(requestReceiver);
			} catch (IllegalArgumentException e) {
				Logger.error(TAG, e.getLocalizedMessage(), e);
			}
		}
	}

	private void showNameToast(String name) {
		showToast("You are logged in as\n" + name);
	}
	
	private void showToast(String message) {
		if (!isFinishing()) {
			Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		switch (item.getItemId()) {
		case R.id.logout:
			AuthorizationManager.getInstance(this).logout();
			Intent login = new Intent(this, LoginActivity.class);
			startActivity(login);
			finish();
			break;
		case R.id.about:
			Intent about = new Intent(this, AboutActivity.class);
			startActivity(about);
			break;
		}
		return false;
	}

	@Override
	protected void refresh() {
		requestId = mTwitterServiceHelper.getProfile();
	}
}
