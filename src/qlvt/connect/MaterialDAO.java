package qlvt.connect;

import qlvt.model.Material;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MaterialDAO {

    private DistributedDatabaseConnection dbConnection;

    public MaterialDAO() {
        dbConnection = new DistributedDatabaseConnection();
    }

    // Lấy danh sách vật tư
    public List<Material> getAllMaterials() throws SQLException {
        List<Material> materials = new ArrayList<>();
        String query = "SELECT TOP (1000) [MaVatTu], [TenVatTu], [MoTa], [DonViTinh], [Gia], [MaNhaCungCap] FROM [VT_XD_TOANMINH].[dbo].[VatTu]";

        try (Connection connection = dbConnection.connectToServer(DistributedDatabaseConnection.SERVER1_URL);
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                materials.add(new Material(
                        rs.getInt("MaVatTu"),
                        rs.getString("TenVatTu"),
                        rs.getString("MoTa"),
                        rs.getString("DonViTinh"),
                        rs.getDouble("Gia"),
                        rs.getInt("MaNhaCungCap")
                ));
            }
        }
        return materials;
    }

    // Thêm vật tư
    public void addMaterial(Material material) throws SQLException {
        String query = "INSERT INTO [VT_XD_TOANMINH].[dbo].[VatTu] (MaVatTu, TenVatTu, MoTa, DonViTinh, Gia, MaNhaCungCap) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = dbConnection.connectToServer(DistributedDatabaseConnection.SERVER1_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, material.getMaVatTu());
            preparedStatement.setString(2, material.getTenVatTu());
            preparedStatement.setString(3, material.getMoTa());
            preparedStatement.setString(4, material.getDonViTinh());
            preparedStatement.setDouble(5, material.getGia());
            preparedStatement.setInt(6, material.getMaNhaCungCap());
            preparedStatement.executeUpdate();
        }
    }

    // Cập nhật vật tư
    public void updateMaterial(Material material) throws SQLException {
        String query = "UPDATE [VT_XD_TOANMINH].[dbo].[VatTu] SET TenVatTu = ?, MoTa = ?, DonViTinh = ?, Gia = ?, MaNhaCungCap = ? WHERE MaVatTu = ?";

        try (Connection connection = dbConnection.connectToServer(DistributedDatabaseConnection.SERVER1_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, material.getTenVatTu());
            preparedStatement.setString(2, material.getMoTa());
            preparedStatement.setString(3, material.getDonViTinh());
            preparedStatement.setDouble(4, material.getGia());
            preparedStatement.setInt(5, material.getMaNhaCungCap());
            preparedStatement.setInt(6, material.getMaVatTu());
            preparedStatement.executeUpdate();
        }
    }

    // Xóa vật tư
    public void deleteMaterial(int maVatTu) throws SQLException {
        String query = "DELETE FROM [VT_XD_TOANMINH].[dbo].[VatTu] WHERE MaVatTu = ?";

        try (Connection connection = dbConnection.connectToServer(DistributedDatabaseConnection.SERVER1_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, maVatTu);
            preparedStatement.executeUpdate();
        }
    }
}
