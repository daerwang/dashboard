/**
 * 
 * Copyright (c) 2014-2015 the original author or authors.
 */
package com.oceanbank.webapp.common.handler;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.oceanbank.webapp.common.model.DataTablesResponse;


/**
 * The Class GsonDataTableTypeAdapter.
 *
 * @author Marinell Medina
 * @since 03.10.2015
 * @param <E> the element type
 */
public class GsonDataTableTypeAdapter<E> implements JsonSerializer<DataTablesResponse<E>>{

	/* (non-Javadoc)
	 * @see com.google.gson.JsonSerializer#serialize(java.lang.Object, java.lang.reflect.Type, com.google.gson.JsonSerializationContext)
	 */
	public JsonElement serialize(DataTablesResponse<E> response, Type arg1, JsonSerializationContext context) {
		
		final JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("draw", response.getDraw());
	    jsonObject.addProperty("recordsTotal", response.getRecordsTotal());
	    jsonObject.addProperty("recordsFiltered", response.getRecordsFiltered());
	    
	    final JsonElement jsonSpecial = context.serialize(response.getData());
	    
	    jsonObject.add("data", jsonSpecial);
	    
		return jsonObject;
	}

}
