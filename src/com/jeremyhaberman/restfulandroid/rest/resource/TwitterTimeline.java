package com.jeremyhaberman.restfulandroid.rest.resource;

import java.util.Collections;
import java.util.List;

import org.json.JSONObject;

import com.jeremyhaberman.restfulandroid.rest.Resource;

/**
 * Facade representing the Twitter Timeline data
 * 
 * @author hashbrown
 *
 */
public class TwitterTimeline implements Resource{
	
	private JSONObject timelineData;

	public TwitterTimeline(JSONObject timelineData) {
		this.timelineData = timelineData;
	}
	
	public List<Tweet> getTweets(){
		//TODO
		
		return Collections.emptyList();
	}
	
	

}
