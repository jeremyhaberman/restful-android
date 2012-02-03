package com.jeremyhaberman.restfulandroid.rest.resource;

import org.json.JSONObject;

import com.jeremyhaberman.restfulandroid.rest.Resource;

public class Tweet implements Resource {

	private JSONObject tweet;

	public Tweet(JSONObject tweetData) {
		this.tweet = tweetData;
	}
}
