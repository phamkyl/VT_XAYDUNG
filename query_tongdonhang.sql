SELECT 
    cn.MaChiNhanh AS BranchID,
    cn.TenChiNhanh AS BranchName,
    COUNT(dh.MaDonHang) AS TotalOrders
FROM 
    ChiNhanh cn
LEFT JOIN 
    Kho k ON cn.MaChiNhanh = k.MaChiNhanh
LEFT JOIN 
    PhieuXuat px ON k.MaKho = px.MaKho
LEFT JOIN 
    DonHang dh ON px.MaDonHang = dh.MaDonHang
GROUP BY 
    cn.MaChiNhanh, cn.TenChiNhanh
ORDER BY 
    cn.MaChiNhanh;
