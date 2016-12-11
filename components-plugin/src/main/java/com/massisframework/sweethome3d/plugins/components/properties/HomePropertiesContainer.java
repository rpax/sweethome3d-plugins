package com.massisframework.sweethome3d.plugins.components.properties;

import com.eteks.sweethome3d.model.Home;

public class HomePropertiesContainer implements PropertiesContainer {

	private Home home;

	public HomePropertiesContainer(Home home) {
		this.home = home;
	}

	@Override
	public String getProperty(String name) {
		return this.home.getProperty(name);
	}

	@Override
	public void setProperty(String name, String value) {
		this.home.setProperty(name, value);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((home == null) ? 0 : home.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HomePropertiesContainer other = (HomePropertiesContainer) obj;
		if (home == null) {
			if (other.home != null)
				return false;
		} else if (!home.equals(other.home))
			return false;
		return true;
	}

}
