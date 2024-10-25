package qlvt.GuiView;

import qlvt.model.Branch; // Ensure you have a corresponding Branch class
import qlvt.connect.BranchDAO; // Ensure you have a DAO class for branch operations

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class BranchManagementView extends JDialog {
    private DefaultTableModel model;
    private BranchDAO branchDAO;
    private int maChiNhanh; // Variable to store branch ID

    private JLabel idLabel, nameLabel, addressLabel;

    public BranchManagementView(JFrame parent, int maChiNhanh) {
        super(parent, "Quản Lý Chi Nhánh", true);
        setSize(800, 600);
        setLocationRelativeTo(parent);
        this.maChiNhanh = maChiNhanh; // Initialize branch ID
        branchDAO = new BranchDAO();

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE); // Main background color
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Title label
        JLabel titleLabel = new JLabel("DANH SÁCH CHI NHÁNH");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setForeground(Color.DARK_GRAY);
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Create table to display branch list
        String[] columnNames = {"Mã Chi Nhánh", "Tên Chi Nhánh", "Địa Chỉ"};
        model = new DefaultTableModel(columnNames, 0);
        JTable branchTable = new JTable(model);
        branchTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        branchTable.setBackground(Color.WHITE);
        branchTable.setForeground(Color.BLACK);
        branchTable.getSelectionModel().addListSelectionListener(event -> {
            updateBranchDetails(branchTable);
        });
        JScrollPane scrollPane = new JScrollPane(branchTable);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Buttons for add, edit, delete, back, and exit
        JPanel buttonPanel = new JPanel(new GridLayout(1, 5, 10, 0));
        buttonPanel.setBackground(Color.WHITE);

        JButton addButton = new JButton("Thêm");
        JButton editButton = new JButton("Sửa");
        JButton deleteButton = new JButton("Xóa");
        JButton exitButton = new JButton("Thoát");
        JButton backButton = new JButton("Quay Lại");

        // Change button colors
        for (JButton button : new JButton[]{addButton, editButton, deleteButton, backButton, exitButton}) {
            button.setBackground(new Color(3, 166, 120));
            button.setForeground(Color.WHITE);
            button.setFont(new Font("Arial", Font.BOLD, 14));
            button.setFocusPainted(false);
        }

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(backButton);
        buttonPanel.add(exitButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
/*
        // Branch details panel
        JPanel branchDetailsPanel = new JPanel();
        branchDetailsPanel.setLayout(new GridLayout(3, 2, 5, 5));
        branchDetailsPanel.setBorder(BorderFactory.createTitledBorder("Thông tin chi nhánh"));
        branchDetailsPanel.setBackground(Color.WHITE);

        idLabel = new JLabel();
        nameLabel = new JLabel();
        addressLabel = new JLabel();

        branchDetailsPanel.add(new JLabel("Mã Chi Nhánh:"));
        branchDetailsPanel.add(idLabel);
        branchDetailsPanel.add(new JLabel("Tên Chi Nhánh:"));
        branchDetailsPanel.add(nameLabel);
        branchDetailsPanel.add(new JLabel("Địa Chỉ:"));
        branchDetailsPanel.add(addressLabel);

        mainPanel.add(branchDetailsPanel, BorderLayout.EAST);

 */

        // Action for Add button
        addButton.addActionListener(e -> {
            addBranch();
        });

        // Action for Edit button
        editButton.addActionListener(e -> {
            editBranch(branchTable);
        });

        // Action for Delete button
        deleteButton.addActionListener(e -> {
            deleteBranch(branchTable);
        });

        // Action for Exit button
        exitButton.addActionListener(e -> {
            System.exit(0);
        });

        // Action for Back button
        backButton.addActionListener(e -> {
            this.dispose(); // Close the branch management window
            openMainView(); // Open the main view
        });

        loadBranches(); // Load branches
        setContentPane(mainPanel);
    }

    private void updateBranchDetails(JTable branchTable) {
        int selectedRow = branchTable.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) model.getValueAt(selectedRow, 0);
            String name = (String) model.getValueAt(selectedRow, 1);
            String address = (String) model.getValueAt(selectedRow, 2);

            idLabel.setText(String.valueOf(id));
            nameLabel.setText(name);
            addressLabel.setText(address);
        } else {
            clearBranchDetails();
        }
    }

    private void clearBranchDetails() {
        idLabel.setText("");
        nameLabel.setText("");
        addressLabel.setText("");
    }

    private void addBranch() {
        // Input branch information
        String idStr = JOptionPane.showInputDialog("Nhập Mã Chi Nhánh:");
        String name = JOptionPane.showInputDialog("Nhập Tên Chi Nhánh:");
        String address = JOptionPane.showInputDialog("Nhập Địa Chỉ:");

        if (idStr != null && name != null && address != null) {
            try {
                int id = Integer.parseInt(idStr);
                Branch newBranch = new Branch(id, name, address);
                branchDAO.addBranch(newBranch);
                model.addRow(new Object[]{id, name, address});
            } catch (NumberFormatException | SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
            }
        }
    }

    private void editBranch(JTable branchTable) {
        int selectedRow = branchTable.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) model.getValueAt(selectedRow, 0);
            String name = JOptionPane.showInputDialog("Nhập Tên Chi Nhánh:", model.getValueAt(selectedRow, 1));
            String address = JOptionPane.showInputDialog("Nhập Địa Chỉ:", model.getValueAt(selectedRow, 2));

            if (name != null && address != null) {
                try {
                    Branch updatedBranch = new Branch(id, name, address);
                    branchDAO.updateBranch(updatedBranch);
                    model.setValueAt(name, selectedRow, 1);
                    model.setValueAt(address, selectedRow, 2);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một chi nhánh để sửa.");
        }
    }

    private void deleteBranch(JTable branchTable) {
        int selectedRow = branchTable.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) model.getValueAt(selectedRow, 0);
            try {
                branchDAO.deleteBranch(id);
                model.removeRow(selectedRow);
                clearBranchDetails();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một chi nhánh để xóa.");
        }
    }

    private void openMainView() {
        // Replace with actual values you want to pass
        String userRole = "Admin"; // Example user role, replace with actual
        String userName = "User"; // Example user name, replace with actual

        MainView mainView = new MainView(userRole, userName, maChiNhanh); // Create MainView with parameters
        mainView.setVisible(true); // Show MainView
    }

    private void loadBranches() {
        try {
            List<Branch> branches = branchDAO.getAllBranches(maChiNhanh);
            for (Branch branch : branches) {
                model.addRow(new Object[]{
                        branch.getMaChiNhanh(),
                        branch.getTenChiNhanh(),
                        branch.getDiaChi()
                });
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
        }
    }
}
