package com.massisframework.sweethome3d.javafx.properties;

import java.util.Objects;

import javafx.beans.Observable;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.ReadOnlyListWrapper;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;

public class MetadataSection {

	private final ReadOnlyListWrapper<MetadataEntry> attributes;
	private final ReadOnlyStringWrapper id;
	private final ReadOnlyStringWrapper description;
	private final ReadOnlyStringWrapper name;
	private boolean editable;

	public MetadataSection(String id)
	{
		this(id, null, null);
	}

	public MetadataSection(String id, String friendlyName)
	{
		this(id, friendlyName, null);
	}

	public MetadataSection(String id, String friendlyName, String description)
	{
		Objects.requireNonNull(id);
		friendlyName = friendlyName == null ? "" : friendlyName;
		description = description == null ? "" : description;
		this.id = new ReadOnlyStringWrapper(id);
		this.name = new ReadOnlyStringWrapper(friendlyName);
		this.description = new ReadOnlyStringWrapper(description);
		this.attributes = new ReadOnlyListWrapper<>(FXCollections
				.observableArrayList(p -> new Observable[] {
						p.getKeyProperty(),
						p.getValueProperty()
				}));
	}

	public ReadOnlyListProperty<MetadataEntry> attributesProperty()
	{
		return attributes.getReadOnlyProperty();
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

	public boolean addEntry(Enum<?> key, String value)
	{
		return addEntry(key.toString(), value);
	}

	public boolean addEntry(String key, String value)
	{
		if (this.attributes.get().stream()
				.anyMatch(m -> m.getKey().equals(key)))
		{
			return false;
		} else
		{
			this.attributes.add(new MetadataEntry(key, value));
			return true;
		}
	}

	public void setEditable(boolean b)
	{
		this.editable = false;
	}

	public boolean isEditable()
	{
		return editable;
	}
}
