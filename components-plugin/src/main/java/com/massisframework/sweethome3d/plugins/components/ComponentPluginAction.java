package com.massisframework.sweethome3d.plugins.components;

import java.awt.Container;
import java.awt.EventQueue;

import javax.swing.JComponent;
import javax.swing.JSplitPane;

import com.eteks.sweethome3d.model.Home;
import com.eteks.sweethome3d.model.HomeObject;
import com.eteks.sweethome3d.model.SelectionEvent;
import com.eteks.sweethome3d.model.SelectionListener;
import com.eteks.sweethome3d.plugin.PluginAction;
import com.eteks.sweethome3d.swing.HomePane;

public class ComponentPluginAction extends PluginAction {

	private ComponentPlugin plugin;
	private HomePane homePane;
	private Home home;

	public ComponentPluginAction(ComponentPlugin plugin)
	{
		this.plugin = plugin;
		putPropertyValue(Property.MENU, "Tests");
		putPropertyValue(Property.NAME, "Component plugin");
		this.setEnabled(true);
	}

	@Override
	public void execute()
	{
		this.homePane = (HomePane) plugin.getHomeController().getView();
		this.home = this.plugin.getHome();
		this.addSidePane();
		// Component table
	}

	private void addSidePane()
	{
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run()
			{
				// try{
				Container contentPane = homePane.getContentPane();
				Container mainPane = (Container) contentPane.getComponent(1);

				JComponent furniturePane = (JComponent) mainPane
						.getComponent(0);
				JComponent tablePane = (JComponent) furniturePane
						.getComponent(1);
				furniturePane.remove(tablePane);


				final JSplitPane splitPane = new JSplitPane(
						JSplitPane.VERTICAL_SPLIT, tablePane,new ComponentInfoPanel());
				furniturePane.add(splitPane, 1);
				splitPane.setDividerLocation(150);
				home.addSelectionListener(new SelectionListener() {

					@Override
					public void selectionChanged(SelectionEvent selectionEvent)
					{
						if (!selectionEvent.getSelectedItems().isEmpty())
						{
							HomeObject homeObject = (HomeObject) selectionEvent
									.getSelectedItems()
									.get(0);
							// componentTable.fillWith(homeObject);
						} else
						{
							// componentTable.clearItems();
						}
					}
				});

			}
		});

	}
}
