package com.jeremyhaberman.restfulandroid.service;

import java.io.IOException;

import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Verb;

import com.jeremyhaberman.restfulandroid.security.AuthorizationManager;

import android.net.Uri;

class RESTRequest {

	public static final int METHOD_GET_PROFILE = 1;

	private static final String PROFILE_PATH = "https://api.twitter.com/1/account/verify_credentials.json";

	private OAuthRequest mRequest;
	private Uri mUri;

	public RESTRequest(int method) throws InvalidRequestMethodException {
		switch (method) {
		case METHOD_GET_PROFILE:
			createGetProfileRequest();
			break;
		default:
			throw new InvalidRequestMethodException("Unknown method type: "
					+ method);
		}
	}

	private void createGetProfileRequest() {
		mUri = Uri.parse(PROFILE_PATH);
		mRequest = new OAuthRequest(Verb.GET, mUri.toString());
		AuthorizationManager.getInstance().signRequest(mRequest);
	}

	public void execute(ResponseHandler handler) throws IOException {
		Response response = mRequest.send();
		RESTResponse restResponse = new RESTResponse(response);
		handler.handleResponse(restResponse, mUri);
	}

}
