package qlvt.GuiView;

import qlvt.connect.report_thongkeDao;
import qlvt.model.ReportData;

import javax.swing.*;
import java.awt.*;

public class ReportGUI {
    private JDialog dialog;
    private JLabel lblTongPhieuNhap;
    private JLabel lblTongPhieuXuat;
    private JLabel lblTongVatTuNhap;
    private JLabel lblTongVatTuXuat;
    private JLabel lblTongHoaDon;
    private JLabel lblTongDoanhThu;
    private JLabel lblTongKho;
    private JLabel lblKhoChiNhanh1;
    private JLabel lblKhoChiNhanh2;
    private JLabel lblTongNhaCungCap;
    private JLabel lblTongNhanVien;
    private JButton btnFetchData;

    public ReportGUI(Frame parent) {
        initialize(parent);
    }

    private void initialize(Frame parent) {
        dialog = new JDialog(parent, "Thống Kê", true);
        dialog.setSize(800, 600);
        dialog.setLayout(new BorderLayout());

        // Tạo JPanel với GridLayout 4x3 cho các nhãn
        JPanel gridPanel = new JPanel(new GridLayout(4, 3, 9, 9));

        // Tạo các JLabel để hiển thị dữ liệu
        lblTongPhieuNhap = createLabel("Tổng Phiếu Nhập: ");
        lblTongPhieuXuat = createLabel("Tổng Phiếu Xuất: ");
        lblTongVatTuNhap = createLabel("Tổng Vật Tư Nhập: ");
        lblTongVatTuXuat = createLabel("Tổng Vật Tư Xuất: ");
        lblTongHoaDon = createLabel("Tổng Hóa Đơn: ");
        lblTongDoanhThu = createLabel("Tổng Doanh Thu: ");
        lblTongKho = createLabel("Tổng Kho: ");
        lblKhoChiNhanh1 = createLabel("Kho Chi Nhánh 1: ");
        lblKhoChiNhanh2 = createLabel("Kho Chi Nhánh 2: ");
        lblTongNhaCungCap = createLabel("Tổng Nhà Cung Cấp: ");
        lblTongNhanVien = createLabel("Tổng Nhân Viên: ");

        // Thêm các nhãn vào lưới
        gridPanel.add(lblTongPhieuNhap);
        gridPanel.add(lblTongPhieuXuat);
        gridPanel.add(lblTongVatTuNhap);
        gridPanel.add(lblTongVatTuXuat);
        gridPanel.add(lblTongHoaDon);
        gridPanel.add(lblTongDoanhThu);
        gridPanel.add(lblTongKho);
        gridPanel.add(lblKhoChiNhanh1);
        gridPanel.add(lblKhoChiNhanh2);
        gridPanel.add(lblTongNhaCungCap);
        gridPanel.add(lblTongNhanVien);

        // Tạo nút để lấy dữ liệu thống kê
        btnFetchData = new JButton("Lấy Dữ Liệu");
        btnFetchData.setBackground(new Color(76, 175, 80)); // Custom button color
        btnFetchData.setForeground(Color.WHITE); // Text color for button
        btnFetchData.setFocusPainted(false);
        btnFetchData.addActionListener(e -> fetchData());

        // Thêm lưới và nút vào BorderLayout của dialog
        dialog.add(gridPanel, BorderLayout.CENTER);
        dialog.add(btnFetchData, BorderLayout.SOUTH);

        dialog.setLocationRelativeTo(parent); // Center the dialog relative to the parent
        dialog.setVisible(true);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        label.setOpaque(true); // Cho phép hiển thị màu nền
        label.setForeground(Color.WHITE);
        label.setBackground(new Color(76, 175, 80)); // Màu nền xanh lá cây
        label.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1)); // Viền xám quanh nhãn
        label.setHorizontalAlignment(SwingConstants.CENTER); // Căn giữa chữ
        return label;
    }


    private void fetchData() {
        report_thongkeDao reportDao = new report_thongkeDao();
        ReportData reportData = reportDao.getReportData();

        // Cập nhật các JLabel với dữ liệu nhận được
        lblTongPhieuNhap.setText("Tổng Phiếu Nhập: " + reportData.getTongPhieuNhap());
        lblTongPhieuXuat.setText("Tổng Phiếu Xuất: " + reportData.getTongPhieuXuat());
        lblTongVatTuNhap.setText("Tổng Vật Tư Nhập: " + reportData.getTongVatTuNhap());
        lblTongVatTuXuat.setText("Tổng Vật Tư Xuất: " + reportData.getTongVatTuXuat());
        lblTongHoaDon.setText("Tổng Hóa Đơn: " + reportData.getTongHoaDon());
        lblTongDoanhThu.setText("Tổng Doanh Thu: " + reportData.getTongDoanhThu());
        lblTongKho.setText("Tổng Kho: " + reportData.getTongKho());
        lblKhoChiNhanh1.setText("Kho Chi Nhánh 1: " + reportData.getKhoChiNhanh1());
        lblKhoChiNhanh2.setText("Kho Chi Nhánh 2: " + reportData.getKhoChiNhanh2());
        lblTongNhaCungCap.setText("Tổng Nhà Cung Cấp: " + reportData.getTongNhaCungCap());
        lblTongNhanVien.setText("Tổng Nhân Viên: " + reportData.getTongNhanVien());
    }
}
