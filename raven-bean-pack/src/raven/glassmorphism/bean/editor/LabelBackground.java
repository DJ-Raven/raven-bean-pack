package raven.glassmorphism.bean.editor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JLabel;

/**
 *
 * @author Raven
 */
public class LabelBackground extends JLabel {

    public LabelBackground() {
        setHorizontalAlignment(JLabel.CENTER);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        int width = getWidth();
        int height = getHeight();
        int size = 10;
        int col = width / size;
        int row = height / size;
        int x = 0;
        int y = 0;
        for (int i = 0; i <= row; i++) {
            boolean start = i % 2 == 0;
            for (int j = 0; j <= col; j++) {
                if (start) {
                    g2.setColor(getBackground());
                } else {
                    g2.setColor(new Color(190, 190, 190));
                }
                g2.fill(new Rectangle2D.Double(x, y, size, size));
                start = !start;
                x += size;
            }
            y += size;
            x = 0;
        }
        g2.dispose();
        super.paintComponent(g);
    }
}
