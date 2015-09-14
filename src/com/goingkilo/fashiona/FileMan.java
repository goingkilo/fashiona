package com.goingkilo.fashiona;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

public class FileMan {

	private static final String APP_NAME = "fashunk";
	public static boolean initDone = false;
	public static boolean api8Plus = false;
	private static Context c;

	private static int cur_index;
	private static List<String> bitmaps;

	// create app folder
	public static void init(Context c) {
		FileMan.c = c;
		getVersion();
		checkStorage();

		File appDir = getAppDir();

		if (appDir.exists() && appDir.isDirectory()) {
			Log.v("goingkilo", " already exists:" + appDir.getAbsolutePath());
		}
		boolean b = appDir.mkdirs();
		Log.v("goingkilo", "Created app folder :" + appDir.getAbsolutePath() + " > " + b);

		initFileList(appDir);
		
		initDone = true;
	}

	private static void initFileList(File appDir) {
		bitmaps = new ArrayList<String>();
		File[] files = appDir.listFiles();
		for(File f: files){
			bitmaps.add( f.getAbsolutePath());
		}
	}

	static void getVersion() {
		int api = android.os.Build.VERSION.SDK_INT;
		if (api >= android.os.Build.VERSION_CODES.FROYO) {
			api8Plus = true;
			Log.d("goingkilo", "version level greater than Froyo");
		}
	}

	static void checkStorage() {
		String state = Environment.getExternalStorageState();

		if (Environment.MEDIA_MOUNTED.equals(state)) {
			Log.d(" goingkilo", " We can read and write the media");
		} else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
			Log.d(" goingkilo", "  We can only read the media");
		} else {
			Log.d(" goingkilo", " Something else is wrong we can neither read nor write");
		}
	}

	private static File getAppDir() {
		File rootDir = c.getExternalFilesDir(null);
		File target = new File(rootDir, APP_NAME);
		return target;
	}

	public static File newFile(String prefix) {
		try {
			File newFile = File.createTempFile(prefix, ".jpg", getAppDir());
			return newFile;
		} catch (IOException e) {
			return null;
		}
	}

	public static String getLatestBitmapPath() {

		File appPath = getAppDir();

		File[] files = appPath.listFiles();

		if (files.length == 0)
			return null;

		Arrays.sort(files, new Comparator<File>() {

			@Override
			public int compare(File lhs, File rhs) {
				return (int) (rhs.lastModified() - lhs.lastModified());
			}
		});

		File latest = files[0];
		Log.d("goingkilo", "returning " + latest.lastModified());

		return latest.getAbsolutePath();
	}

	public static void addPhoto(String path){
		bitmaps.add(path);
		cur_index = bitmaps.size() -1;
	}
	
	public static String getBitmapPath() {
		return (bitmaps.size() == 0 ? null : bitmaps.get(cur_index));
	}

	public static void left() {
		cur_index = cur_index -1;
		if( cur_index < 0 ){
			cur_index = cur_index + bitmaps.size(); 
		}
	}

	public static void right() {
		cur_index = cur_index +1;
		if( cur_index >= bitmaps.size() ){
			cur_index = 0; 
		}
	}
}