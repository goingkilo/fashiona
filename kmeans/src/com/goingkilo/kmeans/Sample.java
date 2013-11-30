package com.goingkilo.kmeans;

public class Sample {

	int x,y,r,g,b;

	public Sample(int i, int j, int k) {
		this.r = i;
		this.g = j;
		this.b = k;
	}
	
	public void setXY(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int distanceToCentroid(Sample s) {
		return (int)
				Math.sqrt(
						(r - s.r)*(r -s.r)
						+
						(g - s.g)*(g -s.g) 
						+
						(b - s.b)*(b -s.b) 
						)
						;
	}

	public Sample accumulate( Sample s) {
		return new Sample( r + s.r , g + s.g, b + s.b );
	}

	public Sample divide( int k) {
		return new Sample( r/k , g/k, b/k );
	}

	public int delta(Sample previousCentroid) {
		return (r - previousCentroid.r ) 
				+
				(g - previousCentroid.g ) 
				+
				(b - previousCentroid.b ) 
				;
	}
	
	public String toString(){
		return "("+r+","+g+","+b+")";
	}
	
	public String toString1(){
		return x+","+y+","+","+r+","+g+","+b;
	}

}
