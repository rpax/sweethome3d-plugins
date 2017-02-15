package com.massisframework.sweethome3d.javafx;

import java.beans.PropertyChangeListener;
import java.util.Optional;

import com.eteks.sweethome3d.model.HomeObject;
import com.massisframework.sweethome3d.javafx.properties.HomeObjectMetadata;
import com.massisframework.sweethome3d.javafx.properties.KnownMetadataHeader;
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
	private static final String METADATA_KEY = "__MASSIS_METADATA_v"
			+ FXHome.VERSION;

	public FXHomeObject(HO piece)
	{
		this.homeObject = piece;
		this.loadProperties();
		this.metadataListener = c -> {
			System.out.println("Metadata changed!");
			this.saveProperties();
		};
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
//			MapMetadataSection commonMet = new MapMetadataSection(
//					KnownMetadataHeader.COMMON_METADATA);
//			this.metadata.addSection(commonMet);
			
			this.massisProperties = new MetadataWrapper<>(
					KnownMetadataHeader.ID_VERSION, new MassisProperties());
			this.metadata.addSection(this.massisProperties.getMetadataObject());

		} else
		{
			this.metadata = HomeObjectMetadata.fromJson(json);
			Optional<MetadataObject> section = this.metadata
					.getSection(KnownMetadataHeader.ID_VERSION.getId());
			if (section.isPresent())
			{
				this.massisProperties = new MetadataWrapper<>(section.get(),
						MassisProperties.class);
			} else
			{
				throw new RuntimeException(
						"not found " + KnownMetadataHeader.ID_VERSION.getId()
								+ " in : " + json);
			}
//			MapMetadataSection commonMet = new MapMetadataSection(
//					KnownMetadataHeader.COMMON_METADATA);
//			this.metadata.addSection(commonMet);
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

	public MassisProperties getMassisProperties()
	{
		return this.massisProperties.getObject();
	}

}
