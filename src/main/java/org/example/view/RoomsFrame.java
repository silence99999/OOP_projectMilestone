package org.example.view;

import org.example.model.Building;
import org.example.model.Room;
import org.example.util.db.DBBuilding;
import org.example.util.db.DBRoom;
import org.example.util.view.ButtonEditor;
import org.example.util.view.ButtonRenderer;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import static org.example.model.ModelType.BUILDING;
import static org.example.model.ModelType.ROOM;

public class RoomsFrame extends JFrame {
    public RoomsFrame(int buildingId) {
        setTitle("City application");
        setSize(800,800);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());
        JLabel inputNumberLabel = new JLabel("Room number:");
        JTextField inputNumberField = new JTextField(15);
        JLabel inputAreaLabel = new JLabel("Area:");
        JTextField inputAreaField = new JTextField(15);
        JButton createBtn = new JButton("Create Room");

        topPanel.add(inputNumberLabel);
        topPanel.add(inputNumberField);
        topPanel.add(inputAreaLabel);
        topPanel.add(inputAreaField);
        topPanel.add(createBtn);

        String[] columnNames = {"ID", "Number","Area", "Action"};

        List<Room>  rooms = DBRoom.getRoomsByBuildingId(buildingId);
        Object[][] data = null;

        if (rooms != null) {
            data = new Object[rooms.size()][5];

            for (int i = 0; i < data.length; i++) {
                data[i] = rooms.get(i).getTableData();
            }
        }

        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {

                return column == 1 || column == 2 || column == 3;
            }
        };

        JTable table = new JTable(tableModel);

        table.getColumnModel().getColumn(3).setCellRenderer(new ButtonRenderer());
        table.getColumnModel().getColumn(3).setCellEditor(new ButtonEditor(new JCheckBox(), table, ROOM));
        JScrollPane scrollPane = new JScrollPane(table);

        createBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String number = inputNumberField.getText();
                String area = inputAreaField.getText();
                Room r = new Room(Integer.parseInt(number), Float.parseFloat(area));

                boolean created = DBRoom.createRoom(r, buildingId);

                if (created) {
                    updateData(tableModel, buildingId);
                }
            }
        });


        tableModel.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                int row = e.getFirstRow();
                int column = e.getColumn();

                if (column == 1 || column == 2) {
                    int id = (int) tableModel.getValueAt(row, 0);
                    String number = tableModel.getValueAt(row, 1).toString();
                    String area = tableModel.getValueAt(row, 2).toString();

                    Room room = new Room(Integer.parseInt(number), Float.parseFloat(area));
                    room.setId(id);

                    DBRoom.updateRoom(room);
                }
            }
        });

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    private static void updateData(DefaultTableModel tableModel, int buildingId) {
        tableModel.setRowCount(0);

        List<Room> rooms = DBRoom.getRoomsByBuildingId(buildingId);

        if (rooms != null) {
            for (Room room : rooms) {
                tableModel.addRow(room.getTableData());
            }
        }
    }
}