package com.jeremyhaberman.restfulandroid.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.jeremyhaberman.restfulandroid.R;
import com.jeremyhaberman.restfulandroid.auth.OAuthManager;

public class Login extends Activity {

	private OAuthManager mOAuthManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.login);

		mOAuthManager = OAuthManager.getInstance();

		Button buttonLogin = (Button) findViewById(R.id.button_login);
		buttonLogin.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				authorize();
			}
		});

	}

	private void startHomeActivity() {
		Intent startHomeActivity = new Intent(this, Home.class);
		startActivity(startHomeActivity);
		finish();
	}

	/**
	 * Authorizes app for use with Twitter.
	 */
    void authorize() {
		Uri authUrl = mOAuthManager.getAuthorizationUrl();
		Intent openAuthUrl = new Intent(Intent.ACTION_VIEW, authUrl);
		startActivity(openAuthUrl);
	}

	@Override
	protected void onResume() {
		super.onResume();

		// Check to see if we're resuming after having authenticated with
		// Twitter
		if (getIntent().getData() != null) {
			mOAuthManager.setAccessToken(getIntent());

			if (mOAuthManager.loggedIn()) {
				startHomeActivity();
			} else {
				Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show();
			}
		} else {
			// No Intent with a callback Uri was found, so let's just see if
			// we've alredy logged in, and if so start the Home activity
			if (mOAuthManager.loggedIn()) {
				startHomeActivity();
			}
		}
	}
}
