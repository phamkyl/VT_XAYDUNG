package qlvt.GuiView;

import qlvt.model.Branch; // Đảm bảo bạn có lớp Branch tương ứng
import qlvt.connect.BranchDAO; // Đảm bảo bạn có lớp DAO để thao tác với chi nhánh

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class BranchManagementView extends JDialog {
    private DefaultTableModel model;
    private BranchDAO branchDAO;
    private int maChiNhanh; // Biến để lưu ID chi nhánh

    public BranchManagementView(JFrame parent, int maChiNhanh) {
        super(parent, "Quản Lý Chi Nhánh", true);
        setSize(600, 450);
        setLocationRelativeTo(parent);
        this.maChiNhanh = maChiNhanh; // Khởi tạo ID chi nhánh
        branchDAO = new BranchDAO();

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Tạo bảng để hiển thị danh sách chi nhánh
        String[] columnNames = {"Mã Chi Nhánh", "Tên Chi Nhánh", "Địa Chỉ"};
        model = new DefaultTableModel(columnNames, 0);
        JTable branchTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(branchTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Thêm các nút để thêm, sửa, xóa, quay lại và thoát
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

        // Hành động cho nút Thêm
        addButton.addActionListener(e -> {
            addBranch();
        });

        // Hành động cho nút Sửa
        editButton.addActionListener(e -> {
            editBranch(branchTable);
        });

        // Hành động cho nút Xóa
        deleteButton.addActionListener(e -> {
            deleteBranch(branchTable);
        });

        // Hành động cho nút Thoát
        exitButton.addActionListener(e -> {
            System.exit(0);
        });

        // Hành động cho nút Quay lại
        backButton.addActionListener(e -> {
            this.dispose(); // Đóng cửa sổ quản lý chi nhánh
            openMainView(); // Mở lại giao diện chính
        });

        loadBranches(); // Tải danh sách chi nhánh
        setContentPane(panel);
    }

    private void addBranch() {
        // Nhập thông tin chi nhánh
        String idStr = JOptionPane.showInputDialog("Nhập Mã Chi Nhánh:");
        String name = JOptionPane.showInputDialog("Nhập Tên Chi Nhánh:");
        String address = JOptionPane.showInputDialog("Nhập Địa Chỉ:");

        if (idStr != null && name != null && address != null) {
            try {
                int id = Integer.parseInt(idStr);
                Branch newBranch = new Branch(id, name, address); // Tạo đối tượng chi nhánh
                branchDAO.addBranch(newBranch); // Thêm chi nhánh vào cơ sở dữ liệu
                model.addRow(new Object[]{id, name, address}); // Thêm vào bảng
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
                    Branch updatedBranch = new Branch(id, name, address); // Cập nhật chi nhánh
                    branchDAO.updateBranch(updatedBranch); // Cập nhật vào cơ sở dữ liệu
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
                branchDAO.deleteBranch(id); // Xóa chi nhánh trong cơ sở dữ liệu
                model.removeRow(selectedRow); // Xóa khỏi bảng
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một chi nhánh để xóa.");
        }
    }

    private void openMainView() {
        // Thay thế bằng các giá trị thực tế mà bạn muốn truyền
        String userRole = "Admin"; // Ví dụ về vai trò, thay thế bằng thực tế
        String userName = "User"; // Ví dụ về tên người dùng, thay thế bằng thực tế

        MainView mainView = new MainView(userRole, userName, maChiNhanh); // Tạo MainView với tham số
        mainView.setVisible(true); // Hiển thị MainView
    }

    private void loadBranches() {
        try {
            List<Branch> branches = branchDAO.getAllBranches(maChiNhanh); // Lấy danh sách chi nhánh
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
