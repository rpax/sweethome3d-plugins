package com.massisframework.sweethome3d.plugins.components.fx;

import com.massisframework.sweethome3d.javafx.AbstractJFXController;
import com.massisframework.sweethome3d.javafx.properties.MetadataEntry;
import com.massisframework.sweethome3d.javafx.properties.MetadataSection;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ComponentInfoTableController extends AbstractJFXController {

	@FXML
	private TableView<MetadataEntry> metadataTable;
	@FXML
	private TableColumn<MetadataEntry, String> metadataKeys;
	@FXML
	private TableColumn<MetadataEntry, String> metadataValues;

	@FXML
	public void initialize()
	{
		// Observable home object
	}

	public void setSection(MetadataSection section)
	{
		this.metadataTable.setItems(section.attributesProperty());
		// Cell renderer
		this.metadataKeys.setCellValueFactory(
				param -> param.getValue().getKeyProperty());
		this.metadataValues.setCellValueFactory(
				param -> param.getValue().getValueProperty());
	}

}
