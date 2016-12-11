package com.massisframework.sweethome3d.plugins.components.datatypes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.massisframework.sweethome3d.plugins.components.properties.MassisProperty;

public class JsonSchemas implements MassisProperty, Iterable<DataTypeSchema> {

	@Override
	public String toString() {
		return "JsonSchemas [schemas=" + schemas + "]";
	}

	private List<DataTypeSchema> schemas;

	public JsonSchemas() {
		this.schemas = new ArrayList<>();
	}

	public boolean addSchema(DataTypeSchema schema) {
		for (DataTypeSchema s : schemas) {
			if (s.getName().equals(schema.getName()))
				return false;
		}
		this.schemas.add(schema);
		return true;
	}

	public boolean removeSchema(DataTypeSchema schema) {
		return schemas.remove(schema);
	}

	public int size() {
		return schemas.size();
	}

	public DataTypeSchema get(int index) {
		return this.schemas.get(index);
	}

	@Override
	public Iterator<DataTypeSchema> iterator() {
		return this.schemas.iterator();
	}

	public void addOrReplace(DataTypeSchema sch) {
		Iterator<DataTypeSchema> it = this.iterator();
		while (it.hasNext()) {
			DataTypeSchema next = it.next();
			if (next.getName().equals(sch.getName())) {
				it.remove();
				break;
			}
		}
		this.schemas.add(sch);
	}
}
