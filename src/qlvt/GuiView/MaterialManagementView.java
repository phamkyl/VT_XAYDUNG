package qlvt.GuiView;

import qlvt.model.Material;
import qlvt.connect.MaterialDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class MaterialManagementView extends JDialog {
    private DefaultTableModel model;
    private MaterialDAO materialDAO;

    public MaterialManagementView(JFrame parent) {
        super(parent, "Quản Lý Vật Tư", true);
        setSize(600, 450);
        setLocationRelativeTo(parent);
        materialDAO = new MaterialDAO();

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Tạo bảng để hiển thị danh sách vật tư
        String[] columnNames = {"Mã Vật Tư", "Tên Vật Tư", "Mô Tả", "Đơn Vị Tính", "Giá", "Mã Nhà Cung Cấp"};
        model = new DefaultTableModel(columnNames, 0);
        JTable materialTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(materialTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Thêm nút cho thêm, sửa, xóa, thoát, và quay lại
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

        // Action cho nút Thêm
        addButton.addActionListener(e -> {
            // Nhập thông tin vật tư
            String idStr = JOptionPane.showInputDialog("Nhập Mã Vật Tư:");
            String name = JOptionPane.showInputDialog("Nhập Tên Vật Tư:");
            String description = JOptionPane.showInputDialog("Nhập Mô Tả:");
            String unit = JOptionPane.showInputDialog("Nhập Đơn Vị Tính:");
            String priceStr = JOptionPane.showInputDialog("Nhập Giá:");
            String supplierIdStr = JOptionPane.showInputDialog("Nhập Mã Nhà Cung Cấp:");

            if (idStr != null && name != null && description != null && unit != null && priceStr != null && supplierIdStr != null) {
                try {
                    int id = Integer.parseInt(idStr);
                    double price = Double.parseDouble(priceStr);
                    int supplierId = Integer.parseInt(supplierIdStr);
                    Material newMaterial = new Material(id, name, description, unit, price, supplierId);
                    materialDAO.addMaterial(newMaterial);
                    model.addRow(new Object[]{id, name, description, unit, price, supplierId});
                } catch (NumberFormatException | SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
                }
            }
        });

        // Action cho nút Sửa
        editButton.addActionListener(e -> {
            int selectedRow = materialTable.getSelectedRow();
            if (selectedRow != -1) {
                int id = (int) model.getValueAt(selectedRow, 0);
                String name = JOptionPane.showInputDialog("Nhập Tên Vật Tư:", model.getValueAt(selectedRow, 1));
                String description = JOptionPane.showInputDialog("Nhập Mô Tả:", model.getValueAt(selectedRow, 2));
                String unit = JOptionPane.showInputDialog("Nhập Đơn Vị Tính:", model.getValueAt(selectedRow, 3));
                String priceStr = JOptionPane.showInputDialog("Nhập Giá:", model.getValueAt(selectedRow, 4));
                String supplierIdStr = JOptionPane.showInputDialog("Nhập Mã Nhà Cung Cấp:", model.getValueAt(selectedRow, 5));

                if (priceStr != null && supplierIdStr != null) {
                    try {
                        double price = Double.parseDouble(priceStr);
                        int supplierId = Integer.parseInt(supplierIdStr);
                        Material updatedMaterial = new Material(id, name, description, unit, price, supplierId);
                        materialDAO.updateMaterial(updatedMaterial);
                        model.setValueAt(name, selectedRow, 1);
                        model.setValueAt(description, selectedRow, 2);
                        model.setValueAt(unit, selectedRow, 3);
                        model.setValueAt(price, selectedRow, 4);
                        model.setValueAt(supplierId, selectedRow, 5);
                    } catch (NumberFormatException | SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
                    }
                }
            }
        });

        // Action cho nút Xóa
        deleteButton.addActionListener(e -> {
            int selectedRow = materialTable.getSelectedRow();
            if (selectedRow != -1) {
                int id = (int) model.getValueAt(selectedRow, 0);
                try {
                    materialDAO.deleteMaterial(id);
                    model.removeRow(selectedRow);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
                }
            }
        });

        // Action cho nút Thoát
        exitButton.addActionListener(e -> {
            System.exit(0);
        });

        // Action cho nút Quay Lại
        backButton.addActionListener(e -> {
            this.dispose(); // Close the order management window

            // Replace these with the actual values you want to pass
            String userRole = "Admin"; // Example role, replace with actual
            String userName = "User"; // Example user name, replace with actual
            int maChiNhanh = 1; // Example branch ID, replace with actual

            MainView mainView = new MainView(userRole, userName, maChiNhanh); // Create MainView with parameters
            mainView.setVisible(true); // Show the MainView
        });


        loadMaterials();
        setContentPane(panel);
    }

    private void loadMaterials() {
        try {
            List<Material> materials = materialDAO.getAllMaterials();
            for (Material material : materials) {
                model.addRow(new Object[]{
                        material.getMaVatTu(),
                        material.getTenVatTu(),
                        material.getMoTa(),
                        material.getDonViTinh(),
                        material.getGia(),
                        material.getMaNhaCungCap()
                });
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
        }
    }
}
