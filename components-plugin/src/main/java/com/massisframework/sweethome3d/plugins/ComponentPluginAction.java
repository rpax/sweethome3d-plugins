package com.massisframework.sweethome3d.plugins;

import java.awt.Container;
import java.awt.EventQueue;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.Field;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.metawidget.inspector.annotation.MetawidgetAnnotationInspector;
import org.metawidget.inspector.beanvalidation.BeanValidationInspector;
import org.metawidget.inspector.composite.CompositeInspector;
import org.metawidget.inspector.composite.CompositeInspectorConfig;
import org.metawidget.inspector.propertytype.PropertyTypeInspector;
import org.metawidget.swing.SwingMetawidget;
import org.metawidget.swing.widgetbuilder.SwingWidgetBuilder;
import org.metawidget.swing.widgetprocessor.binding.beansbinding.BeansBindingProcessor;
import org.metawidget.swing.widgetprocessor.binding.beansbinding.BeansBindingProcessorConfig;
import org.metawidget.util.WidgetBuilderUtils;
import org.metawidget.widgetbuilder.iface.WidgetBuilder;

import com.eteks.sweethome3d.model.CollectionEvent;
import com.eteks.sweethome3d.model.CollectionEvent.Type;
import com.eteks.sweethome3d.model.CollectionListener;
import com.eteks.sweethome3d.model.Home;
import com.eteks.sweethome3d.model.HomePieceOfFurniture;
import com.eteks.sweethome3d.model.SelectionEvent;
import com.eteks.sweethome3d.model.SelectionListener;
import com.eteks.sweethome3d.plugin.PluginAction;
import com.eteks.sweethome3d.swing.HomePane;

public class ComponentPluginAction extends PluginAction {

	private ComponentPlugin plugin;
	private PropertyChangeListener clickListener;
	private HomePane homePane;
	private Home home;

	public ComponentPluginAction(ComponentPlugin plugin) {
		this.plugin = plugin;
		putPropertyValue(Property.MENU, "Tests");
		putPropertyValue(Property.NAME, "Component plugin");
		this.setEnabled(true);
	}

	@Override
	public void execute() {
		System.out.println("HELOU");
		this.clickListener = createClickListener();
		this.homePane = (HomePane) plugin.getHomeController().getView();
		this.home = this.plugin.getHome();
		this.addSidePane();
	}


	private void addSidePane() {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				// try{
				Container contentPane = homePane.getContentPane();
				Container mainPane = (Container) contentPane.getComponent(1);

				JComponent furniturePane = (JComponent) mainPane.getComponent(0);
				JComponent tablePane = (JComponent) furniturePane.getComponent(1);
				furniturePane.remove(tablePane);

				final SwingMetawidget metawidget = new SwingMetawidget();

				JScrollPane scp = new JScrollPane(metawidget);
				final JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, tablePane, scp);
				furniturePane.add(splitPane, 1);
				splitPane.setDividerLocation(150);
				home.addSelectionListener(new SelectionListener() {

					@Override
					public void selectionChanged(SelectionEvent selectionEvent) {
							if (!selectionEvent.getSelectedItems().isEmpty()) {

								HomePieceOfFurniture selected = (HomePieceOfFurniture) selectionEvent.getSelectedItems().get(0);
//								metawidget.setMaximumInspectionDepth(1);
								metawidget.setToInspect(Location.getLocationOf(selected));
//								metawidget.setInspector(
//										new CompositeInspector(
//										new CompositeInspectorConfig()
//										.setInspectors(
//												new PropertyTypeInspector(),
//												new MetawidgetAnnotationInspector())));
								metawidget
										.addWidgetProcessor(
												new BeansBindingProcessor(new BeansBindingProcessorConfig()
												.setUpdateStrategy(UpdateStrategy.READ_WRITE)));

							}
					}
				});

			}
		});

	}

	private PropertyChangeListener createClickListener() {
		return new FurnitureClickListener();
	}

}
