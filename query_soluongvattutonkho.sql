SELECT 
    v.MaVatTu,
    v.TenVatTu,
    COALESCE(SUM(cpn.SoLuongNhap), 0) AS SoLuongNhap,
    COALESCE(SUM(cpx.SoLuongXuat), 0) AS SoLuongXuat,
    COALESCE(SUM(cpn.SoLuongNhap), 0) - COALESCE(SUM(cpx.SoLuongXuat), 0) AS TonKho
FROM 
    VatTu v
LEFT JOIN 
    ChiTietPhieuNhap cpn ON v.MaVatTu = cpn.MaVatTu
LEFT JOIN 
    ChiTietPhieuXuat cpx ON v.MaVatTu = cpx.MaVatTu
GROUP BY 
    v.MaVatTu, v.TenVatTu;