package com.massisframework.sweethome3d.javafx.properties;

import java.util.Optional;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import javafx.beans.Observable;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.ReadOnlyListWrapper;
import javafx.collections.FXCollections;

public class HomeObjectMetadata {

	public static final long VERSION = 1000;

	private final ReadOnlyListWrapper<MetadataObject> sections;
	private static final String ATTRIBUTES_KEY = "attributes";
	private static final String ID_KEY = "id";
	private static final String NAME_KEY = "name";
	private static final String DESCRIPTION_KEY = "description";
	private static final String KEY_KEY = "key";
	private static final String VALUE_KEY = "value";

	private static final Gson GSON = new Gson();

	public HomeObjectMetadata()
	{
		this.sections = new ReadOnlyListWrapper<>(
				FXCollections.observableArrayList(p -> new Observable[] {
						p.idProperty(),
						p.nameProperty(),
						p.descriptionProperty(),
						p.entriesProperty()
				}));
	}

	public ReadOnlyListProperty<MetadataObject> sectionsProperty()
	{
		return this.sections.getReadOnlyProperty();
	}

	public MetadataObject addSection(MetadataObject section)
	{
		this.sections.add(section);
		return section;
	}


	public Optional<MetadataObject> getSection(String id)
	{
		return this.sections.stream()
				.filter(s -> s.getId().equals(id)).findAny();
	}

	public boolean removeSection(String id)
	{
		return this.sections.removeIf(s -> s.idProperty().get().equals(id));
	}

	private static MetadataObject deserializeSection(JsonElement json)
	{
		JsonObject obj = json.getAsJsonObject();
		String id = obj.get(ID_KEY).getAsString();
		String name = obj.get(NAME_KEY).getAsString();
		String description = obj.get(DESCRIPTION_KEY).getAsString();
		MapMetadataSection metadataSection = new MapMetadataSection(id, name,
				description);
		JsonArray attributes = obj.get(ATTRIBUTES_KEY).getAsJsonArray();
		for (JsonElement attr : attributes)
		{
			String key = attr.getAsJsonObject().get(KEY_KEY).getAsString();
			String value = attr.getAsJsonObject().get(VALUE_KEY).getAsString();
			metadataSection.put(key, value);
		}
		return metadataSection;
	}

	public static String toJson(HomeObjectMetadata metadata)
	{
		return GSON.toJson(toJsonArray(metadata));
	}

	public static HomeObjectMetadata fromJson(String json)
	{
		return fromJson(GSON.fromJson(json, JsonArray.class));
	}

	private static JsonArray toJsonArray(HomeObjectMetadata metadata)
	{
		JsonArray array = new JsonArray();
		for (MetadataObject section : metadata.sectionsProperty())
		{
			array.add(serialize(section));
		}
		return array;
	}

	private static HomeObjectMetadata fromJson(JsonElement json)
	{
		JsonArray array = json.getAsJsonArray();
		HomeObjectMetadata metadata = new HomeObjectMetadata();
		for (JsonElement section : array)
		{
			metadata.addSection(deserializeSection(section));
		}
		return metadata;
	}

	public static JsonObject serialize(MetadataObject metadataSection)
	{
		JsonObject obj = new JsonObject();
		obj.addProperty(ID_KEY, metadataSection.getId());
		obj.addProperty(NAME_KEY, metadataSection.nameProperty().get());
		obj.addProperty(DESCRIPTION_KEY,
				metadataSection.descriptionProperty().get());
		JsonArray attributes = new JsonArray();

		metadataSection.entriesProperty()
				.stream()
				.map(HomeObjectMetadata::serialize)
				.forEach(attributes::add);
		obj.add(ATTRIBUTES_KEY, attributes);
		return obj;
	}

	private static JsonObject serialize(MetadataEntry entry)
	{
		JsonObject obj = new JsonObject();
		obj.addProperty(KEY_KEY, entry.getKey());
		obj.addProperty(VALUE_KEY, entry.getValue());
		return obj;
	}

}
