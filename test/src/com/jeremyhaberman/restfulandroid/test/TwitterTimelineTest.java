package com.jeremyhaberman.restfulandroid.test;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;

import junit.framework.Assert;

import org.json.JSONArray;
import org.json.JSONTokener;


import android.test.InstrumentationTestCase;

import com.jeremyhaberman.restfulandroid.rest.resource.Tweet;
import com.jeremyhaberman.restfulandroid.rest.resource.TwitterTimeline;


public class TwitterTimelineTest extends InstrumentationTestCase{

	JSONArray timelineData;

	@Override
    protected void setUp() throws Exception {

		if(timelineData == null){
			InputStream is = getInstrumentation().getContext().getResources().openRawResource(R.raw.timeline_response);
			Writer writer = new StringWriter();
			char[] buffer = new char[1024];
			try {
				Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
				int n;
				while ((n = reader.read(buffer)) != -1) {
					writer.write(buffer, 0, n);
				}
			} finally {
				is.close();
			}

			String json = writer.toString();
			
			JSONTokener tokener = new JSONTokener(json);
			timelineData = (JSONArray) tokener.nextValue();
		}
	}
	
	public void testCreateNewFromJsonArray(){
		try{
			TwitterTimeline timeline = new TwitterTimeline(timelineData);
			Assert.assertNotNull(timeline);
		} catch (Exception ex){
			Assert.fail("Failed to instantiate TwitterTimeline from json string");
		}
	}
	
	public void testGetTweetsFromTimeline(){
		TwitterTimeline timeline = new TwitterTimeline(timelineData);
		List<Tweet> tweets = timeline.getTweets();
		Assert.assertNotNull(tweets);
		Assert.assertTrue(tweets.size() == 20);
		
	}

}
