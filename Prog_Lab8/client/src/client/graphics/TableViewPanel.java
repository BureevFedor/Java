package client.graphics;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.table.TableRowSorter;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.Color;

import client.CommandHandler;

public class TableViewPanel extends JScrollPane {
    private JTable table;
    private JLabel filterLabel;

    public TableViewPanel(VehiclesTableModel model, CommandHandler handler, JPanel filterPanel, CollectionWindow parent) {    
        setBackground(Color.lightGray);
        setBounds(350,35,1325,410);
        setBorder(BorderFactory.createLoweredBevelBorder());

        table = new JTable(model);
        getViewport().add(table);

        JComboBox<String> cbType = new JComboBox<String>(VehicleItem.getTypeStrings());
        cbType.setEditable(true);
        table.getColumnModel().getColumn(7).setCellEditor(new DefaultCellEditor(cbType));

        JComboBox<String> cbFuelType = new JComboBox<String>(VehicleItem.getFuelTypeStrings());
        cbFuelType.setEditable(true);
        table.getColumnModel().getColumn(8).setCellEditor(new DefaultCellEditor(cbFuelType));

        TableRowSorter<VehiclesTableModel> sorter = new TableRowSorter<VehiclesTableModel>(model);
        table.setRowSorter(sorter);

        filterLabel = new JLabel(Resources.getString(Resources.FILTER_LABEL) + ":");
        JTextField filterField = new JTextField(15);

        JComboBox<String> filterColumn = new JComboBox<String>(model.getColumnNames());
        filterColumn.setSelectedItem("NAME");

        filterField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                update(e);
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                update(e);
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                update(e);
            }
            private void update(DocumentEvent e) {
                String text = filterField.getText();
                if (text.trim().length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, filterColumn.getSelectedIndex()));
                }
            }
        });
        filterPanel.add(filterLabel);
        filterPanel.add(filterField);
        filterPanel.add(filterColumn);  
    }

    public void updateLocale() {
        filterLabel.setText(Resources.getString(Resources.FILTER_LABEL) + ":");
    }
}
