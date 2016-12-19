package com.massisframework.sweethome3d.javafx.properties;

public enum KnownMetadataHeader implements MetadataObjectHeader {

	ID_VERSION("_ID_VERSION", "MASSIS Common Data"), COMMON_METADATA(
			"_COMMON_METADATA", "Common metadata");

	private String name;
	private String description;
	private String id;

	KnownMetadataHeader(String id, String name)
	{
		this(id, name, "");
	}

	KnownMetadataHeader(String id, String name, String description)
	{
		this.id = id;
		this.name = name;
		this.description = description;
	}

	public String getId()
	{
		return id;
	}

	public String getName()
	{
		return name;
	}

	public String getDescription()
	{
		return description;
	}

}
