package com.massisframework.sweethome3d.javafx.properties;

import javafx.beans.property.ReadOnlyStringProperty;

public interface MetadataEntry {

	ReadOnlyStringProperty getKeyProperty();

	ReadOnlyStringProperty getValueProperty();

	boolean setValue(String val);
	
	default String getValue()
	{
		return getValueProperty().get();
	}

	default String getKey()
	{
		return getKeyProperty().get();
	}
	
	

}