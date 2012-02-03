package com.jeremyhaberman.restfulandroid.rest;

public abstract class AbstractRestMethod<T extends Resource> implements RestMethod<Resource>{
	
	public T execute(){
		
		Request request  = buildRequest();
		Response response = doRequest(request);
		return parseResponse(response);
	}
	
	private Response doRequest(Request request){
		// TODO Jeremy: connect with Http client impl here
		return null;
	}
	
	protected abstract Request buildRequest();
	protected abstract T parseResponse(Response response);

}
