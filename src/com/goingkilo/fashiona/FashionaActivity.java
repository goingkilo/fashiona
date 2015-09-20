package com.goingkilo.fashiona;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

//TODO:  speed up combined-image creation .Use transparent brush to load image instead of porterduff 
public class FashionaActivity extends Activity {
	FashionaView fashionaView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		if (!FileMan.initDone) {
			FileMan.init(this);
		}
		fashionaView = (FashionaView) findViewById(R.id.fashionaView);
		Button left = (Button) findViewById(R.id.leftButton);
		Button right = (Button) findViewById(R.id.rightButton);

		left.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				FileMan.left();
				fashionaView.playSoundEffect(android.view.SoundEffectConstants.CLICK);
				fashionaView.invalidate();
			}
		});

		right.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				FileMan.right();
				fashionaView.playSoundEffect(android.view.SoundEffectConstants.CLICK);
				fashionaView.invalidate();
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate menu from XML resource
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle all of the possible menu actions.
		switch (item.getItemId()) {

		case R.id.action_compose:
			Toast.makeText(this, "Let's take a picture", Toast.LENGTH_SHORT).show();
			Intent i = new Intent(this, PhotoMan.class);
			startActivity(i);
			break;

		case R.id.action_share:
			Intent share = new Intent(Intent.ACTION_SEND);
			share.setType("image/jpeg");
			share.putExtra(Intent.EXTRA_STREAM, Uri.parse( fashionaView.getCompositeBitmap()));
			if (isPackageInstalled("com.whatsapp", this)) {
				share.setPackage("com.whatsapp");
				startActivity(Intent.createChooser(share, "Share Image"));

			} else {
				Toast.makeText(getApplicationContext(), "Please Install Whatsapp", Toast.LENGTH_LONG).show();
			}
			// share text
			// Intent sendIntent = new Intent();
			// sendIntent.setAction(Intent.ACTION_SEND);
			// sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to
			// send.");
			// sendIntent.setType("text/plain");
			// startActivity(sendIntent);
			break;

		case R.id.action_del:
			Toast.makeText(this, "Delete is not yet implemented", Toast.LENGTH_SHORT).show();
			break;

		}
		// consumed
		return true;
	}

	private boolean isPackageInstalled(String packagename, Context context) {
		PackageManager pm = context.getPackageManager();
		try {
			pm.getPackageInfo(packagename, PackageManager.GET_ACTIVITIES);
			return true;
		} catch (NameNotFoundException e) {
			return false;
		}
	}

	@Override
	public void onBackPressed() {
		  Intent intent = new Intent(Intent.ACTION_MAIN);
		   intent.addCategory(Intent.CATEGORY_HOME);
		   intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		   startActivity(intent);
		   super.onBackPressed();
	}

}
