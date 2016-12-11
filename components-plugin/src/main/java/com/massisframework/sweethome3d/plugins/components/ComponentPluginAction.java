package com.massisframework.sweethome3d.plugins.components;

import java.awt.Container;
import java.awt.EventQueue;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.metawidget.swing.SwingMetawidget;
import org.metawidget.swing.widgetprocessor.binding.beansbinding.BeansBindingProcessor;
import org.metawidget.swing.widgetprocessor.binding.beansbinding.BeansBindingProcessorConfig;

import com.eteks.sweethome3d.model.Home;
import com.eteks.sweethome3d.model.HomePieceOfFurniture;
import com.eteks.sweethome3d.model.SelectionEvent;
import com.eteks.sweethome3d.model.SelectionListener;
import com.eteks.sweethome3d.plugin.PluginAction;
import com.eteks.sweethome3d.swing.HomePane;
import com.massisframework.sweethome3d.plugins.components.DataTypeEditor.SaveCallBack;

public class ComponentPluginAction extends PluginAction {

	private ComponentPlugin plugin;
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
		this.homePane = (HomePane) plugin.getHomeController().getView();
		this.home = this.plugin.getHome();
		this.addSidePane();
		DataTypeEditor.createDataTypeEditor(null, new SaveCallBack() {
			@Override
			public void onSave(DataTypeSchema values) {
				System.out.println(values);
				//SchemaManager
				
			}
			@Override
			public void onCancel() {
				System.out.println("Cancelled");
			}
		});
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

							HomePieceOfFurniture selected = (HomePieceOfFurniture) selectionEvent.getSelectedItems()
									.get(0);
							// metawidget.setMaximumInspectionDepth(1);
							metawidget.setToInspect(Location.getLocationOf(selected));
							// metawidget.setInspector(
							// new CompositeInspector(
							// new CompositeInspectorConfig()
							// .setInspectors(
							// new PropertyTypeInspector(),
							// new MetawidgetAnnotationInspector())));
							metawidget.addWidgetProcessor(new BeansBindingProcessor(
									new BeansBindingProcessorConfig().setUpdateStrategy(UpdateStrategy.READ_WRITE)));

						}
					}
				});

			}
		});

	}
}
