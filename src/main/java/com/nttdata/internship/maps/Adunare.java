package com.nttdata.internship.maps;

class Adunare implements Operatie {
	public int op(int a) {
		return ++a;
	}

	@Override
	public boolean stop(int a, int b)  {
		// TODO Auto-generated method stub
		return a <= b;
	}

	@Override
	public void writeToFile() {
		// TODO Auto-generated method stub
		
	}
}
