package qlvt.GuiView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class MainView extends JFrame {
    private String userRole;
    private String userName;
    private int maChiNhanh; // Store branch ID
    private JPanel panel;
    private JTable optionsTable;

    // Constructor to initialize MainView with user details
    public MainView(String userRole, String userName, int maChiNhanh) {
        this.userRole = userRole;
        this.userName = userName;
        this.maChiNhanh = maChiNhanh; // Assign branch ID
        initialize(); // Call initialization method
    }

    public MainView() {

    }



    // Method to initialize the GUI
    private void initialize() {
        setTitle("Giao Diện Chính"); // Set window title
        setSize(400, 400); // Set window size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window
        setResizable(false); // Disable resizing

        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.WHITE);
        add(panel);

        // Load image icon and resize it
        ImageIcon originalIcon = new ImageIcon("image/account.png");
        Image scaledImage = originalIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH); // Resize image
        ImageIcon resizedIcon = new ImageIcon(scaledImage);
        JLabel imageLabel = new JLabel(resizedIcon); // Create label for the image

        // Welcome to label with user info
        JLabel welcomeLabel = new JLabel("Chào " + userName + " (" + userRole + ")");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18)); // Font style
        welcomeLabel.setForeground(new Color(0, 102, 204)); // Set text color
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding

        // Create a panel to hold the image and welcome label
        JPanel userInfoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Align to the left
        userInfoPanel.add(imageLabel); // Add image label first
        userInfoPanel.add(welcomeLabel); // Add welcome label next
        userInfoPanel.setBackground(Color.WHITE); // Set background color if needed

        // Add userInfoPanel to the main panel
        panel.add(userInfoPanel, BorderLayout.NORTH);


        // Create options table
        String[] columnNames = {"Tùy Chọn"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        optionsTable = new JTable(model);
        optionsTable.setFillsViewportHeight(true);
        optionsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Single selection mode
        optionsTable.setRowHeight(30); // Set row height for better appearance

        // Add options based on user role
        showOptions(model); // Populate table with role-specific options

        JScrollPane scrollPane = new JScrollPane(optionsTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Add event listener for option selection
        optionsTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = optionsTable.getSelectedRow();
                if (selectedRow != -1) {
                    String selectedOption = (String) optionsTable.getValueAt(selectedRow, 0);
                    handleOptionSelection(selectedOption); // Handle selected option
                }
            }
        });
    }

    // Method to display options based on user role
    public void showOptions(DefaultTableModel model) {
        model.setRowCount(0); // Clear current rows before adding new ones

        if (userRole != null) {
            switch (userRole) {
                case "admin":
                    addAdminOptions(model); // Admin options
                    break;
                case "Quản lý":
                    addManagerOptions(model); // Manager options
                    break;
                case "employee":
                    addEmployeeOptions(model); // Employee options
                    break;
                default:
                    JOptionPane.showMessageDialog(this, "Vai trò không hợp lệ.");
                    break;
            }
        } else {
            JOptionPane.showMessageDialog(this, "Về Trang chủ");
        }
    }

    // Add options for Admin
    private void addAdminOptions(DefaultTableModel model) {
        model.addRow(new Object[]{"Quản lý nhân viên"});
        model.addRow(new Object[]{"Quản lý chi nhánh"});
        model.addRow(new Object[]{"Quản lý kho"});
        model.addRow(new Object[]{"Quản lý vật tư"});
        model.addRow(new Object[]{"Danh sách nhà cung cấp"});
        model.addRow(new Object[]{"Danh sách khách hàng"});
        model.addRow(new Object[]{"Danh sách đơn hàng"});
        model.addRow(new Object[]{"Báo cáo - thống kê"});
    }

    // Add options for Manager
    private void addManagerOptions(DefaultTableModel model) {
        model.addRow(new Object[]{"Quản lý nhân viên (chi nhánh của mình)"});
        model.addRow(new Object[]{"Xem danh sách nhân viên (cả hai chi nhánh)"});
        model.addRow(new Object[]{"Quản lý kho"});
        model.addRow(new Object[]{"Quản lý vật tư"});
        model.addRow(new Object[]{"Danh sách nhà cung cấp"});
        model.addRow(new Object[]{"Danh sách khách hàng"});
        model.addRow(new Object[]{"Danh sách đơn hàng"});
        model.addRow(new Object[]{"Báo cáo - thống kê"});
    }

    // Add options for Employee
    private void addEmployeeOptions(DefaultTableModel model) {
        model.addRow(new Object[]{"Xem danh sách nhân viên (cả hai chi nhánh)"});
        model.addRow(new Object[]{"Quản lý kho"});
        model.addRow(new Object[]{"Quản lý vật tư"});
        model.addRow(new Object[]{"Danh sách nhà cung cấp"});
        model.addRow(new Object[]{"Danh sách khách hàng"});
        model.addRow(new Object[]{"Danh sách đơn hàng"});
        model.addRow(new Object[]{"Báo cáo - thống kê"});
    }

    // Handle selection of options
    private void handleOptionSelection(String selectedOption) {
        Map<String, Runnable> optionActions = new HashMap<>();
        optionActions.put("Quản lý nhân viên", this::openEmployeeManagementView);
        optionActions.put("Quản lý chi nhánh", this::openBranchManagementView);
        optionActions.put("Quản lý kho", this::openKhoManagementView);
        optionActions.put("Quản lý vật tư", this::openMaterialManagementView);
        optionActions.put("Danh sách nhà cung cấp", this::openSupplierManagementView);
        optionActions.put("Danh sách khách hàng", this::openCustomerManagementView);
        optionActions.put("Danh sách đơn hàng", this::openOrderManagementView);
        optionActions.put("Báo cáo - thống kê", this::viewReports);
        optionActions.put("Quản lý nhân viên (chi nhánh của mình)", this::manageEmployeesInBranch);
        optionActions.put("Xem danh sách nhân viên (cả hai chi nhánh)", this::viewAllEmployees);
        optionActions.put("Xem thông tin cá nhân", this::viewPersonalInfo);

        Runnable action = optionActions.get(selectedOption);
        if (action != null) {
            action.run(); // Run the selected action
        } else {
            JOptionPane.showMessageDialog(this, "Tùy chọn không hợp lệ.");
        }
    }

    // Methods for each option (these would open different views or perform actions)

    private void openEmployeeManagementView() {
        EmployeeManagementView employeeManagementView = new EmployeeManagementView(this, maChiNhanh); // Pass branch ID
        employeeManagementView.setVisible(true);
    }

    private void openBranchManagementView() {
        BranchManagementView branchManagementView = new BranchManagementView(this, maChiNhanh);
        branchManagementView.setVisible(true);
    }

    private void openKhoManagementView()  {
        WarehouseManagementView warehouseManagementView = null;
        try {
            warehouseManagementView = new WarehouseManagementView(this,maChiNhanh);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        warehouseManagementView.setVisible(true);
    }

    private void openMaterialManagementView() {
        MaterialManagementView materialManagementView = new MaterialManagementView(this);
        materialManagementView.setVisible(true);
    }

    private void openSupplierManagementView() {
        SupplierManagementView supplierManagementView = new SupplierManagementView(this);
        supplierManagementView.setVisible(true);
    }

    private void openCustomerManagementView() {
        CustomerManagementView customerManagementView = new CustomerManagementView(this);
        customerManagementView.setVisible(true);
    }

    private void openOrderManagementView() {
        OrderManagementView orderManagementView = new OrderManagementView(this);
        orderManagementView.setVisible(true);
    }

    private void viewReports() {
        JOptionPane.showMessageDialog(this, "Xem báo cáo được chọn.");
    }

    private void manageEmployeesInBranch() {
        JOptionPane.showMessageDialog(this, "Quản lý nhân viên trong chi nhánh của mình được chọn.");
    }

    private void viewAllEmployees() {
        JOptionPane.showMessageDialog(this, "Danh sách nhân viên của cả hai chi nhánh được chọn.");
    }

    private void viewPersonalInfo() {
        JOptionPane.showMessageDialog(this, "Thông tin cá nhân được chọn.");
    }
}
