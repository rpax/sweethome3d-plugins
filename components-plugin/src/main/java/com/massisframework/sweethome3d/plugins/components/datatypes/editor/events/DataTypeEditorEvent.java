package com.massisframework.sweethome3d.plugins.components.datatypes.editor.events;

import com.massisframework.sweethome3d.plugins.PluginEvent;
import com.massisframework.sweethome3d.plugins.components.datatypes.DataTypeSchema;

public class DataTypeEditorEvent implements PluginEvent {
	private final DataTypeSchema dataType;
	private final EventType eventType;

	public static enum EventType {
		CHANGED, DELETED,
	}

	public DataTypeEditorEvent(DataTypeSchema dataType, DataTypeEditorEvent.EventType eventType) {
		this.dataType = dataType;
		this.eventType = eventType;
	}


	public DataTypeSchema getDataType() {
		return dataType;
	}

	public EventType getEventType() {
		return eventType;
	}
}
