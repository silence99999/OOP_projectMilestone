package org.example.view;

import org.example.model.Building;
import org.example.model.ModelType;
import org.example.util.db.DBBuilding;
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

public class BuildingsFrame extends JFrame {
    public BuildingsFrame(int cityId) {
        setTitle("City application");
        setSize(800, 800);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());
        JLabel inputStreetLabel = new JLabel("Building street:");
        JTextField inputStreetField = new JTextField(15);
        JLabel inputNumberLabel = new JLabel("Building number:");
        JTextField inputNumberField = new JTextField(15);
        JLabel inputPaymentLabel = new JLabel("Building payment month per square meters:");
        JTextField inputPaymentField = new JTextField(15);
        JButton createBtn = new JButton("Create Building");

        topPanel.add(inputStreetLabel);
        topPanel.add(inputStreetField);
        topPanel.add(inputNumberLabel);
        topPanel.add(inputNumberField);
        topPanel.add(inputPaymentLabel);
        topPanel.add(inputPaymentField);
        topPanel.add(createBtn);
        topPanel.setPreferredSize(new Dimension(800, 100));

        String[] columnNames = {"ID", "Street", "Number", "Payment month/sq m", "Action"};

        List<Building> buildings = DBBuilding.getBuildingsByCityId(cityId);
        Object[][] data = null;

        if (buildings != null) {
            data = new Object[buildings.size()][5];

            for (int i = 0; i < data.length; i++) {
                data[i] = buildings.get(i).getTableData();
            }
        }

        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 1 || column == 2 || column == 3 || column == 4;
            }
        };

        JTable table = new JTable(tableModel);
        table.setRowSelectionAllowed(true);
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = table.getSelectedRow();

                    if (row >= 0) {
                        int id = (int) table.getValueAt(row, 0);

                        new RoomsFrame(id);
                    }
                }
            }
        });

        table.getColumnModel().getColumn(4).setCellRenderer(new ButtonRenderer());
        table.getColumnModel().getColumn(4).setCellEditor(new ButtonEditor(new JCheckBox(), table, BUILDING));
        JScrollPane scrollPane = new JScrollPane(table);

        createBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String street = inputStreetField.getText();
                String number = inputNumberField.getText();
                String payment = inputPaymentField.getText();
                Building b = new Building(street, Integer.parseInt(number), Integer.parseInt(payment));

                boolean created = DBBuilding.createBuilding(b, cityId);

                if (created) {
                    updateData(tableModel, cityId);
                }
            }
        });

        tableModel.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                int row = e.getFirstRow();
                int column = e.getColumn();

                if (column == 1 || column == 2 || column == 3) {
                    int id = (int) tableModel.getValueAt(row, 0);
                    String street = tableModel.getValueAt(row, 1).toString();
                    String number = tableModel.getValueAt(row, 2).toString();
                    String payment = tableModel.getValueAt(row, 3).toString();

                    Building building = new Building(street, Integer.parseInt(number), Integer.parseInt(payment));
                    building.setId(id);


                    boolean updated = DBBuilding.updateBuilding(building);

                    if (updated) {
                        System.out.println("Building updated successfully!");
                    } else {
                        System.out.println("Failed to update building.");
                    }
                }
            }
        });

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    private static void updateData(DefaultTableModel tableModel, int cityId) {
        tableModel.setRowCount(0);

        List<Building> buildings = DBBuilding.getBuildingsByCityId(cityId);

        if (buildings != null) {
            for (Building building : buildings) {
                tableModel.addRow(building.getTableData());
            }
        }
    }
}