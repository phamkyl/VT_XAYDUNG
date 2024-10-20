package qlvt.model;


import java.time.LocalDate;

public class PhieuNhap {
    private int maPhieuNhap;
    private LocalDate ngayNhap;
    private int maNhanVien;
    private int maKho;
    private int maNhaCungCap;

    // Constructor
    public PhieuNhap(int maPhieuNhap, LocalDate ngayNhap, int maNhanVien, int maKho, int maNhaCungCap) {
        this.maPhieuNhap = maPhieuNhap;
        this.ngayNhap = ngayNhap;
        this.maNhanVien = maNhanVien;
        this.maKho = maKho;
        this.maNhaCungCap = maNhaCungCap;
    }



    // Getters and Setters
    public int getMaPhieuNhap() {
        return maPhieuNhap;
    }

    public void setMaPhieuNhap(int maPhieuNhap) {
        this.maPhieuNhap = maPhieuNhap;
    }

    public LocalDate getNgayNhap() {
        return ngayNhap;
    }

    public void setNgayNhap(LocalDate ngayNhap) {
        this.ngayNhap = ngayNhap;
    }

    public int getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(int maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public int getMaKho() {
        return maKho;
    }

    public void setMaKho(int maKho) {
        this.maKho = maKho;
    }

    public int getMaNhaCungCap() {
        return maNhaCungCap;
    }

    public void setMaNhaCungCap(int maNhaCungCap) {
        this.maNhaCungCap = maNhaCungCap;
    }
}

