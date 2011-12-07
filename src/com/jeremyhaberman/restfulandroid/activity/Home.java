package com.jeremyhaberman.restfulandroid.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.jeremyhaberman.restfulandroid.OnCredentialsVerifiedCallback;
import com.jeremyhaberman.restfulandroid.R;
import com.jeremyhaberman.restfulandroid.Twitter;
import com.jeremyhaberman.restfulandroid.auth.OAuthManager;

public class Home extends Activity {

    private ProgressBar mProgressIndicator;
    private TextView mWelcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.home);


        mProgressIndicator = (ProgressBar) findViewById(R.id.progress_indicator);
        mWelcome = (TextView) findViewById(R.id.welcome);

        Twitter twitter = new Twitter(OAuthManager.getInstance());
        twitter.verifyCredentials(new OnCredentialsVerifiedCallback() {
            @Override
            public void onCredentialsVerified(String name, Exception e) {
                mProgressIndicator.setVisibility(View.INVISIBLE);
                mWelcome.setVisibility(View.VISIBLE);

                if (name != null && e == null) {
                    showWelcome(name);
                } else {
                    showError();
                }
            }
        });


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
