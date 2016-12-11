package com.massisframework.sweethome3d.plugins.components.datatypes.editor;

import com.eteks.sweethome3d.model.Home;
import com.eteks.sweethome3d.plugin.PluginAction;
import com.google.common.eventbus.Subscribe;
import com.massisframework.sweethome3d.plugins.PluginEventBus;
import com.massisframework.sweethome3d.plugins.components.ComponentPlugin;
import com.massisframework.sweethome3d.plugins.components.datatypes.JsonSchemas;
import com.massisframework.sweethome3d.plugins.components.datatypes.editor.events.DataTypeEditorEvent;
import com.massisframework.sweethome3d.plugins.components.properties.MassisPropertyManager;

public class DataTypeEditorAction extends PluginAction {

	private ComponentPlugin plugin;
	private Home home;
	private PluginEventBus eventBus;

	public DataTypeEditorAction(ComponentPlugin plugin) {
		this.plugin = plugin;
		this.home = this.plugin.getHome();
		this.eventBus = PluginEventBus.get(this.home);
		putPropertyValue(Property.MENU, "Tools");
		putPropertyValue(Property.NAME, "Data Type Editor");
		this.setEnabled(true);
		this.eventBus.register(this);
	}

	@Override
	public void execute() {
		DataTypeEditor.createDataTypeEditor(getSchemas(), this.eventBus);
	}

	private JsonSchemas getSchemas() {
		JsonSchemas schemas= MassisPropertyManager.getProperty(this.home, "SCHEMAS", JsonSchemas.class);
		System.out.println("SCHEMAS: "+schemas);
		return schemas;
	}

	@Subscribe
	public void onDataTypesChanged(DataTypeEditorEvent evt) {
		JsonSchemas sch = getSchemas();
		System.out.println("EVENT! "+evt);
		switch (evt.getEventType()) {
		case CHANGED:
			sch.addOrReplace(evt.getDataType());
			MassisPropertyManager.setProperty(this.home, "SCHEMAS", sch);
			break;
		case DELETED:
			sch.removeSchema(evt.getDataType());
			MassisPropertyManager.setProperty(this.home, "SCHEMAS", sch);
			break;
		default:
			break;
		}
	}

}
