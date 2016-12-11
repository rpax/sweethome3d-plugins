package com.massisframework.sweethome3d.plugins.components;

import java.util.HashMap;
import java.util.Map;

import com.eteks.sweethome3d.model.Home;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MassisPropertyManager {

	private static Map<Home, MassisPropertyManager> managers = new HashMap<>();
	private static final ThreadLocal<Gson> gson_TL = new ThreadLocal<Gson>() {
		@Override
		protected Gson initialValue() {
			return new GsonBuilder().create();
		}
	};

	public static MassisPropertyManager get(Home home) {
		synchronized (home) {
			MassisPropertyManager manager = managers.get(home);
			if (manager == null) {
				manager = new MassisPropertyManager(home);
				managers.put(home, manager);
			}
			return manager;
		}
	}

	private Home home;

	private MassisPropertyManager(Home home) {
		this.home = home;
	}

	public void setProperty(String propertyName, MassisProperty val) {
		home.setProperty(propertyName, gson_TL.get().toJson(val));
	}

	public <T extends MassisProperty> T getProperty(String propertyName, Class<T> type) {
		return gson_TL.get().fromJson(this.home.getProperty(propertyName), type);
	}

}
