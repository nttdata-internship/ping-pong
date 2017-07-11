package com.nttdata.internship.maps;

import java.io.IOException;
import java.util.List;

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

	public static void main(String[] args) {

		ObjectReader<Location> objectReader = new ObjectReader<Location>("locations.json", Location.class);
		try {
			List<Location> location = (List<Location>) objectReader.readList();

			System.out.println(location.size());
			
			location.forEach(l -> System.out.println(l.getCity() + " " + l.getRegion()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
