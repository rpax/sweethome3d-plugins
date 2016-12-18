package com.massisframework.sweethome3d.plugins.components.fx;

import com.eteks.sweethome3d.model.HomePieceOfFurniture;
import com.massisframework.sweethome3d.plugins.FXHomeObject;
import com.massisframework.sweethome3d.plugins.FXHomeObject.HomeObjectProperty;
import com.massisframework.sweethome3d.plugins.FXHomePieceOfFurniture;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ComponentInfoTableController {

	@FXML
	private TableView<HomeObjectProperty> metadataTable;
	@FXML
	private TableColumn<FXHomePieceOfFurniture, String> metadataKeys;
	@FXML
	private TableColumn<FXHomePieceOfFurniture, String> metadataValues;

	@FXML
	public void initialize()
	{
		// Observable home object
	}

	public void setHomeObject(HomePieceOfFurniture obj)
	{

	}

	public void setHomeObject(FXHomeObject<?> obj)
	{
		this.metadataTable.setItems(obj.getProperties());
	}
}
