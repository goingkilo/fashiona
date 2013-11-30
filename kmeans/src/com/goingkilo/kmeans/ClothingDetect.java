package com.goingkilo.kmeans;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ClothingDetect {

	Cluster[] clusters ;
	Sample[] samples;
	String pixelFile;

	public ClothingDetect(){
		clusters = new Cluster[5];
	}

	//load samples from file
	void init(String pixelFile) throws IOException {
		FileInputStream fis = new FileInputStream( new File( pixelFile));
		byte[] b = new byte[fis.available()];
		fis.read(b);
		fis.close();

		String s = new String(b);
		String[] lines = s.split("\n");
		System.out.println( "lines " + lines.length);
		int  i = 0;

		List<int[]> rawSamples = new ArrayList<int[]>();
		for(String s0 : lines) {

			if( s0.trim().equals("")) {
				System.out.println(" bloop");
				continue;
			}

			String[] s1 = s0.split(",");
			int x = Integer.parseInt( s1[0]);
			int y = Integer.parseInt( s1[1]);
			int r = Integer.parseInt( s1[2]);
			int g = Integer.parseInt( s1[3]);
			int b0 = Integer.parseInt( s1[4]);

			int[] t  = new int[]{x,y,r,g,b0};
			rawSamples.add(t);
			i++;

		}
		Sample[] samples = new Sample[ rawSamples.size() ];
		i = 0;
		System.out.println("C"+ rawSamples.size());
		while( i < rawSamples.size() ) {
			samples[i] = new Sample( 
					rawSamples.get(i)[2], 
					rawSamples.get(i)[3], 
					rawSamples.get(i)[4]);
			samples[i].setXY( rawSamples.get(i)[0],rawSamples.get(i)[1]);
			i++;
		}
		this.samples = samples;

		//assign random centroids
		Random r = new Random( System.currentTimeMillis());
		for( int x = 0 ; x < clusters.length ; x++ ) {
			clusters[x] = new Cluster();
			clusters[x].centroid = samples[ r.nextInt( samples.length -1)];
		}
	}

	void go( Sample[] samples, Cluster[] clusters) throws IOException {
		do {
//			System.out.println(" ** start ** ");
			//resetClusters
			for( int i = 0 ; i < clusters.length ; i++ ) {
				clusters[i].reset();
			}
//			System.out.println(" reset clusters");

			//assignCentroids
			for( int j = 0 ; j < samples.length ; j ++ ) {
				int closestIndex = closestIndex( samples[j] );
				clusters[closestIndex].addSample( samples[j]);
			}
//			System.out.println(" assigned centroids");

			//recalculatecentroids
			for( int k = 0 ; k < clusters.length ; k++ ) {
				clusters[k].recalculateCentroid();
			}
//			System.out.println("  recalculated ");
			//			for( Cluster c : clusters ) {
			//				System.out.println( "new centroid is at " + c.centroid);
			//			}
//			System.out.println("  -- end -- ");

		}
		while( !zeroDelta(clusters) );
		for( Cluster c : clusters) {
			System.out.println( "[]" + c.centroid + " (" +c.samples.size()+")" );
		}
	}

	private int closestIndex(Sample sample) {

		int minIndex = 0;
		int minDistance = Integer.MAX_VALUE;

		for ( int i  = 0 ; i < clusters.length; i ++ ) {
			int distance =  sample.distanceToCentroid( clusters[i].centroid);
			if( distance < minDistance ) {
				minDistance = distance;
				minIndex = i;
			}
		}
		return minIndex;
	}

	private boolean zeroDelta(Cluster[] clusters) {
		boolean ret = true;

		for( int i = 0 ;  i < clusters.length ; i++ ) {
			if( clusters[i].valid ) {
				if( !clusters[i].zeroDelta()) {
					ret = false;
				}
			}
		}
		return ret;
	}

	public static void main(String[] args) throws IOException {
		ClothingDetect c = new ClothingDetect();
		c.init("/home/kraghu/Desktop/pixels.csv");
		c.go( c.samples, c.clusters);
	}

}
