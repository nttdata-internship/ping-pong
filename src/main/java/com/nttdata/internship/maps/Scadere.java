package com.nttdata.internship.maps;

class Scadere implements Operatie {
	public int op(int adunare) {
		int param = adunare;
		return --param;
	}

	@Override
	public boolean stop(int adunare, int b) {
		// TODO Auto-generated method stub
		return adunare >= b;
	}

	@Override
	public void writeToFile() {
		// TODO Auto-generated method stub

	}
}
