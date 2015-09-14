package com.goingkilo.fashiona;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

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
	int w, h;

	@Override
	public void onCreate(Bundle b) {
		super.onCreate(b);
		Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		mPhoto = FileMan.newFile("fash");
		Log.v(" goingkilo", " new file for photo " + mPhoto.getAbsolutePath());
		i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mPhoto));
		startActivityForResult(i, 1);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Log.e("xy)", "[" + resultCode + "]" + "start at " + new Date(System.currentTimeMillis()));
		if( resultCode == RESULT_CANCELED){
			//else thsi is a zero byte file sitting there messing up the (latestBitmap)
			mPhoto.delete();
		}
		else if (resultCode == RESULT_OK){
			FileMan.addPhoto( mPhoto.getAbsolutePath());
		}
		Intent i = new Intent(this, FashionaActivity.class);
		startActivity(i);
	}

	Point getSSize() {
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		return size;
	}

}