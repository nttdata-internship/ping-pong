package com.nttdata.internship.strategy.calculus;

import com.nttdata.internship.strategy.shape.Perimeter;
import com.nttdata.internship.strategy.shape.implementations.Circle;
import com.nttdata.internship.strategy.shape.implementations.Square;
import com.nttdata.internship.strategy.shape.implementations.Triangle;
import com.nttdata.internship.strategy.shape.utils.ShapeUtils;

public class CalculusMain {

	// O sa avem o interfata pe nume Perimeter;
	// Clase cerg, padrad , dreptunghi ,triunghi
	public static void main(String[] args) {

		Perimeter perimetruFals = new Perimeter() {

			@Override
			public float perimetru() {

				return (float) (Math.PI * Math.E);
			}
		};

		ShapeUtils.compute(new Square(4));
		ShapeUtils.compute(new Triangle(1, 2, 3));
		ShapeUtils.compute(new Circle(1));
		ShapeUtils.compute(perimetruFals);

	}
}
