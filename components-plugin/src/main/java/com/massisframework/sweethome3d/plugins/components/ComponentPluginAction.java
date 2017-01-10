package com.massisframework.sweethome3d.plugins.components;

import java.awt.Container;
import java.net.URL;

import javax.swing.JComponent;
import javax.swing.JSplitPane;

import com.eteks.sweethome3d.plugin.PluginAction;
import com.eteks.sweethome3d.swing.HomePane;
import com.eteks.sweethome3d.viewcontroller.HomeController;
import com.massisframework.sweethome3d.javafx.FXHome;
import com.massisframework.sweethome3d.javafx.JFXPanelFactory;
import com.massisframework.sweethome3d.plugins.HomeReadyListener;
import com.massisframework.sweethome3d.plugins.components.fx.ComponentInfoController;

public class ComponentPluginAction extends PluginAction
		implements HomeReadyListener {

	private FXHome home;
	private HomeController homeController;

	public ComponentPluginAction(ComponentPlugin plugin)
	{
		this.home = new FXHome(plugin.getHome());
		this.setEnabled(true);
		this.homeController = plugin.getHomeController();
	}

	@Override
	public void execute()
	{

	}

	@Override
	public void onHomeReady()
	{
		this.addSidePane();
	}

	private void addSidePane()
	{

		URL fxmlLocation = ComponentInfoController.class
				.getResource("ComponentInfoPanel.fxml");
		JFXPanelFactory.wrapInFXPanel(fxmlLocation,
				(sidePane, controller) -> {
					
					((ComponentInfoController) controller)
							.setFXHome(this.home);
					try
					{
						HomePane homePane = (HomePane) homeController
								.getView();
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

					} catch (Exception e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				});

		// cip.revalidate();
	}

}
