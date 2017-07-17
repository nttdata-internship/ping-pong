package com.nttdata.internship.maps;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class ThreadTimer implements Runnable {

	private Operatie op;

	Integer start;
	int end;
	FileWriter out;

	public ThreadTimer(Operatie op, int i, int end) throws FileNotFoundException {
		super();

		this.op = op;

		this.start = i;
		this.end = end;
	}

	// @Override
	// public void run() {
	//
	// try {
	// this.out = new FileWriter(Thread.currentThread().getName() + ".txt");
	// } catch (IOException e1) {
	//
	// e1.printStackTrace();
	// }
	//
	// try {
	// // / pre initializare
	// int i = start;
	// System.out.println("i" + i);
	// if (GlobalVariable.ok) {
	//
	// while (op.stop(i, end)) {
	//
	// i = op.op(i);
	//
	// out.write("" + i);
	// out.write('\n');
	// System.out.println(Thread.currentThread().getName() + " " + i);
	// i++;
	// }
	// GlobalVariable.ok = false;
	// } else {
	// int i1 = start + 1;
	// System.out.println("i1" + i1);
	// while (op.stop(i1, end)) {
	//
	// i1 = op.op(i1);
	//
	// out.write("" + i1);
	// out.write('\n');
	// System.out.println(Thread.currentThread().getName() + " " + i1);
	// i1++;
	// }
	// GlobalVariable.ok = true;
	// }
	// out.flush();
	// // post initializare
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }

	@Override
	public  void run() {
		try {
			this.out = new FileWriter(Thread.currentThread().getName() + ".txt");

			while (start <= end) {

//				synchronized (start) {
					start++;
					out.write(start.toString() + "\n");

					System.out.println(Thread.currentThread().getName() + " " + start);
//				}

			}
			out.flush();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
