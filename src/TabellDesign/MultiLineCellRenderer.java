/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TabellDesign;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableCellRenderer;

public class MultiLineCellRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JTextArea textArea = new JTextArea(value == null ? "" : value.toString());
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(table.getFont());
        
        if (isSelected) {
            textArea.setBackground(table.getSelectionBackground());
            textArea.setForeground(table.getSelectionForeground());
        } else {
            textArea.setBackground(table.getBackground());
            textArea.setForeground(table.getForeground());
        }
        
        textArea.setBorder(BorderFactory.createEmptyBorder());
        return textArea;
    }
}

