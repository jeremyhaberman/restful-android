package com.jeremyhaberman.restfulandroid.service;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

import com.jeremyhaberman.restfulandroid.provider.Constants;
import com.jeremyhaberman.restfulandroid.util.Logger;

class ProfileProcessor {

	protected static final String TAG = ProfileProcessor.class.getSimpleName();

	private ProfileProcessorCallback mCallback;

	private Context mContext;

	public ProfileProcessor(Context context) {
		mContext = context;
	}

	void getProfile(ProfileProcessorCallback callback) {

		// (4) Insert-Update the ContentProvider with a status column and
		// results column
		// Look at ContentProvider example, and build a content provider
		// that tracks the necessary data.

		// (5) Call the REST method
		// Create a RESTMethod class that knows how to assemble the URL,
		// and performs the HTTP operation.

		try {
			RESTRequest request = new RESTRequest(RESTRequest.METHOD_GET_PROFILE);
			ResponseHandler handler = new ProfileResponseHandler(callback);
			request.execute(handler);
		} catch (InvalidRequestMethodException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private class ProfileResponseHandler implements ResponseHandler {

		ProfileProcessorCallback mCallback;

		public ProfileResponseHandler(ProfileProcessorCallback callback) {
			mCallback = callback;
		}

		@Override
		public void handleResponse(RESTResponse response, Uri uri)
				throws IOException {

			// (7) REST method complete callback
			// At this stage of development, the Processor doesn't retry, just
			// records outcome.

			// (8) Insert-Update the ContentProvider status, and insert the
			// result
			// on success
			// Parsing the JSON response (on success) and inserting into the
			// content
			// provider

			Logger.debug(TAG, "Received response for Uri " + uri.toString()
					+ ":");
			Logger.debug(TAG, "Response code: " + response.getResponseCode());
			Logger.debug(TAG, "Response body: " + response.getBody());

			String name = getName(response.getBody());

			if (name != null) {

				ContentValues values = new ContentValues();
				values.put(Constants.NAME, name);

				Cursor cursor = mContext.getContentResolver().query(
						Constants.CONTENT_URI, null, null, null, null);
				if (cursor.moveToFirst()) {
					int id = cursor.getInt(cursor
							.getColumnIndexOrThrow(BaseColumns._ID));
					mContext.getContentResolver().update(
							ContentUris.withAppendedId(Constants.CONTENT_URI,
									id), values, null, null);
				} else {
					mContext.getContentResolver().insert(Constants.CONTENT_URI,
							values);
				}
				cursor.close();
			}

			// (9) Operation complete callback to Service

			mCallback.send(response.getResponseCode());
		}

		private String getName(String json) {

			String name = null;

			try {
				JSONObject obj = new JSONObject(json);
				name = obj.getString("name");
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return name;
		}

	};

	protected ProfileProcessorCallback getCallback() {
		return mCallback;
	}

}
