/*
 * Copyright (c) 2016 Villu Ruusmann
 */
package org.jpmml.model;

import java.io.InputStream;
import java.util.List;

import javax.xml.validation.Schema;

import jakarta.xml.bind.Unmarshaller;
import org.dmg.pmml.Extension;
import org.dmg.pmml.Model;
import org.dmg.pmml.PMML;
import org.dmg.pmml.regression.RegressionModel;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ValidationTest {

	@Test
	public void unmarshal() throws Exception {
		Schema schema = JAXBUtil.getSchema();

		assertNotNull(schema);

		Unmarshaller unmarshaller = JAXBUtil.createUnmarshaller();
		unmarshaller.setSchema(schema);

		PMML pmml;

		try(InputStream is = ResourceUtil.getStream(ValidationTest.class)){
			pmml = (PMML)unmarshaller.unmarshal(is);
		}

		List<Model> models = pmml.getModels();

		assertEquals(1, models.size());

		RegressionModel regressionModel = (RegressionModel)models.get(0);

		List<Extension> extensions = regressionModel.getExtensions();

		assertEquals(2, extensions.size());

		checkExtension("position", "first", extensions.get(0));
		checkExtension("position", "last", extensions.get(1));
	}

	static
	private void checkExtension(String name, String value, Extension extension){
		assertEquals(name, extension.getName());
		assertEquals(value, extension.getValue());
	}
}