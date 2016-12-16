package com.massisframework.sweethome3d.plugins.components;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.massisframework.sweethome3d.plugins.components.properties.MassisProperty;

public class MetadataComponent implements MassisProperty {

	private Map<String, String> values;

	public MetadataComponent()
	{
		this.values = new HashMap<>();
	}

	public void set(String k, String v)
	{
		this.values.put(k, v);
	}

	public String get(String k)
	{
		return this.values.get(k);
	}

	public Set<String> getKeys()
	{
		return this.values.keySet();
	}

	public String remove(String key)
	{
		return this.values.remove(key);
	}

}
