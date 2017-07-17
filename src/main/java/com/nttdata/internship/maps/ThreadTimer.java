package com.nttdata.internship.maps;

public class ThreadTimer implements Runnable {

	private Operatie op;

	int start;
	int end;

	public ThreadTimer(Operatie op, int start, int end) {
		super();
		
		
		this.op = op;

		this.start = start;
		this.end = end;
	}

	@Override
	public void run() {

		// / pre initializare
		int i = start;
		while (op.stop(i, end)) {

			i = op.op(i);

			System.out.println( Thread.currentThread().getName()+"    "+i);

		}
		// post initializare

	}

}
