package com.jeremyhaberman.restfulandroid.rest;

public interface RestMethod<T extends Resource>{

	public T execute();
}
