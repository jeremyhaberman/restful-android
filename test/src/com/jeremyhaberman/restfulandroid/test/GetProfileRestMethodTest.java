package com.jeremyhaberman.restfulandroid.test;

import android.test.AndroidTestCase;

import com.jeremyhaberman.restfulandroid.rest.GetProfileRestMethod;
import com.jeremyhaberman.restfulandroid.rest.RestMethodResult;
import com.jeremyhaberman.restfulandroid.rest.resource.Profile;

public class GetProfileRestMethodTest extends AndroidTestCase {

	public void testExecute() {
		
		GetProfileRestMethod method = new GetProfileRestMethod();
		RestMethodResult<Profile> profile = method.execute();
		assertNotNull(profile);
	}

}
