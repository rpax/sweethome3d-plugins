package com.massisframework.sweethome3d.plugins.components;

import com.eteks.sweethome3d.plugin.PluginAction;
import com.eteks.sweethome3d.viewcontroller.HomeController;
import com.massisframework.sweethome3d.javafx.FXHome;
import com.massisframework.sweethome3d.plugins.HomeReadyListener;

public class ComponentDesignerAction extends PluginAction
		implements HomeReadyListener {

	private FXHome home;
	private HomeController homeController;

	public ComponentDesignerAction(ComponentPlugin plugin)
	{
		this.home = new FXHome(plugin.getHome());
		this.setEnabled(true);
		this.homeController = plugin.getHomeController();
	}

	@Override
	public void onHomeReady()
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void execute()
	{
		
	}

}
