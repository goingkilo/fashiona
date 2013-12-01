package com.goingkilo.deardiary;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Typeface;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

public class TView extends TextView {

	float x = 20, y = 20;
	int color;
	Paint brush1, brush2;
	Typeface tf;

	String textToDisplay = "Stone Soup. How does the newspaper font look like ?";

	public TView(Context context) {
		super(context);

		brush1 = new Paint( Paint.ANTI_ALIAS_FLAG);
		brush1.setColor( Color.BLUE);
		brush1.setStyle( Style.STROKE);

		tf = Typeface.createFromAsset( context.getAssets() , "OldNewspaperTypes.ttf");
		brush1.setTypeface( tf);

		brush2 = new Paint( Paint.ANTI_ALIAS_FLAG);
		brush2.setColor( Color.GRAY);
		brush2.setStyle( Style.FILL);
	}

	@Override
	public boolean onTouchEvent(MotionEvent e) {

		Log.d( "goingkilo", "("+x + "," + y +")" );

		x = e.getX();
		y = e.getY();

		switch (e.getAction()) {
		case MotionEvent.
		ACTION_MOVE:
			brush1.setColor( Color.BLUE);
			if( within( x,y ) ){
				brush2.setColor( Color.BLACK);
			}
			break;
		case MotionEvent.ACTION_DOWN:
			brush1.setColor( Color.RED);
			if( within( x,y ) ){
				brush2.setColor( Color.BLACK);
			}
			break;
		case MotionEvent.ACTION_UP:
			brush1.setColor( Color.GRAY);
			if( within( x,y ) ){
				brush2.setColor( Color.GRAY);
				StoneSoup.sdp.play( StoneSoup.soundId, 1, 1, 0, 0, 1);
			}
			break;
		}
		invalidate();
		return true;
	}

	@Override
	public void onDraw(Canvas canvas){

		canvas.drawColor(Color.WHITE);
		canvas.drawText( textToDisplay, x, y, brush1);
		float bwidth = brush1.measureText( textToDisplay );
		float bheight = getFontHeight();

		canvas.drawRect( 
				x -2,
				getHeight() - y + bheight,		//- paint.getFontSpacing(),
				x + bwidth + 2, 
				getHeight() - y - bheight, 
				brush1);

		canvas.drawRect( 30, 30, 80, 60, brush2);

		Log.v( "goingkilo", "play da sound");

	}

	public int getFontHeight(){
		int a = Math.abs(brush1.getFontMetricsInt().ascent);
		int b = brush1.getFontMetricsInt().descent;
		return  a + b ;
	}

	public boolean within( float a, float b ) {
		return (x >= 30 && x <= 80 && y >= 30 && y <= 60 ); 
	}
	

}
