package com.jeremyhaberman.restfulandroid.service;

import org.scribe.model.Response;

class RESTResponse {

	private Response mResponse;

	public RESTResponse(Response response) {
		mResponse = response;
	}
	
	public String getBody() {
		return mResponse.getBody();
	}

	public int getResponseCode() {
		return mResponse.getCode();
	}

}
