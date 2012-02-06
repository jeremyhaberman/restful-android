package com.jeremyhaberman.restfulandroid.rest;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import com.jeremyhaberman.restfulandroid.rest.RestMethodFactory.Method;

public class RestClient {

	// public Response get(URL uri, Map<String, List<String>> headers) {
	// GET get = new GET(uri, headers);
	// return getOrPost(get);
	// }
	//
	// public Response post(URL uri, Map<String, List<String>> headers, byte[]
	// body) {
	// POST post = new POST(uri, headers, body);
	// return getOrPost(post);
	// }
	//
	// private Response getOrPost(Request request) {
	//
	// HttpURLConnection conn = null;
	// Response response = null;
	// try {
	// conn = (HttpURLConnection) request.uri.openConnection();
	// if (request.headers != null) {
	// for (String header : request.headers.keySet()) {
	// for (String value : request.headers.get(header)) {
	// conn.addRequestProperty(header, value);
	// }
	// }
	// }
	// if (request instanceof POST) {
	// byte[] payload = ((POST) request).body;
	// conn.setDoOutput(true);
	// conn.setFixedLengthStreamingMode(payload.length);
	// conn.getOutputStream().write(payload);
	// int status = conn.getResponseCode();
	// if (status / 100 != 2)
	// response = new Response(status, new Hashtable<String, List<String>>(),
	// conn.getResponseMessage().getBytes());
	// }
	// if (response == null) {
	// BufferedInputStream in = new BufferedInputStream(conn.getInputStream());
	// byte[] body = readStream(in);
	// response = new Response(conn.getResponseCode(), conn.getHeaderFields(),
	// body);
	// }
	//
	// } catch (IOException e) {
	// e.printStackTrace();
	// } finally {
	// if (conn != null)
	// conn.disconnect();
	// }
	// return response;
	// }

	// private class Request {
	// public URL uri;
	// public Map<String, List<String>> headers;
	// public Method method;
	// public Request(URL uri, Map<String, List<String>> headers) {
	// this.uri = uri; this.headers = headers;
	// }
	// }
	// private class POST extends Request {
	// public byte[] body;
	// public POST(URL uri, Map<String, List<String>> headers, byte[] body) {
	// super(uri, headers);
	// this.body = body;
	// this.method = Method.POST;
	// }
	// }
	// private class GET extends Request {
	// public GET(URL uri, Map<String, List<String>> headers) {
	// super(uri, headers);
	// }
	// }

	// utilities
	private static byte[] readStream(InputStream in) throws IOException {
		byte[] buf = new byte[1024];
		int count = 0;
		ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
		while ((count = in.read(buf)) != -1)
			out.write(buf, 0, count);
		return out.toByteArray();
	}

	public Response execute(Request request) {
		HttpURLConnection conn = null;
		Response response = null;
		try {

			URL url = request.getRequestUri().toURL();

			conn = (HttpURLConnection) url.openConnection();
			if (request.getHeaders() != null) {
				for (String header : request.getHeaders().keySet()) {
					for (String value : request.getHeaders().get(header)) {
						conn.addRequestProperty(header, value);
					}
				}
			}
			if (request.getMethod() == Method.GET) {
				conn.setDoOutput(false);
				int status = conn.getResponseCode();
				if (status / 100 != 2)
					response = new Response(status, new Hashtable<String, List<String>>(), conn
							.getResponseMessage().getBytes());
			} else if (request.getMethod() == Method.POST) {
				byte[] payload = request.getBody();
				conn.setDoOutput(true);
				conn.setFixedLengthStreamingMode(payload.length);
				conn.getOutputStream().write(payload);
				int status = conn.getResponseCode();
				if (status / 100 != 2)
					response = new Response(status, new Hashtable<String, List<String>>(), conn
							.getResponseMessage().getBytes());
			}
			if (response == null) {
				BufferedInputStream in = new BufferedInputStream(conn.getInputStream());
				byte[] body = readStream(in);
				response = new Response(conn.getResponseCode(), conn.getHeaderFields(), body);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (conn != null)
				conn.disconnect();
		}
		return response;
	}
}
