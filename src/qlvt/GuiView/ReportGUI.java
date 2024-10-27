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
        dialog.setSize(400, 400);
        dialog.setLayout(new GridLayout(12, 2));

        // Tạo các JLabel để hiển thị dữ liệu
        lblTongPhieuNhap = new JLabel("Tổng Phiếu Nhập: ");
        lblTongPhieuXuat = new JLabel("Tổng Phiếu Xuất: ");
        lblTongVatTuNhap = new JLabel("Tổng Vật Tư Nhập: ");
        lblTongVatTuXuat = new JLabel("Tổng Vật Tư Xuất: ");
        lblTongHoaDon = new JLabel("Tổng Hóa Đơn: ");
        lblTongDoanhThu = new JLabel("Tổng Doanh Thu: ");
        lblTongKho = new JLabel("Tổng Kho: ");
        lblKhoChiNhanh1 = new JLabel("Kho Chi Nhánh 1: ");
        lblKhoChiNhanh2 = new JLabel("Kho Chi Nhánh 2: ");
        lblTongNhaCungCap = new JLabel("Tổng Nhà Cung Cấp: ");
        lblTongNhanVien = new JLabel("Tổng Nhân Viên: ");

        // Tạo nút để lấy dữ liệu thống kê
        btnFetchData = new JButton("Lấy Dữ Liệu");
        btnFetchData.addActionListener(e -> fetchData());

        // Thêm các thành phần vào JDialog
        dialog.add(lblTongPhieuNhap);
        dialog.add(new JLabel()); // Ô trống
        dialog.add(lblTongPhieuXuat);
        dialog.add(new JLabel()); // Ô trống
        dialog.add(lblTongVatTuNhap);
        dialog.add(new JLabel()); // Ô trống
        dialog.add(lblTongVatTuXuat);
        dialog.add(new JLabel()); // Ô trống
        dialog.add(lblTongHoaDon);
        dialog.add(new JLabel()); // Ô trống
        dialog.add(lblTongDoanhThu);
        dialog.add(new JLabel()); // Ô trống
        dialog.add(lblTongKho);
        dialog.add(new JLabel()); // Ô trống
        dialog.add(lblKhoChiNhanh1);
        dialog.add(new JLabel()); // Ô trống
        dialog.add(lblKhoChiNhanh2);
        dialog.add(new JLabel()); // Ô trống
        dialog.add(lblTongNhaCungCap);
        dialog.add(new JLabel()); // Ô trống
        dialog.add(lblTongNhanVien);
        dialog.add(new JLabel()); // Ô trống
        dialog.add(btnFetchData);

        dialog.setLocationRelativeTo(parent); // Center the dialog relative to the parent
        dialog.setVisible(true);
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
