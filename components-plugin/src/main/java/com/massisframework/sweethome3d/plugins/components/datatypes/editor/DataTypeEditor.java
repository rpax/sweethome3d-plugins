package com.massisframework.sweethome3d.plugins.components.datatypes.editor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import com.massisframework.sweethome3d.plugins.PluginEventBus;
import com.massisframework.sweethome3d.plugins.components.datatypes.DataTypeSchema;
import com.massisframework.sweethome3d.plugins.components.datatypes.FieldType;
import com.massisframework.sweethome3d.plugins.components.datatypes.JsonSchemas;
import com.massisframework.sweethome3d.plugins.components.datatypes.editor.events.DataTypeEditorEvent;
@SuppressWarnings("serial")
public class DataTypeEditor {

	private JFrame frmDataTypeEditor;
	private JTable table;
	private JTextField typeNameTextField;
	private static final Color DUPLICATED_COLOR = new Color(255, 168, 168);
	private static final Color INVALID_NAME_COLOR = new Color(255, 245, 142);
	private final Map<Integer, Color> rowColors = new HashMap<>();
	private JComboBox<DataTypeSchema> comboBox;
	private PluginEventBus eventBus;
	private JsonSchemas schemas = new JsonSchemas();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DataTypeEditor window = new DataTypeEditor();
					window.frmDataTypeEditor.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static void createDataTypeEditor(final JsonSchemas schemas, final PluginEventBus eventBus) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				DataTypeEditor window = new DataTypeEditor();
				window.setEventBus(eventBus);
				window.setSchemas(schemas);
				window.frmDataTypeEditor.setVisible(true);
			}
		});
	}

	
	protected void setSchemas(JsonSchemas schemas) {
		this.schemas = schemas;
		DefaultComboBoxModel<DataTypeSchema> model = (DefaultComboBoxModel<DataTypeSchema>) this.comboBox.getModel();
		for (DataTypeSchema dts : this.schemas) {
			model.addElement(dts);
		}
		model.addElement(new DataTypeSchema("NewType"));
		this.comboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				DataTypeSchema currentSchema = (DataTypeSchema) comboBox.getSelectedItem();
				typeNameTextField.setText(currentSchema.getName());
				while (table.getModel().getRowCount() > 0)
					((DefaultTableModel) table.getModel()).removeRow(0);
				for (Entry<String, FieldType> entry : currentSchema.getValues().entrySet()) {
					addRow(entry.getKey(), entry.getValue());
				}
			}
		});
		this.comboBox.setRenderer(new DefaultListCellRenderer() {
			// http://stackoverflow.com/a/24179822/3315914
			@Override
			public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
					boolean cellHasFocus) {

				if (value instanceof DataTypeSchema) {
					value = ((DataTypeSchema) value).getName();
				}
				return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

			}
		});
	}

	protected void setEventBus(PluginEventBus eb) {
		this.eventBus = eb;

	}

	/**
	 * Create the application.
	 */
	public DataTypeEditor() {
		initialize();
	}

	private static boolean isValidVariableName(String txt) {
		return txt != null && !txt.isEmpty() && txt.matches("^[a-z_]\\w*$");
	}

	private static boolean isValidTypeName(String txt) {
		return txt != null && !txt.isEmpty() && txt.matches("^[A-Z]\\w*$");
	}

	private boolean validateTypeName() {
		if (this.typeNameTextField == null)
			return false;
		boolean valid = isValidTypeName(this.typeNameTextField.getText());
		if (!valid) {
			this.typeNameTextField.setBackground(INVALID_NAME_COLOR);
		} else {
			this.typeNameTextField.setBackground(Color.WHITE);
		}
		return valid;
	}

	private boolean checkValidity() {
		boolean valid = true;
		valid = validateTypeName() && valid;
		valid = validateDuplicates() && valid;
		return valid;
	}

	private void setCellBackgroundColor(int row, Color c) {
		rowColors.put(row, c);
	}

	private void repaintTable() {
		((DefaultTableModel) table.getModel()).fireTableDataChanged();
	}

	private boolean validateDuplicates() {
		boolean valid = true;

		for (int i = 0; i < table.getRowCount(); i++) {
			String field1 = String.valueOf(table.getValueAt(i, 0));
			if (!isValidVariableName(field1)) {
				setCellBackgroundColor(i, INVALID_NAME_COLOR);
				valid = false;
			} else {
				for (int j = i - 1; j >= 0; j--) {
					String field2 = String.valueOf(table.getValueAt(j, 0));
					if (field1.equals(field2)) {
						valid = false;
						break;
					}
				}
				if (!valid) {
					setCellBackgroundColor(i, DUPLICATED_COLOR);
				} else {
					setCellBackgroundColor(i, Color.WHITE);
				}
			}

		}
		repaintTable();
		return valid;
	}

	private void addRow(String name, FieldType dt) {
		((DefaultTableModel) this.table.getModel()).addRow(new Object[] { name, dt });
		checkValidity();
	}

	private void addRow() {
		addRow("fieldName", FieldType.STRING);
	}

	private void onSave() {
		// check for data type name
		DataTypeSchema currentSchema = (DataTypeSchema) this.comboBox.getSelectedItem();
		if (checkValidity()) {
			currentSchema.setName(this.typeNameTextField.getText());
			for (int i = 0; i < this.table.getRowCount(); i++) {
				currentSchema.addField((String) this.table.getValueAt(i, 0), (FieldType) this.table.getValueAt(i, 1));
			}
			if (this.eventBus != null)
				this.eventBus.post(new DataTypeEditorEvent(currentSchema, DataTypeEditorEvent.EventType.CHANGED));
			this.frmDataTypeEditor.setVisible(false);
			this.frmDataTypeEditor.dispose();
		}
	}

	private void onCancel() {

	}

	private void onDelete() {
		DataTypeSchema currentSchema = (DataTypeSchema) this.comboBox.getSelectedItem();
		int v = JOptionPane.showConfirmDialog(null, "Are you sure that you want to delete the current data type?");
		if (v == JOptionPane.OK_OPTION) {
			if (this.eventBus != null) {
				this.eventBus.post(new DataTypeEditorEvent(currentSchema, DataTypeEditorEvent.EventType.DELETED));
			}
		} else {

			// nothing
		}
	}

	private static JComboBox<FieldType> newDTCombobox() {
		JComboBox<FieldType> comboBox = new JComboBox<>();
		for (FieldType dt : FieldType.values()) {
			comboBox.addItem(dt);
		}
		return comboBox;
	}

	private TableCellEditor createTypeCellEditor() {
		return new DefaultCellEditor(newDTCombobox());
	}

	// private TableCellEditor createFieldCellEditor() {
	// return new DefaultCellEditor(new JTextField());
	// }

	

	public void removeSelectedRow() {
		int sr = this.table.getSelectedRow();
		if (sr < 0)
			return;
		((DefaultTableModel) this.table.getModel()).removeRow(sr);
		this.checkValidity();
	}

	private JTable createTable() {
		JTable jtable = new JTable() {
			@Override
			public Component prepareRenderer(TableCellRenderer renderer, int rowIndex, int columnIndex) {
				JComponent component = (JComponent) super.prepareRenderer(renderer, rowIndex, columnIndex);
				if (rowColors.get(rowIndex) != null) {
					component.setBackground(rowColors.get(rowIndex));
				}
				component.setForeground(Color.BLACK);
				return component;
			}
		};
		jtable.setForeground(Color.BLACK);
		jtable.addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				checkValidity();
			}
		});

		return jtable;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmDataTypeEditor = new JFrame();
		frmDataTypeEditor.setTitle("Data Type Editor");
		frmDataTypeEditor.setBounds(100, 100, 615, 368);
		frmDataTypeEditor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmDataTypeEditor.getContentPane().setLayout(new BorderLayout(0, 0));

		this.comboBox = new JComboBox<DataTypeSchema>();
		frmDataTypeEditor.getContentPane().add(comboBox, BorderLayout.NORTH);

		JPanel panel = new JPanel();
		frmDataTypeEditor.getContentPane().add(panel, BorderLayout.CENTER);

		table = createTable();
		table.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Field Name", "Field Type" }));
		table.getColumnModel().getColumn(0).setPreferredWidth(134);
		table.getColumnModel().getColumn(1).setPreferredWidth(203);
		table.setBorder(new LineBorder(new Color(0, 0, 0)));

		JScrollPane tableScrollPane = new JScrollPane(table);

		table.getColumnModel().getColumn(1).setCellEditor(createTypeCellEditor());

		JButton buttonCancel = new JButton("Cancel");
		buttonCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onCancel();
			}
		});

		JButton buttonSave = new JButton("Save");
		buttonSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onSave();
			}
		});

		JLabel label = new JLabel("Data Type Name");

		typeNameTextField = new JTextField();
		typeNameTextField.setColumns(10);
		typeNameTextField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				validateTypeName();
			}
		});

		JButton btnAddRow = new JButton("Add Row");
		btnAddRow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addRow();
			}
		});

		JButton buttonRemoveRow = new JButton("Remove Row");
		buttonRemoveRow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeSelectedRow();
			}
		});

		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onDelete();
			}
		});
		btnDelete.setBackground(Color.RED);
		btnDelete.setForeground(Color.WHITE);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
								.addComponent(tableScrollPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 591,
										Short.MAX_VALUE)
								.addGroup(gl_panel.createSequentialGroup().addComponent(btnAddRow)
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addComponent(buttonRemoveRow, GroupLayout.PREFERRED_SIZE, 131,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED, 57, Short.MAX_VALUE)
										.addComponent(btnDelete).addPreferredGap(ComponentPlacement.UNRELATED)
										.addComponent(buttonCancel).addPreferredGap(ComponentPlacement.UNRELATED)
										.addComponent(buttonSave).addGap(6))
								.addGroup(gl_panel.createSequentialGroup()
										.addComponent(label, GroupLayout.PREFERRED_SIZE, 128,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED).addComponent(typeNameTextField,
												GroupLayout.DEFAULT_SIZE, 451, Short.MAX_VALUE)))
						.addContainerGap()));
		gl_panel.setVerticalGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(typeNameTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(label))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(tableScrollPane, GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE).addComponent(buttonCancel)
								.addComponent(buttonSave).addComponent(btnAddRow).addComponent(buttonRemoveRow)
								.addComponent(btnDelete))
						.addContainerGap()));
		panel.setLayout(gl_panel);
	}
}
