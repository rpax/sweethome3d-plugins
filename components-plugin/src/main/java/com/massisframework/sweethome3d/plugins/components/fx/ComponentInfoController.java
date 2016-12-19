package com.massisframework.sweethome3d.plugins.components.fx;

import java.util.HashMap;
import java.util.Map;

import com.massisframework.sweethome3d.javafx.AbstractJFXController;
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

	public ComponentInfoController()
	{
		this.sectionControllers = new HashMap<>();
	}

	@FXML
	public void initialize()
	{
		System.out.println("initialized!");
	}

	public void setFXHomeObject(FXHomeObject<?> obj)
	{
		//
		// iterate over entries
		// Create Panes
		// ETC
		obj.getMetadata().sectionsProperty()
				.addListener((ListChangeListener<MetadataSection>) c -> {
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
						// for (int i = from; i < to; i++)
						// {
						// updatePane();
						// }
					}
				});

	}

	private void addPane(MetadataSection section)
	{
		ComponentInfoTableController controller = this.sectionControllers
				.get(section);
		if (controller == null)
		{

			try
			{
				ComponentInfoTableController c = (ComponentInfoTableController) JFXPanelFactory.loadController(
						getClass().getResource("ComponentInfoPanel.fxml"));
				c.setSection(section);
				this.sectionControllers.put(section, c);
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
