package com.jeremyhaberman.restfulandroid.activity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.jeremyhaberman.restfulandroid.R;

/**
 * Dialog for displaying the open-source attributions and licenses as defined in
 * LICENSE.txt.
 * 
 * @author jeremy
 * 
 */
public class About extends Activity {

	private static final String TAG = About.class.getSimpleName();

	/**
	 * The file containing the text to display in the dialog
	 */
	private static final String LICENSE_FILENAME = "LICENSE.txt";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.about);

		TextView textView = (TextView) findViewById(R.id.license_content);
		String data = readTextFile(this, LICENSE_FILENAME);
		textView.setText(data);
	}

	/**
	 * This method reads simple text file
	 * 
	 * @param context an Android Context
     * @param filename the filename to read
	 * @return data from file
	 */
	private static String readTextFile(Context context, String filename) {

		InputStream inputStream = getInputStream(context, filename);

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		byte buf[] = new byte[1024];
		int len;
		try {
			while ((len = inputStream.read(buf)) != -1) {
				outputStream.write(buf, 0, len);
			}
			outputStream.close();
			inputStream.close();
		} catch (IOException ignored) {
		}
		return outputStream.toString();
	}

	private static InputStream getInputStream(Context context, String filename) {
		AssetManager assetManager = context.getAssets();
		InputStream inputStream = null;
		try {
			inputStream = assetManager.open(filename);
		} catch (IOException e) {
			Log.e(TAG, e.getLocalizedMessage(), e);
		}
		return inputStream;
	}

}