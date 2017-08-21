package com.nttdata.internship.ui.panel;

public class Swap {

	public static int[] schimba(int a[], int i, int j) {
		int aux = a[j];
		a[j] = a[i];
		a[i] = aux;
		return a;

	}

	static void printVect(int a[]) {
		for (int i = 0; i < a.length-1; i++) {
			System.out.print(a[i] + " ,");
		}
		System.out.println(a[a.length-1]);
		System.out.println();
	}

	public static void main(String[] a) {
		int c[] = { 7, 9, 10, 12, 32, 67 };
		printVect(c);
		// & && | ||  % ^ > >> < << ! ~
		c = schimba(c, 1, 2);
		printVect(c);
	}
}
