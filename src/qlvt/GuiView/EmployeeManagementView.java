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

    private JLabel idLabel, nameLabel, roleLabel, branchLabel, permissionLabel;

    public EmployeeManagementView(JFrame parent, int maChiNhanh) {
        super(parent, "Quản Lý Nhân Viên", true);
        setSize(800, 600);
        setLocationRelativeTo(parent);
        this.maChiNhanh = maChiNhanh; // Initialize branch ID
        employeeDAO = new EmployeeDAO();

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE); // Màu nền chính
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Add a title label
        JLabel titleLabel = new JLabel("DANH SÁCH NHÂN VIÊN");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setForeground(Color.DARK_GRAY); // Màu chữ tiêu đề
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Create table to display employee list
        String[] columnNames = {"Mã NV", "Họ Tên", "Chức Vụ", "Mã Chi Nhánh", "Phân Quyền"};
        model = new DefaultTableModel(columnNames, 0);
        JTable employeeTable = new JTable(model);
        employeeTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        employeeTable.setBackground(Color.WHITE); // Màu nền cho bảng
        employeeTable.setForeground(Color.BLACK); // Màu chữ cho bảng
        employeeTable.getSelectionModel().addListSelectionListener(event -> {
            updateEmployeeDetails(employeeTable);
        });
        JScrollPane scrollPane = new JScrollPane(employeeTable);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Add buttons for add, edit, delete, back, and exit
        JPanel buttonPanel = new JPanel(new GridLayout(1, 5, 10, 0));
        buttonPanel.setBackground(Color.WHITE); // Màu nền cho panel chứa nút

        JButton addButton = new JButton("Thêm");
        JButton editButton = new JButton("Sửa");
        JButton deleteButton = new JButton("Xóa");
        JButton exitButton = new JButton("Thoát");
        JButton backButton = new JButton("Quay Lại");

        // Thay đổi màu sắc cho các nút
        for (JButton button : new JButton[]{addButton, editButton, deleteButton, backButton, exitButton}) {
            button.setBackground(new Color(3, 166, 120)); // Màu nền nút
            button.setForeground(Color.WHITE); // Màu chữ nút
            button.setFont(new Font("Arial", Font.BOLD, 14)); // Kiểu chữ nút
            button.setFocusPainted(false); // Tắt viền khi nhấn
        }

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(backButton);
        buttonPanel.add(exitButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Add employee details panel
        JPanel employeeDetailsPanel = new JPanel();
        employeeDetailsPanel.setLayout(new GridLayout(5, 2, 5, 5)); // Giảm khoảng cách giữa các thành phần
        employeeDetailsPanel.setBorder(BorderFactory.createTitledBorder("Thông tin nhân viên"));
        employeeDetailsPanel.setBackground(Color.WHITE); // Màu nền cho panel chi tiết

        idLabel = new JLabel();
        nameLabel = new JLabel();
        roleLabel = new JLabel();
        branchLabel = new JLabel();
        permissionLabel = new JLabel();

        // Tạo các JLabel cho tiêu đề và điều chỉnh màu sắc
        JLabel idLabelTitle = new JLabel("Mã NV:");
        idLabelTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
        idLabelTitle.setForeground(new Color(3, 166, 120)); // Màu chữ xanh

        JLabel nameLabelTitle = new JLabel("Họ Tên:");
        nameLabelTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
        nameLabelTitle.setForeground(new Color(3, 166, 120)); // Màu chữ xanh

        JLabel roleLabelTitle = new JLabel("Chức Vụ:");
        roleLabelTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
        roleLabelTitle.setForeground(new Color(3, 166, 120)); // Màu chữ xanh

        JLabel branchLabelTitle = new JLabel("Mã Chi Nhánh:");
        branchLabelTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
        branchLabelTitle.setForeground(new Color(3, 166, 120)); // Màu chữ xanh

        JLabel permissionLabelTitle = new JLabel("Phân Quyền:");
        permissionLabelTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
        permissionLabelTitle.setForeground(new Color(3, 166, 120)); // Màu chữ xanh

        // Thay đổi màu nền cho các JLabel
        idLabel.setBackground(Color.WHITE);
        nameLabel.setBackground(Color.WHITE);
        roleLabel.setBackground(Color.WHITE);
        branchLabel.setBackground(Color.WHITE);
        permissionLabel.setBackground(Color.WHITE);

        // Đặt màu nền cho các JLabel thành có thể nhìn thấy
        idLabel.setOpaque(true);
        nameLabel.setOpaque(true);
        roleLabel.setOpaque(true);
        branchLabel.setOpaque(true);
        permissionLabel.setOpaque(true);

        employeeDetailsPanel.add(idLabelTitle);
        employeeDetailsPanel.add(idLabel);
        employeeDetailsPanel.add(nameLabelTitle);
        employeeDetailsPanel.add(nameLabel);
        employeeDetailsPanel.add(roleLabelTitle);
        employeeDetailsPanel.add(roleLabel);
        employeeDetailsPanel.add(branchLabelTitle);
        employeeDetailsPanel.add(branchLabel);
        employeeDetailsPanel.add(permissionLabelTitle);
        employeeDetailsPanel.add(permissionLabel);

        mainPanel.add(employeeDetailsPanel, BorderLayout.EAST);

        // Action for Add button
        addButton.addActionListener(e -> {
            addEmployee(employeeTable);
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
        setContentPane(mainPanel);
    }

    private void updateEmployeeDetails(JTable employeeTable) {
        int selectedRow = employeeTable.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) model.getValueAt(selectedRow, 0);
            String name = (String) model.getValueAt(selectedRow, 1);
            String role = (String) model.getValueAt(selectedRow, 2);
            int branchId = (int) model.getValueAt(selectedRow, 3);
            String permission = (String) model.getValueAt(selectedRow, 4);

            idLabel.setText(String.valueOf(id));
            nameLabel.setText(name);
            roleLabel.setText(role);
            branchLabel.setText(String.valueOf(branchId));
            permissionLabel.setText(permission);
        } else {
            clearEmployeeDetails();
        }
    }

    private void clearEmployeeDetails() {
        idLabel.setText("");
        nameLabel.setText("");
        roleLabel.setText("");
        branchLabel.setText("");
        permissionLabel.setText("");
    }

    private void addEmployee(JTable employeeTable) {
        // Tạo dialog cho việc nhập thông tin nhân viên
        JDialog addEmployeeDialog = new JDialog(this, "Thêm Nhân Viên", true);
        addEmployeeDialog.setSize(400, 300);
        addEmployeeDialog.setLocationRelativeTo(this);

        // Tạo panel cho dialog
        JPanel panel = new JPanel(new GridLayout(6, 2, 5, 5));
        panel.setBackground(Color.WHITE); // Màu nền cho panel

        // Tạo các trường nhập liệu
        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField roleField = new JTextField();
        JTextField branchIdField = new JTextField();
        JTextField permissionField = new JTextField();
        JTextField passwordField = new JTextField();

        // Thay đổi màu nền cho các JTextField
        Color textFieldBackground = Color.WHITE;
        idField.setBackground(textFieldBackground);
        nameField.setBackground(textFieldBackground);
        roleField.setBackground(textFieldBackground);
        branchIdField.setBackground(textFieldBackground);
        permissionField.setBackground(textFieldBackground);
        passwordField.setBackground(textFieldBackground);

        // Thêm các label và trường nhập liệu vào panel
        panel.add(new JLabel("Mã NV:"));
        panel.add(idField);
        panel.add(new JLabel("Họ Tên:"));
        panel.add(nameField);
        panel.add(new JLabel("Chức Vụ:"));
        panel.add(roleField);
        panel.add(new JLabel("Mã Chi Nhánh:"));
        panel.add(branchIdField);
        panel.add(new JLabel("Phân Quyền:"));
        panel.add(permissionField);
        panel.add(new JLabel("Mật Khẩu:"));
        panel.add(passwordField);

        // Thay đổi màu sắc cho các JLabel
        for (Component comp : panel.getComponents()) {
            if (comp instanceof JLabel) {
                comp.setForeground(new Color(3, 166, 120)); // Màu chữ cho JLabel
            }
        }

        // Thêm panel vào dialog
        addEmployeeDialog.add(panel, BorderLayout.CENTER);

        // Nút xác nhận
        JButton confirmButton = new JButton("Xác Nhận");
        confirmButton.setBackground(new Color(3, 166, 120)); // Màu nền nút
        confirmButton.setForeground(Color.WHITE); // Màu chữ nút
        confirmButton.addActionListener(e -> {
            String idStr = idField.getText();
            String name = nameField.getText();
            String role = roleField.getText();
            String branchIdStr = branchIdField.getText();
            String permission = permissionField.getText();
            String password = passwordField.getText();

            if (idStr != null && name != null && role != null && branchIdStr != null && permission != null && password != null) {
                try {
                    int id = Integer.parseInt(idStr);
                    int branchId = Integer.parseInt(branchIdStr);
                    Employee newEmployee = new Employee(id, name, role, branchId, permission, password);
                    employeeDAO.addEmployee(newEmployee);
                    model.addRow(new Object[]{id, name, role, branchId, permission});
                    addEmployeeDialog.dispose(); // Đóng dialog sau khi thêm
                } catch (NumberFormatException | SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(addEmployeeDialog, "Lỗi: " + ex.getMessage());
                }
            }
        });

        addEmployeeDialog.add(confirmButton, BorderLayout.SOUTH);
        addEmployeeDialog.setVisible(true); // Hiện dialog
    }

    private void editEmployee(JTable employeeTable) {
        int selectedRow = employeeTable.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) model.getValueAt(selectedRow, 0);

            // Tạo dialog cho việc sửa thông tin nhân viên
            JDialog editEmployeeDialog = new JDialog(this, "Sửa Nhân Viên", true);
            editEmployeeDialog.setSize(400, 300);
            editEmployeeDialog.setLocationRelativeTo(this);

            // Tạo panel cho dialog
            JPanel panel = new JPanel(new GridLayout(6, 2, 5, 5));
            panel.setBackground(Color.WHITE); // Màu nền cho panel

            // Tạo các trường nhập liệu
            JTextField nameField = new JTextField((String) model.getValueAt(selectedRow, 1));
            JTextField roleField = new JTextField((String) model.getValueAt(selectedRow, 2));
            JTextField branchIdField = new JTextField(String.valueOf(model.getValueAt(selectedRow, 3)));
            JTextField permissionField = new JTextField((String) model.getValueAt(selectedRow, 4));
            JTextField passwordField = new JTextField(); // Mật khẩu mới

            // Thêm các label và trường nhập liệu vào panel
            panel.add(new JLabel("Họ Tên:"));
            panel.add(nameField);
            panel.add(new JLabel("Chức Vụ:"));
            panel.add(roleField);
            panel.add(new JLabel("Mã Chi Nhánh:"));
            panel.add(branchIdField);
            panel.add(new JLabel("Phân Quyền:"));
            panel.add(permissionField);
            panel.add(new JLabel("Mật Khẩu (mới):"));
            panel.add(passwordField);

            // Thay đổi màu sắc cho các JLabel
            for (Component comp : panel.getComponents()) {
                if (comp instanceof JLabel) {
                    comp.setForeground(new Color(3, 166, 120)); // Màu chữ cho JLabel
                }
            }

            // Thay đổi màu nền cho các JTextField
            nameField.setBackground(Color.WHITE);
            roleField.setBackground(Color.WHITE);
            branchIdField.setBackground(Color.WHITE);
            permissionField.setBackground(Color.WHITE);
            passwordField.setBackground(Color.WHITE);

            // Thêm panel vào dialog
            editEmployeeDialog.add(panel, BorderLayout.CENTER);

            // Nút xác nhận
            JButton confirmButton = new JButton("Xác Nhận");
            confirmButton.setBackground(new Color(3, 166, 120)); // Màu nền nút
            confirmButton.setForeground(Color.WHITE); // Màu chữ nút
            confirmButton.addActionListener(e -> {
                String name = nameField.getText();
                String role = roleField.getText();
                String branchIdStr = branchIdField.getText();
                String permission = permissionField.getText();
                String password = passwordField.getText(); // Lấy mật khẩu mới

                if (branchIdStr != null && permission != null) {
                    try {
                        int branchId = Integer.parseInt(branchIdStr);
                        Employee updatedEmployee = new Employee(id, name, role, branchId, permission, password);
                        employeeDAO.updateEmployee(updatedEmployee);
                        model.setValueAt(name, selectedRow, 1);
                        model.setValueAt(role, selectedRow, 2);
                        model.setValueAt(branchId, selectedRow, 3);
                        model.setValueAt(permission, selectedRow, 4);
                        updateEmployeeDetails(employeeTable);
                        editEmployeeDialog.dispose(); // Đóng dialog sau khi sửa
                    } catch (NumberFormatException | SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(editEmployeeDialog, "Lỗi: " + ex.getMessage());
                    }
                }
            });

            editEmployeeDialog.add(confirmButton, BorderLayout.SOUTH);
            editEmployeeDialog.setVisible(true); // Hiện dialog
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
                clearEmployeeDetails();
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