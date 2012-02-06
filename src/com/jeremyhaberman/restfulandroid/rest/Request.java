package com.jeremyhaberman.restfulandroid.rest;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jeremyhaberman.restfulandroid.rest.RestMethodFactory.Method;

public class Request {
	
	private URI requestUri;
	private Map<String, List<String>> headers;
	private byte[] body;
	private Method method;
	
	public Request(Method method, URI requestUri, Map<String, List<String>> headers,
			byte[] body) {
		super();
		this.method = method;
		this.requestUri = requestUri;
		this.headers = headers;
		this.body = body;
	}
	
	public Method getMethod() {
		return method;
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

	public void addHeader(String key, List<String> value) {
		
		if (headers == null) {
			headers = new HashMap<String, List<String>>();
		}
		headers.put(key, value);
	}

	
	
	
	
	

}
