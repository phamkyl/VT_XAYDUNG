package qlvt.GuiView;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

// Custom renderer for buttons
class ButtonRenderer extends JButton implements TableCellRenderer {
    public ButtonRenderer() {
        setOpaque(true);
    }



    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (value instanceof JButton) {
            JButton button = (JButton) value;
            return button;
        }
        return this;
    }
}

