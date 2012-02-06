package com.jeremyhaberman.restfulandroid.rest;

import java.util.List;
import java.util.Map;

import com.jeremyhaberman.restfulandroid.rest.resource.Resource;

public abstract class AbstractRestMethod<T extends Resource> implements RestMethod<T> {

	private static final String DEFAULT_ENCODING = "UTF-8";

	public RestMethodResult<T> execute() {

		Request request = buildRequest();
		Response response = doRequest(request);
		return buildResult(response);
	}

	/**
	 * Subclasses can overwrite for full control, eg. need to do special
	 * inspection of response headers, etc.
	 * 
	 * @param response
	 * @return
	 */
	protected RestMethodResult<T> buildResult(Response response) {

		int status = response.status;
		String statusMsg = "";
		String responseBody = null;
		T resource = null;

		try {
			responseBody = new String(response.body, getCharacterEncoding(response.headers));
			resource = parseResponseBody(responseBody);
		} catch (Exception ex) {
			// TODO Should we set some custom status code?
			status = 506; // spec only defines up to 505
			statusMsg = ex.getMessage();
		}
		return new RestMethodResult<T>(status, statusMsg, resource);
	}

	protected abstract Request buildRequest();

	protected abstract T parseResponseBody(String responseBody) throws Exception;

	private Response doRequest(Request request) {

		RestClient client = new RestClient();
		return client.execute(request);
	}

	private String getCharacterEncoding(Map<String, List<String>> headers) {
		// TODO get value from headers
		return DEFAULT_ENCODING;
	}

}
