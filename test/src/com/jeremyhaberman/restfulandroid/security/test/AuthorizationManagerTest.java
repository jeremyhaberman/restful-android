package com.jeremyhaberman.restfulandroid.security.test;

import java.net.URI;

import android.test.AndroidTestCase;

import com.jeremyhaberman.restfulandroid.rest.Request;
import com.jeremyhaberman.restfulandroid.rest.Response;
import com.jeremyhaberman.restfulandroid.rest.RestClient;
import com.jeremyhaberman.restfulandroid.rest.RestMethodFactory.Method;
import com.jeremyhaberman.restfulandroid.security.AuthorizationManager;
import com.jeremyhaberman.restfulandroid.security.RequestSigner;

public class AuthorizationManagerTest extends AndroidTestCase {

	/**
	 * Verifies proper authorization of Requests for Twitter OAuth. This test
	 * makes a request to the Twitter API to ensure the Authorization header has
	 * the correct value.
	 * <p>
	 * <strong>Requires that you have already installed RESTful Android on the
	 * test device and have logged in.</strong>
	 * </p>
	 */
	public void testAuthorize() {

		URI uri = URI.create("https://api.twitter.com/1/account/verify_credentials.json");
		Request request = new Request(Method.GET, uri, null, null);
		
		RequestSigner signer = AuthorizationManager.getInstance(getContext());
		signer.authorize(request);

		RestClient client = new RestClient();
		Response response = client.execute(request);
		assertTrue(response.status == 200);

	}

}
