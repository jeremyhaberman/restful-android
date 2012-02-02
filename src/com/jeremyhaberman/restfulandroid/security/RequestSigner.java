package com.jeremyhaberman.restfulandroid.security;

import java.net.HttpURLConnection;

import org.scribe.model.OAuthRequest;

/**
 * Interface for an OAuth request signer
 * 
 * @author jeremy
 * 
 */
interface RequestSigner {

	/**
	 * Signs an OAuth request
	 * 
	 * @param request
	 *            the request to sign
	 */
	public void signRequest(OAuthRequest request);
	
	/**
	 * Adds the required OAuth information to an HttpUrlConnection
	 * @param conn
	 */
	public void signConnection(HttpURLConnection conn);

}
