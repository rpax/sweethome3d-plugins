package com.massisframework.sweethome3d.plugins.components.fx;

import java.util.HashMap;
import java.util.Map;

import com.massisframework.sweethome3d.javafx.AbstractJFXController;
import com.massisframework.sweethome3d.javafx.FXHome;
import com.massisframework.sweethome3d.javafx.FXHomeObject;
import com.massisframework.sweethome3d.javafx.JFXPanelFactory;
import com.massisframework.sweethome3d.javafx.properties.MetadataSection;

import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class ComponentInfoController extends AbstractJFXController {
	@FXML
	private AnchorPane componentsSectionPane;
	private Map<MetadataSection, ComponentInfoTableController> sectionControllers;
	private FXHome home;
	private FXHomeObject<?> currentObj;
	private ListChangeListener<MetadataSection> changeListener;
	private ListChangeListener<FXHomeObject<?>> selectionListener;

	public ComponentInfoController()
	{
		this.sectionControllers = new HashMap<>();
	}

	@FXML
	public void initialize()
	{
		this.changeListener = (ListChangeListener<MetadataSection>) c -> {
			// Y si iteramos por todos y los ponemos como toca?
			int from = c.getFrom();
			int to = c.getTo();
			c.getRemoved().forEach(s -> {
				removePane(s);
			});
			c.getAddedSubList().forEach(s -> {
				addPane(s);
			});
			if (c.wasUpdated())
			{

			}
		};
		this.selectionListener = (ListChangeListener<FXHomeObject<?>>) c -> {
			if (c.getList().size() == 1)
			{
				setFXHomeObject(c.getList().get(0));
			} else
			{
				clear();
			}
		};
	}

	public void setFXHome(FXHome home)
	{
		this.home = home;
		home.selectedItemsProperty().addListener(this.selectionListener);
	}

	private void clear()
	{
		this.sectionControllers.clear();
		this.componentsSectionPane.getChildren().clear();
		if (this.currentObj != null)
		{
			this.currentObj.getMetadata().sectionsProperty()
					.removeListener(this.changeListener);
		}
		this.currentObj = null;
	}

	private void setFXHomeObject(FXHomeObject<?> obj)
	{
		this.clear();
		this.currentObj = obj;
		this.currentObj.getMetadata().sectionsProperty()
				.forEach(s -> addPane(s));
		this.currentObj.getMetadata().sectionsProperty()
				.addListener(this.changeListener);

	}

	private void addPane(MetadataSection section)
	{
		ComponentInfoTableController controller = this.sectionControllers
				.get(section);
		if (controller == null)
		{
			try
			{
				ComponentInfoTableController c = (ComponentInfoTableController) JFXPanelFactory
						.loadController(getClass()
								.getResource("ComponentInfoTitledPane.fxml"));
				c.setSection(section);
				this.sectionControllers.put(section, c);
				this.componentsSectionPane.getChildren().add(c.getRoot());
				AnchorPane.setBottomAnchor(c.getRoot(), 0.0);
				AnchorPane.setTopAnchor(c.getRoot(), 0.0);
				AnchorPane.setLeftAnchor(c.getRoot(), 0.0);
				AnchorPane.setRightAnchor(c.getRoot(), 0.0);
			} catch (Exception e)
			{
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
	}

	private void removePane(MetadataSection s)
	{
		ComponentInfoTableController controller = this.sectionControllers
				.remove(s);
		if (controller != null)
		{
			this.componentsSectionPane.getChildren()
					.remove(controller.getRoot());
		}
	}
}
