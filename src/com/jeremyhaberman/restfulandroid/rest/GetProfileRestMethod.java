package com.jeremyhaberman.restfulandroid.rest;

import java.net.URI;

import org.json.JSONObject;

import com.jeremyhaberman.restfulandroid.rest.RestMethodFactory.Method;
import com.jeremyhaberman.restfulandroid.rest.resource.Profile;

public class GetProfileRestMethod extends AbstractRestMethod<Profile>{
	
	private static final URI PROFILE_URI = URI.create("https://api.twitter.com/1/account/verify_credentials.json");

	@Override
	protected Request buildRequest() {
		
		return new Request(Method.GET, PROFILE_URI, null, null);
	}

	@Override
	protected Profile parseResponseBody(String responseBody) throws Exception {
		
		JSONObject json = new JSONObject(responseBody);
		return new Profile(json);
		
	}

	@Override
	protected boolean requiresAuthorization() {
		return true;
	}

}
