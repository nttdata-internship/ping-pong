package com.nttdata.internship.maps;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.LongStream;

import com.nttdata.internship.maps.databind.ObjectReader;
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

			locations.forEach(l -> System.out.println("Stream it " + l.getCity() + l.getTemperature()));

			HashMap<Location, Long> map = new HashMap<Location, Long>();
			for (Location location : locations) {
				map.put(location, location.getTemperature());
			}

			for (Location key : map.keySet()) {

				long temperature = map.get(key);
				System.out.println(key.getCity() + "," + " temperature: " + temperature);
			}

			// for (Entry<Location, Long> mapEntry : map.entrySet()) {
			// System.out.println(mapEntry.getKey().getRegion() + " " +
			// mapEntry.getValue());
			// }

			//Long[] values = map.values().toArray(new Long[map.values().size()]);
			//LongStream.of(values);
			// .max(values)
			// .ifPresent(maxInt -> System.out.println("Maximum temperature is: " +
			// maxInt));
			

			//Map.Entry<Location, Long> maxEntry = null;
			//for (Map.Entry<Location, Long> entry : map.entrySet()) {
			//	if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
			//		maxEntry = entry;
			//	}
			//	System.out.println("Maximum temperature: " + maxEntry);
			//}
			
			Collection<Long> c = map.values();
            System.out.println("Maximum temperature: " + Collections.max(c));
            System.out.println("Minimum temperature: " + Collections.min(c));
            
            
            

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

}
