package com.goingkilo.fashiona;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

public class FashionaView extends View {

	Paint paint1;
	Context ctx;
	Rect top;

	void init(Context context) {
		ctx = context;
		paint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
		
		// setting the top rectangle dimensions and creating it
		Point size = getSSize();
		int width = size.x;
		int height = size.y;

		int side = (width > height / 2) ? height / 2 : width;

		Rect lTop = new Rect(0, 0, width, height);
		top = lTop;
	}

	public FashionaView(Context context) {
		super(context);
		init(context);
	}

	public FashionaView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public FashionaView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	@Override
	public void onDraw(Canvas canvas) {
		Resources res = getResources();
		Bitmap compositeBitmap = PhotoMan.getMaskedBitmap(res, R.drawable.sweater, R.drawable.stencil);
		Log.v("goingkilo", "drawing da troll");

		// this is not needed. only the target matters.
		//Rect src = new Rect(0, 0, compositeBitmap.getWidth(), compositeBitmap.getHeight());
		//canvas.drawBitmap(compositeBitmap, src, top, paint1);

		canvas.drawBitmap(compositeBitmap, null, top, paint1);

		Log.v("goingkilo", "done drawin da troll");
	}

	public Point getSSize() {
		WindowManager wm = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();

		Point size = new Point();
		display.getSize(size);
		return size;
		// Display display = getWindowManager().getDefaultDisplay();
		// int width = display.getWidth(); // deprecated
		// int height = display.getHeight(); // deprecated
	}

}