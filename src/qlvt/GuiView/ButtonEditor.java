package qlvt.GuiView;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonEditor extends DefaultCellEditor {
    private JButton button;
    private String label;
    private boolean isPushed;
    private JTable table;

    public ButtonEditor(JCheckBox checkBox) {
        super(checkBox);
        button = new JButton();
        button.setOpaque(true);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fireEditingStopped(); // Ngừng chỉnh sửa
                int row = table.getSelectedRow(); // Lấy dòng hiện tại
                if (row != -1) {
                    int maPhieuNhap = (int) table.getValueAt(row, 0); // Giả sử Mã Phiếu Nhập ở cột 0
                    // Mở một cửa sổ hiển thị thông tin chi tiết
                    JOptionPane.showMessageDialog(button, "Chi tiết phiếu nhập: " + maPhieuNhap);
                }
            }
        });
    }

    @Override
    public Object getCellEditorValue() {
        if (isPushed) {
            // Chỉ thực hiện nếu nút được nhấn
        }
        isPushed = false;
        return label;
    }

    @Override
    public boolean stopCellEditing() {
        isPushed = false;
        return super.stopCellEditing();
    }

    @Override
    protected void fireEditingStopped() {
        super.fireEditingStopped();
    }
}
