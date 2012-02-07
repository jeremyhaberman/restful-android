package com.jeremyhaberman.restfulandroid.rest.resource;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Tweet implements Resource {

	private static final String MESSAGE_KEY = "text";
	private static final String AUTHOR_NAME_KEY = "screen_name";
	private static final String USER_NAME_KEY = "user";
	
	private JSONObject tweet;

	public Tweet(JSONObject tweetData) {
		this.tweet = tweetData;
		JSONArray names = this.tweet.names();
		for (int i = 0; i < names.length(); i++) {
			try {
				System.out.println(names.get(i));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public String getMessage(){
		try {
			return this.tweet.getString(MESSAGE_KEY);
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getAuthorName() {
		String name = null;
		try {
			JSONObject user = this.tweet.getJSONObject(USER_NAME_KEY);
			if(user != null){
				name = user.getString(AUTHOR_NAME_KEY);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return name;
	}
}
