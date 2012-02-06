package com.jeremyhaberman.restfulandroid.rest.resource;

import org.json.JSONException;
import org.json.JSONObject;


public class Profile implements Resource {

	private String name;

	public Profile(JSONObject profileJson) throws JSONException {		
		this.name = profileJson.getString("name");
	}

	public String getName() {
		return name;
	}
}
