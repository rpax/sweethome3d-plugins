package com.massisframework.sweethome3d.javafx.properties;

import java.util.Optional;

import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.ReadOnlyStringProperty;

public interface MetadataObject {

	public ReadOnlyStringProperty nameProperty();

	public default String getName()
	{
		return nameProperty().get();
	}

	public ReadOnlyStringProperty descriptionProperty();

	public default String getDescription()
	{
		return descriptionProperty().get();
	}

	public ReadOnlyStringProperty idProperty();

	public default String getId()
	{
		return idProperty().get();
	}

	public ReadOnlyListProperty<MetadataEntry> entriesProperty();

	public default Optional<String> getValue(String key)
	{
		return getEntry(key).map(e -> e.getValue());
	}

	public default String getValue(Enum<?> key, String defValue)
	{
		return getValue(key.toString(), defValue);
	}

	public default String getValue(String key, String defValue)
	{
		return getValue(key).orElse(defValue);
	}

	public default Optional<MetadataEntry> getEntry(Enum<?> key)
	{
		return getEntry(key.toString());
	}

	public default Optional<MetadataEntry> getEntry(String key)
	{
		return entriesProperty().stream().filter(e -> e.getKey().equals(key))
				.findFirst();
	}

	public default boolean containsKey(String key)
	{
		return getEntry(key).isPresent();
	}

	public default boolean put(MetadataEntry entry)
	{
		String key = entry.getKeyProperty().get();
		String value = entry.getKeyProperty().get();
		return put(key, value);
	}

	public default boolean put(String key, String value)
	{

		Optional<MetadataEntry> entry = getEntry(key);
		if (entry.isPresent())
		{
			return entry.get().setValue(value);
		} else
		{
			MapMetadataEntry mdEntry = new MapMetadataEntry(key, value);
			entriesProperty().add(mdEntry);
		}
		return true;
	}

}
