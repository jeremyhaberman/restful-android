package com.jeremyhaberman.restfulandroid.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.ResultReceiver;

public class TwitterService extends IntentService {

	public static final int TYPE_PROFILE = 1;
	
	
	public static final String OPERATION_EXTRA = "com.jeremyhaberman.restfulandroid.service.OPERATION_EXTRA";

	public static final String OPERATION_GET = "GET";

	public static final String REQUEST_TYPE_EXTRA = "com.jeremyhaberman.restfulandroid.service.REQUEST_TYPE_EXTRA";

	public static final String PROFILE_REQUEST = "profile";

	public static final String SERVICE_CALLBACK = "com.jeremyhaberman.restfulandroid.service.SERVICE_CALLBACK";
	
	public static final String ORIGINAL_INTENT_EXTRA = "com.jeremyhaberman.restfulandroid.service.ORIGINAL_INTENT_EXTRA";

	private static final int REQUEST_INVALID = -1;
	

	private ResultReceiver mCallback;

	private Intent mOriginalRequestIntent;

	public TwitterService() {
		super("TwitterService");
	}

	@Override
	protected void onHandleIntent(Intent requestIntent) {
		
		mOriginalRequestIntent = requestIntent;
		
		// Get request data from Intent
		String operation = requestIntent.getStringExtra(TwitterService.OPERATION_EXTRA);
		String type = requestIntent.getStringExtra(TwitterService.REQUEST_TYPE_EXTRA);
		mCallback = requestIntent.getParcelableExtra(TwitterService.SERVICE_CALLBACK);
		
		if (!type.equals(PROFILE_REQUEST)) {
			mCallback.send(REQUEST_INVALID, getOriginalIntentBundle());
		} else {
		
			// Find processor
			ProfileProcessor processor = new ProfileProcessor(getApplicationContext());
			
			ProfileProcessorCallback callback = new ProfileProcessorCallback() {
				
				@Override
				public void send(int resultCode) {
					if (mCallback != null) {
						mCallback.send(resultCode, getOriginalIntentBundle());
					}
				}
			};
			
			if (operation.equals(OPERATION_GET)) {
				processor.getProfile(callback);
			}
			
		}
		
	}

	protected Bundle getOriginalIntentBundle() {
		Bundle originalRequest = new Bundle();
		originalRequest.putParcelable(ORIGINAL_INTENT_EXTRA, mOriginalRequestIntent);
		return originalRequest;
	}
}
