package com.nttdata.internship.maps;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

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
public class Maps_Main {
	// "a","b","c".......
	public static void main(String... args) {

		final String MIN_TEMPERATURE = "MIN";
		final String MAX_TEMPERATURE = "MAX";

		ObjectReader<Location> objectReader = new ObjectReader<Location>("locations.json", Location.class);
		try {
			List<Location> locations = (List<Location>) objectReader.readList();

			// System.out.println(locations.size());

			// locations.forEach(l -> System.out.println("Stream it: " + l.getCity() + " " +
			// l.getTemperature()));

			Map<Location, Float> map = new HashMap<Location, Float>();
			for (Location location : locations) {
				map.put(location, location.getTemperature());
			}

			for (Location key : map.keySet()) {

				float temperature = map.get(key);
				System.out.println(key.getCity() + "," + " temperature: " + temperature);
			}

			System.out.println();

			Collection<Float> c = map.values();
			System.out.println("Maximum temperature: " + Collections.max(c));
			System.out.println("Minimum temperature: " + Collections.min(c));

			System.out.println();

			Map<Location, Float> treeMap = new TreeMap<Location, Float>(new Comparator<Location>() {

				@Override
				public int compare(Location o1, Location o2) {
					if (o1.getCountry().equals(o2.getCountry()))
						return o1.getCity().compareTo(o2.getCity());
					else
						return o1.getCountry().name().compareTo(o2.getCountry().name());
				}

			});

			treeMap.putAll(map);
			for (Map.Entry<Location, Float> entry : treeMap.entrySet()) {
				Location loc = entry.getKey();
				System.out.println("Country: " + loc.getCountry() + " City: " + loc.getCity());
			}

			System.out.println();

			Iterator<Location> locationIt = treeMap.keySet().iterator();
			Country currentCountry = null;
			float minTemperature = 0;
			float maxTemperature = 0;
			Map<Country, Map<String, Location>> weatherStatistics = new HashMap<Country, Map<String, Location>>();
			while (locationIt.hasNext()) {
				Location currentLocation = locationIt.next();
				float currentTemperature = currentLocation.getTemperature();
				Map<String, Location> countryStatistics = weatherStatistics.get(currentLocation.getCountry());
				if (currentCountry != currentLocation.getCountry()) {
					countryStatistics = new HashMap<String, Location>();
					countryStatistics.put(MAX_TEMPERATURE, currentLocation);
					countryStatistics.put(MIN_TEMPERATURE, currentLocation);
					weatherStatistics.put(currentLocation.getCountry(), countryStatistics);
					minTemperature = currentTemperature;
					maxTemperature = currentTemperature;

				}
				if (currentTemperature < minTemperature) {
					countryStatistics.put(MIN_TEMPERATURE, currentLocation);
					minTemperature = currentTemperature;
				}
				if (currentTemperature > maxTemperature) {
					countryStatistics.put(MAX_TEMPERATURE, currentLocation);
					maxTemperature = currentTemperature;
				}
				currentCountry = currentLocation.getCountry();

			}

			for (Entry<Country, Map<String, Location>> countryData : weatherStatistics.entrySet()) {
				System.out.println("Country Statistic for country=[" + countryData.getKey() + "]");
				Map<String, Location> cityData = countryData.getValue();
				System.out.println(String.format("Min Temparature %s for city %s",
						cityData.get(MIN_TEMPERATURE).getTemperature(), cityData.get(MIN_TEMPERATURE).getCity()));

				System.out.println(String.format("Max Temparature %s for city %s",
						cityData.get(MAX_TEMPERATURE).getTemperature(), cityData.get(MAX_TEMPERATURE).getCity()));
			}

			// TODO citeste de la consola si apeleaza getter
			System.out.println("args.length" + args.length);
			for (String st : args) {
				/// for (Map.Entry<Location, Float> entry : treeMap.entrySet()){
				// if(st.equals(entry.getKey().getCity()))
				// System.out.println(entry.getValue());
				map.get(new Location(Country.valueOf(args[1])));
				System.out.println("Input =" + st + " nu exista!");
			}

			/// Timer: pornesc de la 100 si un timer numara pana la 0 si unu pana la 200.
			/// Outputul in consola.

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
