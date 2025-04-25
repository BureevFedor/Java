package client.graphics;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Component;

public class VehicleWindow {
    private JPanel panel;

    private JTextField tfName;
    private JTextField tfX;
    private JTextField tfY;
    private JTextField tfEnginePower;
    private JTextField tfFuelConsumption;
    private JComboBox<String> cbType;
    private JComboBox<String> cbFuelType;
    private JTextField tfType;
    private JTextField tfFuelType;

    private VehicleItem item;

    private boolean isAdd;
    private boolean isEditable;

    public VehicleWindow() {
        this.item = new VehicleItem();
        isAdd = true;
        isEditable = true;
        createPanel();
    }

    public VehicleWindow(VehicleItem item, boolean isEditable) {
        this.item = item;
        isAdd = false;
        this.isEditable = isEditable;
        createPanel();
    }
        
    public void createPanel() {

        ActionListener tfListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Component c = (Component)e.getSource();
                c.transferFocus();
            }
        }; 

        tfName = new JTextField(item.getName().toString(), 15);
        tfName.addActionListener(tfListener);
        if (!isEditable) tfName.setEditable(false);
 
        tfX = new JTextField(item.getX().toString(), 15);
        tfX.addActionListener(tfListener);
        if (!isEditable) tfX.setEditable(false);

        tfY = new JTextField(item.getY().toString(), 15);
        tfY.addActionListener(tfListener);
        if (!isEditable) tfY.setEditable(false);

        tfEnginePower = new JTextField(item.getEnginePower().toString(), 15);
        tfEnginePower.addActionListener(tfListener);
        if (!isEditable) tfEnginePower.setEditable(false);

        tfFuelConsumption = new JTextField(item.getFuelConsumption().toString(), 15);
        tfFuelConsumption.addActionListener(tfListener);
        if (!isEditable) tfFuelConsumption.setEditable(false);

        if(isEditable) {
            cbType = new JComboBox<String>(VehicleItem.getTypeStrings());
            cbType.setSelectedItem(item.getType() == null ? "" : item.getType().toString());
            cbType.setEditable(true);

            cbFuelType = new JComboBox<String>(VehicleItem.getFuelTypeStrings());
            cbFuelType.setSelectedItem(item.getFuelType() == null ? "" : item.getFuelType().toString());
            cbFuelType.setEditable(true);
        } else {
            tfType = new JTextField(item.getType().toString(), 15);
            if (!isEditable) tfType.setEditable(false);
            tfFuelType = new JTextField(item.getFuelType().toString(), 15);
            if (!isEditable) tfFuelType.setEditable(false);
        }

        panel = new JPanel(new BorderLayout(5,5));

        JPanel labels = new JPanel(new GridLayout(0,1,2,2));

        labels.add(new JLabel(Resources.getString(Resources.LABEL_VEHICLE_NAME) + ":", SwingConstants.TRAILING));
        labels.add(new JLabel(Resources.getString(Resources.LABEL_VEHICLE_X) + ":", SwingConstants.TRAILING));       
        labels.add(new JLabel(Resources.getString(Resources.LABEL_VEHICLE_Y) + ":", SwingConstants.TRAILING));
        labels.add(new JLabel(Resources.getString(Resources.LABEL_VEHICLE_ENGINE_POWER) + ":", SwingConstants.TRAILING));
        labels.add(new JLabel(Resources.getString(Resources.LABEL_VEHICLE_FUEL_CONSUMPTION) + ":", SwingConstants.TRAILING));
        labels.add(new JLabel(Resources.getString(Resources.LABEL_VEHICLE_TYPE) + ":", SwingConstants.TRAILING));
        labels.add(new JLabel(Resources.getString(Resources.LABEL_VEHICLE_FUEL_TYPE) + ":", SwingConstants.TRAILING));

        panel.add(labels, BorderLayout.LINE_START);

        JPanel controls = new JPanel(new GridLayout(0,1,2,2));

        controls.add(tfName);
        controls.add(tfX);
        controls.add(tfY);
        controls.add(tfEnginePower);
        controls.add(tfFuelConsumption);
        controls.add(isEditable ? cbType : tfType);
        controls.add(isEditable ? cbFuelType : tfFuelType);

        panel.add(controls, BorderLayout.CENTER);
    }

    public VehicleItem getVehicleItem() {
        return item;
    }

    public boolean run() {
        if(!isEditable) { 
            JOptionPane.showOptionDialog(null, panel, Resources.getString(Resources.ELEMENT_TITLE),
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
            return true; 
        }

        while(true) {
            int res = JOptionPane.showOptionDialog(null, panel,
                isAdd ? Resources.getString(Resources.ADD_ELEMENT_TITLE) : Resources.getString(Resources.UPDATE_ELEMENT_TITLE),
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
            if(res == -1) {
                return false;
            }

            VehicleItem item1 = item;
            String err = item1.fill(
                null, 
                tfName.getText(), 
                tfX.getText(), 
                tfY.getText(), 
                null, 
                tfEnginePower.getText(),
                tfFuelConsumption.getText(), 
                cbType.getSelectedItem().toString(), 
                cbFuelType.getSelectedItem().toString(), 
                null
            );

            if(err == null) {
                return true;
            } else {
                JOptionPane.showMessageDialog(null, err, Resources.getString(Resources.ERROR_TITLE), JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}