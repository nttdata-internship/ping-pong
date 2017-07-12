package com.nttdata.internship.maps;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import com.nttdata.internship.maps.databind.ObjectReader;
import com.nttdata.internship.maps.entity.Location;
import java.util.stream.*;

import java.util.stream.Collectors;
import java.util.stream.Collectors;

//HashMap cu locatii ca si key si valoarea va fi temperatura maxima a locatiei.
//Compare locations with hashcode or .equals().
//MAPA IN JSON, CUM ADAUG DATE IN JSON, CUM LE CITESC
//sa gasim maximul si minimul pt temp intr-o tara din mapa din json si le afisam si sortam dupa tara si oras
// citim de la tastatura un oras si trebuie sa ne intoarca temperatura

/**
 * Hello world!
 *
 */
public class App {

	public static void main(String[] args) {

		ObjectReader<Location> objectReader = new ObjectReader<Location>("locations.json", Location.class);
		try {

			List<Location> location = (List<Location>) objectReader.readList();
			location.forEach(l -> System.out.println(l.getCity() + " " + l.getTemperature()));
			System.out.println(location.size());

			Map<Location, Long> map = new HashMap<Location, Long>();
			for (Location o : location)
				map.put(o, o.getTemperature());

			for (Location loc : map.keySet()) {
				long temp = map.get(loc);
				System.out.println(loc.getCity() + " " + temp);
			}

			Collection<Long> c = map.values();
			System.out.println(Collections.max(c));

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
				System.out.println("tara: " + loc.getCountry() + " oras : " + loc.getCity());
			}

			
			Map.Entry<Location, Long> maxValueFromCountry =null;
			
			for (Map.Entry<Location, Long> entry : treeMap.entrySet())
			{
				Location loc2 = entry.getKey();
				Location loc = null;

				if(loc == null || loc.getCountry().equals(loc2.getCountry())){
					
					if (maxValueFromCountry == null || entry.getValue().compareTo(maxValueFromCountry.getValue()) > 0)
					{
						maxValueFromCountry = entry;
						loc = maxValueFromCountry.getKey();
					}
				}
				else{
					System.out.println(maxValueFromCountry.getValue());
					maxValueFromCountry = null;
				}	
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}


}
