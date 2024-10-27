package qlvt.GuiView;

import com.itextpdf.text.*;

import com.itextpdf.text.pdf.*;
import qlvt.connect.OrderDAO;
import qlvt.model.ChiTietDonHang;
import qlvt.model.Order;

import javax.swing.*;
import java.awt.*;
import java.awt.Font;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.List;

public class InvoiceView extends JDialog {
    private OrderDAO orderDAO;
    private JTextArea invoiceTextArea;
    private JButton btnGeneratePDF; // Thêm nút để tạo PDF

    public InvoiceView(OrderManagementView parent, int maDonHang) {
        super(parent, "Hóa Đơn", true);
        orderDAO = new OrderDAO();
        initUI(maDonHang);
        setSize(400, 300);
        setLocationRelativeTo(parent);
    }

    private void initUI(int maDonHang) {
        invoiceTextArea = new JTextArea();
        invoiceTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(invoiceTextArea);
        add(scrollPane, BorderLayout.CENTER);

        // Tải chi tiết hóa đơn
        loadInvoiceDetails(maDonHang);

        btnGeneratePDF = new JButton("Tạo PDF"); // Khởi tạo nút
        btnGeneratePDF.addActionListener(e -> generatePDF(maDonHang));
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnGeneratePDF); // Thêm nút vào bảng điều khiển
        JButton btnClose = new JButton("Đóng");
        btnClose.addActionListener(e -> dispose());
        buttonPanel.add(btnClose);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadInvoiceDetails(int maDonHang) {
        Order order = orderDAO.getOrderById(maDonHang);
        List<ChiTietDonHang> details = orderDAO.getOrderDetailsById(maDonHang);

        StringBuilder invoiceBuilder = new StringBuilder();
        invoiceBuilder.append("Hóa Đơn\n");
        invoiceBuilder.append("Mã Đơn Hàng: ").append(order.getMaDonHang()).append("\n");
        invoiceBuilder.append("Mã Khách Hàng: ").append(order.getMaKhachHang()).append("\n");
        invoiceBuilder.append("Ngày Đặt: ").append(order.getNgayDat()).append("\n");
        invoiceBuilder.append("Tình Trạng: ").append(order.getTinhTrangDonHang()).append("\n\n");
        invoiceBuilder.append("Chi Tiết Đơn Hàng:\n");

        BigDecimal totalAmount = BigDecimal.ZERO;
        for (ChiTietDonHang detail : details) {
            invoiceBuilder.append("Mã Vật Tư: ").append(detail.getMaVatTu()).append("\n");
            invoiceBuilder.append("Số Lượng: ").append(detail.getSoLuong()).append("\n");
            invoiceBuilder.append("Giá: ").append(detail.getGia()).append("\n");
            totalAmount = totalAmount.add(detail.getGia().multiply(BigDecimal.valueOf(detail.getSoLuong())));
            invoiceBuilder.append("-------------------\n");
        }
        invoiceBuilder.append("Tổng Tiền: ").append(totalAmount).append("\n");

        invoiceTextArea.setText(invoiceBuilder.toString());
    }

    private void generatePDF(int maDonHang) {
        Order order = orderDAO.getOrderById(maDonHang);
        List<ChiTietDonHang> details = orderDAO.getOrderDetailsById(maDonHang);

        String filePath = "HoaDon_" + order.getMaDonHang() + ".pdf"; // Tên file PDF
        Document document = new Document();
        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            // Thiết lập font chữ
            BaseFont baseFont = BaseFont.createFont("c:/windows/fonts/arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            com.itextpdf.text.Font font = new com.itextpdf.text.Font(baseFont, 12);
            com.itextpdf.text.Font fontBold = new com.itextpdf.text.Font(baseFont, 12, com.itextpdf.text.Font.BOLD);

            // Thêm tiêu đề
            Paragraph title = new Paragraph("Hóa Đơn", fontBold);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            // Thêm thông tin đơn hàng
            document.add(new Paragraph("Mã Đơn Hàng: " + order.getMaDonHang(), font));
            document.add(new Paragraph("Mã Khách Hàng: " + order.getMaKhachHang(), font));
            document.add(new Paragraph("Ngày Đặt: " + order.getNgayDat(), font));
            document.add(new Paragraph("Tình Trạng: " + order.getTinhTrangDonHang(), font));
            document.add(new Paragraph("\nChi Tiết Đơn Hàng:", font));

            // Tạo bảng
            PdfPTable table = new PdfPTable(3); // 3 cột
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);

            // Thêm tiêu đề bảng
            table.addCell(new PdfPCell(new Paragraph("Mã Vật Tư", fontBold)));
            table.addCell(new PdfPCell(new Paragraph("Số Lượng", fontBold)));
            table.addCell(new PdfPCell(new Paragraph("Giá", fontBold)));

            BigDecimal totalAmount = BigDecimal.ZERO;
            for (ChiTietDonHang detail : details) {
                table.addCell(new PdfPCell(new Paragraph(String.valueOf(detail.getMaVatTu()), font)));
                table.addCell(new PdfPCell(new Paragraph(String.valueOf(detail.getSoLuong()), font)));
                table.addCell(new PdfPCell(new Paragraph(String.valueOf(detail.getGia()), font)));
                totalAmount = totalAmount.add(detail.getGia().multiply(BigDecimal.valueOf(detail.getSoLuong())));
            }

            document.add(table);
            document.add(new Paragraph("Tổng Tiền: " + totalAmount, fontBold));

            document.close();
            writer.close();
            JOptionPane.showMessageDialog(this, "Tạo PDF thành công: " + filePath);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tạo PDF.", "Thông báo", JOptionPane.ERROR_MESSAGE);
        }
    }
}
