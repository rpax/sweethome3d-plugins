package com.massisframework.sweethome3d.plugins.components.properties;

import com.eteks.sweethome3d.model.HomeObject;

public class HomeObjectPropertiesContainer implements PropertiesContainer {

	private HomeObject homeObject;

	public HomeObjectPropertiesContainer(HomeObject homeObject) {
		this.homeObject = homeObject;
	}

	@Override
	public String getProperty(String name) {
		return this.homeObject.getProperty(name);
	}

	@Override
	public void setProperty(String name, String value) {
		this.homeObject.setProperty(name, value);
	}

}
