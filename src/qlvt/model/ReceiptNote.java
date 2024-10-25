package qlvt.model;

import java.util.Date;

// phiếu nhập
public class ReceiptNote {
    private int maPhieuNhap;
    private Date ngayNhap;
    private int maNhanVien;
    private int maKho;
    private int maNhaCungCap;

    public ReceiptNote(int maPhieuNhap, Date ngayNhap, int maNhanVien, int maKho, int maNhaCungCap) {
        this.maPhieuNhap = maPhieuNhap;
        this.ngayNhap = ngayNhap;
        this.maNhanVien = maNhanVien;
        this.maKho = maKho;
        this.maNhaCungCap = maNhaCungCap;
    }

    public ReceiptNote() {
    }

    public int getMaPhieuNhap() {
        return maPhieuNhap;
    }

    public void setMaPhieuNhap(int maPhieuNhap) {
        this.maPhieuNhap = maPhieuNhap;
    }

    public Date getNgayNhap() {
        return ngayNhap;
    }

    public void setNgayNhap(Date ngayNhap) {
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

    @Override
    public String toString() {
        return "ReceiptNote{" +
                "maPhieuNhap=" + maPhieuNhap +
                ", ngayNhap=" + ngayNhap +
                ", maNhanVien=" + maNhanVien +
                ", maKho=" + maKho +
                ", maNhaCungCap=" + maNhaCungCap +
                '}';
    }
}
