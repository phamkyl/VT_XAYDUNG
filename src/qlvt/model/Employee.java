package qlvt.model;

public class Employee {
    private int maNhanVien;
    private String hoTen;
    private String chucVu;
    private int maChiNhanh;
    private String phanQuyen;
    private String matKhau;

    // Constructor
    public Employee(int maNhanVien, String hoTen, String chucVu, int maChiNhanh, String phanQuyen, String matKhau) {
        this.maNhanVien = maNhanVien;
        this.hoTen = hoTen;
        this.chucVu = chucVu;
        this.maChiNhanh = maChiNhanh;
        this.phanQuyen = phanQuyen;
        this.matKhau = matKhau;
    }

    // Getters và setters
    public int getMaNhanVien() {
        return maNhanVien;
    }

    public String getHoTen() {
        return hoTen;
    }

    public String getChucVu() {
        return chucVu;
    }

    public int getMaChiNhanh() {
        return maChiNhanh;
    }

    public String getPhanQuyen() {
        return phanQuyen;
    }

    public String getMatKhau() {
        return matKhau;
    }
}
