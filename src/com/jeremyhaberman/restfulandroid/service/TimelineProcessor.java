package com.jeremyhaberman.restfulandroid.service;

import android.content.Context;


/**
 * The TimelineProcessor is a POJO for processing timeline requests.
 * For this pattern, there is one Processor for each resource type.
 * 
 * @author Peter Pascale
 */
public class TimelineProcessor {

	protected static final String TAG = ProfileProcessor.class.getSimpleName();

	private TimelineProcessorCallback mCallback;
	private Context mContext;

	
	public TimelineProcessor(Context context) {
		mContext = context;
	}

	
	void getTimeline(TimelineProcessorCallback callback) {

		/*
		Processor is a POJO
			- Processor for each resource type
			- Processor can handle each method on the resource that is supported.
			- Processor needs a callback (which is how the request gets back to the service)
			- Processor uses a RESTMethod - created through a RESTMethodFactory.create(parameterized) or .createGetTimeline()
			
			First iteration had a callback that updated the content provider
			with the resources. But the eventual implementation simply block 
			for the response and do the update.
		 */

	}
}