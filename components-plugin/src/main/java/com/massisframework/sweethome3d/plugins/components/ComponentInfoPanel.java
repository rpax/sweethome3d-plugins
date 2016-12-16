package com.massisframework.sweethome3d.plugins.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.CellEditor;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import com.eteks.sweethome3d.model.HomeObject;
import com.massisframework.sweethome3d.plugins.components.properties.MassisPropertyManager;

@SuppressWarnings({ "serial", "rawtypes", "unchecked" })
public class ComponentInfoPanel extends JPanel {
	private JTable attributesTable;
	private List<HomeObject> homeObjects = new ArrayList<>();
	private Map<String, String> commonMetadata = new HashMap<>();

	public void setDataSource(Iterable<HomeObject> homeObjects)
	{
		this.homeObjects.clear();
		for (HomeObject ho : homeObjects)
		{
			this.homeObjects.add(ho);
		}
		commonMetadata.clear();
		for (HomeObject ho : homeObjects)
		{
			MetadataComponent metadata = getMetadataOf(ho);
			for (String key : metadata.getKeys())
			{
				String metaVal = metadata.get(key);
				String commonVal = commonMetadata.get(key);
				if (commonVal == null || commonVal.equals(metaVal))
				{
					commonMetadata.put(key, metaVal);
				} else
				{
					commonMetadata.put(key, "");
				}
			}
		}
		for (Entry<String, String> e : commonMetadata.entrySet())
		{
			this.addRow(e.getKey(), e.getValue());
		}
		repaintTable();
	}

	private void setMetadataOf(HomeObject ho, MetadataComponent mdc)
	{
		MassisPropertyManager.setProperty(ho, KnownKey.METADATA,
				mdc);
	}

	private MetadataComponent getMetadataOf(HomeObject ho)
	{
		MetadataComponent metadata = MassisPropertyManager.getProperty(
				ho, KnownKey.METADATA,
				MetadataComponent.class);
		return metadata;
	}

	private void repaintTable()
	{
		((DefaultTableModel) attributesTable.getModel()).fireTableDataChanged();
	}

	private void clearTable()
	{
		while (this.attributesTable.getRowCount() > 0)
		{
			((DefaultTableModel) this.attributesTable.getModel()).removeRow(0);
		}
	}

	private void addRow(String k, String v)
	{
		((DefaultTableModel) this.attributesTable.getModel())
				.addRow(new Object[] { k, v });

	}

	private void updateKeyName(String oldKey, String newKey)
	{
		for (HomeObject homeObject : homeObjects)
		{
			MetadataComponent metadata = getMetadataOf(homeObject);
			String oldValue = metadata.remove(oldKey);
			oldValue = oldValue == null ? "" : oldValue;
			metadata.set(newKey, oldValue);
			this.setMetadataOf(homeObject, metadata);
		}
	}

	private void updateValue(String key, String value)
	{
		for (HomeObject homeObject : homeObjects)
		{
			MetadataComponent metadata = getMetadataOf(homeObject);
			metadata.set(key, value);
			this.setMetadataOf(homeObject, metadata);
		}
	}

	private void removeEntry(String key)
	{
		for (HomeObject homeObject : homeObjects)
		{
			MetadataComponent metadata = getMetadataOf(homeObject);
			metadata.remove(key);
			this.setMetadataOf(homeObject, metadata);
		}
	}

	private void removeItem(int row)
	{
		for (HomeObject homeObject : homeObjects)
		{

			DefaultTableModel model = ((DefaultTableModel) attributesTable
					.getModel());
			String key = (String) this.attributesTable.getValueAt(row, 0);

			int resp = JOptionPane.showConfirmDialog(null,
					"Are you sure that you want to delete the field \"" + key
							+ "\"");
			model.removeRow(row);
			removeEntry(key);
			repaintTable();
		}
	}
	// Edit item???

	private void removeFieldPressed()
	{
		int row = this.attributesTable.getSelectedRow();
		if (row > 0)
		{
			this.removeItem(row);
		}
	}

	private void initEditorListeners()
	{
		this.attributesTable
				.setCellEditor(new DefaultCellEditor(new JTextField()));
		this.attributesTable
				.addPropertyChangeListener(new PropertyChangeListener() {
					private String oldVal = null;

					@Override
					public void propertyChange(PropertyChangeEvent evt)
					{
						if ("tableCellEditor".equals(evt.getPropertyName()))
						{
							if (attributesTable.isEditing())
							{
								oldVal=((JTextField) attributesTable.getEditorComponent()).getText();

							} else
							{

								String newVal = (String) ((CellEditor) evt
										.getOldValue()).getCellEditorValue();
								System.out.println("Changed from " + oldVal
										+ " to " + newVal);
								oldVal = null;
							}
						}

					}
				});

	}

	private void addFieldPressed()
	{

	}

	/**
	 * Create the panel.
	 */
	public ComponentInfoPanel()
	{

		this.attributesTable = new JTable();
		attributesTable.setModel(
				new DefaultTableModel(new Object[][] { { null, null }, },
						new String[] { "Field Name", "Value" }) {
					Class[] columnTypes = new Class[] { String.class,
							String.class };

					public Class getColumnClass(int columnIndex)
					{
						return columnTypes[columnIndex];
					}
				});

		JScrollPane scrollPane = new JScrollPane(this.attributesTable);
		JComboBox componentSelectCBox = new JComboBox();
		componentSelectCBox.setEnabled(false);
		componentSelectCBox.setModel(
				new DefaultComboBoxModel(new String[] { "METADATA" }));

		JButton btnAddField = new JButton("Add Field");
		btnAddField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				addFieldPressed();
			}
		});
		JButton removeFieldBtn = new JButton("Remove Field");
		removeFieldBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				removeFieldPressed();
			}
		});
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
				groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
								.addContainerGap()
								.addGroup(groupLayout
										.createParallelGroup(Alignment.TRAILING)
										.addComponent(scrollPane,
												GroupLayout.DEFAULT_SIZE, 426,
												Short.MAX_VALUE)
										.addComponent(componentSelectCBox, 0,
												426, Short.MAX_VALUE)
										.addGroup(groupLayout
												.createSequentialGroup()
												.addComponent(removeFieldBtn)
												.addPreferredGap(
														ComponentPlacement.RELATED)
												.addComponent(btnAddField)))
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
										GroupLayout.DEFAULT_SIZE, 221,
										Short.MAX_VALUE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(groupLayout
										.createParallelGroup(Alignment.BASELINE)
										.addComponent(btnAddField)
										.addComponent(removeFieldBtn))));
		setLayout(groupLayout);
		initEditorListeners();
	}

}
