package com.jeremyhaberman.restfulandroid.test;

import com.jeremyhaberman.restfulandroid.rest.GetTimelineRestMethod;
import com.jeremyhaberman.restfulandroid.rest.RestMethodResult;
import com.jeremyhaberman.restfulandroid.rest.resource.TwitterTimeline;

import android.test.AndroidTestCase;

public class GetTimelineRestMethodTest extends AndroidTestCase {

	public void testExecute() {
		
		GetTimelineRestMethod method = new GetTimelineRestMethod(null);
		RestMethodResult<TwitterTimeline> timeline = method.execute();
		assertNotNull(timeline);
	}

}
