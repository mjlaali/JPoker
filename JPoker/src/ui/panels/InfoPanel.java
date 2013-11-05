package ui.panels;

import javax.swing.*;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 27/10/13
 * Time: 10:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class InfoPanel extends JPanel {
    public static int panelWidth = 55, panelHeight = 27;
    private String name;
    private double stackSize;

    public InfoPanel(String name, double stackSize) {
        this.name = name;
        this.stackSize = stackSize;
        setSize(100, 50);
    }

    public void paint(Graphics g) {
        drawInfoPanel(g);
    }

    public void update(Graphics g) {
        drawInfoPanel(g);
    }

    private void drawInfoPanel(Graphics g) {
        g.setColor(new Color(57, 51, 25));
        g.fillRect(1, 1, getWidth() - 2, getHeight() - 1);
        g.setColor(new Color(191, 186, 181));
        g.drawRect(1, 1, getWidth() - 2, getHeight() / 2 - 1);
        g.drawRect(1, getHeight() / 2, getWidth() - 2, getHeight() / 2 - 1);
        Font font = new Font("Comic Sans MS", Font.PLAIN, 9);
        g.setFont(font);
        g.drawString(name, 4, getHeight() / 2 - 2);
        g.drawString("$" + stackSize, 5, getHeight() - 4);
    }
}
