-- chi nhanh
INSERT INTO ChiNhanh (MaChiNhanh, TenChiNhanh, DiaChi) VALUES
(1, N'Chi Nhánh 1', N'Số 1, Đường ABC, Thành phố XYZ'),
(2, N'Chi Nhánh 2', N'Số 2, Đường DEF, Thành phố XYZ');
-- nha cung cap
INSERT INTO NhaCungCap (MaNhaCungCap, TenNhaCungCap, DiaChi, SoDienThoai, Email) VALUES
(1, N'Công ty A', N'Số 10, Đường GHI, Thành phố ABC', '0123456789', 'contaya@gmail.com'),
(2, N'Công ty B', N'Số 20, Đường JKL, Thành phố DEF', '0987654321', 'contayb@gmail.com'),
(3, N'Công ty C', N'Số 30, Đường MNO, Thành phố GHI', '0123567890', 'contayc@gmail.com'),
(4, N'Công ty D', N'Số 40, Đường PQR, Thành phố JKL', '0876543210', 'contayd@gmail.com'),
(5, N'Công ty E', N'Số 50, Đường STU, Thành phố MNO', '0456789012', 'contaye@gmail.com'),
(6, N'Công ty F', N'Số 60, Đường VWX, Thành phố PQR', '0234567890', 'contayf@gmail.com'),
(7, N'Công ty G', N'Số 70, Đường YZ, Thành phố STU', '0345678901', 'contayg@gmail.com'),
(8, N'Công ty H', N'Số 80, Đường ABCD, Thành phố VWX', '0456789013', 'contayh@gmail.com'),
(9, N'Công ty I', N'Số 90, Đường EFGH, Thành phố YZ', '0567890124', 'contayi@gmail.com'),
(10, N'Công ty J', N'Số 100, Đường IJKL, Thành phố ABCD', '0678901235', 'contayj@gmail.com');
-- vat tu
INSERT INTO VatTu (MaVatTu, TenVatTu, MoTa, DonViTinh, Gia, MaNhaCungCap) VALUES
(1, N'Sắt', N'Sắt xây dựng', N'Kg', 15000, 1),
(2, N'Thép', N'Thép hình', N'Kg', 20000, 1),
(3, N'Cát', N'Cát xây dựng', N'M3', 300000, 2),
(4, N'Đá', N'Đá xây dựng', N'M3', 250000, 3),
(5, N'Tường', N'Tường gạch', N'M2', 450000, 4),
(6, N'Sê-nô', N'Sê-nô vữa', N'Kg', 50000, 5),
(7, N'Xi măng', N'Xi măng xây dựng', N'Kg', 12000, 6),
(8, N'Tôn', N'Tôn lạnh', N'M2', 55000, 7),
(9, N'Gạch', N'Gạch xây', N'Viên', 500, 8),
(10, N'Bê tông', N'Bê tông tươi', N'M3', 1200000, 9);
-- kho
INSERT INTO Kho (MaKho, TenKho, DiaChi, MaChiNhanh) VALUES
(1, N'Kho 1', N'Số 1, Đường ABC, Thành phố XYZ', 1),
(2, N'Kho 2', N'Số 2, Đường DEF, Thành phố XYZ', 1),
(3, N'Kho 3', N'Số 3, Đường ABC, Thành phố XYZ', 2),
(4, N'Kho 4', N'Số 4, Đường DEF, Thành phố XYZ', 2);
-- nhanh vien
INSERT INTO NhanVien (MaNhanVien, HoTen, ChucVu, MaChiNhanh, MatKhau, PhanQuyen) VALUES
(1, N'Nguyễn Văn A', N'Quản lý', 1, 'password1', N'admin'),
(2, N'Trần Thị B', N'Nhân viên', 1, 'password2', N'employee'),
(3, N'Nguyễn Văn C', N'Nhân viên', 1, 'password3', N'employee'),
(4, N'Phạm Thị D', N'Quản lý', 2, 'password4', N'admin'),
(5, N'Nguyễn Văn E', N'Nhân viên', 2, 'password5', N'employee'),
(6, N'Trần Thị F', N'Nhân viên', 2, 'password6', N'employee'),
(7, N'Nguyễn Văn G', N'Nhân viên', 1, 'password7', N'employee'),
(8, N'Phạm Thị H', N'Nhân viên', 1, 'password8', N'employee'),
(9, N'Nguyễn Văn I', N'Nhân viên', 2, 'password9', N'employee'),
(10, N'Trần Thị J', N'Nhân viên', 2, 'password10', N'employee');
-- khach hang
INSERT INTO KhachHang (MaKhachHang, HoTen, DiaChi, SoDienThoai, Email) VALUES
(1, N'Khách 1', N'Số 1, Đường ABC, Thành phố XYZ', '0912345678', 'khach1@gmail.com'),
(2, N'Khách 2', N'Số 2, Đường DEF, Thành phố XYZ', '0923456789', 'khach2@gmail.com'),
(3, N'Khách 3', N'Số 3, Đường GHI, Thành phố XYZ', '0934567890', 'khach3@gmail.com'),
(4, N'Khách 4', N'Số 4, Đường JKL, Thành phố XYZ', '0945678901', 'khach4@gmail.com'),
(5, N'Khách 5', N'Số 5, Đường MNO, Thành phố XYZ', '0956789012', 'khach5@gmail.com'),
(6, N'Khách 6', N'Số 6, Đường PQR, Thành phố XYZ', '0967890123', 'khach6@gmail.com'),
(7, N'Khách 7', N'Số 7, Đường STU, Thành phố XYZ', '0978901234', 'khach7@gmail.com'),
(8, N'Khách 8', N'Số 8, Đường VWX, Thành phố XYZ', '0989012345', 'khach8@gmail.com'),
(9, N'Khách 9', N'Số 9, Đường YZ, Thành phố XYZ', '0990123456', 'khach9@gmail.com'),
(10, N'Khách 10', N'Số 10, Đường ABCD, Thành phố XYZ', '0911234567', 'khach10@gmail.com');
-- don hang
INSERT INTO DonHang (MaDonHang, MaKhachHang, NgayDat, TinhTrangDonHang) VALUES
(1, 1, '2024-10-01', N'Đang xử lý'),
(2, 2, '2024-10-02', N'Đang giao hàng'),
(3, 3, '2024-10-03', N'Đã giao'),
(4, 4, '2024-10-04', N'Đang xử lý'),
(5, 5, '2024-10-05', N'Đang giao hàng'),
(6, 6, '2024-10-06', N'Đã giao'),
(7, 7, '2024-10-07', N'Đang xử lý'),
(8, 8, '2024-10-08', N'Đang giao hàng'),
(9, 9, '2024-10-09', N'Đã giao'),
(10, 10, '2024-10-10', N'Đang xử lý');
-- chi tiet don hang
INSERT INTO ChiTietDonHang (MaDonHang, MaVatTu, SoLuong, Gia) VALUES
(1, 1, 10, 150000),
(1, 2, 5, 200000),
(2, 3, 15, 300000),
(2, 4, 20, 250000),
(3, 5, 7, 450000),
(3, 6, 10, 50000),
(4, 7, 12, 12000),
(4, 8, 8, 55000),
(5, 9, 14, 500),
(5, 10, 20, 1200000);
-- phieu nhap
INSERT INTO PhieuNhap (MaPhieuNhap, NgayNhap, MaNhanVien, MaKho, MaNhaCungCap) VALUES
(1, '2024-10-11', 1, 1, 1),
(2, '2024-10-12', 2, 1, 2),
(3, '2024-10-13', 3, 2, 3),
(4, '2024-10-14', 4, 2, 4),
(5, '2024-10-15', 5, 1, 5),
(6, '2024-10-16', 6, 2, 6),
(7, '2024-10-17', 7, 1, 7),
(8, '2024-10-18', 8, 2, 8),
(9, '2024-10-19', 9, 1, 9),
(10, '2024-10-20', 10, 2, 10);
-- chi tiet phieu nhap
INSERT INTO ChiTietPhieuNhap (MaPhieuNhap, MaVatTu, SoLuongNhap, GiaNhap) VALUES
(1, 1, 100, 1500000),
(1, 2, 50, 1000000),
(2, 3, 150, 4500000),
(2, 4, 200, 5000000),
(3, 5, 70, 3150000),
(3, 6, 60, 3000000),
(4, 7, 120, 1440000),
(4, 8, 80, 4400000),
(5, 9, 140, 70000),
(5, 10, 200, 24000000);
-- phieu xuat
INSERT INTO PhieuXuat (MaPhieuXuat, NgayXuat, MaNhanVien, MaKho, MaDonHang) VALUES
(1, '2024-10-21', 1, 1, 1),
(2, '2024-10-22', 2, 1, 2),
(3, '2024-10-23', 3, 2, 3),
(4, '2024-10-24', 4, 2, 4),
(5, '2024-10-25', 5, 1, 5),
(6, '2024-10-26', 6, 2, 6),
(7, '2024-10-27', 7, 1, 7),
(8, '2024-10-28', 8, 2, 8),
(9, '2024-10-29', 9, 1, 9),
(10, '2024-10-30', 10, 2, 10);
-- chi tiet phieu xuat
INSERT INTO ChiTietPhieuXuat (MaPhieuXuat, MaVatTu, SoLuongXuat, GiaXuat) VALUES
(1, 1, 50, 750000),
(1, 2, 30, 600000),
(2, 3, 70, 2100000),
(2, 4, 100, 2500000),
(3, 5, 40, 1800000),
(3, 6, 20, 1000000),
(4, 7, 60, 720000),
(4, 8, 30, 1650000),
(5, 9, 80, 40000),
(5, 10, 100, 12000000);
-- hoa don
INSERT INTO HoaDon (MaHoaDon, MaDonHang, NgayLap, TongTien, TinhTrangThanhToan) VALUES
(1, 1, '2024-10-31', 8250000, N'Chưa thanh toán'),
(2, 2, '2024-10-31', 6900000, N'Đã thanh toán'),
(3, 3, '2024-10-31', 5650000, N'Chưa thanh toán'),
(4, 4, '2024-10-31', 8000000, N'Đã thanh toán'),
(5, 5, '2024-10-31', 5800000, N'Chưa thanh toán'),
(6, 6, '2024-10-31', 7000000, N'Đã thanh toán'),
(7, 7, '2024-10-31', 6400000, N'Chưa thanh toán'),
(8, 8, '2024-10-31', 7600000, N'Đã thanh toán'),
(9, 9, '2024-10-31', 7100000, N'Chưa thanh toán'),
(10, 10, '2024-10-31', 8300000, N'Đã thanh toán');

