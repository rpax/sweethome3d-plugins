package com.massisframework.sweethome3d.plugins.components;

import static org.metawidget.inspector.InspectionResultConstants.ENTITY;
import static org.metawidget.inspector.InspectionResultConstants.NAME;
import static org.metawidget.inspector.InspectionResultConstants.NAMESPACE;
import static org.metawidget.inspector.InspectionResultConstants.PROPERTY;
import static org.metawidget.inspector.InspectionResultConstants.ROOT;
import static org.metawidget.inspector.InspectionResultConstants.TYPE;

import java.util.ArrayList;
import java.util.Map;

import javax.swing.JFrame;

import org.apache.commons.lang3.tuple.Pair;
import org.metawidget.inspector.iface.Inspector;
import org.metawidget.inspector.iface.InspectorException;
import org.metawidget.swing.SwingMetawidget;
import org.metawidget.util.CollectionUtils;
import org.metawidget.util.XmlUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class MapInspector implements Inspector {
	private Map<String, Map<String, Class<?>>> mObjectProperties = CollectionUtils.newHashMap();

	public static void main(String... args) {
		SwingMetawidget metawidget = new SwingMetawidget();
		metawidget.setInspector(new MapInspector());
		metawidget.setPath("Component");
		JFrame frame = new JFrame("Metawidget Tutorial");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(metawidget);
		frame.setSize(400, 210);
		frame.setVisible(true);
	}
	static class KVList extends ArrayList<Pair<String, String>>{
		
	}
	public MapInspector() {
		// Some dummy data
		Map<String, Class<?>> personProperties = CollectionUtils.newLinkedHashMap();
		personProperties.put("Component Name",    String.class);
		personProperties.put("Fields",     KVList.class);
		mObjectProperties.put("Component", personProperties);
		
//		new JsonSchemaInspector(new JsonInspectorConfig().setInputStream(new ByteArrayInputStream("{SCHEMA_CONTENTS}")));
	}

	public String inspect(Object toInspect, String type, String... names) throws InspectorException {
		Map<String, Class<?>> properties = mObjectProperties.get(type);

		if (properties == null)
			return null;

		try {
			Document document = XmlUtils.newDocument();
			Element elementRoot = document.createElementNS(NAMESPACE, ROOT);
			document.appendChild(elementRoot);

			Element elementEntity = document.createElementNS(NAMESPACE, ENTITY);
			elementEntity.setAttribute(TYPE, type);
			elementRoot.appendChild(elementEntity);

			for (Map.Entry<String, Class<?>> entry : properties.entrySet()) {
				Element element = document.createElementNS(NAMESPACE, PROPERTY);
				element.setAttribute(NAME, entry.getKey());
				element.setAttribute(TYPE, entry.getValue().getName());
				elementEntity.appendChild(element);

			}

			String dts = XmlUtils.documentToString(document, false);
			System.out.println(dts);
			return dts;
		} catch (Exception e) {
			throw InspectorException.newException(e);
		}
	}
}
