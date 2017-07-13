package com.nttdata.internship.maps.entity;

import java.io.Serializable;
import java.util.Comparator;

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

	public Location() {

	}

	public Location(String city) {
		super();
		this.city = city;
	}

	public Location(String string, Country valueOf) {
		// TODO Auto-generated constructor stub
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

}
