package qlvt.connect;


import qlvt.model.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {

    private DistributedDatabaseConnection dbConnection;

    public OrderDAO() {
        dbConnection = new DistributedDatabaseConnection();
    }

    // Phương thức để tìm đơn hàng dựa trên mã đơn hàng
    public Order getOrderById(int maDonHang) throws SQLException {
        String query = "SELECT * FROM DonHang WHERE MaDonHang = ?";
        try (Connection connection = dbConnection.connectToServer(DistributedDatabaseConnection.SERVER1_URL);
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, maDonHang);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Order(
                        rs.getInt("MaDonHang"),
                        rs.getInt("MaKhachHang"),
                        rs.getDate("NgayDat"),
                        rs.getString("TinhTrangDonHang")
                );
            }
        }
        return null; // Trả về null nếu không tìm thấy
    }

    // Lấy danh sách tất cả đơn hàng
    public List<Order> getAllOrders() throws SQLException {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM DonHang"; // Cập nhật với cấu trúc bảng thực tế

        try (Connection connection = dbConnection.connectToServer(DistributedDatabaseConnection.SERVER1_URL);
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                orders.add(new Order(
                        rs.getInt("MaDonHang"),
                        rs.getInt("MaKhachHang"),
                        rs.getDate("NgayDat"),
                        rs.getString("TinhTrangDonHang")
                ));
            }
        }
        return orders;
    }

    // Thêm đơn hàng
    public void addOrder(Order order) throws SQLException {
        String query = "INSERT INTO DonHang (MaDonHang, MaKhachHang, NgayDat, TinhTrangDonHang) VALUES (?, ?, ?, ?)";

        try (Connection connection = dbConnection.connectToServer(DistributedDatabaseConnection.SERVER1_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, order.getMaDonHang());
            preparedStatement.setInt(2, order.getMaKhachHang());
            preparedStatement.setDate(3, order.getNgayDat());
            preparedStatement.setString(4, order.getTinhTrangDonHang());
            preparedStatement.executeUpdate();
        }
    }

    // Cập nhật đơn hàng
    public void updateOrder(Order order) throws SQLException {
        String query = "UPDATE DonHang SET MaKhachHang = ?, NgayDat = ?, TinhTrangDonHang = ? WHERE MaDonHang = ?";

        try (Connection connection = dbConnection.connectToServer(DistributedDatabaseConnection.SERVER1_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, order.getMaKhachHang());
            preparedStatement.setDate(2, order.getNgayDat());
            preparedStatement.setString(3, order.getTinhTrangDonHang());
            preparedStatement.setInt(4, order.getMaDonHang());
            preparedStatement.executeUpdate();
        }
    }

    // Xóa đơn hàng
    public void deleteOrder(int maDonHang) throws SQLException {
        String query = "DELETE FROM DonHang WHERE MaDonHang = ?";

        try (Connection connection = dbConnection.connectToServer(DistributedDatabaseConnection.SERVER1_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, maDonHang);
            preparedStatement.executeUpdate();
        }
    }
}
