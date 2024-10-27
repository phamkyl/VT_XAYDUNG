package qlvt.GuiView;

import qlvt.Controller.LoginController;
import qlvt.connect.PurchaseOrderDAO;
import qlvt.model.Employee;
import qlvt.model.PhieuNhap;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class MainView extends JFrame {
    private String userRole;
    private String userName;
    private int maChiNhanh; // Store branch ID
    private JPanel panel;

    // Constructor to initialize MainView with user details
    public MainView(String userRole, String userName, int maChiNhanh) {
        this.userRole = userRole;
        this.userName = userName;
        this.maChiNhanh = maChiNhanh; // Assign branch ID
        initialize(); // Call initialization method


    }



    // Method to initialize the GUI
    private void initialize() {
        setTitle("Giao Diện Chính"); // Set window title
        setSize(800, 600); // Set window size
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

        // Welcome label with user info
        JLabel welcomeLabel = new JLabel("Chào " + userName + " (" + userRole + ")");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18)); // Font style
        welcomeLabel.setForeground(new Color(3, 166, 120)); // Set text color
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding

        // Create a panel to hold the image and welcome label
        JPanel userInfoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Align to the left
        userInfoPanel.add(imageLabel); // Add image label first
        userInfoPanel.add(welcomeLabel); // Add welcome label next
        userInfoPanel.setBackground(Color.WHITE); // Set background color if needed

        // Add userInfoPanel to the main panel
        panel.add(userInfoPanel, BorderLayout.NORTH);

        // Create options panel
        JPanel optionsPanel = new JPanel(new GridLayout(3, 2, 10, 10)); // 3 rows, 2 columns with 10px spacing
        optionsPanel.setBackground(Color.WHITE);
        optionsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding

        // Add options based on user role
        showOptions(optionsPanel);

        panel.add(optionsPanel, BorderLayout.CENTER);


        // Add a new panel for personal information
        JPanel personalInfoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        personalInfoPanel.setBackground(Color.WHITE);
        personalInfoPanel.setBorder(BorderFactory.createTitledBorder("Thông tin cá nhân"));

// Create a table model to hold the personal information
        DefaultTableModel personalInfoModel = new DefaultTableModel(new Object[]{"Thông tin", "Giá trị"}, 0);

// Add the user's personal information to the table model
        personalInfoModel.addRow(new Object[]{"Tên", userName});
        personalInfoModel.addRow(new Object[]{"Vai trò", userRole});
        personalInfoModel.addRow(new Object[]{"Chi nhánh", String.valueOf(maChiNhanh)});

// Create a JTable to display the personal information
        JTable personalInfoTable = new JTable(personalInfoModel);
        personalInfoTable.setFont(new Font("Arial", Font.PLAIN, 14));
        personalInfoTable.setRowHeight(30);
        personalInfoTable.getColumnModel().getColumn(0).setPreferredWidth(200);
        personalInfoTable.getColumnModel().getColumn(1).setPreferredWidth(600);
        personalInfoTable.setBackground(Color.WHITE);
        personalInfoTable.setFillsViewportHeight(true);

// Add the table to a scroll pane
        JScrollPane personalInfoScrollPane = new JScrollPane(personalInfoTable);
        personalInfoScrollPane.setPreferredSize(new Dimension(800, 150));

// Add the scroll pane to the personalInfoPanel
        personalInfoPanel.add(personalInfoScrollPane);

