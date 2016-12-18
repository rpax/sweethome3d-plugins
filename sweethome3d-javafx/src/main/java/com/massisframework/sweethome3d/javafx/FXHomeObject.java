package com.massisframework.sweethome3d.javafx;

import java.beans.PropertyChangeListener;
import java.util.UUID;

import com.eteks.sweethome3d.model.HomeObject;
import com.massisframework.sweethome3d.javafx.properties.HomeObjectMetadata;
import com.massisframework.sweethome3d.javafx.properties.MetadataSection;

import javafx.collections.ListChangeListener;

public abstract class FXHomeObject<HO extends HomeObject>
		implements PropertyChangeListener {

	public enum MainSectionKey {
		ID, VERSION;
	}

	protected final HO piece;
	protected HomeObjectMetadata metadata;
	private static final String METADATA_KEY = "__MASSIS_METADATA";
	public static final String MAIN_PARAMS_KEY = "__MASSIS_MAIN_PARAMS";

	public FXHomeObject(HO piece)
	{
		this.piece = piece;
		this.loadProperties();
		this.metadata.sectionsProperty()
				.addListener((ListChangeListener<MetadataSection>) c -> {
					this.saveProperties();
				});
	}

	private void saveProperties()
	{
		this.piece.setProperty(METADATA_KEY,
				HomeObjectMetadata.toJson(this.metadata));
	}

	private void loadProperties()
	{
		String json = this.piece.getProperty(METADATA_KEY);
		if (json == null)
		{
			this.metadata = new HomeObjectMetadata();
			MetadataSection defaultSection = new MetadataSection(
					MAIN_PARAMS_KEY);

			defaultSection.addEntry(MainSectionKey.ID,
					UUID.randomUUID().toString());

			defaultSection.addEntry(MainSectionKey.VERSION,
					String.valueOf(HomeObjectMetadata.VERSION));
			metadata.addSection(defaultSection);
		} else
		{
			// TODO check errors, version stuff
			this.metadata = HomeObjectMetadata.fromJson(json);
		}
	}

	public HO getPiece()
	{
		return piece;
	}

}
