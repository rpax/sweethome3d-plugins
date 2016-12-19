package com.massisframework.sweethome3d.javafx.properties;

import java.util.Objects;

import javafx.beans.Observable;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.ReadOnlyListWrapper;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;

public class MapMetadataSection implements MetadataObject {

	private final ReadOnlyListWrapper<MetadataEntry> entries;
	private final ReadOnlyStringWrapper id;
	private final ReadOnlyStringWrapper description;
	private final ReadOnlyStringWrapper name;

	public MapMetadataSection(MetadataObjectHeader header){
		this(header.getId(),header.getName(),header.getDescription());
	}

	public MapMetadataSection(String id, String friendlyName,
			String description)
	{
		Objects.requireNonNull(id);
		this.id = new ReadOnlyStringWrapper(id);
		this.name = new ReadOnlyStringWrapper(friendlyName);
		this.description = new ReadOnlyStringWrapper(description);
		this.entries = new ReadOnlyListWrapper<>(FXCollections
				.observableArrayList(p -> new Observable[] {
						p.getKeyProperty(),
						p.getValueProperty()
				}));

	}

	@Override
	public ReadOnlyListProperty<MetadataEntry> entriesProperty()
	{
		return entries.getReadOnlyProperty();
	}

	public ReadOnlyStringProperty idProperty()
	{
		return id.getReadOnlyProperty();
	}

	public ReadOnlyStringProperty nameProperty()
	{
		return name.getReadOnlyProperty();
	}

	public ReadOnlyStringProperty descriptionProperty()
	{
		return description.getReadOnlyProperty();
	}

}
