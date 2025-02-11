package org.example.util.view;

import org.example.model.ModelType;
import org.example.util.db.DBBuilding;
import org.example.util.db.DBCity;
import org.example.util.db.DBRoom;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonEditor extends DefaultCellEditor {
    private JButton button;
    private String label;
    private boolean isPushed;
    private JTable table;
    private ModelType type;

    public ButtonEditor(JCheckBox checkBox, JTable table, ModelType type) {
        super(checkBox);
        this.table = table;
        this.type = type;
        button = new JButton();
        button.setOpaque(true);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fireEditingStopped();
            }
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
                                                 boolean isSelected, int row, int column) {
        label = (value == null) ? "" : value.toString();
        button.setText(label);
        isPushed = true;
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        if (isPushed) {
            int row = table.convertRowIndexToModel(table.getEditingRow());

            int id = (int) table.getModel().getValueAt(row, 0);

            boolean deleted = false;

            switch (this.type) {
                case CITY:
                    deleted = DBCity.deleteCity(id);
                    break;

                case BUILDING:
                    deleted = DBBuilding.deleteBuilding(id);
                    break;

                case ROOM:
                    deleted = DBRoom.deleteRoom(id);
                    break;
            }

            if (deleted) {
                ((DefaultTableModel) table.getModel()).removeRow(row);
            }
        }
        isPushed = false;
        return label;
    }

    @Override
    public boolean stopCellEditing() {
        isPushed = false;
        return super.stopCellEditing();
    }
}
