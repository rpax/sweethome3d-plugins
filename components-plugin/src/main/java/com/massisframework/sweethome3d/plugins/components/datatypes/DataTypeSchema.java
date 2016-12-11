package com.massisframework.sweethome3d.plugins.components.datatypes;

import java.util.HashMap;
import java.util.Map;

public class DataTypeSchema {

	private Map<String, FieldType> values;
	private String name;

	public DataTypeSchema(String name) {
		this();
		this.name=name;
	}
	public DataTypeSchema() {
		this.values = new HashMap<>();
	}

	public Map<String, FieldType> getValues() {
		return values;
	}

	public void setValues(Map<String, FieldType> values) {
		this.values = values;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addField(String name, FieldType type) {
		this.values.put(name, type);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((values == null) ? 0 : values.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DataTypeSchema other = (DataTypeSchema) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (values == null) {
			if (other.values != null)
				return false;
		} else if (!values.equals(other.values))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DataTypeSchema [values=" + values + ", name=" + name + "]";
	}
}
