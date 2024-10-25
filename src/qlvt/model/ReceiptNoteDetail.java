package qlvt.model;

import java.math.BigDecimal;

// phiếu nhập chi tiết
public class ReceiptNoteDetail {
    private int maPhieuNhap;
    private int maVatTu;
    private int soLuongNhap;
    private BigDecimal giaNhap;


    public ReceiptNoteDetail(int maPhieuNhap, int maVatTu, int soLuongNhap, BigDecimal giaNhap) {
        this.maPhieuNhap = maPhieuNhap;
        this.maVatTu = maVatTu;
        this.soLuongNhap = soLuongNhap;
        this.giaNhap = giaNhap;
    }

    public int getMaPhieuNhap() {
        return maPhieuNhap;
    }

    public void setMaPhieuNhap(int maPhieuNhap) {
        this.maPhieuNhap = maPhieuNhap;
    }

    public int getMaVatTu() {
        return maVatTu;
    }

    public void setMaVatTu(int maVatTu) {
        this.maVatTu = maVatTu;
    }

    public int getSoLuongNhap() {
        return soLuongNhap;
    }

    public void setSoLuongNhap(int soLuongNhap) {
        this.soLuongNhap = soLuongNhap;
    }

    public BigDecimal getGiaNhap() {
        return giaNhap;
    }

    public void setGiaNhap(BigDecimal giaNhap) {
        this.giaNhap = giaNhap;
    }

    @Override
    public String toString() {
        return "ReceiptNoteDetail{" +
                "maPhieuNhap=" + maPhieuNhap +
                ", maVatTu=" + maVatTu +
                ", soLuongNhap=" + soLuongNhap +
                ", giaNhap=" + giaNhap +
                '}';
    }
}
