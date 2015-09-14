package com.goingkilo.fashiona;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.os.Build;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.TextView;

public class FashionaView extends TextView {

	Paint paint1 ;
	Context ctx;
	Rect top, bottom;

	public FashionaView(Context context) {
		super(context);
		ctx = context;
		paint1 = new Paint( Paint.ANTI_ALIAS_FLAG);
	}

	@Override
	public void onDraw(Canvas canvas){
		Resources res = getResources();
		Bitmap bmp = getMaskedBitmap( res, R.drawable.sweater, R.drawable.stencil);
		Log.v( "goingkilo", "drawing da troll");
	
		Rect src = new Rect( 0,0, bmp.getWidth(), bmp.getHeight() );

		Point size = getSSize();
		int width 	= size.x;
		int height 	= size.y;
		
		int side = (width > height/2) ? height/2 : width;
		
		Rect lTop 	= new Rect( 0, 0, width , height );
		top = lTop;
		canvas.drawBitmap( bmp, src, lTop, paint1);

		Log.v( "goingkilo", "done drawin da troll");
	}
	
	public Point getSSize(){
		WindowManager wm = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();

		Point size 	= new Point();
		display.getSize(size);
		return size;
//		Display display = getWindowManager().getDefaultDisplay(); 
//		int width = display.getWidth();  // deprecated
//		int height = display.getHeight();  // deprecated
	}

	// mask is always a resource (for now :)
	// if Fileman has nothing, then we use passed in source 
	public static Bitmap getMaskedBitmap(Resources res, int sourceResId, int maskResId) {

		BitmapFactory.Options options = new BitmapFactory.Options();
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			options.inMutable = true;
		}
		options.inPreferredConfig = Bitmap.Config.ARGB_8888;

		Log.d( "goingkilo", "FashionaView:getLatestBitmapPath returned :" + FileMan.getBitmapPath());
		Bitmap source1;
		String latest = FileMan.getBitmapPath();
		if ( latest == null ) {
			source1 = BitmapFactory.decodeResource(res, sourceResId, options);
		}
		else {
			source1 = BitmapFactory.decodeFile( latest , options);
		}
		Log.e("goingkilo","source1 is " + source1);
		Bitmap mask = BitmapFactory.decodeResource(res, maskResId);
		Bitmap source = Bitmap.createScaledBitmap( source1, mask.getWidth(), mask.getHeight(), false);
		
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
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP  ));
		canvas.drawBitmap(mask, 0, 0, paint);
		mask.recycle();
		return bitmap;
	}
	
}