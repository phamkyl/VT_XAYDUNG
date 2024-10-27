package qlvt.GuiView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PhieuXuatManagement extends JFrame {
    private JTextField txtMaPhieuXuat;
    private JTextField txtMaNhanVien;
    private JTextField txtMaKho;
    private JTextField txtMaDonHang;
    private JTable table; // Giả định có một bảng để hiển thị danh sách phiếu xuất

    public PhieuXuatManagement() {
        setTitle("Quản Lý Phiếu Xuất");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(5, 2));
        panel.add(new JLabel("Mã Phiếu Xuất:"));
        txtMaPhieuXuat = new JTextField();
        panel.add(txtMaPhieuXuat);

        panel.add(new JLabel("Mã Nhân Viên:"));
        txtMaNhanVien = new JTextField();
        panel.add(txtMaNhanVien);

        panel.add(new JLabel("Mã Kho:"));
        txtMaKho = new JTextField();
        panel.add(txtMaKho);

        panel.add(new JLabel("Mã Đơn Hàng:"));
        txtMaDonHang = new JTextField();
        panel.add(txtMaDonHang);

        JButton btnAdd = new JButton("Thêm");
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addPhieuXuat();
            }
        });
        panel.add(btnAdd);

        add(panel, BorderLayout.NORTH);
        // Thêm JScrollPane cho JTable để hiển thị danh sách phiếu xuất
        // table = new JTable(...);
        // add(new JScrollPane(table), BorderLayout.CENTER);
    }

    private void addPhieuXuat() {
        // Thêm mã logic để lưu phiếu xuất vào cơ sở dữ liệu
        int maPhieuXuat = Integer.parseInt(txtMaPhieuXuat.getText());
        int maNhanVien = Integer.parseInt(txtMaNhanVien.getText());
        int maKho = Integer.parseInt(txtMaKho.getText());
        int maDonHang = Integer.parseInt(txtMaDonHang.getText());

        // Logic để thêm phiếu xuất vào database
        // Sử dụng PreparedStatement hoặc Hibernate, JPA để lưu dữ liệu
    }

    // Các phương thức khác: sửa, xóa, xem danh sách phiếu xuất

}

