package com.jeremyhaberman.restfulandroid.rest;

import org.scribe.model.OAuthRequest;

/**
 * Interface for an OAuth request signer
 * 
 * @author jeremy
 * 
 */
public interface RequestSigner {

	/**
	 * Signs an OAuth request
	 * 
	 * @param request
	 *            the request to sign
	 */
	public void signRequest(OAuthRequest request);

}
