package com.goingkilo.fashiona;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.net.Uri;
import android.os.Build;
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
		if (resultCode == RESULT_CANCELED) {
			// else thsi is a zero byte file sitting there messing up the
			// (latestBitmap)
			mPhoto.delete();
		} else if (resultCode == RESULT_OK) {
			FileMan.addPhoto(mPhoto.getAbsolutePath());
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

	// mask is always a resource (for now :)
	// if Fileman has nothing, then we use passed in source
	public static Bitmap getMaskedBitmap(Resources res, int sourceResId, int maskResId) {

		BitmapFactory.Options options = new BitmapFactory.Options();
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			options.inMutable = true;
		}
		options.inPreferredConfig = Bitmap.Config.ARGB_8888;

		Log.d("goingkilo", "FashionaView:getLatestBitmapPath returned :" + FileMan.getBitmapPath());
		Bitmap source1;
		String latest = FileMan.getBitmapPath();
		if (latest == null) {
			source1 = BitmapFactory.decodeResource(res, sourceResId, options);
		} else {
			source1 = BitmapFactory.decodeFile(latest, options);
		}
		Log.e("goingkilo", "source1 is " + source1);
		Bitmap mask = BitmapFactory.decodeResource(res, maskResId);
		Bitmap source = Bitmap.createScaledBitmap(source1, mask.getWidth(), mask.getHeight(), false);

		Bitmap bitmap;
		if (source.isMutable()) {
			bitmap = source;
		} else {
			bitmap = source.copy(Bitmap.Config.ARGB_8888, true);
			source.recycle();
		}
		bitmap.setHasAlpha(true);

		Canvas canvas = new Canvas(bitmap);
		Paint paint = new Paint();
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
		canvas.drawBitmap(mask, 0, 0, paint);
		mask.recycle();
		return bitmap;
	}

}