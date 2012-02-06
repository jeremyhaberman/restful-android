package com.jeremyhaberman.restfulandroid.rest;

import com.jeremyhaberman.restfulandroid.rest.resource.Resource;

public interface RestMethod<T extends Resource>{

	public RestMethodResult<T> execute();
}
