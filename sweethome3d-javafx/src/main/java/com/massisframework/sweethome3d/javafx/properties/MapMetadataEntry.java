package com.massisframework.sweethome3d.javafx.properties;

import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;

public class MapMetadataEntry implements MetadataEntry {

	private final ReadOnlyStringWrapper keyProperty;
	private final ReadOnlyStringWrapper valueProperty;

	public MapMetadataEntry(String key, String value)
	{
		this.keyProperty = new ReadOnlyStringWrapper(key);
		this.valueProperty = new ReadOnlyStringWrapper(value);
	}

	@Override
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

	@Override
	public ReadOnlyStringProperty getValueProperty()
	{
		return valueProperty.getReadOnlyProperty();
	}

	@Override
	public boolean setValue(String val)
	{
		if (this.valueProperty.get().equals(val))
		{
			return false;
		} else
		{
			this.valueProperty.set(val);
			return true;
		}
	}

}
