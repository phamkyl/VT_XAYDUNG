package qlvt.GuiView;

import qlvt.connect.PhieuXuatDAO;
import qlvt.model.PhieuXuat;
import qlvt.model.ChiTietPhieuXuat;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

public class PhieuXuatGUI extends JDialog {
    private JTextField txtMaPhieuXuat, txtMaNhanVien, txtMaKho, txtMaDonHang;
    private JFormattedTextField txtNgayXuat;
    private JButton btnSave, btnDelete, btnUpdate, btnClose;
    private JTable tablePhieuXuat, detailTable;
    private DefaultTableModel tableModel, detailTableModel;
    private PhieuXuatDAO phieuXuatDAO;

    public PhieuXuatGUI(Frame parent) {
        super(parent, "Quản lý Phiếu Xuất", true);
        phieuXuatDAO = new PhieuXuatDAO();

        initUI();
        loadPhieuXuats();
        setupTableSelectionListener();
        setSize(800, 600);
        setLocationRelativeTo(parent);
    }

    private void setupTableSelectionListener() {
        tablePhieuXuat.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int selectedRow = tablePhieuXuat.getSelectedRow();
                if (selectedRow != -1) {
                    // Populate text fields with the selected row's data
                    txtMaPhieuXuat.setText(tableModel.getValueAt(selectedRow, 0).toString());
                    txtNgayXuat.setValue(tableModel.getValueAt(selectedRow, 1));
                    txtMaNhanVien.setText(tableModel.getValueAt(selectedRow, 2).toString());
                    txtMaKho.setText(tableModel.getValueAt(selectedRow, 3).toString());
                    txtMaDonHang.setText(tableModel.getValueAt(selectedRow, 4).toString());

                    loadChiTietPhieuXuat(Integer.parseInt(txtMaPhieuXuat.getText()));
                }
            }
        });
    }

    private void loadChiTietPhieuXuat(int maPhieuXuat) {
        detailTableModel.setRowCount(0);
        List<ChiTietPhieuXuat> details = phieuXuatDAO.getChiTietPhieuXuatByMaPhieuXuat(maPhieuXuat);
        for (ChiTietPhieuXuat detail : details) {
            detailTableModel.addRow(new Object[]{
                    detail.getMaVatTu(),
                    detail.getSoLuong(),
                    detail.getGia()
            });
        }
    }

    private void initUI() {
        txtMaPhieuXuat = new JTextField(15);
        txtMaNhanVien = new JTextField(15);
        txtMaKho = new JTextField(15);
        txtMaDonHang = new JTextField(15);
        txtNgayXuat = new JFormattedTextField(new SimpleDateFormat("yyyy-MM-dd"));
        txtNgayXuat.setValue(new Date(System.currentTimeMillis()));

        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        inputPanel.add(new JLabel("Mã Phiếu Xuất:"));
        inputPanel.add(txtMaPhieuXuat);
        inputPanel.add(new JLabel("Ngày Xuất:"));
        inputPanel.add(txtNgayXuat);
        inputPanel.add(new JLabel("Mã Nhân Viên:"));
        inputPanel.add(txtMaNhanVien);
        inputPanel.add(new JLabel("Mã Kho:"));
        inputPanel.add(txtMaKho);
        inputPanel.add(new JLabel("Mã Đơn Hàng:"));
        inputPanel.add(txtMaDonHang);

        btnSave = new JButton("Thêm");
        btnUpdate = new JButton("Cập Nhật");
        btnDelete = new JButton("Xóa");
        btnClose = new JButton("Đóng");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnSave);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnClose);

        btnSave.addActionListener(e -> addPhieuXuat());
        btnUpdate.addActionListener(e -> updatePhieuXuat());
        btnDelete.addActionListener(e -> deletePhieuXuat());
        btnClose.addActionListener(e -> dispose());

        tableModel = new DefaultTableModel(new Object[]{"Mã Phiếu Xuất", "Ngày Xuất", "Mã Nhân Viên", "Mã Kho", "Mã Đơn Hàng"}, 0);
        tablePhieuXuat = new JTable(tableModel);

        detailTableModel = new DefaultTableModel(new Object[]{"Mã Vật Tư", "Số Lượng", "Giá Xuất"}, 0);
        detailTable = new JTable(detailTableModel);

        // Panel for detail management
        JPanel detailPanel = new JPanel(new BorderLayout());
        detailPanel.add(new JScrollPane(detailTable), BorderLayout.CENTER);

        JButton btnAddDetail = new JButton("Thêm Chi Tiết");
        JButton btnUpdateDetail = new JButton("Cập Nhật Chi Tiết");
        JButton btnDeleteDetail = new JButton("Xóa Chi Tiết");

        JPanel detailButtonPanel = new JPanel();
        detailButtonPanel.add(btnAddDetail);
        detailButtonPanel.add(btnUpdateDetail);
        detailButtonPanel.add(btnDeleteDetail);

        detailPanel.add(detailButtonPanel, BorderLayout.SOUTH);

        btnAddDetail.addActionListener(e -> addDetail());
        btnUpdateDetail.addActionListener(e -> updateDetail());
        btnDeleteDetail.addActionListener(e -> deleteDetail());

        add(new JScrollPane(tablePhieuXuat), BorderLayout.CENTER);
        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);
        add(detailPanel, BorderLayout.EAST);
    }

    private void loadPhieuXuats() {
        tableModel.setRowCount(0);
        List<PhieuXuat> phieuXuats = phieuXuatDAO.getAllPhieuXuat();
        for (PhieuXuat phieuXuat : phieuXuats) {
            tableModel.addRow(new Object[]{
                    phieuXuat.getMaPhieuXuat(),
                    phieuXuat.getNgayXuat(),
                    phieuXuat.getMaNhanVien(),
                    phieuXuat.getMaKho(),
                    phieuXuat.getMaDonHang()
            });
        }
    }

    private void addPhieuXuat() {
        PhieuXuat phieuXuat = new PhieuXuat();
        phieuXuat.setMaPhieuXuat(Integer.parseInt(txtMaPhieuXuat.getText()));
        phieuXuat.setNgayXuat((Date) txtNgayXuat.getValue());
        phieuXuat.setMaNhanVien(Integer.parseInt(txtMaNhanVien.getText()));
        phieuXuat.setMaKho(Integer.parseInt(txtMaKho.getText()));
        phieuXuat.setMaDonHang(Integer.parseInt(txtMaDonHang.getText()));
        phieuXuatDAO.addPhieuXuat(phieuXuat);
        loadPhieuXuats(); // Refresh table
    }

    private void updatePhieuXuat() {
        PhieuXuat phieuXuat = new PhieuXuat();
        phieuXuat.setMaPhieuXuat(Integer.parseInt(txtMaPhieuXuat.getText()));
        phieuXuat.setNgayXuat((Date) txtNgayXuat.getValue());
        phieuXuat.setMaNhanVien(Integer.parseInt(txtMaNhanVien.getText()));
        phieuXuat.setMaKho(Integer.parseInt(txtMaKho.getText()));
        phieuXuat.setMaDonHang(Integer.parseInt(txtMaDonHang.getText()));
        phieuXuatDAO.updatePhieuXuat(phieuXuat);
        loadPhieuXuats(); // Refresh table
    }

    private void deletePhieuXuat() {
        int maPhieuXuat = Integer.parseInt(txtMaPhieuXuat.getText());
        phieuXuatDAO.deletePhieuXuat(maPhieuXuat);
        loadPhieuXuats(); // Refresh table
    }

    private void addDetail() {
        int maPhieuXuat = Integer.parseInt(txtMaPhieuXuat.getText());
        String maVatTu = JOptionPane.showInputDialog("Nhập Mã Vật Tư:");
        int soLuong = Integer.parseInt(JOptionPane.showInputDialog("Nhập Số Lượng:"));
        double gia = Double.parseDouble(JOptionPane.showInputDialog("Nhập Giá:"));

        ChiTietPhieuXuat chiTietPhieuXuat = new ChiTietPhieuXuat(maPhieuXuat, Integer.parseInt(maVatTu), soLuong, new BigDecimal(gia));
        phieuXuatDAO.addChiTietPhieuXuat(chiTietPhieuXuat);
        loadChiTietPhieuXuat(maPhieuXuat); // Refresh detail table
    }

    private void updateDetail() {
        int selectedRow = detailTable.getSelectedRow();
        if (selectedRow != -1) {
            int maPhieuXuat = Integer.parseInt(txtMaPhieuXuat.getText());
            int maVatTu = Integer.parseInt(detailTableModel.getValueAt(selectedRow, 0).toString());
            int soLuong = Integer.parseInt(JOptionPane.showInputDialog("Nhập Số Lượng Mới:", detailTableModel.getValueAt(selectedRow, 1).toString()));
            double gia = Double.parseDouble(JOptionPane.showInputDialog("Nhập Giá Mới:", detailTableModel.getValueAt(selectedRow, 2).toString()));

            ChiTietPhieuXuat chiTietPhieuXuat = new ChiTietPhieuXuat(maPhieuXuat, maVatTu, soLuong, new BigDecimal(gia));
            phieuXuatDAO.updateChiTietPhieuXuat(chiTietPhieuXuat);
            loadChiTietPhieuXuat(maPhieuXuat); // Refresh detail table
        }
    }

    private void deleteDetail() {
        int selectedRow = detailTable.getSelectedRow();
        if (selectedRow != -1) {
            int maPhieuXuat = Integer.parseInt(txtMaPhieuXuat.getText());
            int maVatTu = Integer.parseInt(detailTableModel.getValueAt(selectedRow, 0).toString());
            phieuXuatDAO.deleteChiTietPhieuXuat(maPhieuXuat, maVatTu);
            loadChiTietPhieuXuat(maPhieuXuat); // Refresh detail table
        }
    }
}
