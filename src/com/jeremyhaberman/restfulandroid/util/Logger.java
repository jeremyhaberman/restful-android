package com.jeremyhaberman.restfulandroid.util;

import java.util.Set;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

/**
 * Utility class for logging
 * 
 * @author jeremy
 * 
 */
public class Logger {

	public static final int VERBOSE = Log.VERBOSE;
	public static final int DEBUG = Log.DEBUG;
	public static final int INFO = Log.INFO;
	public static final int WARN = Log.WARN;
	public static final int ERROR = Log.ERROR;
	public static final int ASSERT = Log.ASSERT;

	private static int mCurrentLevel = ASSERT;

	private static String mAppLogTag = "";

	public static void setAppTag(String appTag) {
		mAppLogTag = appTag;
	}

	public static void setLevel(int level) {
		mCurrentLevel = level;
	}

	public static int getLevel() {
		return mCurrentLevel;
	}

	public static boolean isEnabled(int level) {
		return level >= mCurrentLevel;
	}

	public static void debug(String tag, String message) {
		Log.d(mAppLogTag, formatMessage(tag, message));
	}

	public static void debug(String tag, String message, Throwable throwable) {
		Log.d(mAppLogTag, formatMessage(tag, message));
	}

	public static void debug(String tag, Intent intent) {
		debug(tag, "Intent action=" + intent.getAction());

		// log extras

		Bundle extras = intent.getExtras();

		if (extras != null) {
			Set<String> keys = extras.keySet();

			if (keys == null || (keys != null && keys.isEmpty())) {
				debug(tag, "    extras: none");
			} else {

				debug(tag, "    extras:");

				for (String key : keys) {
					debug(tag, "       " + key + "=" + extras.get(key));
				}

			}
		}
	}

	public static void info(String tag, String message) {
		Log.i(mAppLogTag, formatMessage(tag, message));
	}

	public static void error(String tag, String error) {
		Log.e(mAppLogTag, formatMessage(tag, error));
	}

	public static void error(String tag, String error, Throwable throwable) {
		Log.e(mAppLogTag, formatMessage(tag, error), throwable);
	}

	public static void warn(String tag, String message) {
		Log.w(mAppLogTag, formatMessage(tag, message));
	}

	public static void warn(String tag, String message, Throwable throwable) {
		Log.w(mAppLogTag, formatMessage(tag, message), throwable);
	}

	/**
	 * Formats a log message
	 * 
	 * @param tag
	 *            message prefix, typically the requesting class name
	 * @param message
	 *            message to write
	 * @return formatted string of the log message
	 */
	private static String formatMessage(String tag, String message) {
		StringBuilder builder = new StringBuilder();

		if (tag.length() > 20) {
			tag = tag.substring(0, 20);
		}

		tag = "[" + tag + "]";

		String prefix = String.format("%-22s ", tag);

		builder.append(prefix).append(message);
		return builder.toString();
	}

}
