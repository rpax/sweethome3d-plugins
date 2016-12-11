package com.massisframework.sweethome3d.plugins.components;

import java.util.HashMap;
import java.util.Map;

public class HomeObjectComponent {

	// Sigue un DataTypeSchema,y tiene unos valores
	private String schemaName;
	private Map<String, String> values;

	public HomeObjectComponent(String schemaName)
	{
		this.schemaName = schemaName;
		this.values = new HashMap<>();
	}

	public void setValue(String fieldName, Object value)
	{
		this.values.put(fieldName, String.valueOf(value));
	}

	public String getAsString(String fieldName)
	{
		return String.valueOf(this.values.get(fieldName));
	}

	public Integer getAsInt(String fieldName)
	{
		return Integer.valueOf(this.values.get(fieldName));
	}

	public Double getAsDouble(String fieldName)
	{
		return Double.valueOf(this.values.get(fieldName));
	}

	public String getSchemaName()
	{
		return schemaName;
	}

}
