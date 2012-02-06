package com.jeremyhaberman.restfulandroid.rest.resource;

import java.util.Collections;
import java.util.List;

import org.json.JSONObject;

import android.net.Uri;

import com.jeremyhaberman.restfulandroid.provider.ProfileConstants;

/**
 * Facade representing the Twitter Timeline data
 * 
 * @author hashbrown
 *
 */
public class TwitterTimeline implements Resource{
	
	private static final Uri CONTENT_URI = Uri.parse("content://" + ProfileConstants.AUTHORITY + "/" + ProfileConstants.TIMELINE_TABLE_NAME);
	
	private JSONObject timelineData;

	public TwitterTimeline(JSONObject timelineData) {
		this.timelineData = timelineData;
	}
	
	public List<Tweet> getTweets(){
		//TODO
		
		return Collections.emptyList();
	}
	
	

}
