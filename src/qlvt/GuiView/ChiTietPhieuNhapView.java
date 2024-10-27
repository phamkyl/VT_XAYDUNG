package qlvt.GuiView;

import qlvt.connect.PurchaseOrderDAODetail;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

public class ChiTietPhieuNhapView extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton editButton, addButton;
    private int selectedOrderId;
    private PurchaseOrderDAODetail purchaseOrderDAODetail; // DAO instance

    public ChiTietPhieuNhapView(int orderId) {
        this.selectedOrderId = orderId;
        this.purchaseOrderDAODetail = new PurchaseOrderDAODetail(); // Initialize DAO

        setTitle("Detail of Purchase Order");
        setSize(600, 400);
        setLayout(new BorderLayout());

        tableModel = new DefaultTableModel(new Object[]{"Material ID", "Quantity Received", "Unit Price"}, 0);
        table = new JTable(tableModel);
        loadPurchaseOrderDetails();

        // Action buttons
        addButton = new JButton("Add Detail");
        editButton = new JButton("Edit Detail");

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openEditDialog("add", 0, 0, 0); // Open dialog to add new detail
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();
                if (row != -1) {
                    int materialId = (int) table.getValueAt(row, 0);
                    int quantityReceived = (int) table.getValueAt(row, 1);
                    double unitPrice = (double) table.getValueAt(row, 2);
                    openEditDialog("edit", materialId, quantityReceived, unitPrice);
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a row to edit.");
                }
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);

        add(new JScrollPane(table), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void loadPurchaseOrderDetails() {
        // Load details from the DAO
        tableModel.setRowCount(0);
        for (Object[] detail : purchaseOrderDAODetail.getPurchaseOrderDetails(selectedOrderId)) {
            tableModel.addRow(detail);
        }
    }

    private void openEditDialog(String action, int materialId, int quantityReceived, double unitPrice) {
        JDialog dialog = new JDialog(this, "Update Purchase Order Detail", true);
        dialog.setSize(300, 200);
        dialog.setLayout(new GridLayout(4, 2));

        JTextField materialIdField = new JTextField(String.valueOf(materialId));
        JTextField quantityField = new JTextField(String.valueOf(quantityReceived));
        JTextField unitPriceField = new JTextField(String.valueOf(unitPrice));

        dialog.add(new JLabel("Material ID:"));
        dialog.add(materialIdField);
        dialog.add(new JLabel("Quantity Received:"));
        dialog.add(quantityField);
        dialog.add(new JLabel("Unit Price:"));
        dialog.add(unitPriceField);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int matId = Integer.parseInt(materialIdField.getText());
                int qty = Integer.parseInt(quantityField.getText());
                double price = Double.parseDouble(unitPriceField.getText());

                if (action.equals("add")) {
                    purchaseOrderDAODetail.addPurchaseOrderDetail(selectedOrderId, matId, qty, BigDecimal.valueOf(price));
                } else if (action.equals("edit")) {
                    purchaseOrderDAODetail.updatePurchaseOrderDetail(selectedOrderId, matId, qty, BigDecimal.valueOf(price));
                }
                loadPurchaseOrderDetails();
                dialog.dispose();
            }
        });
        dialog.add(saveButton);
        dialog.setVisible(true);
    }
}
