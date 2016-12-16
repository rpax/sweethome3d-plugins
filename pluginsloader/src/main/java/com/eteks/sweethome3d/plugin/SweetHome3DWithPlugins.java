/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eteks.sweethome3d.plugin;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.undo.UndoableEditSupport;

import com.eteks.sweethome3d.SweetHome3D;
import com.eteks.sweethome3d.io.FileUserPreferences;
import com.eteks.sweethome3d.model.CollectionEvent;
import com.eteks.sweethome3d.model.CollectionListener;
import com.eteks.sweethome3d.model.Home;
import com.eteks.sweethome3d.model.HomeApplication;
import com.eteks.sweethome3d.model.Library;
import com.eteks.sweethome3d.model.UserPreferences;
import com.eteks.sweethome3d.viewcontroller.HomeController;

/**
 * @author Rafael Pax
 */
public class SweetHome3DWithPlugins extends SweetHome3D {

	static{
		System.setProperty("com.eteks.sweethome3d.j3d.useOffScreen3DView","true");
	}
	/*
	 * Harcoded in parent, too
	 */
	private static final String APPLICATION_PLUGINS_SUB_FOLDER = "plugins";
	private final List<Class<? extends Plugin>> dynamicPlugins;
	private boolean pluginManagerInitialized = false;

	public static void run(String[] args, List<Class<? extends Plugin>> dynamicPlugins) {
		new SweetHome3DWithPlugins(dynamicPlugins).init(args);
	}

	public static void main(String[] args) {
		new SweetHome3DWithPlugins().init(args);
	}

	public SweetHome3DWithPlugins(List<Class<? extends Plugin>> dynamicPlugins) {
		super();
		this.dynamicPlugins = dynamicPlugins;

	}

	public SweetHome3DWithPlugins() {
		super();
		this.dynamicPlugins = Collections.emptyList();
	}

	@Override
	protected PluginManager getPluginManager() {

		if (!this.pluginManagerInitialized) {

			try {
				UserPreferences userPreferences = getUserPreferences();
				if (userPreferences instanceof FileUserPreferences) {
					File[] applicationPluginsFolders = ((FileUserPreferences) userPreferences)
							.getApplicationSubfolders(APPLICATION_PLUGINS_SUB_FOLDER);

					// Access parent pluginManager by reflection
					Field f = SweetHome3D.class.getDeclaredField("pluginManager");
					f.setAccessible(true);

					f.set(this, new DynamicPluginManager(applicationPluginsFolders, this.dynamicPlugins));
				}
			} catch (IOException ex) {
			} catch (NoSuchFieldException | SecurityException ex) {
				Logger.getLogger(SweetHome3DWithPlugins.class.getName()).log(Level.SEVERE, null, ex);
			} catch (IllegalArgumentException | IllegalAccessException ex) {
				Logger.getLogger(SweetHome3DWithPlugins.class.getName()).log(Level.SEVERE, null, ex);
			}
			this.pluginManagerInitialized = true;
		}
		return getPluginManagerFieldValue();
	}

	private PluginManager getPluginManagerFieldValue() {
		try {
			Field f = SweetHome3D.class.getDeclaredField("pluginManager");
			f.setAccessible(true);
			return (PluginManager) f.get(this);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) {
			Logger.getLogger(SweetHome3DWithPlugins.class.getName()).log(Level.SEVERE, null, ex);
		}
		return null;
	}

	private static class DynamicPluginManager extends PluginManager {

		private List<DynamicPluginLibrary> dynamicPluginLibs = new ArrayList<>();
		private final Map<Home, List<Plugin>> dynamicHomePlugins = new LinkedHashMap<>();

		@SuppressWarnings("unused")
		public DynamicPluginManager(File pluginFolder, List<Class<? extends Plugin>> pluginClasses) {
			this(new File[] { pluginFolder }, pluginClasses);
		}

		public DynamicPluginManager(File[] pluginFolders, List<Class<? extends Plugin>> pluginClasses) {
			super(pluginFolders);
			this.loadPluginsFromClasses(pluginClasses);
		}

		public DynamicPluginManager(URL[] pluginUrls, List<Class<? extends Plugin>> pluginClasses) {
			super(pluginUrls);
			this.loadPluginsFromClasses(pluginClasses);
		}

