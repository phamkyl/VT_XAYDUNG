package qlvt.GuiView;



import qlvt.connect.CustomerDAO;
import qlvt.model.Customer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class CustomerManagementView extends JDialog {
    private DefaultTableModel model;
    private CustomerDAO customerDAO;

    public CustomerManagementView(JFrame parent) {
        super(parent, "Quản Lý Khách Hàng", true);
        setSize(600, 450);
        setLocationRelativeTo(parent);

        try {
            customerDAO = new CustomerDAO();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi kết nối tới cơ sở dữ liệu: " + e.getMessage());
            return;
        }

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Tạo bảng để hiển thị danh sách khách hàng
        String[] columnNames = {"Mã Khách Hàng", "Họ Tên", "Địa Chỉ", "Số Điện Thoại", "Email"};
        model = new DefaultTableModel(columnNames, 0);
        JTable customerTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(customerTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        loadCustomers();
        setContentPane(panel);
    }

    private void loadCustomers() {
        try {
            List<Customer> customers = customerDAO.getAllCustomers();
            for (Customer customer : customers) {
                model.addRow(new Object[]{
                        customer.getMaKhachHang(),
                        customer.getHoTen(),
                        customer.getDiaChi(),
                        customer.getSoDienThoai(),
                        customer.getEmail()
                });
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
        }
    }
}

