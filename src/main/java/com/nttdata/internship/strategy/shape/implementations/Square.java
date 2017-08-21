package com.nttdata.internship.strategy.shape.implementations;

import com.nttdata.internship.strategy.shape.Perimeter;

public class Square implements Perimeter {

	int l;

	public Square(int l) {
		this.l = l;
	}

	@Override
	public float perimetru() {

		return 4 * l;
	}

}
