package qlvt.Controller;

import qlvt.GuiView.MainView;
import qlvt.connect.EmployeeDAO;
import qlvt.model.Employee;
import qlvt.GuiView.LoginView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class LoginController {
    private final EmployeeDAO employeeDAO;
    private final LoginView loginView;
    private static Employee currentEmployee;

    public LoginController(LoginView view) {
        this.employeeDAO = new EmployeeDAO();
        this.loginView = view;
    }

    public void login(String maNhanVien, String matKhau, String maChiNhanhStr) {
        try {
            // Convert maChiNhanhStr to Integer
            int maChiNhanh = Integer.parseInt(maChiNhanhStr);

            // Check login credentials
            Employee employee = employeeDAO.getEmployeeByCredentials(maNhanVien, matKhau);

            // If login is successful
            if (employee != null && employee.getMaChiNhanh() == maChiNhanh) {
                String phanQuyen = employee.getPhanQuyen();

                loginView.showLoginSuccess(employee);

                // Switch to main view based on role
                SwingUtilities.invokeLater(() -> {
                    MainView mainView;
                    JPanel optionsPanel = new JPanel(); // Create a new JPanel for options

                    switch (phanQuyen) {
                        case "admin": // quản lý 2 chi nhánh
                            mainView = new MainView("admin", employee.getHoTen(), maChiNhanh);
                            break;
                        case "ADMIN0": // quản lý 1 chi nhánh
                            mainView = new MainView("ADMIN0", employee.getHoTen(), maChiNhanh);
                            break;
                        case "employee": // nhân viên 1 chi nhánh
                            mainView = new MainView("employee", employee.getHoTen(), maChiNhanh);
                            break;
                        default:
                            loginView.showError("Không xác định quyền!");
                            return; // Do not switch to main view
                    }

                    mainView.showOptions(optionsPanel); // Update options for each role
                    mainView.setVisible(true); // Show the main view
                    loginView.dispose(); // Close the login form
                });

            } else {
                loginView.showError("Sai mã nhân viên, mật khẩu hoặc mã chi nhánh!");
            }
        } catch (NumberFormatException e) {
            loginView.showError("Mã chi nhánh phải là số nguyên!");
        } catch (Exception e) {
            e.printStackTrace();
            loginView.showError("Lỗi khi đăng nhập: " + e.getMessage());
        }
    }

    public static Employee getCurrentEmployee() {
        return currentEmployee; // Phương thức lấy nhân viên hiện tại
    }
}