package com.nttdata.internship.maps.threads;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;

/**
 * 
 * Program pentru a sincroniza N threaduri cu access concurent pe o variabila
 * comuna.
 * 
 * Incercam sa numaram pana la o limita in asa fel incat fiecare Thread in parte
 * sa preia cate o parte din sirul de numere.
 * 
 * Numerele nu trebuie sa fie consecutive, dar in ordine. La fel rezultatele per
 * thread nu trebuie neaparat sa fie impartite egal.(i.e Daca vreau sa numar
 * pana la 100 cu 4 threaduri, atunci outputul pt fiecare thread nu trebuie sa
 * fie de 100/4 = 25 neaparat).
 * 
 * @author stefan.neacsu
 *
 */

public class ThreadsMain {

	public static void main(String[] args) {

		final int limit = 100;
		List<Integer> list = new ArrayList<Integer>();
		list.add(limit);
		Collections.sort(list);

		try {

			final SharedCounter sharedCounter = new SharedCounter();

			ThreadTimer counter1 = new ThreadTimer(sharedCounter, limit);

			ThreadTimer counter2 = new ThreadTimer(sharedCounter, limit);

			Thread thread1 = new Thread(counter1);
			Thread thread2 = new Thread(counter2);

			Thread thread3 = new Thread(new ThreadTimer(sharedCounter, limit));
			Thread thread4 = new Thread(new ThreadTimer(sharedCounter, limit));

			thread2.start();
			thread1.start();
			thread3.start();
			thread4.start();

			thread1.join();
			thread2.join();
			thread3.join();

		}

		catch (Exception interruptedEx) {
			interruptedEx.printStackTrace();
		}

		BufferedReader br = null;
		try {
			Files.walk(Paths.get("src/main/resources/generated")).filter(Files::isRegularFile)
					.collect(Collectors.toList());

			File folder = new File("src/main/resources/generated");
			File[] listOfFiles = folder.listFiles();

			Set<String> allFileContents = new HashSet<>();
			for (int i = 0; i < listOfFiles.length; i++) {
				File filesInFolder = listOfFiles[i];
				if (filesInFolder.isFile() && filesInFolder.getName().endsWith(".txt")) {
					String content = FileUtils.readFileToString(filesInFolder);

					allFileContents.add(content);
				}
			}

			/*
			 * List<File> filesInFolder = null;
			 * Files.walk(Paths.get("src/main/resources/generated"))
			 * .filter(Files::isRegularFile).map(Path::toFile).collect(Collectors.toList
			 * ());
			 */

			System.out.println("All file contents " + allFileContents.size());

			Collections.sort(new ArrayList<>(allFileContents));

			System.out.println("Test ="+(allFileContents.size() == limit));

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) {
					br.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}
}
