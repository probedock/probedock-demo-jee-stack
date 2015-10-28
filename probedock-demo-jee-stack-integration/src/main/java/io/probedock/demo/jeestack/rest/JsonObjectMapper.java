package io.probedock.demo.jeestack.rest;

import com.fasterxml.jackson.annotation.JsonInclude;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * Configuration of Jackson serialization and deserialization.
 *
 * @author Laurent Prevost <laurent.prevost@probedock.io>
 */
@Provider
@Produces(MediaType.APPLICATION_JSON)
public class JsonObjectMapper implements ContextResolver<ObjectMapper> {

	private ObjectMapper jacksonObjectMapper;

	public JsonObjectMapper() {
		jacksonObjectMapper = new ObjectMapper();
		jacksonObjectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		jacksonObjectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
	}

	@Override
	public ObjectMapper getContext(Class<?> arg0) {
		return jacksonObjectMapper;
	}
}
