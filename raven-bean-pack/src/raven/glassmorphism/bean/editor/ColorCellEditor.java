package raven.glassmorphism.bean.editor;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import raven.glassmorphism.bean.editor.util.GlassIconEditorUtil;

/**
 *
 * @author Raven
 */
public class ColorCellEditor extends DefaultCellEditor {

    private final InputColor input;
    private final EventClick evnetClick;
    private Elements elements;
    private final int space = 2;
    private final int buttonSize = 15;
    private int row;

    public ColorCellEditor(EventClick evnetClick) {
        super(new JCheckBox());
        this.evnetClick = evnetClick;
        input = new InputColor();
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        super.getTableCellEditorComponent(table, value, isSelected, row, column);
        elements = (Elements) table.getValueAt(row, 0);
        this.row = row;
        input.setValue(value);
        input.setBorder(new EmptyBorder(1, 5, 1, buttonSize + space * 2));
        return input;
    }

    @Override
    public Object getCellEditorValue() {
        Object value = getValue();
        if (!elements.getEditColor().equals(value)) {
            elements.setEditColor(value.toString());
            evnetClick.onValueChange(row);
        }
        return value;
    }

    private Object getValue() {
        String v = input.getText().trim();
        String supportColor = GlassIconEditorUtil.supportColor(v);
        if (supportColor != null) {
            return supportColor;
        }
        try {
            String text = GlassIconEditorUtil.fixColor(v);
            Color.decode(text);
            return text;
        } catch (NumberFormatException e) {
            return elements.getEditColor();
        }
    }

    public interface EventClick {

        public void onClick(int row);

        public void onValueChange(int row);
    }

    private class InputColor extends JTextField {

        public InputColor() {
            MouseAdapter mouseEvent = new MouseAdapter() {
                private boolean press;

                private boolean isOver(MouseEvent e) {
                    return (e.getX() >= getWidth() - buttonSize - space && e.getX() <= getWidth() - space);
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    if (isOver(e)) {
                        press = true;
                    }
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    if (press && isOver(e)) {
                        evnetClick.onClick(row);
                    }
                }

                @Override
                public void mouseMoved(MouseEvent e) {
                    if (isOver(e)) {
                        setCursor(new Cursor(Cursor.HAND_CURSOR));
                    } else {
                        setCursor(new Cursor(Cursor.TEXT_CURSOR));
                    }
                }
            };
            addMouseListener(mouseEvent);
            addMouseMotionListener(mouseEvent);
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
            setText(value.toString());
        }

        private Object value;

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int size = getHeight() - space * 2;
            GlassIconEditorUtil.createCustomButton(this, g2, buttonSize, size, space, getBackground());
            g2.dispose();
        }
    }
}
