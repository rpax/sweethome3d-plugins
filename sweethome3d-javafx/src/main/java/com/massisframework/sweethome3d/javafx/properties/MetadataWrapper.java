package com.massisframework.sweethome3d.javafx.properties;

import java.lang.reflect.Field;

import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class MetadataWrapper<W> {

	private ChangeListener changeListener;
	private W obj;
	private MetadataObject metadataObject;

	public MetadataWrapper(MetadataObject mo, W obj)
	{
		this.obj = obj;
		this.metadataObject = mo;
		this.changeListener = createChangeListener();
		this.load();
		this.bindProperties();
	}

	public MetadataWrapper(MetadataObjectHeader header, W obj)
	{
		this(new MapMetadataSection(header), obj);
	}

	public MetadataWrapper(MetadataObject mo, Class<W> type)
	{
		this(mo, createInstance(type));

	}

	private static <T> T createInstance(Class<T> type)
	{
		try
		{
			return type.newInstance();
		} catch (InstantiationException | IllegalAccessException e)
		{
			throw new RuntimeException(e);
		}
	}

	private void load()
	{
		try
		{
			Field[] fields = obj.getClass().getDeclaredFields();
			for (Field field : fields)
			{
				field.setAccessible(true);
				if (ReadOnlyProperty.class.isAssignableFrom(field.getType()))
				{
					ReadOnlyProperty prop = (ReadOnlyProperty) field.get(obj);
					prop.addListener(this.changeListener);
					String key = prop.getName();
					String defVal = String.valueOf(prop.getValue());
					String val = metadataObject.getValue(key, defVal);
					metadataObject.put(key, val);
				}
			}
		} catch (IllegalArgumentException | IllegalAccessException e)
		{
			throw new RuntimeException(e);
		}

	}

	public void unbind()
	{
		try
		{
			Field[] fields = obj.getClass().getDeclaredFields();
			for (Field field : fields)
			{
				field.setAccessible(true);
				if (ReadOnlyProperty.class.isAssignableFrom(field.getType()))
				{
					ReadOnlyProperty prop = (ReadOnlyProperty) field.get(obj);
					prop.removeListener(this.changeListener);
				}
			}
		} catch (IllegalArgumentException | IllegalAccessException e)
		{
			throw new RuntimeException(e);
		}
	}

	private void bindProperties()
	{
		try
		{
			Field[] fields = obj.getClass().getDeclaredFields();
			for (Field field : fields)
			{
				field.setAccessible(true);
				if (ReadOnlyProperty.class.isAssignableFrom(field.getType()))
				{
					ReadOnlyProperty prop = (ReadOnlyProperty) field.get(obj);
					prop.addListener(this.changeListener);
				}
			}
		} catch (IllegalArgumentException | IllegalAccessException e)
		{
			throw new RuntimeException(e);
		}
	}

	public ChangeListener createChangeListener()
	{
		return new ChangeListener() {
			@Override
			public void changed(
					ObservableValue observable,
					Object oldValue,
					Object newValue)
			{
				ReadOnlyProperty property = (ReadOnlyProperty) observable;
				String key = property.getName();
				String value = String.valueOf(property.getValue());
				metadataObject.put(key, value);
			}
		};
	}

	public W getObject()
	{
		return this.obj;
	}

	public MetadataObject getMetadataObject()
	{
		return this.metadataObject;
	}

}
