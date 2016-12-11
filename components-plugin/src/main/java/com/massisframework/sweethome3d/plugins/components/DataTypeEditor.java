package com.massisframework.sweethome3d.plugins.components;

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
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import javax.swing.DefaultCellEditor;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.InputVerifier;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

public class DataTypeEditor {

	private JFrame frmDataTypeEditor;
	private JTable table;
	private JTextField typeNameTextField;
	private SaveCallBack onsaveCallback;
	private static final Color DUPLICATED_COLOR = new Color(255, 168, 168);
	private static final Color INVALID_NAME_COLOR = new Color(255, 245, 142);
	private final Map<Integer, Color> rowColors = new HashMap<>();

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

	public static void createDataTypeEditor(DataTypeSchema in,final SaveCallBack callback) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				DataTypeEditor window = new DataTypeEditor();
				window.setOnSaveAction(callback);
				window.frmDataTypeEditor.setVisible(true);
			}
		});
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
			setCellBackgroundColor(i, Color.WHITE);
		}

		for (int i = 0; i < table.getRowCount(); i++) {
			String field1 = String.valueOf(table.getValueAt(i, 0));
			if (!isValidVariableName(field1)) {
				setCellBackgroundColor(i, INVALID_NAME_COLOR);
				valid = false;
			}
			for (int j = i + 1; j < table.getRowCount(); j++) {
				String field2 = String.valueOf(table.getValueAt(j, 0));
				if (field1.equals(field2)) {
					setCellBackgroundColor(j, DUPLICATED_COLOR);
					valid = false;
				}
			}
		}
		repaintTable();
		return valid;
	}

	public void setOnSaveAction(SaveCallBack callback) {
		this.onsaveCallback = callback;
	}

	private void addRow() {
		((DefaultTableModel) this.table.getModel()).addRow(new Object[] { "fieldName", DataType.STRING });
		checkValidity();
	}

	private void onSave() {
		// check for data type name
		if (checkValidity()) {
			if (this.onsaveCallback != null) {
				DataTypeSchema sc = new DataTypeSchema();
				sc.setName(this.typeNameTextField.getText());
				for (int i = 0; i < this.table.getRowCount(); i++) {
					sc.addField((String) this.table.getValueAt(i, 0), (DataType) this.table.getValueAt(i, 1));
				}
				this.onsaveCallback.onSave(sc);
			}
			this.frmDataTypeEditor.setVisible(false);
			this.frmDataTypeEditor.dispose();
		}
	}

	private void onCancel() {

	}

	private static JComboBox<DataType> newDTCombobox() {
		JComboBox<DataType> comboBox = new JComboBox<>();
		for (DataType dt : DataType.values()) {
			comboBox.addItem(dt);
		}
		return comboBox;
	}

	private TableCellEditor createTypeCellEditor() {
		return new DefaultCellEditor(newDTCombobox());
	}

	private TableCellEditor createFieldCellEditor() {
		return new DefaultCellEditor(new JTextField());
	}

	private InputVerifier createNonDuplicatedInputVerifier() {
		final InputVerifier iv = new InputVerifier() {

			@Override
			public boolean verify(JComponent input) {
				JTextField field = (JTextField) input;
				int matches = 0;
				for (int i = 0; i < table.getRowCount(); i++) {
					if (field.getText().equals(table.getValueAt(i, 0))) {
						matches++;
					}
				}
				return matches < 1;
			}

			@Override
			public boolean shouldYieldFocus(JComponent input) {
				boolean valid = verify(input);
				if (!valid) {
					// JOptionPane.showMessageDialog(null, "invalid");
				}
				return valid;
			}

		};
		return iv;
	}

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
				return component;
			}
		};
		jtable.addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				checkValidity();
			}
		});

		return jtable;
	}

	public static interface SaveCallBack {
		public void onSave(DataTypeSchema values);

		public void onCancel();
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

		JPanel panel = new JPanel();
		frmDataTypeEditor.getContentPane().add(panel, BorderLayout.SOUTH);

		table = createTable();
		table.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Field Name", "Field Type" }));
		table.getColumnModel().getColumn(0).setPreferredWidth(134);
		table.getColumnModel().getColumn(1).setPreferredWidth(203);
		table.setBorder(new LineBorder(new Color(0, 0, 0)));

		JScrollPane tableScrollPane = new JScrollPane(table);

		table.getColumnModel().getColumn(0).setCellEditor(createFieldCellEditor());
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
										.addPreferredGap(ComponentPlacement.RELATED, 186, Short.MAX_VALUE)
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
						.addComponent(tableScrollPane, GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE).addComponent(buttonCancel)
								.addComponent(buttonSave).addComponent(btnAddRow).addComponent(buttonRemoveRow))
						.addContainerGap()));
		panel.setLayout(gl_panel);
	}
}
