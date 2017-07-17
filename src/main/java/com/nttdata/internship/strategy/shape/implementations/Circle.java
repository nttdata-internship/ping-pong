package com.nttdata.internship.strategy.shape.implementations;

import com.nttdata.internship.strategy.shape.Perimeter;

public class Circle implements Perimeter{

	float radius;
	
	public Circle(float radius){
		this.radius = radius;
	}
	
	@Override
	public float perimetru() {
		
		return (float) (2 * radius * (Math.PI));
	}
	

	
}
