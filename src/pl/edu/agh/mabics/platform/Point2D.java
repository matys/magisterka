package pl.edu.agh.mabics.platform;

import java.util.List;


public class Point2D {

	int x;
	int y;
	
	public Point2D(List<Integer> coordinates){
		x = coordinates.get(0);
		y = coordinates.get(1);
	}
}
