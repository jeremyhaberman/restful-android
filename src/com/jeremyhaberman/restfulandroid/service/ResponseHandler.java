package com.jeremyhaberman.restfulandroid.service;

import java.io.IOException;

import android.net.Uri;

/**
 * Enables custom handling of HttpResponse and the entities they contain.
 */
interface ResponseHandler {
    void handleResponse(RESTResponse response, Uri uri)
            throws IOException;
}
