package com.goingkilo.kmeans;

import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import processing.core.PApplet;
import processing.core.PImage;

public class Img1 extends PApplet{

	int count = 0;

	boolean flag;
	PImage img;

	String pixelFile = "/home/kraghu/Desktop/pixels.csv";
	//limited_diamonds.jpg"; limited_red.jpg";
	String imgFile = "/home/kraghu/New_California_Girl_by_Sweater.jpg";
	
	Cluster[] clusters ;

	public void limg() {
		//img = loadImage("/home/kraghu/Desktop/k/3984811_071_1.jpg");
		img = loadImage(imgFile);
	}
	
	public void setup() {
		limg();
		size( img.width , img.height);
		background( 100,100,100);
	}

	public void draw() {
		image(img,0,0);
	}

	public void mouseClicked() {
		if( clusters == null ) 
			return;
		
		count ++;
		count = count % clusters.length;
		println( "count " + count);
		limg();
		shade();
	}
	
	public void keyPressed() {
		if( key == 's' ) {
			try {
				println( "saving pixels to file");
				FileOutputStream fos = new FileOutputStream( new File(pixelFile));
				for( int i = 0 ; i < img.width ; i ++ ) {
					for( int j = 0 ; j < img.width ; j ++ ) {
						int x = img.get(i,j);
						Color c = new Color(x);
						int r = c.getRed();
						int g = c.getGreen();
						int b = c.getBlue();

						String s = i + "," +j+ "," + r+ "," + g+ "," + b+ "\n";   
						fos.write( s.getBytes());
					}
				}
				fos.close();
			}
			catch(Exception e) {
				e.printStackTrace();
			}

		}
		if (key == 'c' ) {
			try {
				println("begin clustering ");
				ClothingDetect c = new ClothingDetect();
				c.init(pixelFile);
				c.go( c.samples, c.clusters);
				clusters = c.clusters;
				println("finished clustering");
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	void shade() {
		println( "cluster " + count + " centroid :"+ clusters[count].centroid);
		Cluster c1 = clusters[count];
		List<Sample> l  = c1.samples;
		for( Sample s : l) {
			img.set( s.x, s.y , color( 200,0,0) );
		}
	}

}
