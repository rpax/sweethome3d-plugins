package com.massisframework.sweethome3d.plugins;

import java.util.ArrayList;
import java.util.List;

import com.eteks.sweethome3d.plugin.Plugin;
import com.eteks.sweethome3d.plugin.PluginAction;
import com.eteks.sweethome3d.plugin.SweetHome3DWithPlugins;

public class ComponentPlugin extends Plugin {

	@Override
	public PluginAction[] getActions() {
		return new PluginAction[] { new ComponentPluginAction(this) };
	}

	public static void main(String[] args) {

		
		List<Class<? extends Plugin>> plugins = new ArrayList<>();
		plugins.add(ComponentPlugin.class);
		SweetHome3DWithPlugins.run(args,plugins);
	}
}
