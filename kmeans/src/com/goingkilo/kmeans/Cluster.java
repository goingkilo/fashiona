package com.goingkilo.kmeans;

import java.util.ArrayList;
import java.util.List;

public class Cluster {
	
	public Sample centroid, previousCentroid;
	boolean valid = true;
	List<Sample> samples;
	
	//new centroid is average of cluster samples
	public void recalculateCentroid() {
		if( samples.size() == 0) {
			valid = false;
//			System.out.println( " \tinvalid"  +centroid);
			return;
		}
		Sample ave = new Sample(0,0,0);
		ave.setXY( centroid.x, centroid.y);
		
		for( int i = 0 ; i <  samples.size(); i++ ) {
			ave = ave.accumulate( samples.get(i));
		}
		ave = ave.divide(samples.size());
		
		previousCentroid  = centroid;
		centroid = ave;
//		System.out.println( " Delta : " +  centroid + "/" + previousCentroid);
	}
	
	public void addSample( Sample s) {
		samples.add(s);
	}
	
	public void reset() {
		samples = new ArrayList<Sample>();
	}

	public boolean zeroDelta(){
		return   centroid.delta(previousCentroid) == 0;
	}
	
	
	
}
