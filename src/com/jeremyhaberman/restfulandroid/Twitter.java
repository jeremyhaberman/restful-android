package com.jeremyhaberman.restfulandroid;

import org.json.JSONException;
import org.json.JSONObject;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Verb;

import com.jeremyhaberman.restfulandroid.auth.RequestSigner;

/**
 * Twitter API
 * 
 * @author jeremyhaberman
 * 
 */
public class Twitter {

	private final RequestSigner mRequestSigner;

	/**
	 * Construct the Twitter API.
	 * 
	 * @param signer
	 *            the {@link RequestSigner} to use to sign requests that require
	 *            authentication
	 */
	public Twitter(RequestSigner signer) {
		mRequestSigner = signer;
	}

	/**
	 * Verifies that a user is authenticated to Twitter.
	 * 
	 * @return the name of the currently-authenticated Twitter user (or
	 *         <code>null</code> if not authenticated)
	 */
	public String verifyCredentials() {
		OAuthRequest request = new OAuthRequest(Verb.GET,
				"http://api.twitter.com/1/account/verify_credentials.json");

		mRequestSigner.signRequest(request);
		Response response = request.send();

		String name = null;
		try {
			JSONObject json = new JSONObject(response.getBody());
			return json.getString("name");
		} catch (JSONException ignored) {
		}

		return name;
	}

}