// Add the personalInfoPanel to the main panel
        panel.add(personalInfoPanel, BorderLayout.SOUTH);
    }

    // Add options based on user role
    public void showOptions(JPanel optionsPanel) {
        if (userRole != null) {
            switch (userRole) {
                case "admin":
                    addAdminOptions(optionsPanel);
                    break;
                case "ADMIN0":
                    addManagerOptions(optionsPanel);
                    break;
                case "employee":
                    addEmployeeOptions(optionsPanel);
                    break;
                default:
                    JOptionPane.showMessageDialog(this, "Vai trò không hợp lệ.");
                    break;
            }
        } else {
            JOptionPane.showMessageDialog(this, "Về Trang chủ");
        }
    }

    // Add options for Quản lý - Admin
    private void addAdminOptions(JPanel optionsPanel) {
        addButton(optionsPanel, "Quản lý nhân viên", "image/team.png", this::openEmployeeManagementView);
        addButton(optionsPanel, "Quản lý chi nhánh", "image/organization.png", this::openBranchManagementView);
        addButton(optionsPanel, "Quản lý kho", "image/warehouse.png", this::openKhoManagementView);
        addButton(optionsPanel, "Quản lý vật tư", "image/supplies.png", this::openMaterialManagementView);
        addButton(optionsPanel, "Danh sách nhà cung cấp", "image/manufacture.png", this::openSupplierManagementView);
        addButton(optionsPanel, "Danh sách khách hàng", "image/customer-loyalty.png", this::openCustomerManagementView);
        addButton(optionsPanel, "Danh sách đơn hàng", "image/cargo.png", this::openOrderManagementView);
        addButton(optionsPanel, "Báo cáo - thống kê", "image/report.png", this::viewReports);
    }

    // Add options for Quản trị viên
    private void addManagerOptions(JPanel optionsPanel) {
        addButton(optionsPanel, "Quản lý nhân viên", "image/team.png", this::openEmployeeManagementView);
        addButton(optionsPanel, "Quản lý chi nhánh", "image/organization.png", this::openBranchManagementView);
        addButton(optionsPanel, "Quản lý kho", "image/warehouse.png", this::openKhoManagementView);
        addButton(optionsPanel, "Quản lý vật tư", "image/supplies.png", this::openMaterialManagementView);
        addButton(optionsPanel, "Danh sách nhà cung cấp", "image/manufacture.png", this::openSupplierManagementView);
        addButton(optionsPanel, "Danh sách khách hàng", "image/customer-loyalty.png", this::openCustomerManagementView);
        addButton(optionsPanel, "Danh sách đơn hàng", "image/cargo.png", this::openOrderManagementView);
        addButton(optionsPanel, "Báo cáo - thống kê", "image/report.png", this::viewReports);
    }



    // Add options for Employee
    private void addEmployeeOptions(JPanel optionsPanel) {
        //addButton(optionsPanel, "Quản lý nhân viên", "image/team.png", this::openEmployeeManagementView);
        //addButton(optionsPanel, "Quản lý chi nhánh", "image/organization.png", this::openBranchManagementView);
        addButton(optionsPanel, "Quản lý kho", "image/warehouse.png", this::openKhoManagementView);
        addButton(optionsPanel, "Quản lý vật tư", "image/supplies.png", this::openMaterialManagementView);
        addButton(optionsPanel, "Danh sách nhà cung cấp", "image/manufacture.png", this::openSupplierManagementView);
        addButton(optionsPanel, "Danh sách khách hàng", "image/customer-loyalty.png", this::openCustomerManagementView);
        addButton(optionsPanel, "Danh sách đơn hàng", "image/cargo.png", this::openOrderManagementView);
        addButton(optionsPanel, "Quản lý Phiếu Nhập ", "image/export.png", this::openExportManagementView);
        addButton(optionsPanel, "Quản lý Phiếu Xuất", "image/export.png", this::openExportXuatManagementView);

        addButton(optionsPanel, "Báo cáo - thống kê", "image/report.png", this::viewReports);

    }




    // Helper method to add a button to the options panel
    private void addButton(JPanel optionsPanel, String buttonText, String iconFilePath, Runnable action) {
        // Load and scale the icon image
        ImageIcon originalIcon = new ImageIcon(iconFilePath);
        Image scaledImage = originalIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH); // Kích thước mới
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        // Create the button with the icon and text
        JButton button = new JButton(buttonText, scaledIcon);
        button.setFont(new Font("Arial", Font.PLAIN, 16));
        button.setBackground(new Color(3, 166, 120));
        button.setForeground(Color.WHITE);
        button.setHorizontalTextPosition(JButton.RIGHT);
        button.setVerticalTextPosition(JButton.CENTER);
        button.addActionListener(e -> action.run());
        optionsPanel.add(button);
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

    private void openKhoManagementView() {
        try {
            WarehouseManagementView warehouseManagementView = new WarehouseManagementView(this, maChiNhanh,userRole);
            warehouseManagementView.setVisible(true);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi mở quản lý kho: " + e.getMessage());
        }
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

    private void openExportManagementView() {
        PurchaseOrderGUI ReceiptNoteView = new PurchaseOrderGUI(this);
        ReceiptNoteView.setVisible(true);
    }

    private void viewReports() {
        ReportGUI ReportGUI = new ReportGUI(this);

    }

    private void openExportXuatManagementView() {
    }

    private void manageEmployeesInBranch()
    {
        JOptionPane.showMessageDialog(this, "Quản lý nhân viên trong chi nhánh của mình được chọn.");
    }

    private void viewAllEmployees()
    {
        JOptionPane.showMessageDialog(this, "Danh sách nhân viên của cả hai chi nhánh được chọn.");
    }

    private void viewPersonalInfo() {
        JOptionPane.showMessageDialog(this, "Thông tin cá nhân được chọn.");
    }
}
