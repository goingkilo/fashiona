package com.goingkilo.deardiary;

import java.io.File;
import java.io.FileOutputStream;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Display;

public class PhotoMan extends Activity {

	File mPhoto;
	boolean flag = false;
	int w,h;

	@Override
	public void onCreate(Bundle b){
		super.onCreate(b);
		Intent i  = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		mPhoto = FileMan.newFile("ff_");
		Log.v(" goingkilo", " new file for photo " + mPhoto.getAbsolutePath());
		i.putExtra( MediaStore.EXTRA_OUTPUT, Uri.fromFile( mPhoto ));
		startActivityForResult(i, 1);
	}


	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		try {

			////
			Bitmap bmp0 = BitmapFactory.decodeFile(mPhoto.getAbsolutePath());
			Point p = getSSize();
			int w1  = p.x;
			int h1  = p.y;

			Log.v( "goingkilo", "Bitmap is >>>>> "  + bmp0);
			Log.v( "goingkilo", "Bitmap scale size is  ----  "  + w1 + "," + h1);
			
			Bitmap bmp  = Bitmap.createScaledBitmap( bmp0, 1041, 1359, true);
			String path = mPhoto.getAbsolutePath().replace("ff", "ft");

			try {
				//save bitmap thumbnail to disk
				FileOutputStream fos = new FileOutputStream(new File(path));
				bmp.compress(Bitmap.CompressFormat.PNG, 90, fos);
				fos.close();
			}
			catch(Exception ioe){
				ioe.printStackTrace();
			}

			bmp.recycle();
			////

			Log.d( "goingkilo", "finished taking photo at " + mPhoto.getAbsolutePath()  + " ,returning");
			Intent i = new Intent(this, Fashiona.class);
			startActivity(i);
		}
		catch(Exception e){
			Log.d( "goingkilo", " error " + e.getMessage() );
		}
	}

	Point getSSize() {
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		return size;
	}

}