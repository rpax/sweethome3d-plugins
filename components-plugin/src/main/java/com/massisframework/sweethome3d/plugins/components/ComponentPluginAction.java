package com.massisframework.sweethome3d.plugins.components;

import java.awt.Container;
import java.net.URL;
import java.util.concurrent.Future;

import javax.swing.JComponent;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;

import com.eteks.sweethome3d.plugin.PluginAction;
import com.eteks.sweethome3d.swing.HomePane;
import com.eteks.sweethome3d.viewcontroller.HomeController;
import com.massisframework.sweethome3d.javafx.FXHome;
import com.massisframework.sweethome3d.javafx.JFXPanelFactory;
import com.massisframework.sweethome3d.plugins.MassisPlugin;
import com.massisframework.sweethome3d.plugins.components.fx.ComponentInfoController;

public class ComponentPluginAction extends PluginAction {

	private ComponentPlugin plugin;
	private FXHome home;
	private HomeController homeController;

	public ComponentPluginAction(ComponentPlugin plugin)
	{
		this.plugin = plugin;
		this.home = new FXHome(plugin.getHome());
		putPropertyValue(Property.MENU, "Tests");
		putPropertyValue(Property.NAME, "Component plugin");
		this.setEnabled(true);
		this.homeController = plugin.getHomeController();
	}

	@Override
	public void execute()
	{

	}

	public void onHomeReady()
	{
		addSidePane();
	}

	private void addSidePane()
	{
		try
		{

			// try{
			URL fxmlLocation = ComponentInfoController.class
					.getResource("ComponentInfoPanel.fxml");

			JComponent sidePane = null;
			Future<JComponent> future = JFXPanelFactory
					.wrapInFXPanel(fxmlLocation);
			sidePane = future.get();

			HomePane homePane = (HomePane) homeController.getView();
			Container contentPane = homePane.getContentPane();
			Container mainPane = (Container) contentPane
					.getComponent(1);
			JComponent furniturePane = (JComponent) mainPane
					.getComponent(0);
			JComponent tablePane = (JComponent) furniturePane
					.getComponent(1);
			furniturePane.remove(tablePane);
			final JSplitPane splitPane = new JSplitPane(
					JSplitPane.VERTICAL_SPLIT,
					tablePane,
					sidePane);
			furniturePane.add(splitPane, 1);
			splitPane.setDividerLocation(150);
			// home.selectedItemsProperty().addListener(
			// (ListChangeListener<FXHomeObject<?>>) c -> {
			// System.out.println("Selection changed! "
			// + getClass() + " - " + c);
			// if (c.getList().size() == 1)
			// {
			// controller.setFXHomeObject(c.getList().get(0));
			// }
			// });

		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// cip.revalidate();
	}

}
