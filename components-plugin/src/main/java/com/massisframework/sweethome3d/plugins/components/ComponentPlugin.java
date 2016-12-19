package com.massisframework.sweethome3d.plugins.components;

import java.util.ArrayList;
import java.util.List;

import com.eteks.sweethome3d.SweetHome3DWithPlugins;
import com.eteks.sweethome3d.plugin.Plugin;
import com.eteks.sweethome3d.plugin.PluginAction;
import com.massisframework.sweethome3d.plugins.MassisPlugin;

public class ComponentPlugin extends Plugin implements MassisPlugin {

	private ComponentPluginAction action;
	private boolean homeReady;

	public ComponentPlugin()
	{
		this.homeReady = false;
	}

	@Override
	public void onHomeReady()
	{
		this.action.onHomeReady();
		this.homeReady = true;
	}

	@Override
	public PluginAction[] getActions()
	{
		if (this.action == null)
		{
			this.action = new ComponentPluginAction(this);
		}
		if (this.homeReady)
		{
			this.action.onHomeReady();
		}
		return new PluginAction[] { this.action };
	}

	public static void main(String[] args)
	{

		List<Class<? extends Plugin>> plugins = new ArrayList<>();
		plugins.add(ComponentPlugin.class);
		SweetHome3DWithPlugins.run(args, plugins);

	}

}
