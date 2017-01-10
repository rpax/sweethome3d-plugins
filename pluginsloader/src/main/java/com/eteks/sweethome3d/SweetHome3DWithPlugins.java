/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eteks.sweethome3d;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.eteks.sweethome3d.io.FileUserPreferences;
import com.eteks.sweethome3d.model.Home;
import com.eteks.sweethome3d.model.UserPreferences;
import com.eteks.sweethome3d.plugin.DynamicPluginManager;
import com.eteks.sweethome3d.plugin.HomePluginController;
import com.eteks.sweethome3d.plugin.Plugin;
import com.eteks.sweethome3d.plugin.PluginManager;
import com.massisframework.sweethome3d.plugins.HomeReadyListener;

/**
 * @author Rafael Pax
 */
public class SweetHome3DWithPlugins extends SweetHome3D {

	static
	{
		System.setProperty("com.eteks.sweethome3d.j3d.useOffScreen3DView",
				"true");
	}
	/*
	 * Harcoded in parent, too
	 */
	private static final String APPLICATION_PLUGINS_SUB_FOLDER = "plugins";
	private final List<Class<? extends Plugin>> dynamicPlugins;
	private boolean pluginManagerInitialized = false;

	public static void run(String[] args,
			List<Class<? extends Plugin>> dynamicPlugins)
	{
		new SweetHome3DWithPlugins(dynamicPlugins).init(args);
	}

	public static void main(String[] args)
	{
		new SweetHome3DWithPlugins().init(args);
	}

	public SweetHome3DWithPlugins()
	{
		this(Collections.emptyList());
	}

	public SweetHome3DWithPlugins(List<Class<? extends Plugin>> dynamicPlugins)
	{
		super();
		this.dynamicPlugins = dynamicPlugins;
	}

	private void postHomeReady(Home home)
	{

		getLoadedPlugins(home).stream()
				.filter(p -> (p instanceof HomeReadyListener))
				.map(HomeReadyListener.class::cast)
				.forEach(HomeReadyListener::onHomeReady);

	}

	//
	private List<Plugin> getLoadedPlugins(Home home)
	{
		HomeFrameController hfc = this.getHomeFrameController(home);
		HomePluginController hpc = ((HomePluginController) hfc
				.getHomeController());
		return hpc.getPlugins();
	}

	@Override
	protected PluginManager getPluginManager()
	{

		if (!this.pluginManagerInitialized)
		{

			try
			{
				UserPreferences userPreferences = getUserPreferences();
				if (userPreferences instanceof FileUserPreferences)
				{
					File[] applicationPluginsFolders = ((FileUserPreferences) userPreferences)
							.getApplicationSubfolders(
									APPLICATION_PLUGINS_SUB_FOLDER);

					// Access parent pluginManager by reflection
					Field f = SweetHome3D.class
							.getDeclaredField("pluginManager");
					f.setAccessible(true);
					f.set(this, new DynamicPluginManager(
							applicationPluginsFolders, this.dynamicPlugins));
				}
			} catch (IOException ex)
			{
			} catch (NoSuchFieldException | SecurityException ex)
			{
				Logger.getLogger(SweetHome3DWithPlugins.class.getName())
						.log(Level.SEVERE, null, ex);
			} catch (IllegalArgumentException | IllegalAccessException ex)
			{
				Logger.getLogger(SweetHome3DWithPlugins.class.getName())
						.log(Level.SEVERE, null, ex);
			}
			this.pluginManagerInitialized = true;
		}
		return getPluginManagerFieldValue();
	}

	private PluginManager getPluginManagerFieldValue()
	{
		try
		{
			Field f = SweetHome3D.class.getDeclaredField("pluginManager");
			f.setAccessible(true);
			return (PluginManager) f.get(this);
		} catch (NoSuchFieldException | SecurityException
				| IllegalArgumentException | IllegalAccessException ex)
		{
			Logger.getLogger(SweetHome3DWithPlugins.class.getName())
					.log(Level.SEVERE, null, ex);
		}
		return null;
	}

	@Override
	protected void start(String[] args)
	{
		super.start(args);
		Home firstHome = this.getHomes().get(0);
		firstHome.setModified(!firstHome.isModified());
		firstHome.setModified(!firstHome.isModified());
		this.getHomes().forEach(this::postHomeReady);
		this.addHomesListener(ev -> {
			final Home home = ev.getItem();
			switch (ev.getType())
			{
			case ADD:
				postHomeReady(home);
				if (firstHome.getName() == null
						&& !firstHome.isRecovered()
						&& !firstHome.isModified())
				{
					this.deleteHome(firstHome);
				}
				break;
			case DELETE:
				break;
			}

		});

	}

}