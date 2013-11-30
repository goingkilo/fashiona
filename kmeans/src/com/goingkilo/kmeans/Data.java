package com.goingkilo.kmeans;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Data {

	static Sample[] loadFromFile(String fileName){
		return null;
	}
	
	static void saveToFile( String filename, Sample[] samples) throws IOException{
		FileOutputStream fos = new FileOutputStream( new File("/home/kraghu/Desktop/limited1.csv"));
		for( Sample s : samples ) {
			fos.write( (s.toString1() +"\n").getBytes() );
		}
		fos.close();
	}
	
	static void saveToFile( String filename, Sample[] s, Cluster[] c, int cluster){
		//save to file
		// change flagged cluster to white pixels
	}
	
	public static void main(String[] args) {

	}

}
