package com.goingkilo.fashiona;

import android.content.Context;
import android.content.Intent;
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
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

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
		//R.drawable.golden_gate, R.drawable.troll_face
		Bitmap bmp = getMaskedBitmap( res, R.drawable.sweater, R.drawable.stencil);
		Log.v( "goingkilo", "drawing da troll");
	
		Rect src = new Rect( 0,0, bmp.getWidth(), bmp.getHeight() );

		//Display display = getWindowManager().getDefaultDisplay();
		Point size = getSSize();
		int width 	= size.x;
		int height 	= size.y;
		
		int side = (width > height/2) ? height/2 : width;
		
		Rect lTop 	= new Rect( 0, 0, width , height );
		top = lTop;
		canvas.drawBitmap( bmp, src, lTop, paint1);
		

		//Rect lBot 	= new Rect( width/4, width/2 + 10, width/2 , width/2 + 10 + width/2 );
//		bottom = lBot;
		//canvas.drawBitmap( bmp, src, lBot, paint1);
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

	public static Bitmap getMaskedBitmap(Resources res, int sourceResId, int maskResId) {

		BitmapFactory.Options options = new BitmapFactory.Options();
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			options.inMutable = true;
		}
		options.inPreferredConfig = Bitmap.Config.ARGB_8888;
		//Bitmap source = BitmapFactory.decodeResource(res, sourceResId, options);
		Log.d( "goingkilo", "FashionaView:getLatestBitmapPath returned :" + FileMan.getLatestBitmapPath());
		
		Bitmap source = BitmapFactory.decodeFile( FileMan.getLatestBitmapPath(), options);
		if ( source == null ) {
			source = BitmapFactory.decodeResource(res, sourceResId, options);
		}
		Bitmap bitmap;
		if (source.isMutable()) {
			bitmap = source;
		} else {
			bitmap = source.copy(Bitmap.Config.ARGB_8888, true);
			source.recycle();
		}
		bitmap.setHasAlpha(true);
		Canvas canvas = new Canvas(bitmap);
		Bitmap mask = BitmapFactory.decodeResource(res, maskResId);

		Paint paint = new Paint();
		//
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP  ));
		canvas.drawBitmap(mask, 0, 0, paint);
		mask.recycle();
		return bitmap;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float x = event.getX();
		float y = event.getY();

		switch (event.getAction()) {
			case MotionEvent.ACTION_UP:
				if( top.contains( (int)x, (int)y)) {
					Log.d( "goingkilo", "Touch");	
					Toast.makeText(ctx, "Calling photoman", Toast.LENGTH_SHORT).show();
					Intent i = new Intent( ctx, PhotoMan.class);
					Point p = getSSize();
					i.putExtra( "x", p.x);
					i.putExtra( "y", p.y);
					ctx.startActivity(i);
				}
				break;
			default:
				break;
		}
		return true;
	}

}
