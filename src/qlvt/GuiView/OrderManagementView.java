package qlvt.GuiView;

import qlvt.connect.OrderDAO;
import qlvt.model.ChiTietDonHang;
import qlvt.model.Order;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

public class OrderManagementView extends JDialog {

    private JTextField txtMaDonHang, txtMaKhachHang, txtNgayDat, txtTinhTrangDonHang;
    private JButton btnAdd, btnUpdate, btnDelete, btnClose;
    private JTable orderTable, detailTable;
    private DefaultTableModel orderTableModel, detailTableModel;
    private OrderDAO orderDAO;

    // Detail fields
    private JTextField txtMaChiTiet, txtMaVatTu, txtSoLuong, txtGia;
    private JButton btnAddDetail, btnUpdateDetail, btnDeleteDetail;
    private JButton btnGenerateInvoice;

    public OrderManagementView(Frame parent) {
        super(parent, "Quản lý đơn hàng", true);
        orderDAO = new OrderDAO();

        initUI();
        loadOrders();
        setupTableSelectionListener();
        setSize(800, 600);
        setLocationRelativeTo(parent);
    }

    private void initUI() {
        // Initialize order fields
        txtMaDonHang = new JTextField(15);
        txtMaKhachHang = new JTextField(15);
        txtNgayDat = new JTextField(15); // Use JTextField for date input
        txtTinhTrangDonHang = new JTextField(15);

        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        inputPanel.add(new JLabel("Mã Đơn Hàng:"));
        inputPanel.add(txtMaDonHang);
        inputPanel.add(new JLabel("Mã Khách Hàng:"));
        inputPanel.add(txtMaKhachHang);
        inputPanel.add(new JLabel("Ngày Đặt (YYYY-MM-DD):"));
        inputPanel.add(txtNgayDat);
        inputPanel.add(new JLabel("Tình Trạng Đơn Hàng:"));
        inputPanel.add(txtTinhTrangDonHang);

        // Initialize buttons
        btnAdd = new JButton("Thêm");
        btnUpdate = new JButton("Cập Nhật");
        btnDelete = new JButton("Xóa");
        btnClose = new JButton("Đóng");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnClose);

        // Action listeners for order buttons
        btnAdd.addActionListener(e -> addOrder());
        btnUpdate.addActionListener(e -> updateOrder());
        btnDelete.addActionListener(e -> deleteOrder());
        btnClose.addActionListener(e -> dispose());

        // Order Table
        orderTableModel = new DefaultTableModel(new Object[]{"Mã Đơn Hàng", "Mã Khách Hàng", "Ngày Đặt", "Tình Trạng Đơn Hàng"}, 0);
        orderTable = new JTable(orderTableModel);

        // Detail Table
        detailTableModel = new DefaultTableModel(new Object[]{"Mã Chi Tiết", "Mã Vật Tư", "Số Lượng", "Giá"}, 0);
        detailTable = new JTable(detailTableModel);

        // Initialize detail fields
        txtMaChiTiet = new JTextField(15);
        txtMaVatTu = new JTextField(15);
        txtSoLuong = new JTextField(15);
        txtGia = new JTextField(15);

        JPanel detailInputPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        detailInputPanel.add(new JLabel("Mã đơn hàng:"));
        detailInputPanel.add(txtMaChiTiet);
        detailInputPanel.add(new JLabel("Mã Vật Tư:"));
        detailInputPanel.add(txtMaVatTu);
        detailInputPanel.add(new JLabel("Số Lượng:"));
        detailInputPanel.add(txtSoLuong);
        detailInputPanel.add(new JLabel("Giá:"));
        detailInputPanel.add(txtGia);

        // Detail buttons
        btnAddDetail = new JButton("Thêm Chi Tiết");
        btnUpdateDetail = new JButton("Cập Nhật Chi Tiết");
        btnDeleteDetail = new JButton("Xóa Chi Tiết");

        JPanel detailButtonPanel = new JPanel();
        detailButtonPanel.add(btnAddDetail);
        detailButtonPanel.add(btnUpdateDetail);
        detailButtonPanel.add(btnDeleteDetail);

        // Action listeners for detail buttons
        btnAddDetail.addActionListener(e -> addDetail());
        btnUpdateDetail.addActionListener(e -> updateDetail());
        btnDeleteDetail.addActionListener(e -> deleteDetail());

