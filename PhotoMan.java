package com.goingkilo.fashiona;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

public class PhotoMan extends Activity {

    File mPhoto;
    File Thumbnail;
    boolean flag = false;
    

    @Override
    public void onCreate(Bundle b){
        super.onCreate(b);
        String a = getIntent().getExtras().getString("photo");
        if( a.contains("top.png")) {
            flag = true;
        }
        else {
            flag = false;
        }
        Log.v(" XXXXXXXXXXXXXXXXXXXXXXX", " x"+a);
        Intent i  = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //mPhoto = FileMan.placeholderFile( MainActivity.APPNAME, "Tom", ".png");
        mPhoto = new File(a);
        i.putExtra( MediaStore.EXTRA_OUTPUT, Uri.fromFile( mPhoto ));
        startActivityForResult(i, 1);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {

            String apath = mPhoto.getAbsolutePath();

            Bitmap bmp0 = BitmapFactory.decodeFile(mPhoto.getAbsolutePath());
            int w1  = FileMan.bitmapW ;
            int h1  = FileMan.bitmapH;

            Log.v( "Touchsnap", "Bitmap is >>>>> "  + bmp0);
            Bitmap bmp  = Bitmap.createScaledBitmap(bmp0, w1, h1, true);
            
            String root = Environment.getExternalStoragePublicDirectory( Environment.DIRECTORY_DOWNLOADS).toString();
            File myDir = null
                    ;
            if( flag) {
                myDir = new File(root + "/saved_images/top.png");
            }
            else {
                myDir = new File(root + "/saved_images/bottom.png");
            }
            
//          String path = FileMan.placeholderFile( MainActivity.APPNAME, "Tom_Thumb",".jpg").getAbsolutePath();
            String path = myDir.getAbsolutePath();

            try {
                FileOutputStream fos = new FileOutputStream(new File(path));
                bmp.compress(Bitmap.CompressFormat.PNG, 90, fos);
                fos.close();
            }
            catch(Exception ioe){
                ioe.printStackTrace();
            }

            bmp.recycle();
            String thumbnail_path = path;
            //what to do with thumbnail ?

            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    private void SaveIamge(Bitmap finalBitmap) {

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/saved_images");    
        myDir.mkdirs();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "Image-"+ n +".jpg";
        File file = new File (myDir, fname);
        if (file.exists ()) file.delete (); 
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
