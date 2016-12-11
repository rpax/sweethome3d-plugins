package com.massisframework.sweethome3d.plugins;

import java.util.HashMap;
import java.util.Map;

import com.eteks.sweethome3d.model.Home;
import com.google.common.eventbus.EventBus;

public class PluginEventBus {

	private EventBus eventBus = new EventBus("PluginEventBus");

	public PluginEventBus(Home home) {
		// ?
	}

	public void register(Object obj) {
		this.eventBus.register(obj);
	}

	public void unregister(Object obj) {
		this.eventBus.unregister(obj);
	}

	public void post(Object evt) {
		this.eventBus.post(evt);
	}

	private static Map<Home, PluginEventBus> eventBuses = new HashMap<>();

	public static synchronized PluginEventBus get(Home h) {
		PluginEventBus eb = eventBuses.get(h);
		if (eb == null) {
			eb = new PluginEventBus(h);
			eventBuses.put(h, eb);
		}
		return eb;

	}
}
