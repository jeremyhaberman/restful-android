package com.jeremyhaberman.restfulandroid.service;

class ProfileProcessor {

	void getProfile(ProfileProcessorCallback callback) {
		
		// (4) Insert-Update the ContentProvider with a status column and results column
		//     Look at ContentProvider example, and build a content provider
		// 	   that tracks the necessary data.
		
		// (5) Call the REST method
		//	   Create a RESTMethod class that knows how to assemble the URL,
		//	   and performs the HTTP operation.
		// (7) REST method complete callback
		// 	   At this stage of development, the Processor doesn't retry, just records outcome.

		// (8) Insert-Update the ContentProvider status, and insert the result on success
		//	   Parsing the JSON response (on success) and inserting into the content provider
		
		// (9) Operation complete callback to Service
		
		int responseCode = 200;
		
		callback.send(responseCode);
	}
	
}
