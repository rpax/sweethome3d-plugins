package com.massisframework.sweethome3d.plugins.components;

import java.util.ArrayList;
import java.util.List;

import com.eteks.sweethome3d.SweetHome3DWithPlugins;
import com.eteks.sweethome3d.plugin.Plugin;
import com.eteks.sweethome3d.plugin.PluginAction;
import com.massisframework.sweethome3d.plugins.HomeReadyListener;

public class ComponentPlugin extends Plugin implements HomeReadyListener {

	private PluginAction[] actions;
	private boolean homeReady;

	public ComponentPlugin()
	{
		this.homeReady = false;
	}

	@Override
	public void onHomeReady()
	{
		for (PluginAction pluginAction : actions)
		{
			if (pluginAction instanceof HomeReadyListener)
			{
				((HomeReadyListener) pluginAction).onHomeReady();
			}
		}
	}

	@Override
	public PluginAction[] getActions()
	{
		this.actions = new PluginAction[] { new ComponentPluginAction(this) };
		return this.actions;
	}

	public static void main(String[] args)
	{

		List<Class<? extends Plugin>> plugins = new ArrayList<>();
		plugins.add(ComponentPlugin.class);
		SweetHome3DWithPlugins.run(args, plugins);

	}

}
