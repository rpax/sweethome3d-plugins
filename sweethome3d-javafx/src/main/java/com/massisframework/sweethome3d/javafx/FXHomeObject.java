package com.massisframework.sweethome3d.javafx;

import java.beans.PropertyChangeListener;

import com.eteks.sweethome3d.model.HomeObject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public abstract class FXHomeObject<HO extends HomeObject>
		implements PropertyChangeListener {

	protected final HO piece;
	protected ObservableList<HomeObjectProperty> properties;

	public FXHomeObject(HO piece)
	{
		this.piece = piece;
		this.properties = FXCollections.observableArrayList();
		this.updateProperties();
	}

	private void updateProperties()
	{

		for (String name : piece.getPropertyNames())
		{
			this.properties
					.add(new HomeObjectProperty(name, piece.getProperty(name)));
		}

	}

	public HO getPiece()
	{
		return piece;
	}

	public ObservableList<HomeObjectProperty> getProperties()
	{
		return properties;
	}

	public static class HomeObjectProperty {
		private String key;
		private String value;

		public HomeObjectProperty(String key, String value)
		{
			super();
			this.key = key;
			this.value = value;
		}

		public String getKey()
		{
			return key;
		}

		public void setKey(String key)
		{
			this.key = key;
		}

		public String getValue()
		{
			return value;
		}

		public void setValue(String value)
		{
			this.value = value;
		}
	}

	protected void forceUpdate()
	{
		this.updateProperties();
	}
}
