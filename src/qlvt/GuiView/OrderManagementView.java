package qlvt.GuiView;

import qlvt.model.Order;
import qlvt.connect.OrderDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class OrderManagementView extends JDialog {
    private DefaultTableModel model;
    private OrderDAO orderDAO;
    private String userRole;

    public OrderManagementView(JFrame parent) {
        super(parent, "Quản Lý Đơn Hàng", true);
        this.userRole = userRole; // Lưu vai trò người dùng
        setSize(600, 450);
        setLocationRelativeTo(parent);
        orderDAO = new OrderDAO();

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Tạo bảng để hiển thị danh sách đơn hàng
        String[] columnNames = {"Mã Đơn Hàng", "Mã Khách Hàng", "Ngày Đặt", "Tình Trạng Đơn Hàng"};
        model = new DefaultTableModel(columnNames, 0);
        JTable orderTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(orderTable);
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
            if (userRole.equals("Quản trị viên")) {
                String maDonHangStr = JOptionPane.showInputDialog("Nhập Mã Đơn Hàng:");
                String maKhachHangStr = JOptionPane.showInputDialog("Nhập Mã Khách Hàng:");
                String ngayDatStr = JOptionPane.showInputDialog("Nhập Ngày Đặt (yyyy-MM-dd):");
                String tinhTrangDonHang = JOptionPane.showInputDialog("Nhập Tình Trạng Đơn Hàng:");

                if (maDonHangStr != null && maKhachHangStr != null && ngayDatStr != null && tinhTrangDonHang != null) {
                    try {
                        int maDonHang = Integer.parseInt(maDonHangStr);
                        int maKhachHang = Integer.parseInt(maKhachHangStr);
                        Order newOrder = new Order(maDonHang, maKhachHang, java.sql.Date.valueOf(ngayDatStr), tinhTrangDonHang);
                        orderDAO.addOrder(newOrder);
                        model.addRow(new Object[]{maDonHang, maKhachHang, ngayDatStr, tinhTrangDonHang});
                    } catch (NumberFormatException | SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Bạn không có quyền thêm đơn hàng.");
            }
        });

        // Action cho nút Sửa
        editButton.addActionListener(e -> {
            if (userRole.equals("Quản trị viên")) {
                int selectedRow = orderTable.getSelectedRow();
                if (selectedRow != -1) {
                    int maDonHang = (int) model.getValueAt(selectedRow, 0);
                    String maKhachHangStr = JOptionPane.showInputDialog("Nhập Mã Khách Hàng:", model.getValueAt(selectedRow, 1));
                    String ngayDatStr = JOptionPane.showInputDialog("Nhập Ngày Đặt (yyyy-MM-dd):", model.getValueAt(selectedRow, 2));
                    String tinhTrangDonHang = JOptionPane.showInputDialog("Nhập Tình Trạng Đơn Hàng:", model.getValueAt(selectedRow, 3));

                    if (maKhachHangStr != null && ngayDatStr != null && tinhTrangDonHang != null) {
                        try {
                            int maKhachHang = Integer.parseInt(maKhachHangStr);
                            Order updatedOrder = new Order(maDonHang, maKhachHang, java.sql.Date.valueOf(ngayDatStr), tinhTrangDonHang);
                            orderDAO.updateOrder(updatedOrder);
                            model.setValueAt(maKhachHangStr, selectedRow, 1);
                            model.setValueAt(ngayDatStr, selectedRow, 2);
                            model.setValueAt(tinhTrangDonHang, selectedRow, 3);
                        } catch (NumberFormatException | SQLException ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
                        }
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Bạn không có quyền sửa đơn hàng.");
            }
        });

        // Action cho nút Xóa
        deleteButton.addActionListener(e -> {
            if (userRole.equals("Quản trị viên")) {
                int selectedRow = orderTable.getSelectedRow();
                if (selectedRow != -1) {
                    int maDonHang = (int) model.getValueAt(selectedRow, 0);
                    try {
                        orderDAO.deleteOrder(maDonHang);
                        model.removeRow(selectedRow);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Bạn không có quyền xóa đơn hàng.");
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


        loadOrders();
        setContentPane(panel);
    }

    private void loadOrders() {
        try {
            List<Order> orders = orderDAO.getAllOrders();
            for (Order order : orders) {
                model.addRow(new Object[]{
                        order.getMaDonHang(),
                        order.getMaKhachHang(),
                        order.getNgayDat(),
                        order.getTinhTrangDonHang()
                });
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
        }
    }
}
