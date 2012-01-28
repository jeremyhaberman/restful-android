package com.jeremyhaberman.restfulandroid.rest;

import java.io.IOException;

import android.net.Uri;

/**
 * Enables custom handling of HttpResponse and the entities they contain.
 */
public interface ResponseHandler {
    void handleResponse(RESTResponse response, Uri uri)
            throws IOException;
}
