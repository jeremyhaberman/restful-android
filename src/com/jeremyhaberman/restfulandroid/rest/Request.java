package com.jeremyhaberman.restfulandroid.rest;

import java.net.URI;
import java.util.List;
import java.util.Map;

public class Request {
	
	private URI requestUri;
	private Map<String, List<String>> headers;
	private byte[] body;
	
	public Request(URI requestUri, Map<String, List<String>> headers,
			byte[] body) {
		super();
		this.requestUri = requestUri;
		this.headers = headers;
		this.body = body;
	}

	public URI getRequestUri() {
		return requestUri;
	}

	public Map<String, List<String>> getHeaders() {
		return headers;
	}

	public byte[] getBody() {
		return body;
	}
	
	
	
	

}
