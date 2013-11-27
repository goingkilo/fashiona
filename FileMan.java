package com.goingkilo.fashiona;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

public class FileMan {

	String mAppName;
	static String appFolder;

	public static int bitmapW ,bitmapH;



	public static void init(String appName) {
	    
	    String root = Environment.getExternalStoragePublicDirectory( Environment.DIRECTORY_DOWNLOADS).toString();
        File myDir = new File(root + "/saved_images");    
        boolean b = myDir.mkdirs();
	    Log.v(MainActivity.APPNAME , "Created app folder :" + myDir.getAbsolutePath() + " > " + b);
	}

	public static File placeholderFile( String appname, String namePrefix, String type) {

		try {
			File rootDir = Environment.getExternalStorageDirectory();
			File target  = new File(rootDir,appname);

			return File.createTempFile( namePrefix, type, target);
		} catch (IOException e) {
			return null;
		}
	}

	public static Bitmap getLatestBitmap(){

		File rootDir = Environment.getExternalStorageDirectory();
		File appPath  = new File( rootDir, MainActivity.APPNAME);
		File[] thumbs =  appPath.listFiles( new FilenameFilter() {

			@Override
			public boolean accept(File dir, String filename) {

				return filename.startsWith("Tom_Thumb");
			}
		});
		if( thumbs != null && thumbs.length > 0 ) {
			long lastModified = thumbs[0].lastModified();
			int lastModifiedFile  = 0;

			for( int i = 0 ; i< thumbs.length ; i ++ ) {
				if( thumbs[i].lastModified() > lastModified) {
					lastModifiedFile = i;
				}
			}
			return BitmapFactory.decodeFile(thumbs[lastModifiedFile].getAbsolutePath());
		}
		return BitmapFactory.decodeFile( appPath.getAbsolutePath()+"/default.png" );
	}
}
