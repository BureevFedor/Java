package client.graphics;

import javax.swing.table.DefaultTableModel;

import java.util.ArrayList;
import java.util.Collections;

import client.CommandHandler;

public class VehiclesTableModel extends DefaultTableModel {

    private static String[] columnNames = { "ID", "NAME", "X", "Y", "CREATION DATA", "ENGINE POWER", "FUEL CONSUMPTION", "TYPE", "FUEL TYPE", "USER ID" };

    private int userId;
    private ObjectViewPanel objectView;
    private CommandHandler handler;

    public VehiclesTableModel () {
        super(columnNames, 0);

        userId = 0;
        objectView = null;
        handler = null;
    }

    public String[] getColumnNames() {
        return columnNames;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setObjectView(ObjectViewPanel objectView) {
        this.objectView = objectView;
    }

    public void setCommandHandler(CommandHandler handler) {
        this.handler = handler;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0: return Integer.class;
            case 1: return String.class;
            case 2: return Float.class;
            case 3: return Long.class;
            case 4: return String.class;
            case 5: return Integer.class;
            case 6: return Long.class;
            case 7: return String.class;
            case 8: return String.class;
            case 9: return Integer.class;
            default:
                System.out.println("Unknown columnIndex " + columnIndex);
                return super.getColumnClass(columnIndex);
        }
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return (col != 0) && (col != 4) && (col != 9) && ( (Integer) getValueAt(row, 9) == userId);
    }
    
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        super.setValueAt(aValue, rowIndex, columnIndex);

        VehicleItem item = getObjByIndex(rowIndex);

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("model: run doUpdate thread");
                handler.doUpdate(item.getId(), item.createSerializable());
                System.out.println("model: stop doUpdate thread");
            }
        }).start();
    }

    public void update(ArrayList<VehicleItem> items) {
        // первый массив содержит row index текущей модели
        ArrayList<Integer> toBeRemoved = new ArrayList<Integer>();
        // второй массив содержит индексы элементов новой модели
        ArrayList<Integer> toBeAdded = new ArrayList<Integer>(); 

        // находим элементы для удаления
        for(int row = 0; row < getRowCount(); row++) {
            boolean found = false; 
            Integer id = (Integer) this.getValueAt(row, 0);
            for(VehicleItem v : items) {
                if(id.equals(v.getId())) {
                    found = true;
                    break;
                }
            }
            if(!found) {
                toBeRemoved.add(row);
            }
        }

        // удаляем элементы toBeRemoved
        Collections.reverse(toBeRemoved);
        for(int row : toBeRemoved) {
            System.out.println("REMOVE ROW " + row);
            if (objectView != null) {
                objectView.remove(getObjByIndex(row));
            }
            this.removeRow(row);
        }

        // находим элементы для обновления и потом добавления
        for(int idx = 0; idx < items.size(); idx++) {
            boolean found = false;
            for(int row = 0; row < getRowCount(); row++) {
                Integer id = (Integer) this.getValueAt(row, 0);
                VehicleItem itemNew = items.get(idx);
                if(id.equals(itemNew.getId())) {
                    found = true;
                    VehicleItem itemOld = getObjByIndex(row);
                    if(!itemNew.getName().equals(itemOld.getName())) {
                        super.setValueAt(itemNew.getName(), row, 1);
                        System.out.println("UPDATE ROW " + row + " COLUMN 1");
                    }
                    if(!itemNew.getX().equals(itemOld.getX())) {
                        super.setValueAt(itemNew.getX(), row, 2);
                        System.out.println("UPDATE ROW " + row + " COLUMN 2");
                    }
                    if(!itemNew.getY().equals(itemOld.getY())) {
                        super.setValueAt(itemNew.getY(), row, 3);
                        System.out.println("UPDATE ROW " + row + " COLUMN 3");
                    }
                    if(!itemNew.getCreationDate().equals(itemOld.getCreationDate())) {
                        super.setValueAt(itemNew.getCreationDate(), row, 4);
                        System.out.println("UPDATE ROW " + row + " COLUMN 4");
                    }
                    if(!itemNew.getEnginePower().equals(itemOld.getEnginePower())) {
                        super.setValueAt(itemNew.getEnginePower(), row, 5);
                        System.out.println("UPDATE ROW " + row + " COLUMN 5");
                    }
                    if(!itemNew.getFuelConsumption().equals(itemOld.getFuelConsumption())) {
                        super.setValueAt(itemNew.getFuelConsumption(), row, 6);
                        System.out.println("UPDATE ROW " + row + " COLUMN 6");
                    }
                    if(!itemNew.getType().equals(itemOld.getType())) {
                        super.setValueAt(itemNew.getType(), row, 7);
                        System.out.println("UPDATE ROW " + row + " COLUMN 7");
                    }
                    if(!itemNew.getFuelType().equals(itemOld.getFuelType())) {
                        super.setValueAt(itemNew.getFuelType(), row, 8);
                        System.out.println("UPDATE ROW " + row + " COLUMN 8");
                    }
                    if(!itemNew.getUserId().equals(itemOld.getUserId())) {
                        super.setValueAt(itemNew.getUserId(), row, 9);
                        System.out.println("UPDATE ROW " + row + " COLUMN 9");
                    }
                }
            }
            if(!found) {
                toBeAdded.add(idx);
            }
        }

        // добавляем элементы toBeAdded
        for(int row : toBeAdded) {
            System.out.println("ADD ROW");
            VehicleItem item = items.get(row);
            if (objectView != null) {
                objectView.add(item);
            }
            addRow( new Object[]{
                item.getId(), 
                item.getName(), 
                item.getX(), 
                item.getY(), 
                item.getCreationDate(), 
                item.getEnginePower(), 
                item.getFuelConsumption(), 
                item.getType(), 
                item.getFuelType(), 
                item.getUserId()
            });
        }
    }

    public VehicleItem getObjById(int id) {
        for(int i = 0; i < getRowCount(); i++) {
            int objId = (Integer) getValueAt(i, 0);
            if(objId == id) {
                return new VehicleItem(
                    (Integer) objId,
                    (String) getValueAt(i, 1),
                    (Float) getValueAt(i, 2),
                    (Long) getValueAt(i, 3),
                    (String) getValueAt(i, 4),
                    (Integer) getValueAt(i, 5),
                    (Long) getValueAt(i, 6),
                    (String) getValueAt(i, 7),
                    (String) getValueAt(i, 8),
                    (Integer) getValueAt(i, 9)
                );
            }
        }
        return null;
    }

    public VehicleItem getObjByIndex(int idx) {
        if(idx < getRowCount()) {
            return new VehicleItem(
                (Integer) getValueAt(idx, 0),
                (String) getValueAt(idx, 1),
                (Float) getValueAt(idx, 2),
                (Long) getValueAt(idx, 3),
                (String) getValueAt(idx, 4),
                (Integer) getValueAt(idx, 5),
                (Long) getValueAt(idx, 6),
                (String) getValueAt(idx, 7),
                (String) getValueAt(idx, 8),
                (Integer) getValueAt(idx, 9)
            );
        }
        return null;
    }
}
