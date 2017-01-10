package com.massisframework.sweethome3d.javafx.properties;

import java.util.UUID;

import com.massisframework.sweethome3d.javafx.FXHome;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class MassisProperties {

	private final StringProperty objectId;
	private final LongProperty version;

	public MassisProperties()
	{
		this.objectId = new SimpleStringProperty(null, "objectId",
				UUID.randomUUID().toString());
		this.version = new SimpleLongProperty(null, "version", FXHome.VERSION);
	}

	public StringProperty objectIdProperty()
	{
		return objectId;
	}

	public LongProperty versionProperty()
	{
		return version;
	}
}
