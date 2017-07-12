package com.nttdata.internship.maps;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

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

	private static final String MIN_TEMPERATURE = "MIN";
	private static final String MAX_TEMPERATURE = "MAX";

	public static void main(String[] args) {

		ObjectReader<Location> objectReader = new ObjectReader<Location>("locations.json", Location.class);
		try {
			List<Location> locations = objectReader.readList();

			System.out.println(locations.size());

			Map<Location, Float> weatherData = new TreeMap<Location, Float>(new Comparator<Location>() {

				public int compare(Location o1, Location o2) {
					if (o1.getCountry().equals(o2.getCountry())) {
						return o1.getCity().compareTo(o2.getCity());
					} else
						return o1.getCountry().compareTo(o2.getCountry());
				}
			});

			for (Location location : locations) {
				weatherData.put(location, location.getTemperature());

			}

			// TODO citeste de la consola si apeleaza getter

			Iterator<Location> locationIt = weatherData.keySet().iterator();
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

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