        // Layout
        JPanel detailPanel = new JPanel(new BorderLayout());
        detailPanel.add(detailInputPanel, BorderLayout.NORTH);
        detailPanel.add(new JScrollPane(detailTable), BorderLayout.CENTER);
        detailPanel.add(detailButtonPanel, BorderLayout.SOUTH);

        add(new JScrollPane(orderTable), BorderLayout.CENTER);
        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);
        add(detailPanel, BorderLayout.EAST); // Details panel on the right

        btnGenerateInvoice = new JButton("Lập Hóa Đơn");
        buttonPanel.add(btnGenerateInvoice);
    }

    private void loadOrders() {
        orderTableModel.setRowCount(0);
        List<Order> orders = orderDAO.getAllOrders();
        for (Order order : orders) {
            orderTableModel.addRow(new Object[]{
                    order.getMaDonHang(),
                    order.getMaKhachHang(),
                    order.getNgayDat(),
                    order.getTinhTrangDonHang()
            });
        }
    }

    private void setupTableSelectionListener() {
        orderTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int selectedRow = orderTable.getSelectedRow();
                if (selectedRow != -1) {
                    // Populate text fields with the selected row's data
                    txtMaDonHang.setText(orderTableModel.getValueAt(selectedRow, 0).toString());
                    txtMaKhachHang.setText(orderTableModel.getValueAt(selectedRow, 1).toString());
                    txtNgayDat.setText(orderTableModel.getValueAt(selectedRow, 2).toString());
                    txtTinhTrangDonHang.setText(orderTableModel.getValueAt(selectedRow, 3).toString());

                    // Load order details
                    loadOrderDetails(Integer.parseInt(txtMaDonHang.getText()));
                }
            }
        });
    }

    private void loadOrderDetails(int maDonHang) {
        detailTableModel.setRowCount(0);
        List<ChiTietDonHang> details = orderDAO.getOrderDetailsById(maDonHang);
        for (ChiTietDonHang detail : details) {
            detailTableModel.addRow(new Object[]{
                    detail.getMaDonHang(), // Assuming this is the order ID
                    detail.getMaVatTu(),
                    detail.getSoLuong(),
                    detail.getGia() // Include the price
            });
        }
    }

    private void addOrder() {
        // Create Order object and add it
        Order order = new Order(
                Integer.parseInt(txtMaDonHang.getText()),
                Integer.parseInt(txtMaKhachHang.getText()),
                Date.valueOf(txtNgayDat.getText()),
                txtTinhTrangDonHang.getText()
        );
        orderDAO.addOrder(order);
        loadOrders();
    }

    private void updateOrder() {
        // Update existing Order
        Order order = new Order(
                Integer.parseInt(txtMaDonHang.getText()),
                Integer.parseInt(txtMaKhachHang.getText()),
                Date.valueOf(txtNgayDat.getText()),
                txtTinhTrangDonHang.getText()
        );
        orderDAO.updateOrder(order);
        loadOrders();
    }

    private void deleteOrder() {
        // Delete order by ID
        int maDonHang = Integer.parseInt(txtMaDonHang.getText());
        orderDAO.deleteOrder(maDonHang);
        loadOrders();
        detailTableModel.setRowCount(0); // Clear details when order is deleted
    }

    private void addDetail() {
        // Create ChiTietDonHang object and add it
        ChiTietDonHang detail = new ChiTietDonHang(
                Integer.parseInt(txtMaDonHang.getText()), // Assuming this is the order ID
                Integer.parseInt(txtMaVatTu.getText()),
                Integer.parseInt(txtSoLuong.getText()),
                new BigDecimal(txtGia.getText()) // Use BigDecimal for price
        );
        orderDAO.addDetail(detail);
        loadOrderDetails(Integer.parseInt(txtMaDonHang.getText()));
    }

    private void updateDetail() {
        // Update existing detail
        ChiTietDonHang detail = new ChiTietDonHang(
                Integer.parseInt(txtMaDonHang.getText()),
                Integer.parseInt(txtMaVatTu.getText()),
                Integer.parseInt(txtSoLuong.getText()),
                new BigDecimal(txtGia.getText()) // Use BigDecimal for price
        );
        orderDAO.updateDetail(detail);
        loadOrderDetails(Integer.parseInt(txtMaDonHang.getText()));
    }

    private void deleteDetail() {
        // Delete detail by order and item ID
        int maDonHang = Integer.parseInt(txtMaDonHang.getText());
        int maVatTu = Integer.parseInt(txtMaVatTu.getText());
        orderDAO.deleteDetail(maDonHang, maVatTu);
        loadOrderDetails(maDonHang);
    }
}
