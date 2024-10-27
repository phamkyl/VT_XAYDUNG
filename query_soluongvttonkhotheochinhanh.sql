SELECT 
    cn.MaChiNhanh AS BranchID,
    cn.TenChiNhanh AS BranchName,
    v.MaVatTu AS MaterialID,
    v.TenVatTu AS MaterialName,
    COALESCE(SUM(cpn.SoLuongNhap), 0) AS TotalQuantityReceived,
    COALESCE(SUM(cpx.SoLuongXuat), 0) AS TotalQuantityIssued,
    COALESCE(SUM(cpn.SoLuongNhap), 0) - COALESCE(SUM(cpx.SoLuongXuat), 0) AS StockQuantity
FROM 
    VatTu v
LEFT JOIN 
    ChiTietPhieuNhap cpn ON v.MaVatTu = cpn.MaVatTu
LEFT JOIN 
    PhieuNhap pn ON cpn.MaPhieuNhap = pn.MaPhieuNhap
LEFT JOIN 
    ChiTietPhieuXuat cpx ON v.MaVatTu = cpx.MaVatTu
LEFT JOIN 
    PhieuXuat px ON cpx.MaPhieuXuat = px.MaPhieuXuat
LEFT JOIN 
    Kho k ON pn.MaKho = k.MaKho OR px.MaKho = k.MaKho
LEFT JOIN 
    ChiNhanh cn ON k.MaChiNhanh = cn.MaChiNhanh
GROUP BY 
    cn.MaChiNhanh, cn.TenChiNhanh, v.MaVatTu, v.TenVatTu
ORDER BY 
    cn.MaChiNhanh, v.MaVatTu;
