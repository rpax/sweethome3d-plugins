package com.massisframework.sweethome3d.plugins.components.fx;

import com.massisframework.sweethome3d.javafx.AbstractJFXController;
import com.massisframework.sweethome3d.javafx.properties.MapMetadataEntry;
import com.massisframework.sweethome3d.javafx.properties.MetadataEntry;
import com.massisframework.sweethome3d.javafx.properties.MetadataObject;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;

public class ComponentInfoTableController extends AbstractJFXController {

	@FXML
	private TitledPane titledPane;
	@FXML
	private TableView<MetadataEntry> metadataTable;
	@FXML
	private TableColumn<MapMetadataEntry, String> metadataKeys;
	@FXML
	private TableColumn<MapMetadataEntry, String> metadataValues;

	@FXML
	public void initialize()
	{
		// Observable home object
		this.metadataTable.setEditable(true);
		this.metadataTable.setFixedCellSize(25);
		AnchorPane.setLeftAnchor(metadataTable, 0.0);
		AnchorPane.setRightAnchor(metadataTable, 0.0);
		AnchorPane.setLeftAnchor(titledPane, 0.0);
		AnchorPane.setRightAnchor(titledPane, 0.0);
	}

	public void setSection(MetadataObject section)
	{
		this.titledPane.setText(section.nameProperty().get());
		this.metadataTable.setItems(section.entriesProperty());
	
		metadataTable.prefHeightProperty()
				.bind(Bindings.size(metadataTable.getItems()).add(1)
						.multiply(metadataTable.getFixedCellSize()).add(30));
		// Cell renderer
		this.metadataKeys.setCellValueFactory(
				param -> param.getValue().getKeyProperty());
		metadataValues.setCellFactory(TextFieldTableCell.forTableColumn());
		metadataValues.setCellValueFactory(
				param -> param.getValue().getValueProperty());
		metadataValues.setOnEditCommit(event -> {
			event.getTableView().getItems()
					.get(event.getTablePosition().getRow())
					.setValue(event.getNewValue());
		});
		
		
	}

}
