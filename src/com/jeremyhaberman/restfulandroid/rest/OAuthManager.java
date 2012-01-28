package com.jeremyhaberman.restfulandroid.rest;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.TwitterApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Token;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;

import com.jeremyhaberman.restfulandroid.RestfulAndroid;

/**
 * OAuthManager handles OAuth authentication with the Twitter API.
 * 
 * @author jeremy
 * 
 */
public class OAuthManager implements RequestSigner {

	// Singleton instance of the OAuthManager
	private static OAuthManager mInstance;

	// RESTful Android Twitter app settings
	private static final String TWITTER_API_KEY = "7Ng6OZUMZASslVjUsngtow";
	private static final String TWITTER_API_SECRET = "sXKW39NK47ijfHCTvzaLdnBeLAVkwPFUxxZ9Ulf8fkY";
	private static final String TWITTER_CALLBACK_URL = "restful-android://callback";

	// Names for preferences values
	private static final String PREF_NAME_TWITTER_REQUEST_TOKEN = "twitterRequestToken";
	private static final String PREF_NAME_TWITTER_REQUEST_TOKEN_SECRET = "twitterRequestTokenSecret";
	private static final String PREF_NAME_TWITTER_ACCESS_TOKEN = "twitterAccessToken";
	private static final String PREF_NAME_TWITTER_ACCESS_TOKEN_SECRET = "twitterAccessTokenSecret";

	private final OAuthService mOAuthService;

	// Preferences in which to store the request and access tokens
	private final SharedPreferences prefs;

	private Token mRequestToken;
	private Token mAccessToken;

	/**
	 * Returns the singleton instance of the OAuthManager
	 * 
	 * @return singleton instance of the OAuthManager
	 */
	public static OAuthManager getInstance() {
		if (mInstance == null) {
			mInstance = new OAuthManager();
		}
		return mInstance;
	}

	/**
	 * Private constructor for the OAuthManager. Initializes the persistent
	 * storate and OAuthService
	 */
	private OAuthManager() {

		prefs = PreferenceManager.getDefaultSharedPreferences(RestfulAndroid
				.getAppContext());

		mOAuthService = new ServiceBuilder().provider(TwitterApi.class)
				.apiKey(TWITTER_API_KEY).apiSecret(TWITTER_API_SECRET)
				.callback(TWITTER_CALLBACK_URL).build();

	}

	/**
	 * Returns the authorization URL for Twitter
	 * 
	 * @return Twitter auth URL
	 */
	public Uri getAuthorizationUrl() {
		mRequestToken = mOAuthService.getRequestToken();
		saveRequestToken(mRequestToken);
		String authUrl = mOAuthService.getAuthorizationUrl(mRequestToken);
		return Uri.parse(authUrl);
	}

	/**
	 * Parses the oauth_verifier from the callback Uri in an Intent after
	 * authorization and saves the access token.
	 * 
	 * @param intent
	 *            and Intent that contains a Uri as its data, with the Uri being
	 *            the callback Uri after Twitter authorization
	 */
	public void setAccessToken(Intent intent) {
		mAccessToken = getAccessToken(intent, mOAuthService, getRequestToken());

		saveAccessToken(mAccessToken);
	}

	/**
	 * Persists a request token. Pass in <code>null</code> to clear the saved
	 * token.
	 * 
	 * @param token
	 *            the request token to persist, or <code>null</code> to clear it
	 * @return <code>true</code> if the save was successful
	 */
	private boolean saveRequestToken(Token token) {

		SharedPreferences.Editor editor = prefs.edit();

		if (token == null) {
			editor.remove(PREF_NAME_TWITTER_REQUEST_TOKEN);
			editor.remove(PREF_NAME_TWITTER_REQUEST_TOKEN_SECRET);
		} else {
			editor.putString(PREF_NAME_TWITTER_REQUEST_TOKEN, token.getToken());
			editor.putString(PREF_NAME_TWITTER_REQUEST_TOKEN_SECRET,
					token.getSecret());
		}

		return editor.commit();
	}

	/**
	 * Persists an access token. Pass in <code>null</code> to clear the saved
	 * token.
	 * 
	 * @param token
	 *            the access token to persist, or <code>null</code> to clear it
	 * @return <code>true</code> if the save was successful
	 */
	private boolean saveAccessToken(Token token) {

		SharedPreferences.Editor editor = prefs.edit();

		if (token == null) {
			editor.remove(PREF_NAME_TWITTER_ACCESS_TOKEN);
			editor.remove(PREF_NAME_TWITTER_ACCESS_TOKEN_SECRET);
		} else {
			editor.putString(PREF_NAME_TWITTER_ACCESS_TOKEN, token.getToken());
			editor.putString(PREF_NAME_TWITTER_ACCESS_TOKEN_SECRET,
					token.getSecret());
		}

		return editor.commit();
	}

	/**
	 * Creates an access token
	 * 
	 * @param intent
	 *            an Intent with a Uri as its data. The Uri must contain a query
	 *            string parameter named 'oauth_verifier'.
	 * @param oAuthService
	 *            OAuthService
	 * @param requestToken
	 *            the request token that was used for authorization
	 * @return access token (or null if it failed)
	 */
	private Token getAccessToken(Intent intent, OAuthService oAuthService,
			Token requestToken) {

		if (oAuthService == null || requestToken == null) {
			return null;
		}

		Uri uri = intent.getData();

		if (uri == null) {
			return null;
		}

		String oAuthVerifierString = uri.getQueryParameter("oauth_verifier");

		if (oAuthVerifierString != null) {
			Verifier oAuthVerifier = new Verifier(oAuthVerifierString);
			return oAuthService.getAccessToken(requestToken, oAuthVerifier);
		} else {
			return null;
		}
	}

	/**
	 * Returns a request token for authorizing the app with Twitter
	 * 
	 * @return Twitter request token
	 */
    Token getRequestToken() {
		if (mRequestToken == null) {
			String requestToken = prefs.getString(
					PREF_NAME_TWITTER_REQUEST_TOKEN, null);
			String requestTokenSecret = prefs.getString(
					PREF_NAME_TWITTER_REQUEST_TOKEN_SECRET, null);
			if (requestToken != null && requestTokenSecret != null) {
				mRequestToken = new Token(requestToken, requestTokenSecret);
			}
		}
		return mRequestToken;
	}

	/**
	 * Returns the saved access token (may be null)
	 * 
	 * @return saved access token (or null if it does not exist)
	 */
    Token getAccessToken() {
		if (mAccessToken == null) {
			String accessToken = prefs.getString(
					PREF_NAME_TWITTER_ACCESS_TOKEN, null);
			String accessTokenSecret = prefs.getString(
					PREF_NAME_TWITTER_ACCESS_TOKEN_SECRET, null);

			if (accessToken != null && accessTokenSecret != null) {
				mAccessToken = new Token(accessToken, accessTokenSecret);
			}
		}
		return mAccessToken;
	}

	/**
	 * Determines whether a user is currently logged in
	 * 
	 * @return <code>true</code> if user is logged in, <code>false</code>
	 *         otherwise
	 */
	public boolean loggedIn() {
		return getAccessToken() != null;
	}

	/**
	 * Signs a request
	 * 
	 * @param request
	 *            the OAuthRequest to sign
	 */
	public void signRequest(OAuthRequest request) {
		mOAuthService.signRequest(getAccessToken(), request);
	}

	/**
	 * Log out of the application
	 */
	public void logout() {
		mRequestToken = null;
		mAccessToken = null;
		saveRequestToken(mRequestToken);
		saveAccessToken(mAccessToken);
	}

}
