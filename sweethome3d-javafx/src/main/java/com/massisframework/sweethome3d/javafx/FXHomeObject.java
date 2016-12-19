package com.massisframework.sweethome3d.javafx;

import java.beans.PropertyChangeListener;

import com.eteks.sweethome3d.model.HomeObject;
import com.massisframework.sweethome3d.javafx.properties.HomeObjectMetadata;
import com.massisframework.sweethome3d.javafx.properties.KnownMetadataHeader;
import com.massisframework.sweethome3d.javafx.properties.MapMetadataSection;
import com.massisframework.sweethome3d.javafx.properties.MassisProperties;
import com.massisframework.sweethome3d.javafx.properties.MetadataObject;
import com.massisframework.sweethome3d.javafx.properties.MetadataWrapper;

import javafx.collections.ListChangeListener;

public abstract class FXHomeObject<HO extends HomeObject>
		implements PropertyChangeListener {


	protected final HO homeObject;
	protected HomeObjectMetadata metadata;
	private ListChangeListener<MetadataObject> metadataListener;
	private MetadataWrapper<MassisProperties> massisProperties;
	private static final String METADATA_KEY = "__MASSIS_METADATA";
	public static final String MAIN_PARAMS_KEY = "__MASSIS_MAIN_PARAMS";
	
	public FXHomeObject(HO piece)
	{
		this.homeObject = piece;
		this.loadProperties();
		this.metadataListener = c -> {
			System.out.println("Metadata changed!");
			this.saveProperties();};
		this.metadata.sectionsProperty().addListener(this.metadataListener);
	}

	private void saveProperties()
	{
		this.homeObject.setProperty(
				METADATA_KEY,
				HomeObjectMetadata.toJson(this.metadata));
	}

	private void loadProperties()
	{
		String json = this.homeObject.getProperty(METADATA_KEY);
		if (json == null)
		{
			this.metadata = new HomeObjectMetadata();
			this.massisProperties=new MetadataWrapper<>(KnownMetadataHeader.ID_VERSION, new MassisProperties());
			this.metadata.addSection(this.massisProperties.getMetadataObject());
			MapMetadataSection commonMet = new MapMetadataSection(KnownMetadataHeader.COMMON_METADATA);
			this.metadata.addSection(commonMet);
			
		} else
		{
			this.metadata = HomeObjectMetadata.fromJson(json);
		}
		this.saveProperties();
	}

	public HomeObjectMetadata getMetadata()
	{
		return this.metadata;
	}

	public HO getHomeObject()
	{
		return homeObject;
	}
	public MassisProperties getMassisProperties(){
		return this.massisProperties.getObject();
	}

}
