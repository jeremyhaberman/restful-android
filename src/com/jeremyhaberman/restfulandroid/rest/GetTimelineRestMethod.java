package com.jeremyhaberman.restfulandroid.rest;

import java.net.URI;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;

import android.content.Context;

import com.jeremyhaberman.restfulandroid.rest.RestMethodFactory.Method;
import com.jeremyhaberman.restfulandroid.rest.resource.TwitterTimeline;

public class GetTimelineRestMethod extends AbstractRestMethod<TwitterTimeline>{
	
	private Context mContext;
	
	private static final URI TIMELINE_URI = URI.create("https://api.twitter.com/1/statuses/home_timeline.json");
	
	private Map<String, List<String>> headers;
	
	public GetTimelineRestMethod(Context context, Map<String, List<String>> headers){
		mContext = context.getApplicationContext();
		this.headers = headers;
	}

	@Override
	protected Request buildRequest() {
		
		Request request = new Request(Method.GET, TIMELINE_URI, headers, null);
		return request;
	}

	@Override
	protected TwitterTimeline parseResponseBody(String responseBody) throws Exception {
		
		JSONArray json = new JSONArray(responseBody);
		return new TwitterTimeline(json);
		
	}

	@Override
	protected Context getContext() {
		return mContext;
	}

}