		private void loadPluginsFromClasses(List<Class<? extends Plugin>> pluginClasses) {
			for (Class<? extends Plugin> pluginClass : pluginClasses) {
				System.err.println("loading " + pluginClass);
				this.dynamicPluginLibs.add(new DynamicPluginLibrary(pluginClass));
			}

		}

		@Override
		public List<Library> getPluginLibraries() {
			ArrayList<Library> libs = new ArrayList<>(super.getPluginLibraries());
			libs.addAll(this.dynamicPluginLibs);
			return libs;
		}

		@Override
		List<Plugin> getPlugins(HomeApplication application, Home home, UserPreferences preferences,
				HomeController homeController, UndoableEditSupport undoSupport) {
			ArrayList<Plugin> plugins = new ArrayList<>(
					super.getPlugins(application, home, preferences, homeController, undoSupport));
			plugins.addAll(this.getDynamicPlugins(application, home, preferences, homeController, undoSupport));
			return plugins;

		}

		private Collection<? extends Plugin> getDynamicPlugins(final HomeApplication application, final Home home,
				UserPreferences preferences, HomeController homeController, UndoableEditSupport undoSupport) {
			if (application.getHomes().contains(home)) {
				List<Plugin> plugins = this.dynamicHomePlugins.get(home);
				if (plugins == null) {
					plugins = new ArrayList<>();
					// Instantiate each plug-in class
					for (DynamicPluginLibrary pluginLibrary : this.dynamicPluginLibs) {
						try {
							Plugin plugin = pluginLibrary.getPluginClass().newInstance();
							plugin.setPluginClassLoader(pluginLibrary.getPluginClassLoader());
							plugin.setName(pluginLibrary.getName());
							plugin.setDescription(pluginLibrary.getDescription());
							plugin.setVersion(pluginLibrary.getVersion());
							plugin.setLicense(pluginLibrary.getLicense());
							plugin.setProvider(pluginLibrary.getProvider());
							plugin.setUserPreferences(preferences);
							plugin.setHome(home);
							plugin.setHomeController(homeController);
							plugin.setUndoableEditSupport(undoSupport);
							plugins.add(plugin);
						} catch (InstantiationException | IllegalAccessException ex) {
							// Shouldn't happen : plug-in class was checked
							// during
							// readPlugin call
							throw new RuntimeException(ex);
						}
					}

					plugins = Collections.unmodifiableList(plugins);
					this.dynamicHomePlugins.put(home, plugins);

					// Add a listener that will destroy all plug-ins when home
					// is
					// deleted
					application.addHomesListener(new CollectionListener<Home>() {
						@Override
						public void collectionChanged(CollectionEvent<Home> ev) {
							if (ev.getType() == CollectionEvent.Type.DELETE && ev.getItem() == home) {
								for (Plugin plugin : dynamicHomePlugins.get(home)) {
									plugin.destroy();
								}
								dynamicHomePlugins.remove(home);
								application.removeHomesListener(this);
							}
						}
					});
				}
				return plugins;
			} else {
				return Collections.emptyList();
			}
		}

		protected static class DynamicPluginLibrary implements Library {

			private final Class<? extends Plugin> pluginClass;
			private final String dynamicInfo;

			/**
			 * Creates plug-in properties from parameters.
			 */
			public DynamicPluginLibrary(Class<? extends Plugin> pluginClass) {
				this.pluginClass = pluginClass;
				this.dynamicInfo = this.pluginClass.getName() + "[DYNAMICALLY_LOADED]";
			}

			public Class<? extends Plugin> getPluginClass() {
				return this.pluginClass;
			}

			public ClassLoader getPluginClassLoader() {
				return this.pluginClass.getClassLoader();
			}

			@Override
			public String getType() {
				return PluginManager.PLUGIN_LIBRARY_TYPE;
			}

			@Override
			public String getLocation() {
				throw new UnsupportedOperationException("This plugin was loaded dynamically, it has no location.");
			}

			@Override
			public String getId() {
				return null;
			}

			@Override
			public String getName() {
				return this.dynamicInfo;
			}

			@Override
			public String getDescription() {
				return this.dynamicInfo;
			}

			@Override
			public String getVersion() {
				return this.dynamicInfo;
			}

			@Override
			public String getLicense() {
				return this.dynamicInfo;
			}

			@Override
			public String getProvider() {
				return this.dynamicInfo;
			}
		}
	}
}