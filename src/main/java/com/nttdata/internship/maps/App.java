package com.nttdata.internship.maps;

import java.io.IOException;
import java.text.Collator;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import com.nttdata.internship.maps.databind.ObjectReader;
import com.nttdata.internship.maps.entity.Country;
import com.nttdata.internship.maps.entity.Location;

//HashMap cu locatii ca si key si valoarea va fi temperatura maxima a locatiei.
//Compare locations with hashcode or .equals().
//MAPA IN JSON, CUM ADAUG DATE IN JSON, CUM LE CITESC
//sa gasim maximul si minimul intr-o tara din mapa din json
/**
 * Hello world!
 *
 */
public class App {
	// "a","b","c".......
	public static void main(String... args) {

		ObjectReader<Location> objectReader = new ObjectReader<Location>("locations.json", Location.class);
		try {
			List<Location> locations = (List<Location>) objectReader.readList();

			// System.out.println(locations.size());

			locations.forEach(l -> System.out.println("Stream it: " + l.getCity() + " " + l.getTemperature()));

			HashMap<Location, Long> map = new HashMap<Location, Long>();
			for (Location location : locations) {
				map.put(location, location.getTemperature());
			}

			for (Location key : map.keySet()) {

				long temperature = map.get(key);
				System.out.println(key.getCity() + "," + " temperature: " + temperature);
			}
			
			System.out.println();

			Collection<Long> c = map.values();
			System.out.println("Maximum temperature: " + Collections.max(c));
			System.out.println("Minimum temperature: " + Collections.min(c));
			
			System.out.println();

			Map<Location, Long> treeMap = new TreeMap<Location, Long>(new Comparator<Location>() {

				@Override
				public int compare(Location o1, Location o2) {
					if (o1.getCountry().equals(o2.getCountry()))
						return o1.getCity().compareTo(o2.getCity());
					else
						return o1.getCountry().name().compareTo(o2.getCountry().name());
				}

			});

			treeMap.putAll(map);
			for (Map.Entry<Location, Long> entry : treeMap.entrySet()) {
				Location loc = entry.getKey();
				System.out.println("Country: " + loc.getCountry() + " City: " + loc.getCity());
			}
			
			
			System.out.println();
			
			
			Collection<Long> c2 = ()
			System.out.println("Maximum temperature: " + Collections.max(c2));
			System.out.println("Minimum temperature: " + Collections.min(c2));

		} catch (IOException e) {
			e.printStackTrace();
		}

		// List<Location> newList = new ArrayList<Location>();
		// Location l1 = new Location();
		// Location l2 = new Location();
		// Location l3 = new Location();
		// Location l4 = new Location();
		// Location l5 = new Location();
		//
		// l1.setTemperature(0);
		// l2.setTemperature(30);
		// l3.setTemperature(40);
		// l4.setTemperature(25);
		// l5.setTemperature(30);
		// l1.setCity("Berlin");
		// l2.setCity("Dortmund");
		// l3.setCity("Oslo");
		// l4.setCity("Copenhaga");
		// l5.setCity("Frankfurt");
		//
		// newList.add(l1);
		// newList.add(l2);
		// newList.add(l3);
		// newList.add(l4);
		// newList.add(l5);

	}

	/*
	 * static <Location, Long extends Comparable<? super Long>>
	 * SortedSet<Map.Entry<Location, Long>> entriesSortedByValues(Map<Location,
	 * Long> map) { SortedSet<Map.Entry<Location, Long>> sortedEntries = new
	 * TreeSet<Map.Entry<Location, Long>>( new Comparator<Map.Entry<Location,
	 * Long>>() {
	 * 
	 * @Override public int compare(Map.Entry<Location, Long> e1,
	 * Map.Entry<Location, Long> e2) { int res =
	 * e1.getValue().compareTo(e2.getValue()); return res != 0 ? res : 1; } } );
	 * sortedEntries.addAll(map.entrySet()); return sortedEntries;
	 */
}
