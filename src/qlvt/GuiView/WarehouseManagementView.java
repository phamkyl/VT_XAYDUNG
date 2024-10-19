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
    private int maChiNhanh; // Branch ID

    public WarehouseManagementView(JFrame parent, int maChiNhanh) throws SQLException {
        super(parent, "Quản Lý Kho", true);
        setSize(600, 450);
        setLocationRelativeTo(parent);
        this.maChiNhanh = maChiNhanh; // Initialize branch ID
        warehouseDAO = new WarehouseDAO();

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Create a table to display the list of warehouses
        String[] columnNames = {"Mã Kho", "Tên Kho", "Địa Chỉ", "Mã Chi Nhánh"};
        model = new DefaultTableModel(columnNames, 0);
        JTable warehouseTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(warehouseTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Add buttons for adding, editing, deleting, exiting, and going back
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

        // Add action
        addButton.addActionListener(e -> addWarehouse());

        // Edit action
        editButton.addActionListener(e -> editWarehouse(warehouseTable));

        // Delete action
        deleteButton.addActionListener(e -> deleteWarehouse(warehouseTable));

        // Exit action
        exitButton.addActionListener(e -> System.exit(0));

        // Back action
        backButton.addActionListener(e -> {
            this.dispose();
            openMainView();
        });

        loadWarehouses();
        setContentPane(panel);
    }

    private void addWarehouse() {
        // Input warehouse info
        String maKhoStr = JOptionPane.showInputDialog("Nhập Mã Kho:");
        String tenKho = JOptionPane.showInputDialog("Nhập Tên Kho:");
        String diaChi = JOptionPane.showInputDialog("Nhập Địa Chỉ:");

        if (maKhoStr != null && tenKho != null && diaChi != null) {
            try {
                int maKho = Integer.parseInt(maKhoStr);
                Warehouse newWarehouse = new Warehouse(maKho, tenKho, diaChi, maChiNhanh);
                warehouseDAO.addWarehouse(newWarehouse);
                model.addRow(new Object[]{maKho, tenKho, diaChi, maChiNhanh});
            } catch (NumberFormatException | SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
            }
        }
    }

    private void editWarehouse(JTable warehouseTable) {
        int selectedRow = warehouseTable.getSelectedRow();
        if (selectedRow != -1) {
            int maKho = (int) model.getValueAt(selectedRow, 0);
            String tenKho = JOptionPane.showInputDialog("Nhập Tên Kho:", model.getValueAt(selectedRow, 1));
            String diaChi = JOptionPane.showInputDialog("Nhập Địa Chỉ:", model.getValueAt(selectedRow, 2));

            if (tenKho != null && diaChi != null) {
                try {
                    Warehouse updatedWarehouse = new Warehouse(maKho, tenKho, diaChi, maChiNhanh);
                    warehouseDAO.updateWarehouse(updatedWarehouse);
                    model.setValueAt(tenKho, selectedRow, 1);
                    model.setValueAt(diaChi, selectedRow, 2);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một kho để sửa.");
        }
    }

    private void deleteWarehouse(JTable warehouseTable) {
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
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một kho để xóa.");
        }
    }

    private void openMainView() {
        String userRole = "Admin"; // Example role
        String userName = "User"; // Example user
        MainView mainView = new MainView(userRole, userName, maChiNhanh); // Open the main view with parameters
        mainView.setVisible(true);
    }

    private void loadWarehouses() throws SQLException {
        List<Warehouse> warehouses = warehouseDAO.getWarehousesByBranch(maChiNhanh);
        for (Warehouse warehouse : warehouses) {
            model.addRow(new Object[]{
                    warehouse.getMaKho(),
                    warehouse.getTenKho(),
                    warehouse.getDiaChi(),
                    warehouse.getMaChiNhanh()
            });
        }
    }
}
