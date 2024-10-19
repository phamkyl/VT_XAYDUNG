package qlvt.connect;

import qlvt.model.Warehouse; // Make sure you have a Warehouse model class

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

    // Get all warehouses
    public List<Warehouse> getAllWarehouses() throws SQLException {
        List<Warehouse> warehouses = new ArrayList<>();
        String query = "SELECT TOP (1000) [MaKho], [TenKho], [DiaChi], [MaChiNhanh] FROM [VT_XD_TOANMINH].[dbo].[Kho]"; // Adjust the table name if necessary

        try (Connection connection = dbConnection.connectToServer(DistributedDatabaseConnection.SERVER1_URL);
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                warehouses.add(new Warehouse(
                        rs.getInt("MaKho"),
                        rs.getString("TenKho"),
                        rs.getString("DiaChi"),
                        rs.getInt("MaChiNhanh")
                ));
            }
        }
        return warehouses;
    }

    // Add a new warehouse
    public void addWarehouse(Warehouse warehouse) throws SQLException {
        String query = "INSERT INTO Kho (MaKho, TenKho, DiaChi, MaChiNhanh) VALUES (?, ?, ?, ?)";

        try (Connection connection = dbConnection.connectToServer(DistributedDatabaseConnection.SERVER1_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, warehouse.getMaKho());
            preparedStatement.setString(2, warehouse.getTenKho());
            preparedStatement.setString(3, warehouse.getDiaChi());
            preparedStatement.setInt(4, warehouse.getMaChiNhanh());
            preparedStatement.executeUpdate();
        }
    }

    // Update an existing warehouse
    public void updateWarehouse(Warehouse warehouse) throws SQLException {
        String query = "UPDATE Kho SET TenKho = ?, DiaChi = ?, MaChiNhanh = ? WHERE MaKho = ?";

        try (Connection connection = dbConnection.connectToServer(DistributedDatabaseConnection.SERVER1_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, warehouse.getTenKho());
            preparedStatement.setString(2, warehouse.getDiaChi());
            preparedStatement.setInt(3, warehouse.getMaChiNhanh());
            preparedStatement.setInt(4, warehouse.getMaKho());
            preparedStatement.executeUpdate();
        }
    }

    // Delete a warehouse
    public void deleteWarehouse(int maKho) throws SQLException {
        String query = "DELETE FROM Kho WHERE MaKho = ?";

        try (Connection connection = dbConnection.connectToServer(DistributedDatabaseConnection.SERVER1_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, maKho);
            preparedStatement.executeUpdate();
        }
    }
}
