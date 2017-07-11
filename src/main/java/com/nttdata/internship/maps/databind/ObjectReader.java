package com.nttdata.internship.maps.databind;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectReader<T> {

	private ObjectMapper objectMapper;

	private InputStream is;

	private Class<T> clazz;

	public ObjectReader(String resourceName, Class<T> dtoClass) {
		this.objectMapper = new ObjectMapper();
		this.is = Thread.currentThread().getContextClassLoader().getResourceAsStream(resourceName);
		this.clazz = dtoClass;

	}

	public <T> T readSingle() throws JsonParseException, JsonMappingException, IOException {

		return (T) objectMapper.readValue(is, clazz);
	}

	public <T> List readList() throws JsonParseException, JsonMappingException, IOException {

		return objectMapper.readValue(is, objectMapper.getTypeFactory().
				  constructCollectionType(List.class, clazz));
	}

}
