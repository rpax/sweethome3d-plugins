package com.massisframework.sweethome3d.plugins.components.properties;

import com.eteks.sweethome3d.model.Home;
import com.eteks.sweethome3d.model.HomeObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.massisframework.sweethome3d.plugins.components.KnownKey;
import com.massisframework.sweethome3d.plugins.components.properties.HomeObjectPropertiesContainer;
import com.massisframework.sweethome3d.plugins.components.properties.HomePropertiesContainer;
import com.massisframework.sweethome3d.plugins.components.properties.PropertiesContainer;

public class MassisPropertyManager {

	private static Gson GSON = new GsonBuilder().create();

	public static <T extends MassisProperty> void setProperty(Home item, String name, T value) {
		setProperty(new HomePropertiesContainer(item), name, value);
	}

	public static <T extends MassisProperty> void setProperty(HomeObject item, String name, T value) {
		setProperty(new HomeObjectPropertiesContainer(item), name, value);
	}
	public static <T extends MassisProperty> void setProperty(HomeObject item, KnownKey key, T value) {
		setProperty(new HomeObjectPropertiesContainer(item), key.getKeyName(), value);
	}

	public static <T extends MassisProperty> T getProperty(Home item, String name, Class<T> type) {
		return getProperty(new HomePropertiesContainer(item), name, type);
	}
	public static <T extends MassisProperty> T getProperty(HomeObject item, KnownKey key, Class<T> type) {
		return getProperty(new HomeObjectPropertiesContainer(item), key.getKeyName(), type);
	}
	public static <T extends MassisProperty> T getProperty(HomeObject item, String name, Class<T> type) {
		return getProperty(new HomeObjectPropertiesContainer(item), name, type);
	}

	private static <T extends MassisProperty> T getProperty(PropertiesContainer item, String name, Class<T> type) {
		String jsonProperty = item.getProperty(name);
		T property = null;
		if (jsonProperty == null) {
			property = createProperty(type);
		} else {
			property = GSON.fromJson(jsonProperty, type);
		}
		return (T) property;
	}

	private static <T extends MassisProperty> void setProperty(PropertiesContainer item, String name, T value) {
		item.setProperty(name, GSON.toJson(value));
	}

	private static <T extends MassisProperty> T createProperty(Class<T> type) {
		try {
			return type.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

}
