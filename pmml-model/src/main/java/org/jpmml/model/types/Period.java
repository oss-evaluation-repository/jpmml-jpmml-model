/*
 * Copyright (c) 2018 Villu Ruusmann
 */
package org.jpmml.model.types;

import org.dmg.pmml.ComplexValue;
import org.dmg.pmml.DataType;

abstract
class Period<P extends Period<P>> extends Number implements ComplexValue, Comparable<P> {

	abstract
	public DataType getDataType();

	@Override
	public Long toSimpleValue(){
		return Long.valueOf(longValue());
	}

	@Override
	public int intValue(){
		long longValue = longValue();

		int intValue = (int)longValue;
		if(intValue != longValue){
			throw new ArithmeticException("integer overflow");
		}

		return intValue;
	}

	@Override
	public float floatValue(){
		return longValue();
	}

	@Override
	public double doubleValue(){
		return longValue();
	}
}