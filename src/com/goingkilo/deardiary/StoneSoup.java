package com.goingkilo.deardiary;

import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

public class StoneSoup extends Activity implements SensorEventListener {

	TView 				t ;
	static SoundPool 	sdp;
	AssetManager 		asm;
	static int 			soundId ;
	
	SensorManager 		sensorManager;
	private long 		lastUpdate;
	private boolean 	color = false;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		t = new TView(this);
		t.setText( "start me ");
		setContentView(t);

		sdp  =  new SoundPool(20, AudioManager.STREAM_MUSIC, 0);
		asm =  getAssets();
		AssetFileDescriptor assetDescriptor = null;
		try {
			assetDescriptor = asm.openFd("beep1.wav");
			Log.v( "goingkilo", "loaded something" );
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		soundId = sdp.load(assetDescriptor, 0);
		Log.v( "goingkilo", "sound id loaded" );
		sdp.play( soundId, 1, 1, 0, 0, 1);
		Log.v( "goingkilo", "sound played" );

		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		boolean ret = t.onTouchEvent(event);

		return ret;
	}

	public boolean isNetworkAvailable() {
		ConnectivityManager cm = (ConnectivityManager)  getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = cm.getActiveNetworkInfo();

		// if no network is available networkInfo will be null
		// otherwise check if we are connected
		if (networkInfo != null && networkInfo.isConnected()) {
			return true;
		}
		return false;
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
			getAccelerometer(event);
		}
	}

	private void getAccelerometer(SensorEvent event) {
		float[] values = event.values;

		float x = values[0];
		float y = values[1];
		float z = values[2];
		
		Log.d("goingkilo", "("+ x + ","+ y +"," + z +")" );

		float accelationSquareRoot = (x * x + y * y + z * z) 
				/ 
				(SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH);
		
		long actualTime = System.currentTimeMillis();
		if (accelationSquareRoot >= 2)  {
			if (actualTime - lastUpdate < 200) {
				return;
			}
			lastUpdate = actualTime;
			Toast.makeText(this, "Device was shuffled", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {

	}

	@Override
	protected void onResume() {
		super.onResume();
		// register this class as a listener for the orientation and
		// accelerometer sensors
		sensorManager.registerListener(
				this,
				sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_NORMAL);
	}

	@Override
	protected void onPause() {
		// unregister listener
		super.onPause();
		sensorManager.unregisterListener(this);
	}
} 
