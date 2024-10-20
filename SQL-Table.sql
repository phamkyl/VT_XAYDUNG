-- Bảng quản lý nhà cung cấp
CREATE TABLE NhaCungCap (
    MaNhaCungCap INT PRIMARY KEY,
    TenNhaCungCap NVARCHAR(255) NOT NULL,
    DiaChi NVARCHAR(500) NOT NULL,
    SoDienThoai NVARCHAR(50) NOT NULL,
    Email NVARCHAR(100) NOT NULL
);

-- Bảng quản lý vật tư
CREATE TABLE VatTu (
    MaVatTu INT PRIMARY KEY,
    TenVatTu NVARCHAR(255) NOT NULL,
    MoTa NVARCHAR(500),
    DonViTinh NVARCHAR(50),
    Gia DECIMAL(18, 2) NOT NULL CHECK (Gia >= 0),
    MaNhaCungCap INT NOT NULL,
    FOREIGN KEY (MaNhaCungCap) REFERENCES NhaCungCap(MaNhaCungCap) ON DELETE CASCADE ON UPDATE CASCADE
);

-- Bảng chi nhánh
CREATE TABLE ChiNhanh (
    MaChiNhanh INT PRIMARY KEY,
    TenChiNhanh NVARCHAR(255) NOT NULL,
    DiaChi NVARCHAR(500) NOT NULL
);

-- Bảng quản lý kho
CREATE TABLE Kho (
    MaKho INT PRIMARY KEY,
    TenKho NVARCHAR(255) NOT NULL,
    DiaChi NVARCHAR(500),
    MaChiNhanh INT NOT NULL,
    FOREIGN KEY (MaChiNhanh) REFERENCES ChiNhanh(MaChiNhanh) ON DELETE CASCADE ON UPDATE CASCADE
);

-- Bảng quản lý nhân viên
CREATE TABLE NhanVien (
    MaNhanVien INT PRIMARY KEY,
    HoTen NVARCHAR(255) NOT NULL,
    ChucVu NVARCHAR(255) NOT NULL,
    MaChiNhanh INT NOT NULL,
    MatKhau NVARCHAR(255) NOT NULL,
    PhanQuyen NVARCHAR(50) NOT NULL,
    FOREIGN KEY (MaChiNhanh) REFERENCES ChiNhanh(MaChiNhanh) ON DELETE CASCADE ON UPDATE CASCADE
);

-- Bảng quản lý khách hàng
CREATE TABLE KhachHang (
    MaKhachHang INT PRIMARY KEY,
    HoTen NVARCHAR(255) NOT NULL,
    DiaChi NVARCHAR(500) NOT NULL,
    SoDienThoai NVARCHAR(50) NOT NULL,
    Email NVARCHAR(100) NOT NULL
);

-- Bảng quản lý phiếu nhập
CREATE TABLE PhieuNhap (
    MaPhieuNhap INT PRIMARY KEY,
    NgayNhap DATE NOT NULL,
    MaNhanVien INT NOT NULL,
    MaKho INT NOT NULL,
    MaNhaCungCap INT NOT NULL,
    FOREIGN KEY (MaNhanVien) REFERENCES NhanVien(MaNhanVien) ON DELETE NO ACTION ON UPDATE NO ACTION,
    FOREIGN KEY (MaKho) REFERENCES Kho(MaKho) ON DELETE NO ACTION ON UPDATE NO ACTION,
    FOREIGN KEY (MaNhaCungCap) REFERENCES NhaCungCap(MaNhaCungCap) ON DELETE NO ACTION ON UPDATE NO ACTION
);

-- Bảng chi tiết phiếu nhập
CREATE TABLE ChiTietPhieuNhap (
    MaPhieuNhap INT NOT NULL,
    MaVatTu INT NOT NULL,
    SoLuongNhap INT NOT NULL CHECK (SoLuongNhap > 0),
    GiaNhap DECIMAL(18, 2) NOT NULL CHECK (GiaNhap >= 0),
    PRIMARY KEY (MaPhieuNhap, MaVatTu),
    FOREIGN KEY (MaPhieuNhap) REFERENCES PhieuNhap(MaPhieuNhap) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (MaVatTu) REFERENCES VatTu(MaVatTu) ON DELETE CASCADE ON UPDATE CASCADE
);

-- Bảng quản lý đơn hàng
CREATE TABLE DonHang (
    MaDonHang INT PRIMARY KEY,
    MaKhachHang INT NOT NULL,
    NgayDat DATE NOT NULL,
    TinhTrangDonHang NVARCHAR(50) NOT NULL,
    FOREIGN KEY (MaKhachHang) REFERENCES KhachHang(MaKhachHang) ON DELETE CASCADE ON UPDATE CASCADE
);

-- Bảng quản lý phiếu xuất
CREATE TABLE PhieuXuat (
    MaPhieuXuat INT PRIMARY KEY,
    NgayXuat DATE NOT NULL,
    MaNhanVien INT NOT NULL,
    MaKho INT NOT NULL,
    MaDonHang INT NOT NULL, -- Thêm mối liên hệ với đơn hàng
    FOREIGN KEY (MaNhanVien) REFERENCES NhanVien(MaNhanVien) ON DELETE NO ACTION ON UPDATE NO ACTION,
    FOREIGN KEY (MaKho) REFERENCES Kho(MaKho) ON DELETE NO ACTION ON UPDATE NO ACTION,
    FOREIGN KEY (MaDonHang) REFERENCES DonHang(MaDonHang) ON DELETE NO ACTION ON UPDATE NO ACTION -- Ràng buộc tới đơn hàng
);

-- Bảng chi tiết phiếu xuất
CREATE TABLE ChiTietPhieuXuat (
    MaPhieuXuat INT NOT NULL,
    MaVatTu INT NOT NULL,
    SoLuongXuat INT NOT NULL CHECK (SoLuongXuat > 0),
    GiaXuat DECIMAL(18, 2) NOT NULL CHECK (GiaXuat >= 0),
    PRIMARY KEY (MaPhieuXuat, MaVatTu),
    FOREIGN KEY (MaPhieuXuat) REFERENCES PhieuXuat(MaPhieuXuat) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (MaVatTu) REFERENCES VatTu(MaVatTu) ON DELETE CASCADE ON UPDATE CASCADE
);



-- Bảng chi tiết đơn hàng
CREATE TABLE ChiTietDonHang (
    MaDonHang INT NOT NULL,
    MaVatTu INT NOT NULL,
    SoLuong INT NOT NULL CHECK (SoLuong > 0),
    Gia DECIMAL(18, 2) NOT NULL CHECK (Gia >= 0),
    PRIMARY KEY (MaDonHang, MaVatTu),
    FOREIGN KEY (MaDonHang) REFERENCES DonHang(MaDonHang) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (MaVatTu) REFERENCES VatTu(MaVatTu) ON DELETE CASCADE ON UPDATE CASCADE
);

-- Bảng quản lý tài chính (hóa đơn)
CREATE TABLE HoaDon (
    MaHoaDon INT PRIMARY KEY,
    MaDonHang INT NOT NULL,
    NgayLap DATE NOT NULL,
    TongTien DECIMAL(18, 2) NOT NULL CHECK (TongTien >= 0),
    TinhTrangThanhToan NVARCHAR(50) NOT NULL,
    FOREIGN KEY (MaDonHang) REFERENCES DonHang(MaDonHang) ON DELETE CASCADE ON UPDATE CASCADE
);
