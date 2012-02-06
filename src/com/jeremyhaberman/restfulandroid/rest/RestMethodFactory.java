package com.jeremyhaberman.restfulandroid.rest;

import java.util.List;
import java.util.Map;

import android.content.UriMatcher;
import android.net.Uri;

import com.jeremyhaberman.restfulandroid.provider.ProfileConstants;

public class RestMethodFactory {

	private static RestMethodFactory instance;
	private static Object lock = new Object();
	private UriMatcher uriMatcher;

	private static final int PROFILE = 1;
	private static final int TIMELINE = 2;

	private RestMethodFactory() {

		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(ProfileConstants.AUTHORITY, ProfileConstants.TABLE_NAME, PROFILE);
	}

	public static RestMethodFactory getInstance() {
		synchronized (lock) {
			if (instance == null) {
				instance = new RestMethodFactory();
			}
		}

		return instance;
	}

	public RestMethod getRestMethod(Uri resourceUri, Method method,
			Map<String, List<String>> headers, byte[] body) {

		switch (uriMatcher.match(resourceUri)) {
		case PROFILE:
			if (method == Method.GET) {
				return new GetProfileRestMethod();
			}
			break;
		case TIMELINE:
			if (method == Method.GET) {
				return new GetTimelineRestMethod(headers);
			}
			break;
		}

		return null;
	}

	public static enum Method {
		GET, POST, PUT, DELETE
	}

}
