import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

public class Shirts {

	public static void main(String[] args) throws IOException {
		new Shirts().clusterIntoGroups();
	}

	public void clusterIntoGroups() throws IOException {
		List<Point> rgb = getRGBValuesFromImage();
		KMeans k = new KMeans(rgb, 100);
		k.cluster();
		;
		Map<Point, Group> groups = k.getGroups();
		
		int i = 0;
		for( Group g : groups.values()) {
			transparentize(g, "goa_files/goa_"+i);
			i++;
		}

	}
	
	public void transparentize(Group g, String fname) throws IOException{
		BufferedImage a = ImageIO.read(new File("/home/karthik/Desktop/goa.jpg"));
		for( Point p : g.getPoints()){
			//Color c = new Color( (int)p.r, (int)p.g, (int)p.b, 0) ;
			Color c = new Color( 100,100,100, 0) ;
			a.setRGB( p.x, p.y,  c.getRGB());
		}
		ImageIO.write(a, "JPG",  new File("/home/karthik/Desktop/"+fname+".jpg"));
		
	}

	public List<Point> getRGBValuesFromImage() throws IOException {

		List<int[]> ret = new ArrayList<int[]>();

		BufferedImage a = ImageIO.read(new File("/home/karthik/Desktop/goa.jpg"));
		int h = a.getHeight();
		int w = a.getWidth();
		System.out.println("width : " + w + " height: " + h);

		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				try {
					int b = a.getRGB(i, j);
					Color c = new Color(b);
					ret.add(new int[] { i, j, c.getRed(), c.getGreen(), c.getBlue() });
				} catch (ArrayIndexOutOfBoundsException e) {
					System.out.println(i + "," + j);
					throw e;
				} catch (Exception e) {
					System.out.println("mutton ");
					throw e;
				}
			}
		}
		return convertToKmeanPoints(ret);

	}

	List<Point> convertToKmeanPoints(List<int[]> points) {
		List<Point> ret = new ArrayList<Point>();
		for (int[] k : points) {
			Point p = new Point(k[2], k[3], k[4]);
			p.setXY(k[0], k[1]);
			ret.add(p);
		}
		return ret;
	}

}
