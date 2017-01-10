package com.massisframework.sweethome3d.plugins.components.fx;

import com.massisframework.sweethome3d.javafx.AbstractJFXController;
import com.massisframework.sweethome3d.javafx.properties.MapMetadataEntry;
import com.massisframework.sweethome3d.javafx.properties.MetadataEntry;
import com.massisframework.sweethome3d.javafx.properties.MetadataObject;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;

public class ComponentInfoTableController extends AbstractJFXController {

	@FXML
	private ScrollPane topScroll;
	@FXML
	private AnchorPane anchorPaneWrapper;
	@FXML
	private TitledPane titledPane;
	@FXML
	private TableView<MetadataEntry> metadataTable;
	@FXML
	private TableColumn<MapMetadataEntry, String> metadataKeys;
	@FXML
	private TableColumn<MapMetadataEntry, String> metadataValues;
	@FXML
	private ContextMenu contextMenu;
	private MetadataObject section;

	@FXML
	public void initialize()
	{
		this.titledPane.setExpanded(true);
		// Observable home object
		this.metadataTable.setEditable(true);
		this.metadataTable.setFixedCellSize(30);

	}

	public void addItem(ActionEvent t)
	{
		TextInputDialog dialog = new TextInputDialog(
				"Please, enter the key name");
		dialog.setTitle("Please, enter the key name");
		dialog.setHeaderText("Please, enter the key name");
		dialog.setContentText("KEY");
		dialog.showAndWait()
				.filter(name -> !name.isEmpty())
				.ifPresent(name -> {
					this.section.put(name, "value");
				});
		this.metadataTable.autosize();
	}

	public void deleteItem(ActionEvent t)
	{
		int index = this.metadataTable.getSelectionModel().getSelectedIndex();
		if (index > -1)
		{
			this.metadataTable.getItems().remove(index);
		}
	}

	public void setSection(MetadataObject section)
	{
		this.section = section;
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
