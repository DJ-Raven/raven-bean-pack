package raven.glassmorphism.bean.editor;

import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import raven.glassmorphism.bean.editor.util.GlassIconEditorUtil;

/**
 *
 * @author Rave
 */
public class ColorCellRender extends DefaultTableCellRenderer {

    private final int space = 2;
    private final int buttonSize = 15;
    private Object value;
    private Elements elements;

    public ColorCellRender() {
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        this.value = value;
        this.elements = (Elements) table.getValueAt(row, 0);
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        setBorder(new EmptyBorder(0, table.getRowHeight() + space, 0, buttonSize + space * 2));
        Font font = getFont();
        if (!elements.getColor().equals(elements.getEditColor())) {
            setFont(font.deriveFont(Font.BOLD));
        } else {
            setFont(font.deriveFont(Font.PLAIN));
        }
        return this;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getForeground());
        int size = getHeight() - space * 2;
        g2.fill(new Rectangle2D.Double(space, space, size, size));
        g2.setColor(GlassIconEditorUtil.toColor(this, value.toString()));
        g2.fill(new Rectangle2D.Double(space + 1, space + 1, size - 2, size - 2));
        GlassIconEditorUtil.createCustomButton(this, g2, buttonSize, size, space, getBackground());
        g2.dispose();
    }
}
