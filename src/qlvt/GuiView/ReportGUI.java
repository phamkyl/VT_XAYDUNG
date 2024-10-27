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
        dialog.setSize(450, 500);
        dialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Add padding around components
        gbc.fill = GridBagConstraints.HORIZONTAL;

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

        // Tạo nút để lấy dữ liệu thống kê
        btnFetchData = new JButton("Lấy Dữ Liệu");
        btnFetchData.setBackground(new Color(76, 175, 80)); // Custom color
        btnFetchData.setForeground(Color.WHITE); // Text color
        btnFetchData.setFocusPainted(false);
        btnFetchData.addActionListener(e -> fetchData());

        // Set GridBagLayout constraints for labels and button
        int row = 0;
        dialog.add(lblTongPhieuNhap, getGbc(gbc, row++, 0));
        dialog.add(lblTongPhieuXuat, getGbc(gbc, row++, 0));
        dialog.add(lblTongVatTuNhap, getGbc(gbc, row++, 0));
        dialog.add(lblTongVatTuXuat, getGbc(gbc, row++, 0));
        dialog.add(lblTongHoaDon, getGbc(gbc, row++, 0));
        dialog.add(lblTongDoanhThu, getGbc(gbc, row++, 0));
        dialog.add(lblTongKho, getGbc(gbc, row++, 0));
        dialog.add(lblKhoChiNhanh1, getGbc(gbc, row++, 0));
        dialog.add(lblKhoChiNhanh2, getGbc(gbc, row++, 0));
        dialog.add(lblTongNhaCungCap, getGbc(gbc, row++, 0));
        dialog.add(lblTongNhanVien, getGbc(gbc, row++, 0));
        dialog.add(btnFetchData, getGbc(gbc, row++, 0));

        dialog.setLocationRelativeTo(parent); // Center the dialog relative to the parent
        dialog.setVisible(true);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        label.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1)); // Border around labels
        return label;
    }

    private GridBagConstraints getGbc(GridBagConstraints gbc, int gridy, int gridx) {
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        return gbc;
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
