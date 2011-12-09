package com.jeremyhaberman.restfulandroid;

import android.os.AsyncTask;
import com.jeremyhaberman.restfulandroid.auth.RequestSigner;
import org.json.JSONException;
import org.json.JSONObject;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Verb;

/**
 * Twitter API
 *
 * @author jeremyhaberman
 */
public class TwitterServiceHelper {

    private final RequestSigner mRequestSigner;

    /**
     * Construct the Twitter API.
     *
     * @param signer the {@link RequestSigner} to use to sign requests that require
     *               authentication
     */
    public TwitterServiceHelper(RequestSigner signer) {
        mRequestSigner = signer;
    }

    /**
     * Verifies that a user is authenticated to Twitter.
     *
     * @param callback callback to use when long-running request is complete
     */
    public void getProfile(final OnGetProfileListener callback) {

        OAuthRequest request = new OAuthRequest(Verb.GET,
                "http://api.twitter.com/1/account/verify_credentials.json");

        mRequestSigner.signRequest(request);

        TwitterAsyncTask twitterTask = new TwitterAsyncTask(new OnTwitterTaskCompleteCallback() {

            @Override
            public void onComplete(Response response, Exception e) {

                String name = null;

                if (response != null) {

                    try {
                        JSONObject json = new JSONObject(response.getBody());
                        name = json.getString("name");
                        callback.onSuccess(name);
                    } catch (JSONException e1) {
                        callback.onError(e1);
                    }
                }
            }
        });
        twitterTask.execute(request);
    }

    class TwitterAsyncTask extends AsyncTask<OAuthRequest, Void, Response> {

        private OnTwitterTaskCompleteCallback mCallback;
        private Exception mException;

        TwitterAsyncTask(OnTwitterTaskCompleteCallback callback) {
            mCallback = callback;
        }

        @Override
        protected Response doInBackground(OAuthRequest... requests) {
            try {
                return requests[0].send();
            } catch (Exception e) {
                mException = e;
                return null;
            }
        }

        @Override
        protected void onPostExecute(Response response) {
            mCallback.onComplete(response, mException);
        }
    }

    public interface OnTwitterTaskCompleteCallback {

        void onComplete(Response response, Exception e);
    }
}
