package com.jeremyhaberman.restfulandroid.security;

import com.jeremyhaberman.restfulandroid.rest.Request;

/**
 * Interface for an OAuth request signer
 * 
 * @author jeremy
 * 
 */
public interface RequestSigner {
	
	/**
	 * Adds the required OAuth information to a Request
	 * @param conn
	 */
	public void authorize(Request request);

}
