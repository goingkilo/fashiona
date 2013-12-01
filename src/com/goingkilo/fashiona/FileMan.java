package com.goingkilo.deardiary;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import android.os.Environment;
import android.util.Log;

public class FileMan {

	public static boolean initDone = false;
	public static boolean api8Plus = false;

	// create app folder
	public static void init() {
		getVersion();
		checkStorage();
		
		String root = Environment.getExternalStoragePublicDirectory( Environment.DIRECTORY_DOWNLOADS).toString();
		File myDir = new File(root + "/fashiona");
		if( myDir.exists() && myDir.isDirectory() ){
			Log.v("goingkilo", " already exists:" + myDir.getAbsolutePath()  );	
		}
		boolean b = myDir.mkdirs();
		Log.v("goingkilo", "Created app folder :" + myDir.getAbsolutePath() + " > " + b);
		initDone = true;
	}
	
	static void  getVersion(){
		int api = android.os.Build.VERSION.SDK_INT;
		if( api >= android.os.Build.VERSION_CODES.FROYO ) {
			api8Plus = true;
			Log.d("goingkilo", "version level greater than Froyo");
		}
	}

	static void checkStorage() {
		String state = Environment.getExternalStorageState();

		if (Environment.MEDIA_MOUNTED.equals(state)) {
			Log.d(" goingkilo" ," We can read and write the media");
		} 
		else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
			Log.d(" goingkilo" ,"  We can only read the media");
		} 
		else {
			Log.d(" goingkilo" ," Something else is wrong we can neither read nor write");
		}
	}

	//prefix - ff_ for regular ft_for thumbnail
	public static File newFile(String prefix) {

		try {
			File rootDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
			File target = new File(rootDir, "fashiona");
			File newFile = File.createTempFile( prefix, ".jpg", target);
			return newFile;
		} catch (IOException e) {
			return null;
		}
	}

	public static String getLatestBitmapPath() {

		File rootDir = Environment.getExternalStoragePublicDirectory( Environment.DIRECTORY_DOWNLOADS);
		File appPath = new File(rootDir, "fashiona");

		File[] thumbs = appPath.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String filename) {
				return filename.startsWith("ft_");
			}
		});
		
//		Log.d("goingkilo", " thumbs is null ? " + (thumbs == null) );
		if (thumbs != null && thumbs.length > 0) {
			Log.d("goingkilo", " thumbs.length " + thumbs.length  );
		
			long lastModified = thumbs[0].lastModified();
			int lastModifiedFile = 0;

			for (int i = 0; i < thumbs.length; i++) {
//				Log.d("goingkilo", "checking file " + thumbs[i].getAbsolutePath());
				if (thumbs[i].lastModified() > lastModified) {
					lastModifiedFile = i;
				}
			}
			String ret = thumbs[lastModifiedFile].getAbsolutePath() ;
//			Log.d("goingkilo", "seelcted file " + ret);
			return  ret;

		}
		Log.d("goingkilo", "something happened, returning null");
		return null;
	}
}