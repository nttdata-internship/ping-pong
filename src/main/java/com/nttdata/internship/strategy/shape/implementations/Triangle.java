package com.nttdata.internship.strategy.shape.implementations;

import com.nttdata.internship.strategy.shape.Perimeter;

public class Triangle implements Perimeter{

	int lA, lB, lC;
	public Triangle(int la, int lb, int lc){
		
		lA = la;
		lB = lb;
		lC = lc;
		
	}
	@Override
	public float perimetru() {
		
		return lA+lB+lC;
	}
	
}
