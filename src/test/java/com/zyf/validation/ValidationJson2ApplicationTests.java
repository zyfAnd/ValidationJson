package com.zyf.validation;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Logger;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jackson.JsonNodeReader;
import com.github.fge.jsonschema.core.report.ProcessingMessage;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchemaFactory;

@SpringBootTest
public class ValidationJson2ApplicationTests {

	//http://json-schema-validator.herokuapp.com/index.jsp
		// https://jsonschema.net/#/editor 
		Logger logger = Logger.getLogger("ValidationJson2ApplicationTests");
		@Test
		public void validateJsonSuccess() {
			JsonNode schema = readJSONfile("src/main/resources/json/Schema.json");
			JsonNode data = readJSONfile("src/main/resources/json/DataGoodExample2.json");
			ProcessingReport report = JsonSchemaFactory.byDefault().getValidator().validateUnchecked(schema, data);
			Iterator<ProcessingMessage> it = report.iterator();
			while (it.hasNext()) {
				logger.info(it.next().getMessage());
			}
			Assert.assertTrue(report.isSuccess());
		}
		@Test
		// wrong data
		public void validateJsonFailure() {
			JsonNode data = readJSONfile("src/main/resources/json/DataBadExample.json");
			JsonNode schema = readJSONfile("src/main/resources/json/Schema.json");

			ProcessingReport report = JsonSchemaFactory.byDefault().getValidator().validateUnchecked(schema, data);
			Assert.assertFalse(report.isSuccess());

			// assert error message
			Iterator<ProcessingMessage> it = report.iterator();
			while (it.hasNext()) {
				logger.info(it.next().getMessage());
			}
			Assert.assertEquals("instance type (string) does not match any allowed primitive type (allowed: [\"boolean\"])",
					it.next().getMessage());
			Assert.assertEquals("instance type (string) does not match any allowed primitive type (allowed: [\"integer\",\"number\"])",
					it.next().getMessage());

		}
		private JsonNode readJSONfile(String filePath) {
			JsonNode instance = null;
			try {
				instance = new JsonNodeReader().fromReader(new FileReader(filePath));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return instance;
		}
}
