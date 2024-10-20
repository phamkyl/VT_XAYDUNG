package qlvt.connect;

import qlvt.model.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {

    private DistributedDatabaseConnection dbConnection;

    public EmployeeDAO() {
        dbConnection = new DistributedDatabaseConnection();
    }

    // Method to find an employee based on employee ID and password
    public Employee getEmployeeByCredentials(String maNhanVien, String matKhau) throws SQLException {
        String query = "SELECT * FROM NhanVien WHERE MaNhanVien = ? AND MatKhau = ?";
        Employee employee = null;

        // First, you need to determine which branch this employee belongs to
        int maChiNhanh = getBranchByEmployeeId(maNhanVien); // Fetch the branch ID by employee ID

        String serverURL = getServerURLByBranch(maChiNhanh); // Get server URL based on branch

        try (Connection connection = dbConnection.connectToServer(serverURL);
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, maNhanVien);
            stmt.setString(2, matKhau);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                employee = new Employee(
                        rs.getInt("MaNhanVien"),
                        rs.getString("HoTen"),
                        rs.getString("ChucVu"),
                        rs.getInt("MaChiNhanh"),
                        rs.getString("PhanQuyen"),
                        rs.getString("MatKhau")
                );
            }
        }
        return employee; // Return the Employee object or null if not found
    }

    // Get the branch ID based on employee ID
    private int getBranchByEmployeeId(String maNhanVien) throws SQLException {
        String query = "SELECT MaChiNhanh FROM NhanVien WHERE MaNhanVien = ?";
        int maChiNhanh = -1; // Initialize with an invalid branch ID
        int defaultBranch = 1; // Default branch if not found

        String serverURL = getServerURLByBranch(defaultBranch); // Connect to the default server

        try (Connection connection = dbConnection.connectToServer(serverURL);
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, maNhanVien);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                maChiNhanh = rs.getInt("MaChiNhanh");
            }
        }
        return maChiNhanh; // Return the branch ID
    }

    // Get the list of all employees based on branch ID
    public List<Employee> getAllEmployees(int maChiNhanh) throws SQLException {
        List<Employee> employees = new ArrayList<>();
        String query = "SELECT * FROM NhanVien WHERE MaChiNhanh = ?"; // Filter by branch ID
        String serverURL = getServerURLByBranch(maChiNhanh); // Get server URL based on branch

        try (Connection connection = dbConnection.connectToServer(serverURL);
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, maChiNhanh); // Set branch ID in the query
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                employees.add(new Employee(
                        rs.getInt("MaNhanVien"),
                        rs.getString("HoTen"),
                        rs.getString("ChucVu"),
                        rs.getInt("MaChiNhanh"),
                        rs.getString("PhanQuyen"),
                        rs.getString("MatKhau")
                ));
            }
        }
        return employees; // Return the list of employees
    }

    // Add a new employee
    public void addEmployee(Employee employee) throws SQLException {
        String query = "INSERT INTO NhanVien (MaNhanVien, HoTen, ChucVu, MaChiNhanh, PhanQuyen, MatKhau) VALUES (?, ?, ?, ?, ?, ?)";
        String serverURL = getServerURLByBranch(employee.getMaChiNhanh()); // Get server URL based on branch

        try (Connection connection = dbConnection.connectToServer(serverURL);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, employee.getMaNhanVien());
            preparedStatement.setString(2, employee.getHoTen());
            preparedStatement.setString(3, employee.getChucVu());
            preparedStatement.setInt(4, employee.getMaChiNhanh());
            preparedStatement.setString(5, employee.getPhanQuyen());
            preparedStatement.setString(6, employee.getMatKhau());
            preparedStatement.executeUpdate(); // Execute the insertion
        }
    }

    // Update an existing employee
    public void updateEmployee(Employee employee) throws SQLException {
        String query = "UPDATE NhanVien SET HoTen = ?, ChucVu = ?, MaChiNhanh = ?, PhanQuyen = ?, MatKhau = ? WHERE MaNhanVien = ?";
        String serverURL = getServerURLByBranch(employee.getMaChiNhanh()); // Get server URL based on branch

        try (Connection connection = dbConnection.connectToServer(serverURL);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, employee.getHoTen());
            preparedStatement.setString(2, employee.getChucVu());
            preparedStatement.setInt(3, employee.getMaChiNhanh());
            preparedStatement.setString(4, employee.getPhanQuyen());
            preparedStatement.setString(5, employee.getMatKhau());
            preparedStatement.setInt(6, employee.getMaNhanVien());
            preparedStatement.executeUpdate(); // Execute the update
        }
    }

    // Delete an employee
    public void deleteEmployee(int maNhanVien, int maChiNhanh) throws SQLException {
        String query = "DELETE FROM NhanVien WHERE MaNhanVien = ?";
        String serverURL = getServerURLByBranch(maChiNhanh); // Get server URL based on branch

        try (Connection connection = dbConnection.connectToServer(serverURL);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, maNhanVien);
            preparedStatement.executeUpdate(); // Execute the deletion
        }
    }

    // Method to get the server URL based on branch
    private String getServerURLByBranch(int maChiNhanh) {
        switch (maChiNhanh) {
            case 1:
                return DistributedDatabaseConnection.SERVER2_URL; // Connect to SERVER1_URL for branch 1
            case 2:
                return DistributedDatabaseConnection.SERVER3_URL; // Connect to SERVER2_URL for branch 2
           // case 3:
                //return DistributedDatabaseConnection.SERVER4_URL; // Connect to SERVER3_URL for branch 3
            // Add more cases as needed for additional branches
            default:
                throw new IllegalArgumentException("Chi nhánh không hợp lệ: " + maChiNhanh);
        }
    }
}
