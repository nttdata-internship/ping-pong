package com.nttdata.internship.strategy.shape.utils;

import com.nttdata.internship.strategy.shape.Perimeter;

public class ShapeUtils {

	public static void compute(Perimeter p) {
		double perimeter = p.perimetru();

		if (perimeter <= 0) {
			System.out.println("ceva nu e bine");
		}
		System.out.println(perimeter);

	}
}
