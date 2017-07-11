package com.nttdata.internship.maps;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.nttdata.internship.maps.databind.ObjectReader;
import com.nttdata.internship.maps.entity.Location;





//HashMap cu locatii ca si key si valoarea va fi temperatura maxima a locatiei.
//Compare locations with hashcode or .equals().
//MAPA IN JSON, CUM ADAUG DATE IN JSON, CUM LE CITESC
//sa gasim maximul si minimul pt temp intr-o tara din mapa din json
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
			for (Location o :location) 
			    map.put( o, o.getTemperature());
			
			for(Location loc : map.keySet()){
				long temp = map.get(loc);
				System.out.println(loc.getCity()+ " " + temp);
			}
			
			Collection<Long> c = map.values();
			System.out.println(Collections.max(c));
		
			
			 Map.Entry<Location, Long> entry=map.entrySet().iterator().next();
			 Long min = entry.getValue();
			
			for(Location loc : map.keySet()){
				long temp = map.get(loc);
				
				if(loc.getCountry().equals("RO") && temp < min)
					min=temp;
					
			}
			System.out.println(min);
			
				
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
	
}
}

