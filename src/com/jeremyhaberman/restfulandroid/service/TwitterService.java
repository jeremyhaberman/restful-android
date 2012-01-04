package com.jeremyhaberman.restfulandroid.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class TwitterService extends Service {

	public static final String OPERATION_EXTRA = null;
	public static final String OPERATION_GET = null;
	public static final String REQUEST_TYPE_EXTRA = null;
	public static final String PROFILE_REQUEST = null;
	public static final String SERVICE_CALLBACK = null;
	public static final String ORIGINAL_INTENT_EXTRA = null;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

}
