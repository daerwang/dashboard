/**
 * 
 * Copyright (c) 2014-2015 the original author or authors.
 */
package com.oceanbank.webapp.common.handler;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.springframework.stereotype.Component;

/**
 * The Class JsonDateSerializer.
 * 
 * @author Marinell Medina
 * @since 03.10.2015
 */
@Component
public class JsonDateSerializer extends JsonSerializer<Date>{

	
	/* (non-Javadoc)
	 * @see org.codehaus.jackson.map.JsonSerializer#serialize(java.lang.Object, org.codehaus.jackson.JsonGenerator, org.codehaus.jackson.map.SerializerProvider)
	 */
	@Override
	public void serialize(Date date, JsonGenerator gen, SerializerProvider provider)throws IOException, JsonProcessingException {
		final SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", new Locale("en"));
		final String formattedDate = dateFormat.format(date);

		gen.writeString(formattedDate);
		
	}

}
