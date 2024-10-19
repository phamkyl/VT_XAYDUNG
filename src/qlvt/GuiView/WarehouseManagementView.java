package qlvt.GuiView;

import qlvt.model.Warehouse;
import qlvt.connect.WarehouseDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class WarehouseManagementView extends JDialog {
    private DefaultTableModel model;
    private WarehouseDAO warehouseDAO;

    public WarehouseManagementView(JFrame parent) {
        super(parent, "Quản Lý Kho", true);
        setSize(600, 450);
        setLocationRelativeTo(parent);
        warehouseDAO = new WarehouseDAO();

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Create a table to display the list of warehouses
        String[] columnNames = {"Mã Kho", "Tên Kho", "Địa Chỉ", "Mã Chi Nhánh"};
        model = new DefaultTableModel(columnNames, 0);
        JTable warehouseTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(warehouseTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Add buttons for add, edit, delete, exit, and back
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Thêm");
        JButton editButton = new JButton("Sửa");
        JButton deleteButton = new JButton("Xóa");
        JButton exitButton = new JButton("Thoát");
        JButton backButton = new JButton("Quay Lại");

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(backButton);
        buttonPanel.add(exitButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Action for the Add button
        addButton.addActionListener(e -> {
            String maKhoStr = JOptionPane.showInputDialog("Nhập Mã Kho:");
            String tenKho = JOptionPane.showInputDialog("Nhập Tên Kho:");
            String diaChi = JOptionPane.showInputDialog("Nhập Địa Chỉ:");
            String maChiNhanhStr = JOptionPane.showInputDialog("Nhập Mã Chi Nhánh:");

            if (maKhoStr != null && tenKho != null && diaChi != null && maChiNhanhStr != null) {
                try {
                    int maKho = Integer.parseInt(maKhoStr);
                    int maChiNhanh = Integer.parseInt(maChiNhanhStr);
                    Warehouse newWarehouse = new Warehouse(maKho, tenKho, diaChi, maChiNhanh);
                    warehouseDAO.addWarehouse(newWarehouse);
                    model.addRow(new Object[]{maKho, tenKho, diaChi, maChiNhanh});
                } catch (NumberFormatException | SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
                }
            }
        });

        // Action for the Edit button
        editButton.addActionListener(e -> {
            int selectedRow = warehouseTable.getSelectedRow();
            if (selectedRow != -1) {
                int maKho = (int) model.getValueAt(selectedRow, 0);
                String tenKho = JOptionPane.showInputDialog("Nhập Tên Kho:", model.getValueAt(selectedRow, 1));
                String diaChi = JOptionPane.showInputDialog("Nhập Địa Chỉ:", model.getValueAt(selectedRow, 2));
                String maChiNhanhStr = JOptionPane.showInputDialog("Nhập Mã Chi Nhánh:", model.getValueAt(selectedRow, 3));

                if (maChiNhanhStr != null) {
                    try {
                        int maChiNhanh = Integer.parseInt(maChiNhanhStr);
                        Warehouse updatedWarehouse = new Warehouse(maKho, tenKho, diaChi, maChiNhanh);
                        warehouseDAO.updateWarehouse(updatedWarehouse);
                        model.setValueAt(tenKho, selectedRow, 1);
                        model.setValueAt(diaChi, selectedRow, 2);
                        model.setValueAt(maChiNhanh, selectedRow, 3);
                    } catch (NumberFormatException | SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
                    }
                }
            }
        });

        // Action for the Delete button
        deleteButton.addActionListener(e -> {
            int selectedRow = warehouseTable.getSelectedRow();
            if (selectedRow != -1) {
                int maKho = (int) model.getValueAt(selectedRow, 0);
                try {
                    warehouseDAO.deleteWarehouse(maKho);
                    model.removeRow(selectedRow);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
                }
            }
        });

        // Action for the Exit button
        exitButton.addActionListener(e -> {
            System.exit(0);
        });

        // Action for the Back button
        backButton.addActionListener(e -> {
            this.dispose(); // Close the warehouse management window
            // Optionally, call a method from the main view to show admin options
            MainView mainView = new MainView(); // Assume you have a MainView class to call back options
            mainView.showOptions(new DefaultTableModel());; // Display the admin options again
        });

        loadWarehouses();
        setContentPane(panel);
    }

    private void loadWarehouses() {
        try {
            List<Warehouse> warehouses = warehouseDAO.getAllWarehouses();
            for (Warehouse warehouse : warehouses) {
                model.addRow(new Object[]{
                        warehouse.getMaKho(),
                        warehouse.getTenKho(),
                        warehouse.getDiaChi(),
                        warehouse.getMaChiNhanh()
                });
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
        }
    }
}
