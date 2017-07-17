package com.nttdata.internship.maps.threads;

import java.io.FileNotFoundException;

import com.nttdata.internship.maps.Adunare;
import com.nttdata.internship.maps.ThreadTimer;

public class ThreadsMain {

	public static void main(String[] args) throws FileNotFoundException {

		// Facem threadurile sa colaboreze in asa fel incat sa numere aman2 pana
		// la 100.
		// Preferabil sa nu se repete numerele.
		// Fiecare Thread va scrie rezultatul intrun fisier propriu cu numele
		// dat de Threads.currentThread().getName()

		int limit = 10;

		ThreadTimer t1 = new ThreadTimer(new Adunare(), 0, limit);

		ThreadTimer t2 = new ThreadTimer(new Adunare(), 0, limit);

		Thread thread2 = new Thread(t2);

		Thread thread1 = new Thread(t1);

		Thread t3 = new Thread(new ThreadTimer(new Adunare(), 0, limit));
		thread2.start();
		thread1.start();
		//t3.start();

		System.out.println("Demo " + (char) 0);

	}
}
