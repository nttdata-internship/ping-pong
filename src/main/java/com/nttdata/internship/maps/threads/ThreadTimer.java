package com.nttdata.internship.maps.threads;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ThreadTimer implements Runnable {

	private Integer start;
	private int end;

	private SharedCounter sharedCounter;

	public ThreadTimer(int i, int end) throws FileNotFoundException {
		super();
		this.start = i;
		this.end = end;
	}

	public ThreadTimer(SharedCounter sharedCounter, int end) {
		this.sharedCounter = sharedCounter;
		this.end = end;
	}

	@Override
	public void run() {
		try {
			Files.createDirectories(Paths.get("src/main/resources/generated"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		try {

			final FileWriter out = new FileWriter(
					"src/main/resources/generated/" + Thread.currentThread().getName() + ".txt");

			while (sharedCounter.getValue() < end) {
				synchronized (sharedCounter) {
					start = sharedCounter.increment();
					System.out.println(Thread.currentThread().getName() + " " + sharedCounter.getValue());

					Thread.sleep(0);

					out.write(start + "\n");
					out.flush();

				}

			}

		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}

	}
}
