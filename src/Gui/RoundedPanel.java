package Gui;

import javax.swing.*;
import java.awt.*;

class RoundedPanel extends JPanel {
    private int radius;

    RoundedPanel(int radius) {
        super();
        this.radius = radius;
        setOpaque(false); // Make the panel transparent
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // Draw the rounded rectangle
        g2d.setColor(getBackground());
        g2d.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, radius, radius);
    }
}

