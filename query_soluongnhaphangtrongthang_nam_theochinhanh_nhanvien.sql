DECLARE @Month INT = 10; -- Replace with the desired month (1-12)
DECLARE @Year INT = 2024; -- Replace with the desired year

SELECT 
    cn.MaChiNhanh AS BranchID,
    cn.TenChiNhanh AS BranchName,
    v.MaVatTu AS MaterialID,
    v.TenVatTu AS MaterialName,
    COALESCE(SUM(ctpn.SoLuongNhap), 0) AS TotalQuantityReceived,
    nv.MaNhanVien AS EmployeeID,
    nv.HoTen AS EmployeeName
FROM 
    ChiNhanh cn
JOIN 
    Kho k ON cn.MaChiNhanh = k.MaChiNhanh
JOIN 
    PhieuNhap pn ON k.MaKho = pn.MaKho
JOIN 
    ChiTietPhieuNhap ctpn ON pn.MaPhieuNhap = ctpn.MaPhieuNhap
JOIN 
    VatTu v ON ctpn.MaVatTu = v.MaVatTu
JOIN 
    NhanVien nv ON pn.MaNhanVien = nv.MaNhanVien
WHERE 
    MONTH(pn.NgayNhap) = @Month AND YEAR(pn.NgayNhap) = @Year
GROUP BY 
    cn.MaChiNhanh, cn.TenChiNhanh, v.MaVatTu, v.TenVatTu, nv.MaNhanVien, nv.HoTen
ORDER BY 
    cn.MaChiNhanh, v.MaVatTu;
