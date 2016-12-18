package com.massisframework.sweethome3d.javafx.properties;

import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;

public class MetadataEntry {

	private final ReadOnlyStringWrapper keyProperty;
	private final ReadOnlyStringWrapper valueProperty;

	public MetadataEntry(String key, String value)
	{
		this.keyProperty = new ReadOnlyStringWrapper(key);
		this.valueProperty = new ReadOnlyStringWrapper(value);
	}

	public ReadOnlyStringProperty getKeyProperty()
	{
		return keyProperty.getReadOnlyProperty();
	}

	public String getKey()
	{
		return getKeyProperty().get();
	}

	public String getValue()
	{
		return getValueProperty().get();
	}

	public ReadOnlyStringProperty getValueProperty()
	{
		return valueProperty.getReadOnlyProperty();
	}

	public void setValue(String val)
	{
		this.valueProperty.set(val);
	}

}
