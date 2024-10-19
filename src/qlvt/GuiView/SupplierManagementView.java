package qlvt.GuiView;

import qlvt.model.Supplier;
import qlvt.connect.SupplierDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class SupplierManagementView extends JDialog {
    private DefaultTableModel model;
    private SupplierDAO supplierDAO;

    public SupplierManagementView(JFrame parent) {
        super(parent, "Quản Lý Nhà Cung Cấp", true);
        setSize(600, 450);
        setLocationRelativeTo(parent);
        supplierDAO = new SupplierDAO();

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Tạo bảng để hiển thị danh sách nhà cung cấp
        String[] columnNames = {"Mã Nhà Cung Cấp", "Tên Nhà Cung Cấp", "Địa Chỉ", "Số Điện Thoại", "Email"};
        model = new DefaultTableModel(columnNames, 0);
        JTable supplierTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(supplierTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Thêm nút cho thêm, sửa, xóa, thoát
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Thêm");
        JButton editButton = new JButton("Sửa");
        JButton deleteButton = new JButton("Xóa");
        JButton exitButton = new JButton("Thoát");

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(exitButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Action cho nút Thêm
        addButton.addActionListener(e -> {
            String idStr = JOptionPane.showInputDialog("Nhập Mã Nhà Cung Cấp:");
            String name = JOptionPane.showInputDialog("Nhập Tên Nhà Cung Cấp:");
            String address = JOptionPane.showInputDialog("Nhập Địa Chỉ:");
            String phone = JOptionPane.showInputDialog("Nhập Số Điện Thoại:");
            String email = JOptionPane.showInputDialog("Nhập Email:");

            if (idStr != null && name != null && address != null && phone != null && email != null) {
                try {
                    int id = Integer.parseInt(idStr);
                    Supplier newSupplier = new Supplier(id, name, address, phone, email);
                    supplierDAO.addSupplier(newSupplier);
                    model.addRow(new Object[]{id, name, address, phone, email});
                } catch (NumberFormatException | SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
                }
            }
        });

        // Action cho nút Sửa
        editButton.addActionListener(e -> {
            int selectedRow = supplierTable.getSelectedRow();
            if (selectedRow != -1) {
                int id = (int) model.getValueAt(selectedRow, 0);
                String name = JOptionPane.showInputDialog("Nhập Tên Nhà Cung Cấp:", model.getValueAt(selectedRow, 1));
                String address = JOptionPane.showInputDialog("Nhập Địa Chỉ:", model.getValueAt(selectedRow, 2));
                String phone = JOptionPane.showInputDialog("Nhập Số Điện Thoại:", model.getValueAt(selectedRow, 3));
                String email = JOptionPane.showInputDialog("Nhập Email:", model.getValueAt(selectedRow, 4));

                if (name != null && address != null && phone != null && email != null) {
                    try {
                        Supplier updatedSupplier = new Supplier(id, name, address, phone, email);
                        supplierDAO.updateSupplier(updatedSupplier);
                        model.setValueAt(name, selectedRow, 1);
                        model.setValueAt(address, selectedRow, 2);
                        model.setValueAt(phone, selectedRow, 3);
                        model.setValueAt(email, selectedRow, 4);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
                    }
                }
            }
        });

        // Action cho nút Xóa
        deleteButton.addActionListener(e -> {
            int selectedRow = supplierTable.getSelectedRow();
            if (selectedRow != -1) {
                int id = (int) model.getValueAt(selectedRow, 0);
                try {
                    supplierDAO.deleteSupplier(id);
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

        loadSuppliers();
        setContentPane(panel);
    }

    private void loadSuppliers() {
        try {
            List<Supplier> suppliers = supplierDAO.getAllSuppliers();
            for (Supplier supplier : suppliers) {
                model.addRow(new Object[]{
                        supplier.getMaNhaCungCap(),
                        supplier.getTenNhaCungCap(),
                        supplier.getDiaChi(),
                        supplier.getSoDienThoai(),
                        supplier.getEmail()
                });
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
        }
    }
}
