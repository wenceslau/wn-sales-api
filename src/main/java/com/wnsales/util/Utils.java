package com.wnsales.util;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.io.File;
import java.lang.reflect.Type;

/**
 * Metodos uteis
 * @author Wenceslau
 *
 */
public class Utils {

	private static final Logger logger = LoggerFactory.getLogger(Utils.class);
	private static final Gson gson = new Gson();

	/* JSON */

	/**
	 * Format object to json
	 * @param object objeto
	 * @return texto
	 */
	public static String objectToJson(Object object) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

		String jsonInString = "Error convert object to json. ";

		try {

			jsonInString = mapper.writeValueAsString(object);

		} catch (Exception e) {

			jsonInString += e.getMessage();

		}

		return jsonInString;

	}

	/**
	 * Converte Objeto para JSON e nao mostra as propriedade com valor null
	 * @param object objeto
	 * @return texto
	 */
	public static String objectToJsonNotIncludNull(Object object) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		mapper.setSerializationInclusion(Include.NON_NULL);

		String jsonInString = "Error convert object to json ";

		try {

			jsonInString = mapper.writeValueAsString(object);

		} catch (Exception e) {

			jsonInString += e.getMessage();

		}

		return jsonInString;

	}

	/**
	 * Converte Sring json a um objeto com o tipo informado
	 * @param json texto
	 * @param type tipo objeto
	 * @return objeto
	 */
	public static Object jsonToObject(String json, Type type) {
		try {
			Object o = gson.fromJson(json, type);
			return o;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
