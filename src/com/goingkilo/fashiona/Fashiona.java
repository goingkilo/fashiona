package com.goingkilo.fashiona;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

public class Fashiona extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if( ! FileMan.initDone ) {
			FileMan.init();
		}
		FashionaView f = new FashionaView(this);
		
//		f.setOnTouchListener( new OnSwipeTouchListener() {
//			public void onSwipeTop() {
//				Toast.makeText( Fashiona.this, "top", Toast.LENGTH_SHORT).show();
//			}
//			public void onSwipeRight() {
//				Toast.makeText( Fashiona.this, "right", Toast.LENGTH_SHORT).show();
//			}
//			public void onSwipeLeft() {
//				Toast.makeText( Fashiona.this, "left", Toast.LENGTH_SHORT).show();
//			}
//			public void onSwipeBottom() {
//				Toast.makeText(	Fashiona.this, "bottom", Toast.LENGTH_SHORT).show();
//			}
//		});
		
		setContentView( f);
	}


}
