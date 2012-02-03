package com.jeremyhaberman.restfulandroid.rest;

import java.util.List;
import java.util.Map;

import android.net.Uri;

public class RestMethodFactory {

	private static RestMethodFactory instance;
	private static Object lock = new Object();

	public static RestMethodFactory getInstance(){
		synchronized (lock) {
			if(instance == null){
				instance = new RestMethodFactory();
			}			
		}

		return instance;
	}
	
	public RestMethod getRestMethod(Uri resourceUri, Method method,Map<String, List<String>> headers, byte[] body){
		return new GetTimelineRestMethod(headers);
	}
	
	public static enum Method {
		GET,
		POST,
		PUT,
		DELETE
	}

}
