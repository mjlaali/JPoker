package ui.panels;

import javax.swing.*;
import java.awt.*;

/**
 * User: Admin
 * Date: 04/11/13
 */
public abstract class ActionPanel extends JPanel {
    public static int panelWidth = 55, panelHeight = 30;

    protected boolean highlighted = true;

    public void paint(Graphics g) {
        drawActionPanel(g);
    }

    public void update(Graphics g) {
        drawActionPanel(g);
    }

    public void setHighlighted(boolean highlighted) {
        this.highlighted = highlighted;
    }

    protected abstract void drawActionPanel(Graphics g);

    protected double dropExtraFloatingPoints(double amount) {
        return ((double) ((int) (amount * 10d))) / 10d;
    }
}
