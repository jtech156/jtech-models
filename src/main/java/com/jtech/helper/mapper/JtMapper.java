package com.jtech.helper.mapper;

import java.util.List;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

public class JtMapper {

	MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
	MapperFacade mapperEngine;

	public JtMapper() {
		this.mapperEngine = mapperFactory.getMapperFacade();
	}

	public <T> T convertModel(Object object, Class<T> clz) {
		T output = mapperEngine.map(object, clz);
		return output;
	}

	public <E, T> List<T> convertModelList(List<E> object, Class<T> clz) {
		List<T> output = mapperEngine.mapAsList(object, clz);
		return output;
	}
}