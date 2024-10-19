package qlvt.connect;

import qlvt.model.Warehouse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WarehouseDAO {

    private DistributedDatabaseConnection dbConnection;

    public WarehouseDAO() {
        dbConnection = new DistributedDatabaseConnection();
    }

    // Lấy kho dựa trên mã kho
    public Warehouse getWarehouseById(int maKho) throws SQLException {
        String query = "SELECT * FROM Kho WHERE MaKho = ?";
        Warehouse warehouse = null;

        // Lấy URL server dựa trên mã chi nhánh của kho
        String serverURL = getServerURLByBranch(maKho);

        try (Connection connection = dbConnection.connectToServer(serverURL);
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, maKho);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                warehouse = new Warehouse(
                        rs.getInt("MaKho"),
                        rs.getString("TenKho"),
                        rs.getString("DiaChi"),
                        rs.getInt("MaChiNhanh")
                );
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy kho: " + e.getMessage());
            throw e;
        }
        return warehouse; // Trả về đối tượng Warehouse hoặc null nếu không tìm thấy
    }

    // Lấy danh sách tất cả các kho theo mã chi nhánh
    public List<Warehouse> getAllWarehouses(int maChiNhanh) throws SQLException {
        List<Warehouse> warehouses = new ArrayList<>();
        String query = "SELECT * FROM Kho WHERE MaChiNhanh = ?";

        // Lấy URL server dựa trên mã chi nhánh
        String serverURL = getServerURLByBranch(maChiNhanh);

        try (Connection connection = dbConnection.connectToServer(serverURL);
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, maChiNhanh); // Set branch ID in the query
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                warehouses.add(new Warehouse(
                        rs.getInt("MaKho"),
                        rs.getString("TenKho"),
                        rs.getString("DiaChi"),
                        rs.getInt("MaChiNhanh")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy danh sách kho: " + e.getMessage());
            throw e;
        }
        return warehouses; // Trả về danh sách kho
    }

    // Thêm kho mới
    public void addWarehouse(Warehouse warehouse) throws SQLException {
        String query = "INSERT INTO Kho (MaKho, TenKho, DiaChi, MaChiNhanh) VALUES (?, ?, ?, ?)";
        String serverURL = getServerURLByBranch(warehouse.getMaChiNhanh()); // Lấy URL server dựa trên mã chi nhánh

        try (Connection connection = dbConnection.connectToServer(serverURL);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, warehouse.getMaKho());
            preparedStatement.setString(2, warehouse.getTenKho());
            preparedStatement.setString(3, warehouse.getDiaChi());
            preparedStatement.setInt(4, warehouse.getMaChiNhanh());
            preparedStatement.executeUpdate(); // Thực hiện thêm mới
        } catch (SQLException e) {
            System.err.println("Lỗi khi thêm kho: " + e.getMessage());
            throw e;
        }
    }

    // Cập nhật thông tin kho
    public void updateWarehouse(Warehouse warehouse) throws SQLException {
        String query = "UPDATE Kho SET TenKho = ?, DiaChi = ?, MaChiNhanh = ? WHERE MaKho = ?";
        String serverURL = getServerURLByBranch(warehouse.getMaChiNhanh()); // Lấy URL server dựa trên mã chi nhánh

        try (Connection connection = dbConnection.connectToServer(serverURL);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, warehouse.getTenKho());
            preparedStatement.setString(2, warehouse.getDiaChi());
            preparedStatement.setInt(3, warehouse.getMaChiNhanh());
            preparedStatement.setInt(4, warehouse.getMaKho());
            preparedStatement.executeUpdate(); // Thực hiện cập nhật
        } catch (SQLException e) {
            System.err.println("Lỗi khi cập nhật kho: " + e.getMessage());
            throw e;
        }
    }

    // Xóa kho
    public void deleteWarehouse(int maKho) throws SQLException {
        String query = "DELETE FROM Kho WHERE MaKho = ?";
        String serverURL = getServerURLByBranch(maKho); // Lấy URL server dựa trên mã chi nhánh

        try (Connection connection = dbConnection.connectToServer(serverURL);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, maKho);
            preparedStatement.executeUpdate(); // Thực hiện xóa
        } catch (SQLException e) {
            System.err.println("Lỗi khi xóa kho: " + e.getMessage());
            throw e;
        }
    }

    // Lấy URL của server dựa trên mã chi nhánh
    private String getServerURLByBranch(int maChiNhanh) {
        switch (maChiNhanh) {
            case 1:
                return DistributedDatabaseConnection.SERVER2_URL; // URL server chứa chi nhánh 1
            case 2:
                return DistributedDatabaseConnection.SERVER3_URL; // URL server chứa chi nhánh 2
            case 3:
                return DistributedDatabaseConnection.SERVER4_URL; // URL server chứa chi nhánh 3
            default:
                throw new IllegalArgumentException("Chi nhánh không hợp lệ: " + maChiNhanh);
        }
    }

    // Lấy danh sách kho dựa trên mã chi nhánh
    public List<Warehouse> getWarehousesByBranch(int maChiNhanh) throws SQLException {
        List<Warehouse> warehouses = new ArrayList<>();
        String query = "SELECT * FROM Kho WHERE MaChiNhanh = ?";

        // Lấy URL server dựa trên mã chi nhánh
        String serverURL = getServerURLByBranch(maChiNhanh);

        try (Connection connection = dbConnection.connectToServer(serverURL);
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, maChiNhanh); // Set branch ID in the query
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                warehouses.add(new Warehouse(
                        rs.getInt("MaKho"),
                        rs.getString("TenKho"),
                        rs.getString("DiaChi"),
                        rs.getInt("MaChiNhanh")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy danh sách kho theo chi nhánh: " + e.getMessage());
            throw e;
        }
        return warehouses; // Trả về danh sách kho
    }
}
