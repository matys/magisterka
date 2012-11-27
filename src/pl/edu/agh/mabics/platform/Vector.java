package pl.edu.agh.mabics.platform;

import java.util.List;


public class Vector {

	int x;
	int y;
	
	public Vector(List<Integer> coordinates){
		x = coordinates.get(0);
		y = coordinates.get(1);
	}
}
