package org.example.view;

import org.example.model.City;
import org.example.model.ModelType;
import org.example.util.db.DBCity;
import org.example.util.view.ButtonEditor;
import org.example.util.view.ButtonRenderer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import static org.example.model.ModelType.CITY;

public class MainFrame extends JFrame {
    public MainFrame() {
        setTitle("City application");
        setSize(800, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());
        JLabel inputLabel = new JLabel("City name:");
        JTextField inputField = new JTextField(15);
        JButton createBtn = new JButton("Create City");

        topPanel.add(inputLabel);
        topPanel.add(inputField);
        topPanel.add(createBtn);

        String[] columnNames = {"ID", "Name", "Action"};

        List<City> cities = DBCity.getAllCities();
        Object[][] data = null;

        if (cities != null) {
            data = new Object[cities.size()][2];

            for (int i = 0; i < data.length; i++) {
                data[i] = cities.get(i).getTableData();
            }
        }

        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 1;
            }
        };

        tableModel.addTableModelListener(e -> {
            int row = e.getFirstRow();
            int column = e.getColumn();

            if (column == 1) {
                int id = (int) tableModel.getValueAt(row, 0);
                String newName = (String) tableModel.getValueAt(row, 1);

                boolean updated = DBCity.updateCityName(id, newName);
                if (!updated) {
                    JOptionPane.showMessageDialog(this, "Failed to update city name.");

                    City city = DBCity.getCityById(id);
                    if (city != null) {
                        tableModel.setValueAt(city.getName(), row, 1);
                    } else {
                        updateData(tableModel);
                    }
                }
            }
        });

        JTable table = new JTable(tableModel);
        table.setRowSelectionAllowed(true);
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = table.getSelectedRow();

                    if (row >= 0) {
                        int id = (int) table.getValueAt(row, 0);

                        new BuildingsFrame(id);
                    }
                }
            }
        });

        table.getColumnModel().getColumn(2).setCellRenderer(new ButtonRenderer());
        table.getColumnModel().getColumn(2).setCellEditor(new ButtonEditor(new JCheckBox(), table, CITY));
        JScrollPane scrollPane = new JScrollPane(table);

        createBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = inputField.getText();
                City city = new City(name);

                boolean created = DBCity.createCity(city);

                if (created) {
                    updateData(tableModel);
                }
            }
        });

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);

    }

    private static void updateData(DefaultTableModel tableModel) {
        tableModel.setRowCount(0);

        List<City> cities = DBCity.getAllCities();

        if (cities != null) {
            for (City city : cities) {
                tableModel.addRow(city.getTableData());
            }
        }
    }
}
