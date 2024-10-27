package qlvt.connect;

import qlvt.model.ReportData;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class report_thongkeDao {
    private static DistributedDatabaseConnection dbConnection;

    public report_thongkeDao() {
        dbConnection = new DistributedDatabaseConnection();
    }

    public ReportData getReportData() {
        ReportData reportData = new ReportData();

        String query = "SELECT " +
                " (SELECT COUNT(*) FROM PhieuNhap) AS TongPhieuNhap, " +
                " (SELECT COUNT(*) FROM PhieuXuat) AS TongPhieuXuat, " +
                " (SELECT SUM(SoLuongNhap) FROM ChiTietPhieuNhap) AS TongVatTuNhap, " +
                " (SELECT SUM(SoLuongXuat) FROM ChiTietPhieuXuat) AS TongVatTuXuat, " +
                " (SELECT COUNT(*) FROM HoaDon) AS TongHoaDon, " +
                " (SELECT SUM(TongTien) FROM HoaDon) AS TongDoanhThu, " +
                " (SELECT COUNT(*) FROM Kho) AS TongKho, " +
                " (SELECT COUNT(*) FROM Kho WHERE MaChiNhanh = 1) AS KhoChiNhanh1, " +
                " (SELECT COUNT(*) FROM Kho WHERE MaChiNhanh = 2) AS KhoChiNhanh2, " +
                " (SELECT COUNT(*) FROM NhaCungCap) AS TongNhaCungCap, " +
                " (SELECT COUNT(*) FROM NhanVien) AS TongNhanVien;";

        try (Connection connection = dbConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            if (resultSet.next()) {
                reportData.setTongPhieuNhap(resultSet.getInt("TongPhieuNhap"));
                reportData.setTongPhieuXuat(resultSet.getInt("TongPhieuXuat"));
                reportData.setTongVatTuNhap(resultSet.getInt("TongVatTuNhap"));
                reportData.setTongVatTuXuat(resultSet.getInt("TongVatTuXuat"));
                reportData.setTongHoaDon(resultSet.getInt("TongHoaDon"));
                reportData.setTongDoanhThu(resultSet.getDouble("TongDoanhThu"));
                reportData.setTongKho(resultSet.getInt("TongKho"));
                reportData.setKhoChiNhanh1(resultSet.getInt("KhoChiNhanh1"));
                reportData.setKhoChiNhanh2(resultSet.getInt("KhoChiNhanh2"));
                reportData.setTongNhaCungCap(resultSet.getInt("TongNhaCungCap"));
                reportData.setTongNhanVien(resultSet.getInt("TongNhanVien"));
            }

        } catch (SQLException e) {
            e.printStackTrace(); // In ra lỗi nếu có
        }

        return reportData;
    }
}
