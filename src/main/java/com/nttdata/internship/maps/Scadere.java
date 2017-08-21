package com.nttdata.internship.maps;

class Scadere implements Operatie {
	public int op(int a) {
		int param = a;
		return --param;
	}

	@Override
	public boolean stop(int a, int b) {
		// TODO Auto-generated method stub
		return a >= b;
	}

	@Override
	public void writeToFile() {
		// TODO Auto-generated method stub

	}
}
