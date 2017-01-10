package com.massisframework.sweethome3d.plugins.components.fx;

import java.util.Optional;

import com.massisframework.sweethome3d.javafx.AbstractJFXController;
import com.massisframework.sweethome3d.javafx.properties.MapMetadataEntry;
import com.massisframework.sweethome3d.javafx.properties.MetadataEntry;
import com.massisframework.sweethome3d.javafx.properties.MetadataObject;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;

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
	private ContextMenu contextMenu;
	private MetadataObject section;

	@FXML
	public void initialize()
	{
		// Observable home object
		this.metadataTable.setEditable(true);
		this.metadataTable.setFixedCellSize(30);

		final TextField name = new TextField();

		final Label hello = new Label();
		Button ok = new Button("ok");
		Button cencel = new Button("cancel");

		VBox popUpVBox = new VBox();
		popUpVBox.getChildren().add(hello);
		popUpVBox.getChildren().add(name);
		popUpVBox.getChildren().add(ok);
		popUpVBox.getChildren().add(cencel);

		AnchorPane.setLeftAnchor(metadataTable, 0.0);
		AnchorPane.setRightAnchor(metadataTable, 0.0);
		AnchorPane.setLeftAnchor(titledPane, 0.0);
		AnchorPane.setRightAnchor(titledPane, 0.0);
	}

	public void addItem(ActionEvent t)
	{
		TextInputDialog dialog = new TextInputDialog(
				"Please, enter the key name");
		dialog.setTitle("Please, enter the key name");
		dialog.setHeaderText("Please, enter the key name");
		dialog.setContentText("");
		// Traditional way to get the response value.
		Optional<String> result = dialog.showAndWait();
		// The Java 8 way to get the response value (with lambda expression).
		result.ifPresent(name -> {
			if (!name.isEmpty())
			{
				this.section.put(name, "");
			}
		});
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
