package com.massisframework.sweethome3d.plugins.components;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class ComponentInfoPanel extends JPanel {
	private JTable attributesTable;

	/**
	 * Create the panel.
	 */
	public ComponentInfoPanel()
	{

		this.attributesTable = new JTable();
		attributesTable.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
						"Value", "Field Name"
				}));
		JScrollPane scrollPane = new JScrollPane(this.attributesTable);
		JComboBox componentSelectCBox = new JComboBox();
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
				groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
								.addContainerGap()
								.addGroup(groupLayout
										.createParallelGroup(Alignment.TRAILING)
										.addComponent(scrollPane,
												Alignment.LEADING,
												GroupLayout.DEFAULT_SIZE, 128,
												Short.MAX_VALUE)
										.addComponent(componentSelectCBox,
												Alignment.LEADING, 0, 128,
												Short.MAX_VALUE))
								.addContainerGap()));
		groupLayout.setVerticalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
								.addContainerGap()
								.addComponent(componentSelectCBox,
										GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(scrollPane,
										GroupLayout.DEFAULT_SIZE, 240,
										Short.MAX_VALUE)
								.addContainerGap()));
		setLayout(groupLayout);

	}
}
