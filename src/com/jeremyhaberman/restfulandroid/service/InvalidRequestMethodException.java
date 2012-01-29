package com.jeremyhaberman.restfulandroid.service;

class InvalidRequestMethodException extends Exception {

	private static final long serialVersionUID = 8348169163014928359L;

	public InvalidRequestMethodException() {
		// TODO Auto-generated constructor stub
	}

	public InvalidRequestMethodException(String detailMessage) {
		super(detailMessage);
		// TODO Auto-generated constructor stub
	}

	public InvalidRequestMethodException(Throwable throwable) {
		super(throwable);
		// TODO Auto-generated constructor stub
	}

	public InvalidRequestMethodException(String detailMessage,
			Throwable throwable) {
		super(detailMessage, throwable);
		// TODO Auto-generated constructor stub
	}

}
