package com.nttdata.internship.maps.entity;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Map;

public class Location implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7188040446023298068L;

	private Country country;

	private String city;

	private String region;

	private long temperature;
	
	public long getTemperature() {
		return temperature;
	}

	public void setTemperature(long temperature) {
		this.temperature = temperature;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	@Override
	public boolean equals(Object obj) {

		return this.country.equals(((Location)obj).country) && this.region.equals(((Location)obj).region);
	}

	
	
	
}
