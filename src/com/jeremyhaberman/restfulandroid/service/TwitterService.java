package com.jeremyhaberman.restfulandroid.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;

public class TwitterService extends IntentService {

	public static final String METHOD_EXTRA = "com.jeremyhaberman.restfulandroid.service.METHOD_EXTRA";

	public static final String METHOD_GET = "GET";

	public static final String RESOURCE_TYPE_EXTRA = "com.jeremyhaberman.restfulandroid.service.RESOURCE_TYPE_EXTRA";

	public static final int RESOURCE_TYPE_PROFILE = 1;

	public static final int RESOURCE_TYPE_TIMELINE = 2;

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
		String method = requestIntent.getStringExtra(TwitterService.METHOD_EXTRA);
		int resourceType = requestIntent.getIntExtra(TwitterService.RESOURCE_TYPE_EXTRA, -1);
		mCallback = requestIntent.getParcelableExtra(TwitterService.SERVICE_CALLBACK);

		switch (resourceType) {
		case RESOURCE_TYPE_PROFILE:

			if (method.equalsIgnoreCase(METHOD_GET)) {
				ProfileProcessor processor = new ProfileProcessor(getApplicationContext());
				processor.getProfile(makeProfileProcessorCallback());
			} else {
				mCallback.send(REQUEST_INVALID, getOriginalIntentBundle());
			}
			break;

		case RESOURCE_TYPE_TIMELINE:

			if (method.equalsIgnoreCase(METHOD_GET)) {
				TimelineProcessor processor = new TimelineProcessor(getApplicationContext());
				processor.getTimeline(makeTimelineProcessorCallback());
			} else {
				mCallback.send(REQUEST_INVALID, getOriginalIntentBundle());
			}
			break;
			
		default:
			mCallback.send(REQUEST_INVALID, getOriginalIntentBundle());
			break;
		}

	}

	private ProfileProcessorCallback makeProfileProcessorCallback() {
		ProfileProcessorCallback callback = new ProfileProcessorCallback() {

			@Override
			public void send(int resultCode) {
				if (mCallback != null) {
					mCallback.send(resultCode, getOriginalIntentBundle());
				}
			}
		};
		return callback;
	}

	private TimelineProcessorCallback makeTimelineProcessorCallback() {
		TimelineProcessorCallback callback = new TimelineProcessorCallback() {

			@Override
			public void send(int resultCode) {
				if (mCallback != null) {
					mCallback.send(resultCode, getOriginalIntentBundle());
				}
			}
		};
		return callback;
	}

	protected Bundle getOriginalIntentBundle() {
		Bundle originalRequest = new Bundle();
		originalRequest.putParcelable(ORIGINAL_INTENT_EXTRA, mOriginalRequestIntent);
		return originalRequest;
	}
}
