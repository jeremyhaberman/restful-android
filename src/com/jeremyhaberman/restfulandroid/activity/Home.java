package com.jeremyhaberman.restfulandroid.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jeremyhaberman.restfulandroid.OnGetProfileListener;
import com.jeremyhaberman.restfulandroid.R;
import com.jeremyhaberman.restfulandroid.auth.OAuthManager;
import com.jeremyhaberman.restfulandroid.service.TwitterServiceHelper;

public class Home extends Activity {

	private ProgressBar mProgressIndicator;
	private TextView mWelcome;
	private Long requestId;
	private BroadcastReceiver requestReceiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.home);

		mProgressIndicator = (ProgressBar) findViewById(R.id.progress_indicator);
		mProgressIndicator.setVisibility(View.INVISIBLE);
		mWelcome = (TextView) findViewById(R.id.welcome);        
	}

	@Override
	protected void onResume() {
		super.onResume();

		/* 1. Register for broadcast from TwitterServiceHelper
		 *
		 * 2. See if we've already made a request.
		 *   a. If so, check the status.
		 *   b. If not, make the request (already coded below).
		 */

		IntentFilter filter = new IntentFilter(TwitterServiceHelper.ACTION_REQUEST_RESULT);
		requestReceiver = new BroadcastReceiver() {

			@Override
			public void onReceive(Context context, Intent intent) {
				long rcvdId = intent.getLongExtra(TwitterServiceHelper.EXTRA_REQUEST_ID, 0);
				if(rcvdId == requestId){
					int result = intent.getIntExtra(TwitterServiceHelper.EXTRA_RESULT_CODE, 0);
					if(result == 200){
						mProgressIndicator.setVisibility(View.INVISIBLE);
						mWelcome.setVisibility(View.VISIBLE);
						showWelcome("Get Name from DB");
					} else {
						showError();
					}
				}

			}
		};

		TwitterServiceHelper twitter = TwitterServiceHelper.getInstance(this);

		if(requestId == null){
			mProgressIndicator.setVisibility(View.VISIBLE);
			this.registerReceiver(requestReceiver, filter);
			requestId = twitter.getProfile();
		} else if (twitter.isRequestPending(requestId)){
			this.registerReceiver(requestReceiver, filter);			
		} else {
			mWelcome.setVisibility(View.VISIBLE);
			showWelcome("Get Name from DB");
		}

	}


	@Override
	protected void onPause() {
		super.onPause();

		// Unregister for broadcast
		if(requestReceiver != null){
			this.unregisterReceiver(requestReceiver);
		}
	}

	private void showError() {
		mWelcome.setText("An error has occurred.");
	}

	private void showWelcome(String name) {
		mWelcome.setText("You are logged in as\n" + name);
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
			OAuthManager.getInstance().logout();
			Intent login = new Intent(this, Login.class);
			startActivity(login);
			finish();
			break;
		case R.id.about:
			Intent about = new Intent(this, About.class);
			startActivity(about);
			break;
		}
		return false;
	}
}
