package qlvt.GuiView;

import qlvt.model.Employee;
import qlvt.connect.EmployeeDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class EmployeeManagementView extends JDialog {
    private DefaultTableModel model;
    private EmployeeDAO employeeDAO;
    private int maChiNhanh; // Variable to store branch ID

    public EmployeeManagementView(JFrame parent, int maChiNhanh) {
        super(parent, "Quản Lý Nhân Viên", true);
        setSize(600, 450);
        setLocationRelativeTo(parent);
        this.maChiNhanh = maChiNhanh; // Initialize branch ID
        employeeDAO = new EmployeeDAO();

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Create table to display employee list
        String[] columnNames = {"Mã NV", "Họ Tên", "Chức Vụ", "Mã Chi Nhánh", "Phân Quyền"};
        model = new DefaultTableModel(columnNames, 0);
        JTable employeeTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(employeeTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Add buttons for add, edit, delete, back, and exit
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

        // Action for Add button
        addButton.addActionListener(e -> {
            addEmployee();
        });

        // Action for Edit button
        editButton.addActionListener(e -> {
            editEmployee(employeeTable);
        });

        // Action for Delete button
        deleteButton.addActionListener(e -> {
            deleteEmployee(employeeTable);
        });

        // Action for Exit button
        exitButton.addActionListener(e -> {
            System.exit(0);
        });

        // Action for Back button
        backButton.addActionListener(e -> {
            this.dispose(); // Close the employee management window
            openMainView(); // Open the main view
        });

        loadEmployees(); // Load employees
        setContentPane(panel);
    }

    private void addEmployee() {
        // Input employee information
        String idStr = JOptionPane.showInputDialog("Nhập Mã Nhân Viên:");
        String name = JOptionPane.showInputDialog("Nhập Họ Tên:");
        String role = JOptionPane.showInputDialog("Nhập Chức Vụ:");
        String branchIdStr = JOptionPane.showInputDialog("Nhập Mã Chi Nhánh:");
        String permission = JOptionPane.showInputDialog("Nhập Phân Quyền:");
        String password = JOptionPane.showInputDialog("Nhập Mật Khẩu:");

        if (idStr != null && name != null && role != null && branchIdStr != null && permission != null && password != null) {
            try {
                int id = Integer.parseInt(idStr);
                int branchId = Integer.parseInt(branchIdStr);
                Employee newEmployee = new Employee(id, name, role, branchId, permission, password);
                employeeDAO.addEmployee(newEmployee);
                model.addRow(new Object[]{id, name, role, branchId, permission});
            } catch (NumberFormatException | SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
            }
        }
    }

    private void editEmployee(JTable employeeTable) {
        int selectedRow = employeeTable.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) model.getValueAt(selectedRow, 0);
            String name = JOptionPane.showInputDialog("Nhập Họ Tên:", model.getValueAt(selectedRow, 1));
            String role = JOptionPane.showInputDialog("Nhập Chức Vụ:", model.getValueAt(selectedRow, 2));
            String branchIdStr = JOptionPane.showInputDialog("Nhập Mã Chi Nhánh:", model.getValueAt(selectedRow, 3));
            String permission = JOptionPane.showInputDialog("Nhập Phân Quyền:", model.getValueAt(selectedRow, 4));
            String password = JOptionPane.showInputDialog("Nhập Mật Khẩu:");

            if (branchIdStr != null && permission != null && password != null) {
                try {
                    int branchId = Integer.parseInt(branchIdStr);
                    Employee updatedEmployee = new Employee(id, name, role, branchId, permission, password);
                    employeeDAO.updateEmployee(updatedEmployee);
                    model.setValueAt(name, selectedRow, 1);
                    model.setValueAt(role, selectedRow, 2);
                    model.setValueAt(branchId, selectedRow, 3);
                    model.setValueAt(permission, selectedRow, 4);
                } catch (NumberFormatException | SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một nhân viên để sửa.");
        }
    }

    private void deleteEmployee(JTable employeeTable) {
        int selectedRow = employeeTable.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) model.getValueAt(selectedRow, 0);
            int branchId = (int) model.getValueAt(selectedRow, 3);
            try {
                employeeDAO.deleteEmployee(id, branchId);
                model.removeRow(selectedRow);
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một nhân viên để xóa.");
        }
    }

    private void openMainView() {
        // Replace these with the actual values you want to pass
        String userRole = "Admin"; // Example role, replace with actual
        String userName = "User"; // Example user name, replace with actual
        int maChiNhanh = this.maChiNhanh; // Use the current branch ID

        MainView mainView = new MainView(userRole, userName, maChiNhanh); // Create MainView with parameters
        mainView.setVisible(true); // Show the MainView
    }

    private void loadEmployees() {
        try {
            List<Employee> employees = employeeDAO.getAllEmployees(maChiNhanh);
            for (Employee employee : employees) {
                model.addRow(new Object[]{
                        employee.getMaNhanVien(),
                        employee.getHoTen(),
                        employee.getChucVu(),
                        employee.getMaChiNhanh(),
                        employee.getPhanQuyen()
                });
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
        }
    }
}
